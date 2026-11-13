package org.example.diplomagradle.service.metric;

import org.example.diplomagradle.datamodel.entity.Metric;

import java.math.BigDecimal;

public interface MetricRestrictionValidatorInterface {
    public void validate(Metric metric, BigDecimal plan, BigDecimal fact);
}
