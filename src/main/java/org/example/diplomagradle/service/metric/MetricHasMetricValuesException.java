package org.example.diplomagradle.service.metric;

public class MetricHasMetricValuesException extends RuntimeException {
    public MetricHasMetricValuesException(Long id) {
      super("This metric id: "+id+" has some metricValues");
    }
  public MetricHasMetricValuesException() {
    super("This metric has some metricValues");
  }
}
