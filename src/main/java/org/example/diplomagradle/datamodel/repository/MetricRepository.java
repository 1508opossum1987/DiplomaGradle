package org.example.diplomagradle.datamodel.repository;

import org.example.diplomagradle.datamodel.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//интерфейс-репозиторий для сущности Metric
@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
    Optional<Metric> findByMetricName(String metricName);
    boolean existsByBlockId(Long blockId);
}
