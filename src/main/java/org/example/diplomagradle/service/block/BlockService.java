package org.example.diplomagradle.service.block;

import net.bytebuddy.agent.builder.AgentBuilder;
import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.datamodel.repository.BlockRepository;

import java.util.List;
import java.util.Optional;

//операции с блоками показателей
public class BlockService implements BlockServiceInterface{
    private final BlockRepository blockRepository;

    public BlockService (BlockRepository blockRepository){
        this.blockRepository = blockRepository;
    }

    //добавление блока показателей
    public void putBlock(String title){
        Block block = new Block();
        if (title!=null && !title.trim().isEmpty()){
            block.setTitle(title);
            blockRepository.save(block);
        }
        else throw new InvalidTitleException(title);

    }

    //получить все блоки
    public List<Block> getAllBlocks() {
        if (blockRepository.findAll().isEmpty()){
            throw new EmptyBlocksException();
        } else
            return blockRepository.findAll();
    }

    //получить блок по id
    public Block getBlockById(Long id){
        Optional<Block> blocks = blockRepository.findById(id);
        if (blocks.isEmpty()){
            throw new InvalidIdException(id);
        }
        return blocks.get();
    }

    //удалить блок по номеру (id)
    public void deleteBlockById(Long id) {
        Block block = getBlockById(id);
        blockRepository.delete(block);
    }

    //удаление всех блоков
    public void deleteAllBlocks(){
        if (blockRepository.findAll().isEmpty()){
            throw new EmptyBlocksException();
        }
        blockRepository.deleteAll();
    }
}
