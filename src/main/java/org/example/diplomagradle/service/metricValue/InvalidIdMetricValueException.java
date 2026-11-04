package org.example.diplomagradle.service.metricValue;

public class InvalidIdMetricValueException extends RuntimeException {
    public InvalidIdMetricValueException(Long id) {
        super("MetricValue with id: " + id + " not found");
    }
}
