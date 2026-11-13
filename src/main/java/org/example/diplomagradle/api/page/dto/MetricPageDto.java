package org.example.diplomagradle.api.page.dto;

import java.math.BigDecimal;

public record MetricPageDto(Long id,
                            String name,
                            String unit,
                            BigDecimal plan,
                            BigDecimal fact) {
}
