package org.example.diplomagradle.service.block;

import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.datamodel.repository.BlockRepository;
import org.example.diplomagradle.datamodel.repository.MetricRepository;

import java.util.List;
import java.util.Optional;

//операции с блоками показателей
public class BlockService implements BlockServiceInterface{
    private final BlockRepository blockRepository;
    private final MetricRepository metricRepository;

    public BlockService (BlockRepository blockRepository,
                         MetricRepository metricRepository){
        this.blockRepository = blockRepository;
        this.metricRepository = metricRepository;
    }

    //добавление блока показателей
    @Override
    public void putBlock(String title){
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidTitleBlockException(title);
        }
        if (blockRepository.existsByTitle(title)) {
            throw new DuplicateTitleBlockException(title);
        }
        Block block = new Block();
        block.setTitle(title);
        blockRepository.save(block);

    }

    //получить все блоки
    @Override
    public List<Block> getAllBlocks() {
        if (blockRepository.findAll().isEmpty()){
            throw new EmptyBlocksException();
        } else
            return blockRepository.findAll();
    }

    //получить блок по id
    @Override
    public Block getBlockById(Long id){
        Optional<Block> blocks = blockRepository.findById(id);
        if (blocks.isEmpty()){
            throw new InvalidIdBlockException(id);
        }
        return blocks.get();
    }

    //удалить блок по номеру (id)
    @Override
    public void deleteBlockById(Long id) {
        Block block = getBlockById(id);
        if (metricRepository.existsByBlockId(id)) {
            throw new BlockHasMetricsException(id);
        }
        blockRepository.delete(block);
    }

    //удаление всех блоков
    @Override
    public void deleteAllBlocks(){
        if (!(metricRepository.count() == 0)){
            throw new BlockHasMetricsException();
        }
        if (blockRepository.findAll().isEmpty()){
            throw new EmptyBlocksException();
        }
        blockRepository.deleteAll();
    }

    //получить блок по title
    @Override
    public Block getBlockByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidTitleBlockException(title);
        }
        Optional<Block> block = blockRepository.findByTitle(title);
        if (block.isEmpty()) {
            throw new InvalidTitleBlockException(title);
        }
        return block.get();
    }
}
