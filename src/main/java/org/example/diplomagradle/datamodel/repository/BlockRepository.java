package org.example.diplomagradle.datamodel.repository;

import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.datamodel.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//интерфейс-репозиторий сущности Block - типов блоков показателей
@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByTitle(String title);
    boolean existsByTitle(String title);
}
