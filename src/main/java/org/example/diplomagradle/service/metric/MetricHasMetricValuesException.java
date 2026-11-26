package org.example.diplomagradle.service.metric;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//попытка удаления метрики с показателями
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MetricHasMetricValuesException extends RuntimeException {
    public MetricHasMetricValuesException(Long id) {
      super("This metric id: "+id+" has some metricValues");
    }
  public MetricHasMetricValuesException() {
    super("This metric has some metricValues");
  }
}
