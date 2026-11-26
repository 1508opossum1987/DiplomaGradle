package org.example.diplomagradle.service.metricValue;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//отлов пустых значений метрики
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyMetricValuesException extends RuntimeException {
    public EmptyMetricValuesException(Long idMetric) {
        super("MetricValue with this idMetric "+idMetric+ " not found");
    }

    public EmptyMetricValuesException(String metricName) {
        super("MetricValue with this metricName "+metricName+ " not found");
    }

    public EmptyMetricValuesException(String metricName, Integer month, Integer year) {
        super("MetricValue for metric '" + metricName + "' and period " + month + "/" + year + " not found");
    }
}
