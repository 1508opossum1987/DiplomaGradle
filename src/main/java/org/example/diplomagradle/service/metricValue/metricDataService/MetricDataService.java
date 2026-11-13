package org.example.diplomagradle.service.metricValue.metricDataService;

import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.datamodel.entity.MetricValue;
import org.example.diplomagradle.datamodel.entity.MetricsType;
import org.example.diplomagradle.datamodel.repository.MetricRepository;
import org.example.diplomagradle.datamodel.repository.MetricValueRepository;
import org.example.diplomagradle.service.metric.MetricRestrictionValidatorInterface;
import org.example.diplomagradle.service.wagon.InvalidWagonPeriodException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MetricDataService implements MetricTestDataServiceInterface {
    private final MetricValueRepository metricValueRepository;

    private final MetricRepository metricRepository;

    private final MetricRestrictionValidatorInterface metricRestrictionValidatorInterface;

    private final Random random = new Random();

    public MetricDataService(MetricValueRepository metricValueRepository,
                             MetricRepository metricRepository,
                             MetricRestrictionValidatorInterface metricRestrictionValidatorInterface) {
        this.metricValueRepository = metricValueRepository;
        this.metricRepository = metricRepository;
        this.metricRestrictionValidatorInterface = metricRestrictionValidatorInterface;
    }

    //помесячная генерация
    //основная ценность - валидация периода в том числе сравнение с текущей датой
    @Override
    public void generateForMonth(Integer year, Integer month) {
        if (year == null || year < 2024) {
            throw new InvalidWagonPeriodException("This year: " + year + " isn't correct");
        }
        if (month == null || month < 1 || month > 12) {
            throw new InvalidWagonPeriodException("This month: " + month + " isn't correct");
        }

        YearMonth target = YearMonth.of(year, month);
        YearMonth now = YearMonth.now();

        if (target.isAfter(now)) {
            throw new InvalidWagonPeriodException("future month is not allowed: " + target);
        }
        if (target.equals(now) && LocalDate.now().getDayOfMonth() == 1) {
            throw new InvalidWagonPeriodException("current month cannot be generated on the 1st day: " + target);
        }
        if (target.isBefore(now.minusMonths(1))) {
            throw new InvalidWagonPeriodException("only current or previous month allowed: " + target);
        }

        generateMonth(year, month);
    }

    //служебный месяц для генерации значений показателя за месяц, без валидации периода
    private void generateMonth(Integer year, Integer month) {
        List<Metric> metrics = metricRepository.findByType(MetricsType.MANUAL);

        for (Metric metric : metrics) {
            BigDecimal plan = randomPlan(metric);
            BigDecimal fact = randomPlan(metric);
            metricRestrictionValidatorInterface.validate(metric, plan, fact);
            //поиск значения показателя уикального по Id, месяцу и году
            Optional<MetricValue> existing = metricValueRepository
                    .findByMetricIdAndMonthAndYear(metric.getId(), month, year);

            //если запись есть - перезапись, за исключением плана
            if (existing.isPresent()) {
                MetricValue metricValue = existing.get();
                metricValue.setFact(fact);
                metricValue.setUpdatedBy("system");//заглушка для будущего
                metricValue.setUpdatedAt(LocalDateTime.now());
                metricValueRepository.save(metricValue);
                continue; //переход к следующему показателю
            }

            //если записи нет - создаем
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
    }

    //генерация ручных показателей за период с 2024-01 по 2026-09
    @Override
    public void generateAllManual() {
        YearMonth start = YearMonth.of(2024, 1);
        YearMonth end = YearMonth.of(2026, 9);

        YearMonth current = start;
        while (!current.isAfter(end)) {
            generateMonth(current.getYear(), current.getMonthValue());
            current = current.plusMonths(1);
        }
    }

    //служебный метод по аналогии с расчетными показателями
    //генерация рандомных значений плана в заданных границах
    private BigDecimal randomPlan(Metric metric) {
        BigDecimal min = metric.getMinValue() != null ? metric.getMinValue() : BigDecimal.ZERO;
        BigDecimal max = metric.getMaxValue() != null ? metric.getMaxValue() : min.add(BigDecimal.valueOf(100));

        double minD = min.doubleValue();
        double maxD = max.doubleValue();
        double value = minD + random.nextDouble() * (maxD - minD);

        //return BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP);//приведение к нужнйо форме записи в БД

        //если нужно целочисленное значение - после запятой 0
        //иначе 1 знак
        BigDecimal result = BigDecimal.valueOf(value);
        return isIntegerUnit(metric.getUnit())
                ? result.setScale(0, RoundingMode.HALF_UP)
                : result.setScale(1, RoundingMode.HALF_UP);
    }

    //служебный класс, выбирающий по единице измеренеия показатели, для которых
    //значение должно быть целым
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
