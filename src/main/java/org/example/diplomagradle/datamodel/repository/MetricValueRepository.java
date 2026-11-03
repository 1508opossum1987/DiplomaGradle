package org.example.diplomagradle.datamodel.repository;

import org.example.diplomagradle.datamodel.entity.MetricValue;
import org.springframework.data.jpa.repository.JpaRepository;

//интерфейс-репозиторий для сущности MetricValue
public interface MetricValueRepository extends JpaRepository<MetricValue, Long> {

}
