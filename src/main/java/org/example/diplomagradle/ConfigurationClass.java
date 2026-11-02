package org.example.diplomagradle;

import org.example.diplomagradle.datamodel.repository.BlockRepository;
import org.example.diplomagradle.datamodel.repository.MetricRepository;
import org.example.diplomagradle.service.block.BlockService;
import org.example.diplomagradle.service.block.BlockServiceInterface;
import org.example.diplomagradle.service.metric.MetricService;
import org.example.diplomagradle.service.metric.MetricServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationClass {
    @Bean
    public BlockServiceInterface blockService(BlockRepository blockRepository) {
        return new BlockService(blockRepository);
    }

    @Bean
    public MetricServiceInterface metricService(MetricRepository metricRepository,
                                                BlockServiceInterface blockServiceInterface ) {
        return new MetricService(metricRepository, blockServiceInterface);
    }
}
