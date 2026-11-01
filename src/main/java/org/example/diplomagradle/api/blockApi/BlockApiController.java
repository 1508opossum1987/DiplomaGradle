package org.example.diplomagradle.api.blockApi;

import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.service.block.BlockServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/block")
public class BlockApiController {
    private final BlockServiceInterface blockServiceInterface;

    public BlockApiController (BlockServiceInterface blockServiceInterface){
        this.blockServiceInterface = blockServiceInterface;
    }

    //добавление блока показателей
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void putBlockApi(@RequestBody String title){
        blockServiceInterface.putBlock(title);
    }

    //получение всех блоков показателей
    @GetMapping
    public List<Block> getAllBlocksApi(){
        return blockServiceInterface.getAllBlocks();
    }

    //получение блока по id
    @GetMapping("/{id}")
    public Block getBlockByIdApi(@PathVariable Long id){
        return blockServiceInterface.getBlockById(id);
    }

    //удаление блока по id
    @DeleteMapping("/{id}")
    public void deleteBlockByIdApi(@PathVariable Long id) {
        blockServiceInterface.deleteBlockById(id);
    }

    //удаление всех блоков
    @DeleteMapping("/all-delete")
    public void deleteAllBlocksApi(){
        blockServiceInterface.deleteAllBlocks();
    }

}
