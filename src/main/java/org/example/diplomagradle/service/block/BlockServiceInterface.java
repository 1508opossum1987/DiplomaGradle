package org.example.diplomagradle.service.block;

import org.example.diplomagradle.datamodel.entity.Block;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

//интерфейс для обработки задач с сущностью Block
public interface BlockServiceInterface {
    //добавить блок
    public void putBlock(String title);

    //получить все блоки
    public List<Block> getAllBlocks();

    //получить блок по id
    public Block getBlockById(Long id);

    //удалить блок по номеру (id)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBlockById(Long id);

    //удалить все блоки
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllBlocks();

    //получить блок по title
    public Block getBlockByTitle(String title);
}
