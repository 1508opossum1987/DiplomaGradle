package org.example.diplomagradle.api.blockApi;

import org.example.diplomagradle.api.messages.StringMessage;
import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.security.AccessCheckerInterface;
import org.example.diplomagradle.service.block.BlockServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/block")
public class BlockApiController {
    private final BlockServiceInterface blockServiceInterface;

    private final AccessCheckerInterface accessCheckerInterface;

    public BlockApiController (BlockServiceInterface blockServiceInterface,
                               AccessCheckerInterface accessCheckerInterface){
        this.blockServiceInterface = blockServiceInterface;
        this.accessCheckerInterface = accessCheckerInterface;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return blockServiceInterface.getAllBlocks().stream()
                .filter(block -> accessCheckerInterface.canViewBlock(auth, block.getId()))
                .toList();
    }

    //получение блока по id
    @GetMapping("/id/{id}")
    @PreAuthorize("@accessChecker.canViewBlock(authentication, #id)")
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
        Block block = blockServiceInterface.getBlockByTitle(title);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!accessCheckerInterface.canViewBlock(auth, block.getId())) {
            throw new AccessDeniedException("You haven't access!");
        }
        return block;
    }

}
