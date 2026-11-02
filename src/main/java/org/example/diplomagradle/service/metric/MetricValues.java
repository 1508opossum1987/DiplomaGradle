package org.example.diplomagradle.service.metric;

import org.example.diplomagradle.datamodel.entity.MetricsType;
import org.example.diplomagradle.service.block.BlockService;

public record MetricValues(String metricName, String unit, MetricsType type,
                           Long blockId, BlockService blockService) {
}
