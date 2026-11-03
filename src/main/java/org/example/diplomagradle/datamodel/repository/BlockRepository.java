package org.example.diplomagradle.datamodel.repository;

import org.example.diplomagradle.datamodel.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//интерфейс-репозиторий сущности Block - типов блоков показателей
@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

}
