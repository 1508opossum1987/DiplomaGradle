package org.example.diplomagradle.api.page.dto;

import java.math.BigDecimal;

public record MetricValueEditDto(
        Long id,
        Long metricId,
        Integer year,
        Integer month,
        BigDecimal plan,
        BigDecimal fact
) {
}
