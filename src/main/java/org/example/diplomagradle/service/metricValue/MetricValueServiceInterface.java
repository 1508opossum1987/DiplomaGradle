package org.example.diplomagradle.service.metricValue;

import org.example.diplomagradle.service.metric.MetricValues;

//интерфейс для обработки задач с сущностью MetricValue
public interface MetricValueServiceInterface {
    //добавить показатель
    public void addMetric(MetricValues values);
}
