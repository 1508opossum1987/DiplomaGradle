package org.example.diplomagradle.datamodel.repository;

import org.example.diplomagradle.datamodel.entity.MetricAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//интерфейс - репозиторий для таблица доступа по ролям для редактирования
public interface MetricAccessRepository extends JpaRepository<MetricAccess, Long> {
    boolean existsByRole_IdAndMetric_Id(Long roleId, Long metricId);
    List<MetricAccess> findByRoleId(Long roleId);
}
