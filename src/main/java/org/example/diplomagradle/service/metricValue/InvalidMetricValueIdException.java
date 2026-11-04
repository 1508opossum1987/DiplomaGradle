package org.example.diplomagradle.service.metricValue;

public class InvalidMetricValueIdException extends RuntimeException {
    public InvalidMetricValueIdException(Long metricValueId) {
        super("This id: "+metricValueId+ " is invalid");
    }
}
