package org.example.diplomagradle.service.metricValue;

import org.example.diplomagradle.datamodel.entity.MetricValue;
import org.example.diplomagradle.service.metric.MetricValues;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.util.List;

//интерфейс для обработки задач с сущностью MetricValue
public interface MetricValueServiceInterface {
    //добавить запись всех значений показателя по id Metric
    @PreAuthorize("@accessChecker.canEditByMetricId(authentication, #values.metricId())")
    public void addMetricValue(MetricValueValues values);

    //добавить запись всех значений показателя по id Metric
    //public void addMetricValueByIdMetric(Long idMetric, MetricValueValues values);

    //получить таблицу всех значений показателей
    public List<MetricValue> getAllMetricValues();

    //получить таблицу всех значений показателей по id записи MetricValue
    public List<MetricValue> getAllMetricValuesByIdMetricValue(Long metricValueId);

    //получить таблицу всех значений показателей по id самого показателя Metric
    public List<MetricValue> getAllMetricValuesByMetricId(Long idMetric);

    //получить таблицу всех значений показателей по названию показателя Metric
    public List<MetricValue> getAllMetricValuesByMetricName(String metricName);

    //удалить все значения показателя по id самого показателя Metric
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllMetricValuesByMetricId(Long idMetric);

    //удалить все значения показателя по названию самого показателя Metric
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllMetricValuesByMetricName(String metricName);

    //обновление всех значений показателя с поиском по названию показателя, году и месяцу
    public void putAllMetricValuesByMetricNameMonthYear(String metricName, Integer year, Integer month, BigDecimal newPlan, BigDecimal newFact);

    //обновление всех значений показателя с поиском по id записи, году и месяцу
    @PreAuthorize("@accessChecker.canEditByMetricValueId(authentication, #metricValueId)")
    public void putAllMetricValuesByMetricValueIdMonthYear(Long metricValueId, Integer year, Integer month, BigDecimal newPlan, BigDecimal newFact);

    //обновление планового значения показателя с поиском по названию показателя, году и месяцу
    public void patchPlanByMetricNameMonthYear(String metricName, Integer year, Integer month, BigDecimal newPlan);

    //обновление фактического значения показателя с поиском по названию показателя, году и месяцу
    public void patchFactByMetricNameMonthYear(String metricName, Integer year, Integer month, BigDecimal newFact);

    //обновление планового и фактического значения показателя с поиском по id записи, году и месяцу
    @PreAuthorize("@accessChecker.canEditByMetricValueId(authentication, #metricValueId)")
    public void patchPlanFactByMetricValueIdMonthYear(Long metricValueId, Integer year, Integer month, BigDecimal newPlan, BigDecimal newFact);

    //удалить ОДНО значение метрики по id
    public void deleteMetricValueById(Long valueId);
}
