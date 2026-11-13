package org.example.diplomagradle.service.page;

import org.example.diplomagradle.api.page.dto.BlockPageDto;
import org.example.diplomagradle.api.page.dto.MetricPageDto;
import org.example.diplomagradle.api.page.dto.MetricValueEditDto;
import org.example.diplomagradle.api.page.dto.MetricValuePageDto;
import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.datamodel.entity.MetricValue;
import org.example.diplomagradle.datamodel.repository.BlockRepository;
import org.example.diplomagradle.datamodel.repository.MetricRepository;
import org.example.diplomagradle.datamodel.repository.MetricValueRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PageService implements PageServiceInterface {
    private final BlockRepository blockRepository;
    private final MetricRepository metricRepository;
    private final MetricValueRepository metricValueRepository;

    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm");

    //реализация общепринятых цветов инфоцентра в БП
    private static final Map<String, String> BLOCK_COLOR_CLASSES = Map.of(
            "Безопасность", "block-safety",
            "Качество", "block-quality",
            "Производство", "block-production",
            "Экономика", "block-economy",
            "Корпоративная культура", "block-culture"
    );

    public PageService(BlockRepository blockRepository,
                       MetricRepository metricRepository,
                       MetricValueRepository metricValueRepository) {
        this.blockRepository = blockRepository;
        this.metricRepository = metricRepository;
        this.metricValueRepository = metricValueRepository;
    }

    @Override
    public List<BlockPageDto> getMainPageData(Integer year, Integer month) {
        List<Block> blocks = blockRepository.findAll();
        List<BlockPageDto> result = new ArrayList<>();

        for (Block block : blocks) {
            List<Metric> metrics = metricRepository.findByBlockId(block.getId());
            List<MetricPageDto> metricDtos = new ArrayList<>();

            for (Metric metric : metrics) {
                Optional<MetricValue> value = metricValueRepository
                        .findByMetricIdAndMonthAndYear(metric.getId(), month, year);

                metricDtos.add(new MetricPageDto(
                        metric.getId(),
                        metric.getMetricName(),
                        metric.getUnit(),
                        value.map(MetricValue::getPlan).orElse(null),
                        value.map(MetricValue::getFact).orElse(null)
                ));
            }

            result.add(new BlockPageDto(
                    block.getId(),
                    block.getTitle(),
                    resolveColorClass(block.getTitle()),
                    metricDtos
            ));
        }

        return result;
    }

    @Override
    public BlockPageDto getBlockPageData(Long blockId, Integer year, Integer month) {
        Block block = blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block not found: " + blockId));

        List<Metric> metrics = metricRepository.findByBlockId(blockId);
        List<MetricPageDto> metricDtos = new ArrayList<>();

        for (Metric metric : metrics) {
            Optional<MetricValue> value = metricValueRepository
                    .findByMetricIdAndMonthAndYear(metric.getId(), month, year);

            metricDtos.add(new MetricPageDto(
                    metric.getId(),
                    metric.getMetricName(),
                    metric.getUnit(),
                    value.map(MetricValue::getPlan).orElse(null),
                    value.map(MetricValue::getFact).orElse(null)
            ));
        }

        return new BlockPageDto(
                block.getId(),
                block.getTitle(),
                resolveColorClass(block.getTitle()),
                metricDtos
        );
    }

    @Override
    public List<MetricValuePageDto> getMetricValues(Long metricId) {
        List<MetricValue> values = metricValueRepository.findByMetricId(metricId);
        List<MetricValuePageDto> result = new ArrayList<>();

        for (MetricValue value : values) {
            String updatedAt = value.getUpdatedAt() != null
                    ? value.getUpdatedAt().format(DATE_TIME_FORMAT)
                    : "";

            result.add(new MetricValuePageDto(
                    value.getId(),
                    value.getYear(),
                    value.getMonth(),
                    value.getPlan(),
                    value.getFact(),
                    value.getUpdatedBy(),
                    updatedAt
            ));
        }

        return result;
    }

    @Override
    public MetricValueEditDto getEditData(Long valueId) {
        MetricValue value = metricValueRepository.findById(valueId)
                .orElseThrow(() -> new RuntimeException("MetricValue not found: " + valueId));

        return new MetricValueEditDto(
                value.getId(),
                value.getMetric().getId(),
                value.getYear(),
                value.getMonth(),
                value.getPlan(),
                value.getFact()
        );
    }

    //служебный метод для подтягивания нужного цвета
    private String resolveColorClass(String blockTitle) {
        return BLOCK_COLOR_CLASSES.getOrDefault(blockTitle, "block-default");
    }
}
