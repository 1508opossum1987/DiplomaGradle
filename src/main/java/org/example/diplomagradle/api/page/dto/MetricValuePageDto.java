package org.example.diplomagradle.api.page.dto;

import java.math.BigDecimal;

public record MetricValuePageDto(Long id,
                                 Integer year,
                                 Integer month,
                                 BigDecimal plan,
                                 BigDecimal fact,
                                 String updatedBy,
                                 String updatedAt) {
}
