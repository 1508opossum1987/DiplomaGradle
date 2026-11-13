package org.example.diplomagradle.service.metric;

import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.service.metricValue.InvalidMetricValueValuesException;

import java.math.BigDecimal;

//проверка границ у показателей
public class MetricRestrictionValidator implements MetricRestrictionValidatorInterface{
    public void validate(Metric metric, BigDecimal plan, BigDecimal fact) {
        if (metric.getMinValue() != null) {
            if (plan != null && plan.compareTo(metric.getMinValue()) < 0) {
                throw new InvalidMetricValueValuesException(
                        "plan below min value for metric '" + metric.getMetricName() + "': " + metric.getMinValue());
            }
            if (fact != null && fact.compareTo(metric.getMinValue()) < 0) {
                throw new InvalidMetricValueValuesException(
                        "fact below min value for metric '" + metric.getMetricName() + "': " + metric.getMinValue());
            }
        }
        if (metric.getMaxValue() != null) {
            if (plan != null && plan.compareTo(metric.getMaxValue()) > 0) {
                throw new InvalidMetricValueValuesException(
                        "plan above max value for metric '" + metric.getMetricName() + "': " + metric.getMaxValue());
            }
            if (fact != null && fact.compareTo(metric.getMaxValue()) > 0) {
                throw new InvalidMetricValueValuesException(
                        "fact above max value for metric '" + metric.getMetricName() + "': " + metric.getMaxValue());
            }
        }
    }
}
