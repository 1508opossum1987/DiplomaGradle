package org.example.diplomagradle.service.metricValue;

import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.datamodel.entity.MetricValue;
import org.example.diplomagradle.datamodel.repository.MetricValueRepository;
import org.example.diplomagradle.service.metric.InvalidMetricNameException;
import org.example.diplomagradle.service.metric.MetricServiceInterface;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
public class MetricValueService implements MetricValueServiceInterface{
    private final MetricValueRepository metricValueRepository;

    private final MetricServiceInterface metricServiceInterface;

    public MetricValueService (MetricValueRepository metricValueRepository,
                               MetricServiceInterface metricServiceInterface) {
        this.metricValueRepository = metricValueRepository;
        this.metricServiceInterface = metricServiceInterface;
    }

    //добавить запись всех значений показателя по id Metric
    @Override
    public void addMetricValue(MetricValueValues values) {
        if (values == null){
            throw new InvalidMetricValueValuesException(null, "values is empty");
        }

        Metric metric = metricServiceInterface.getMetricById(values.metricId());
        validateRestrictions(metric, values.plan(), values.fact());

        if (metricValueRepository.existsByMetricIdAndMonthAndYear(values.metricId(), values.month(), values.year())){
            throw new DuplicateMetricValueException(values.metricId(), values.month(), values.year());
        }

        MetricValue metricValue = new MetricValue();
        metricValue.setMetric(metric);
        metricValue.setMonth(values.month());
        metricValue.setYear(values.year());
        metricValue.setPlan(values.plan());
        metricValue.setFact(values.fact());
        metricValue.setUpdatedAt(LocalDateTime.now());
        metricValue.setUpdatedBy(values.updatedBy());

        metricValueRepository.save(metricValue);
    }

    //получить все записи значений показателей
    @Override
    public List<MetricValue> getAllMetricValues() {
        return metricValueRepository.findAll();
    }

    //получить таблицу всех значений показателей по id записи MetricValue
    @Override
    public List<MetricValue> getAllMetricValuesByIdMetricValue(Long metricValueId) {
        List<MetricValue> metricValues = metricValueRepository.findByMetricId(metricValueId);
        if (metricValues.isEmpty()){
            throw new InvalidMetricValueIdException(metricValueId);
        }
        return metricValues;
    }

    //получить список значений показателей по id показателя
    @Override
    public List<MetricValue> getAllMetricValuesByMetricId(Long idMetric) {
        List<MetricValue> metricValues = metricValueRepository.findByMetricId(idMetric);
        if(metricValues.isEmpty()){
            throw new EmptyMetricValuesException(idMetric);
        } else
            return metricValues;
    }

    //получить список значений показателей по названию показателя
    @Override
    public List<MetricValue> getAllMetricValuesByMetricName(String metricName) {
        Metric metric = metricServiceInterface.getMetricByName(metricName);
        List<MetricValue> metricValues = metricValueRepository.findByMetricId(metric.getId());
        if(metricValues.isEmpty()){
            throw new EmptyMetricValuesException(metricName);
        } else
            return metricValues;
    }

    //удалить все значения показателя по id самого показателя Metric
    @Override
    public void deleteAllMetricValuesByMetricId(Long idMetric) {
        List<MetricValue> metricValues = metricValueRepository.findByMetricId(idMetric);
        if(metricValues.isEmpty()) {
            throw new EmptyMetricValuesException(idMetric);
        } else
            metricValueRepository.deleteByMetricId(idMetric);
    }

    //удалить все значения показателя по name самого показателя Metric
    @Override
    public void deleteAllMetricValuesByMetricName(String metricName) {
        Metric metric = metricServiceInterface.getMetricByName(metricName);
        List<MetricValue> metricValues = metricValueRepository.findByMetricId(metric.getId());
        if(metricValues.isEmpty()){
            throw new EmptyMetricValuesException(metricName);
        } else
            metricValueRepository.deleteByMetricId(metric.getId());
    }

