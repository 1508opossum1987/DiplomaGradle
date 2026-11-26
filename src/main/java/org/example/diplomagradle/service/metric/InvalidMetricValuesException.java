package org.example.diplomagradle.service.metric;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//несоответствие вводимых данных о показателе при добавлении
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMetricValuesException extends RuntimeException {
    public InvalidMetricValuesException(MetricValues values, String reason) {
        super("Invalid metric values: " + values + ". Reason: " + reason);
    }
}
