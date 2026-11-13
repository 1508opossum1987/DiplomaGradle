package org.example.diplomagradle.service.wagon.wagonMetric;

import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.datamodel.entity.MetricValue;
import org.example.diplomagradle.datamodel.entity.wagon.LoadingType;
import org.example.diplomagradle.datamodel.entity.wagon.Wagon;
import org.example.diplomagradle.datamodel.entity.wagon.WagonCategory;
import org.example.diplomagradle.datamodel.repository.MetricValueRepository;
import org.example.diplomagradle.datamodel.repository.WagonRepository;
import org.example.diplomagradle.service.metric.MetricRestrictionValidatorInterface;
import org.example.diplomagradle.service.metric.MetricServiceInterface;
import org.example.diplomagradle.service.wagon.InvalidWagonPeriodException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class WagonMetricService implements WagonMetricServiceInterface{
    private final WagonRepository wagonRepository;
    private final MetricServiceInterface metricServiceInterface;
    private final MetricValueRepository metricValueRepository;
    private final MetricRestrictionValidatorInterface metricRestrictionValidatorInterface;

    private final Random random = new Random();

    public WagonMetricService(WagonRepository wagonRepository,
                               MetricServiceInterface metricServiceInterface,
                               MetricValueRepository metricValueRepository,
                              MetricRestrictionValidatorInterface metricRestrictionValidatorInterface) {
        this.wagonRepository = wagonRepository;
        this.metricServiceInterface = metricServiceInterface;
        this.metricValueRepository = metricValueRepository;
        this.metricRestrictionValidatorInterface = metricRestrictionValidatorInterface;
    }

    //сбор сгенерированных значений и сохранение
    @Override
    public void calculateAndSaveForMonth(Integer year, Integer month) {
        if (year == null || year < 2024) {
            throw new InvalidWagonPeriodException("This year: " + year + " isn't correct");
        }
        if (month == null || month < 1 || month > 12) {
            throw new InvalidWagonPeriodException("This month: " + month + " isn't correct");
        }

        //настройка периода
        YearMonth ym = YearMonth.of(year, month);
        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.plusMonths(1).atDay(1).atStartOfDay();

        //выгрузка списка отправленных в промежутке вагонов
        List<Wagon> wagons = wagonRepository.findByDepartureAtBetween(start, end);

        //расчет показателей через методы
        BigDecimal idleTime = calculateLocalWagonsIdleTime(wagons);
        long loadingCount = calculateLoadingCount(wagons);
        long unloadingCount = calculateUnloadingCount(wagons);

        //обновление значений показателей
        upsertMetricValue("Простой местных вагонов", year, month, idleTime);
        upsertMetricValue("Погрузка", year, month,
                BigDecimal.valueOf(loadingCount).setScale(0, RoundingMode.HALF_UP));
        upsertMetricValue("Выгрузка", year, month,
                BigDecimal.valueOf(unloadingCount).setScale(0, RoundingMode.HALF_UP));
    }

    //вычисление значения простоя местных вагонов
    private BigDecimal calculateLocalWagonsIdleTime(List<Wagon> wagons) {
        long totalMinutes = 0;
        int count = 0;
        for (Wagon wagon : wagons) {
            if (wagon.getCategory() == WagonCategory.LOCAL) {//если местный берем в расчет
                totalMinutes += Duration.between(wagon.getArrivalAt(), wagon.getDepartureAt()).toMinutes();//вычисление минут между отпарвлением и прибытием
                count++;
            }
        }
        if (count == 0) {
            return BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);//метод константы для приведения к одному знаку после ЗП, во избежание ошибок PostgreSQL
        }
        double avgHours = (totalMinutes / 60.0) / count;//расчет простоя
        return BigDecimal.valueOf(avgHours).setScale(1, RoundingMode.HALF_UP);//приведение к 1 знаку
    }

    //определение количества погрузок
    private long calculateLoadingCount(List<Wagon> wagons) {
        return wagons.stream()
                .filter(w -> w.getCategory() == WagonCategory.LOCAL)
                .filter(w -> w.getLoading() == LoadingType.LOADING || w.getLoading() == LoadingType.BOTH)
                .count();
    }

    //определение количества выгрузок
    private long calculateUnloadingCount(List<Wagon> wagons) {
        return wagons.stream()
                .filter(w -> w.getCategory() == WagonCategory.LOCAL)
                .filter(w -> w.getLoading() == LoadingType.UNLOADING || w.getLoading() == LoadingType.BOTH)
                .count();
    }

    //upsert записи t_metric_value
    //здесь принято решение plan задавать рандомно в параметрах ограничениях, а fact вычислять
    //причем план задаем разово при создании
    private void upsertMetricValue(String metricName, Integer year, Integer month, BigDecimal fact) {
        Metric metric = metricServiceInterface.getMetricByName(metricName);

        Optional<MetricValue> existing = metricValueRepository
                .findByMetricIdAndMonthAndYear(metric.getId(), month, year);

        if (existing.isPresent()) {// если запись есть - ее обновляем
            MetricValue metricValue = existing.get();
            metricValue.setFact(fact);
            metricValue.setUpdatedBy("system");
            metricValue.setUpdatedAt(LocalDateTime.now());
            metricValueRepository.save(metricValue);
            return;//и выходим из метода без генерации значения плана
        }

        //если записи нет, генерируем значение плана
        BigDecimal plan = randomPlan(metric);
        metricRestrictionValidatorInterface.validate(metric, plan, null);

        MetricValue metricValue = new MetricValue();
        metricValue.setMetric(metric);
        metricValue.setMonth(month);
        metricValue.setYear(year);
        metricValue.setPlan(plan);
        metricValue.setFact(fact);
        metricValue.setUpdatedBy("system");
        metricValue.setUpdatedAt(LocalDateTime.now());

        metricValueRepository.save(metricValue);
    }

    //генерация значения плана
    private BigDecimal randomPlan(Metric metric) {
        BigDecimal min = metric.getMinValue() != null ? metric.getMinValue() : BigDecimal.ZERO;
        BigDecimal max = metric.getMaxValue() != null ? metric.getMaxValue() : min.add(BigDecimal.valueOf(100));

        double minD = min.doubleValue();
        double maxD = max.doubleValue();
        double value = minD + random.nextDouble() * (maxD - minD);

        BigDecimal result = BigDecimal.valueOf(value);
        return isIntegerUnit(metric.getUnit())
                ? result.setScale(0, RoundingMode.HALF_UP)
                : result.setScale(1, RoundingMode.HALF_UP);
    }

    //служебный метод для проверки необходимости целочисленности значения показателя
    private boolean isIntegerUnit(String unit) {
        if (unit == null) {
            return false;
        }
        return switch (unit) {
            case "случай", "штук", "вагон", "человек" -> true;
            default -> false;
        };
    }
}
