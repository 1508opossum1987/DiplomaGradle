package org.example.diplomagradle.datamodel.repository;

import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.datamodel.entity.wagon.Wagon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WagonRepository extends JpaRepository<Wagon, Long> {
    Optional<Wagon> findByNumber(Long number);
    boolean existsByNumber (Long number);
    List<Wagon> findByDepartureAtBetween(LocalDateTime start, LocalDateTime end);
    long countByDepartureAtBetween(LocalDateTime start, LocalDateTime end);
    void deleteByNumber(Long number);
}
