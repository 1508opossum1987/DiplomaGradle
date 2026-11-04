package org.example.diplomagradle.service.metricValue;

import org.example.diplomagradle.service.metric.InvalidIdMetricException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MetricValueValues(Long metricId, Integer month, Integer year,
                                BigDecimal plan, BigDecimal fact, String updatedBy) {
    public MetricValueValues {
        if (metricId == null) {
            throw new InvalidIdMetricException(null);
        }

        if (month == null) {
            throw new InvalidMetricValueValuesException(this, "month is null");
        }

        if (month > 12 || month < 1) {
            throw new InvalidMetricValueValuesException(this, "month is incorrect");
        }

        if (year == null) {
            throw new InvalidMetricValueValuesException(this, "year is null");
        }

        if (year < 2024 || year > LocalDateTime.now().getYear()) {
            throw new InvalidMetricValueValuesException(this, "year is incorrect");
        }

        if (month > LocalDateTime.now().getMonthValue() && year >= LocalDateTime.now().getYear() && fact != null) {
            throw new InvalidMetricValueValuesException(this, "it is so early for fact");
        }

//        if (plan == null) {
//            throw new InvalidMetricValueValuesException(this, "plan is null");
//        }

        if (plan != null && plan.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMetricValueValuesException(this, "plan is incorrect");
        }

//        if (fact == null) {
//            throw new InvalidMetricValueValuesException(this, "fact is null");
//        }

        if (fact != null && fact.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMetricValueValuesException(this, "fact is incorrect");
        }

        if (updatedBy == null || updatedBy.trim().isEmpty()) {
            throw new InvalidMetricValueValuesException(this, "updatedBy is null or empty");
        }
    }
}
