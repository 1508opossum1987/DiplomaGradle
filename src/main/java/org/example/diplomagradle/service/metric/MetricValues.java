package org.example.diplomagradle.service.metric;

import org.example.diplomagradle.datamodel.entity.MetricsType;
import org.example.diplomagradle.service.block.BlockService;

//класс - хранилище вводимых данных с проверками
public record MetricValues(String metricName, String unit, MetricsType type,
                           Long blockId, BlockService blockService) {
    public MetricValues {
        if (metricName == null || metricName.trim().isEmpty()) {
            throw new InvalidMetricValuesException(this, "metricName is null or empty");
        }
        if (unit == null || unit.trim().isEmpty()) {
            throw new InvalidMetricValuesException(this, "unit is null or empty");
        }
        if (type == null) {
            throw new InvalidMetricValuesException(this, "type is null");
        }
        if (blockId == null) {
            throw new InvalidMetricValuesException(this, "blockId is null");
        }
    }
}
