package org.example.diplomagradle.datamodel.repository;

import org.example.diplomagradle.datamodel.entity.MetricValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//интерфейс-репозиторий для сущности MetricValue
public interface MetricValueRepository extends JpaRepository<MetricValue, Long> {
    boolean existsByMetricId(Long metricId);
    boolean existsByMetricIdAndMonthAndYear(Long metricId, Integer month, Integer year);
    List<MetricValue> findByMetricId(Long metricId);
    void deleteByMetricId(Long metricId);
    Optional<MetricValue> findByMetricIdAndMonthAndYear(Long metricId, Integer month, Integer year);
}
