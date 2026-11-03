package org.example.diplomagradle.service.metric;

//несоответствие вводимых данных о показателе при добавлении
public class InvalidMetricValuesException extends RuntimeException {
    public InvalidMetricValuesException(MetricValues values, String reason) {
        super("Invalid metric values: " + values + ". Reason: " + reason);
    }
}
