package org.example.diplomagradle.api.blockApi;

import org.example.diplomagradle.api.messages.StringMessage;
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
    public StringMessage putBlockApi(@RequestBody String title){
        blockServiceInterface.putBlock(title);
        return (new StringMessage("Success"));
    }

    //получение всех блоков показателей
    @GetMapping
    public List<Block> getAllBlocksApi(){
        return blockServiceInterface.getAllBlocks();
    }

    //получение блока по id
    @GetMapping("/id/{id}")
    public Block getBlockByIdApi(@PathVariable Long id){
        return blockServiceInterface.getBlockById(id);
    }

    //удаление блока по id
    @DeleteMapping("/{id}")
    public StringMessage deleteBlockByIdApi(@PathVariable Long id) {
        blockServiceInterface.deleteBlockById(id);
        return (new StringMessage("Success"));
    }

    //удаление всех блоков
    @DeleteMapping("")
    public StringMessage deleteAllBlocksApi(){
        blockServiceInterface.deleteAllBlocks();
        return (new StringMessage("Success"));
    }

    //получение блока по title
    @GetMapping("/title/{title}")
    public Block getBlockByTitleApi(@PathVariable String title){
        return blockServiceInterface.getBlockByTitle(title);
    }

}
