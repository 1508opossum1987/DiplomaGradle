package org.example.diplomagradle.service.metricValue;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//отлов дубликатов
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateMetricValueException extends RuntimeException {
    public DuplicateMetricValueException(Long metricId, Integer month, Integer year) {
        super("MetricValue already exists for metric id: " + metricId
                + ", month: " + month + ", year: " + year);
    }
}
