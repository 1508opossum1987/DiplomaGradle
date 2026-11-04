package org.example.diplomagradle.service.metricValue;

public class InvalidMetricValueValuesException extends RuntimeException {
    public InvalidMetricValueValuesException(MetricValueValues values, String reason) {
        super("Invalid metricValue values: " + values + ". Reason: " + reason);
    }

    public InvalidMetricValueValuesException(String reason) {
        super("Invalid metricValue values. Reason: " + reason);
    }
}