    //обновление всех значений показателя с поиском по названию показателя, году и месяцу
    @Override
    public void putAllMetricValuesByMetricNameMonthYear(String metricName, Integer year, Integer month,
                                                        BigDecimal newPlan, BigDecimal newFact, String updatedBy) {
        if (metricName == null || metricName.trim().isEmpty()) {
            throw new InvalidMetricNameException(metricName);
        }
        if (month == null || month < 1 || month > 12) {
            throw new InvalidMetricValueValuesException("month is incorrect");
        }
        if (year == null || year < 2024) {
            throw new InvalidMetricValueValuesException("year is incorrect");
        }
        if (newPlan == null || newFact == null) {
            throw new InvalidMetricValueValuesException("plan or fact is null");
        }
        if (updatedBy == null || updatedBy.trim().isEmpty()) {
            throw new InvalidMetricValueValuesException("updatedBy is null or empty");
        }

        Metric metric = metricServiceInterface.getMetricByName(metricName);

        validateRestrictions(metric, newPlan, newFact);

        MetricValue metricValue = metricValueRepository
                .findByMetricIdAndMonthAndYear(metric.getId(), month, year)
                .orElseGet(MetricValue::new);

        metricValue.setMetric(metric);
        metricValue.setMonth(month);
        metricValue.setYear(year);
        metricValue.setPlan(newPlan);
        metricValue.setFact(newFact);
        metricValue.setUpdatedBy(updatedBy);
        metricValue.setUpdatedAt(LocalDateTime.now());

        metricValueRepository.save(metricValue);
    }

