package org.example.diplomagradle.datamodel.repository;

import org.example.diplomagradle.datamodel.entity.BlockView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Интерфейс для таблицы просмотра по ролям
public interface BlockViewRepository extends JpaRepository<BlockView, Long> {
    boolean existsByRole_IdAndBlock_Id(Long roleId, Long blockId);
    List<BlockView> findByRoleId(Long roleId);
}
