package org.example.diplomagradle.service.metricValue;

public class DuplicateMetricValueException extends RuntimeException {
    public DuplicateMetricValueException(Long metricId, Integer month, Integer year) {
        super("MetricValue already exists for metric id: " + metricId
                + ", month: " + month + ", year: " + year);
    }
}
