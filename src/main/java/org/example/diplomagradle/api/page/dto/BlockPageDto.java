package org.example.diplomagradle.api.page.dto;

import java.util.List;

public record BlockPageDto(
        Long id,
        String title,
        String colorClass,
        List<MetricPageDto> metrics
) {
}