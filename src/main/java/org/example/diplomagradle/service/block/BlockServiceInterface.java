package org.example.diplomagradle.service.block;

import org.example.diplomagradle.datamodel.entity.Block;

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
    public void deleteBlockById(Long id);

    //удалить все блоки
    public void deleteAllBlocks();
}