    //обновление всех значений показателя с поиском по id записи, году и месяцу
    @Override
    public void putAllMetricValuesByMetricValueIdMonthYear(Long metricValueId, Integer year, Integer month,
                                                           BigDecimal newPlan, BigDecimal newFact, String updatedBy) {
        if (metricValueId == null || metricValueId <= 0) {
            throw new InvalidIdMetricValueException(metricValueId);
        }
        if (month == null || month < 1 || month > 12) {
            throw new InvalidMetricValueValuesException("month is incorrect");
        }
        if (year == null || year < 2024) {
            throw new InvalidMetricValueValuesException("year is incorrect");
        }
        if (newPlan == null || newFact == null) {
            throw new InvalidMetricValueValuesException("plan or fact is null");
        }
        if (updatedBy == null || updatedBy.trim().isEmpty()) {
            throw new InvalidMetricValueValuesException("updatedBy is null or empty");
        }

        MetricValue metricValue = metricValueRepository.findById(metricValueId)
                .orElseThrow(() -> new InvalidIdMetricValueException(metricValueId));

        validateRestrictions(metricValue.getMetric(), newPlan, newFact);

        metricValueRepository
                .findByMetricIdAndMonthAndYear(metricValue.getMetric().getId(), month, year)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(metricValueId)) {
                        throw new DuplicateMetricValueException(
                                metricValue.getMetric().getId(), month, year);
                    }
                });

        metricValue.setMonth(month);
        metricValue.setYear(year);
        metricValue.setPlan(newPlan);
        metricValue.setFact(newFact);
        metricValue.setUpdatedBy(updatedBy);
        metricValue.setUpdatedAt(LocalDateTime.now());

        metricValueRepository.save(metricValue);
    }

    //обновление планового значения показателя с поиском по названию показателя, году и месяцу
    @Override
    public void patchPlanByMetricNameMonthYear(String metricName, Integer year, Integer month, BigDecimal newPlan) {
        if (metricName == null || metricName.trim().isEmpty()) {
            throw new InvalidMetricNameException(metricName);
        }
        if (month == null || month < 1 || month > 12) {
            throw new InvalidMetricValueValuesException("month is incorrect");
        }
        if (year == null || year < 2024) {
            throw new InvalidMetricValueValuesException("year is incorrect");
        }
        if (newPlan == null) {
            throw new InvalidMetricValueValuesException("plan or fact is null");
        }

        Metric metric = metricServiceInterface.getMetricByName(metricName);
        MetricValue metricValue = metricValueRepository
                .findByMetricIdAndMonthAndYear(metric.getId(), month, year)
                .orElseThrow(() -> new EmptyMetricValuesException(metricName, month, year));

        validateRestrictions(metric, newPlan, null);

        metricValue.setPlan(newPlan);
        metricValue.setUpdatedAt(LocalDateTime.now());
        metricValueRepository.save(metricValue);
    }

    //обновление фактического значения показателя с поиском по названию показателя, году и месяцу
    @Override
    public void patchFactByMetricNameMonthYear(String metricName, Integer year, Integer month, BigDecimal newFact) {
        if (metricName == null || metricName.trim().isEmpty()) {
            throw new InvalidMetricNameException(metricName);
        }
        if (month == null || month < 1 || month > 12) {
            throw new InvalidMetricValueValuesException("month is incorrect");
        }
        if (year == null || year < 2024) {
            throw new InvalidMetricValueValuesException("year is incorrect");
        }
        if (newFact == null) {
            throw new InvalidMetricValueValuesException("plan or fact is null");
        }

        Metric metric = metricServiceInterface.getMetricByName(metricName);
        MetricValue metricValue = metricValueRepository
                .findByMetricIdAndMonthAndYear(metric.getId(), month, year)
                .orElseThrow(() -> new EmptyMetricValuesException(metricName, month, year));

        validateRestrictions(metric, null, newFact);

        metricValue.setFact(newFact);
        metricValue.setUpdatedAt(LocalDateTime.now());
        metricValueRepository.save(metricValue);
    }

    //обновление планового и фактического значения показателя с поиском по id записи, году и месяцу
    @Override
    public void patchPlanFactByMetricValueIdMonthYear(Long metricValueId, Integer year, Integer month, BigDecimal newPlan, BigDecimal newFact) {
        if (metricValueId == null || metricValueId <= 0) {
            throw new InvalidIdMetricValueException(metricValueId);
        }
        if (month == null || month < 1 || month > 12) {
            throw new InvalidMetricValueValuesException("month is incorrect");
        }
        if (year == null || year < 2024) {
            throw new InvalidMetricValueValuesException("year is incorrect");
        }
        if (newPlan == null) {
            throw new InvalidMetricValueValuesException("plan is null");
        }
        if (newFact == null) {
            throw new InvalidMetricValueValuesException("fact is null");
        }

        MetricValue metricValue = metricValueRepository.findById(metricValueId)
                .orElseThrow(() -> new InvalidIdMetricValueException(metricValueId));

        if (!metricValue.getMonth().equals(month) || !metricValue.getYear().equals(year)) {
            throw new InvalidMetricValueValuesException(
                    "period mismatch: expected " + metricValue.getMonth() + "/" + metricValue.getYear()
                            + ", got " + month + "/" + year);
        }

        validateRestrictions(metricValue.getMetric(), newPlan, newFact);

        metricValue.setPlan(newPlan);
        metricValue.setFact(newFact);
        metricValue.setUpdatedAt(LocalDateTime.now());

        metricValueRepository.save(metricValue);
    }

    //метод для проверки вхождения плановых и фактических значений показателя в допустимые
    //границы minValue, maxValue, установленные для показателя
    private void validateRestrictions(Metric metric, BigDecimal plan, BigDecimal fact) {
        if (metric.getMinValue() != null) {
            if (plan != null && plan.compareTo(metric.getMinValue()) < 0) {
                throw new InvalidMetricValueValuesException(
                        "plan below min value for metric '" + metric.getMetricName() + "': " + metric.getMinValue());
            }
            if (fact != null && fact.compareTo(metric.getMinValue()) < 0) {
                throw new InvalidMetricValueValuesException(
                        "fact below min value for metric '" + metric.getMetricName() + "': " + metric.getMinValue());
            }
        }
        if (metric.getMaxValue() != null) {
            if (plan != null && plan.compareTo(metric.getMaxValue()) > 0) {
                throw new InvalidMetricValueValuesException(
                        "plan above max value for metric '" + metric.getMetricName() + "': " + metric.getMaxValue());
            }
            if (fact != null && fact.compareTo(metric.getMaxValue()) > 0) {
                throw new InvalidMetricValueValuesException(
                        "fact above max value for metric '" + metric.getMetricName() + "': " + metric.getMaxValue());
            }
        }
    }

}
