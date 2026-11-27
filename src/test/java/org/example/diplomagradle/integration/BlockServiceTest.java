package org.example.diplomagradle.integration;

import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.datamodel.repository.BlockRepository;
import org.example.diplomagradle.service.block.BlockServiceInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlockServiceTest {
    private final BlockServiceInterface service;
    private final BlockRepository repository;
    private String testTitle;

    @Autowired
    public BlockServiceTest(BlockServiceInterface service, BlockRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        testTitle = "Тестовый блок";
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    //успешный сценарий добавки блока
    @Test
    public void putBlockSuccess() {
        service.putBlock(testTitle);
        Block actual = repository.findByTitle(testTitle).orElseThrow();
        assertEquals(testTitle, actual.getTitle());
        assertNotNull(actual.getId());
    }

    //попытка добавить имя-дубликат
    @Test
    public void putBlockSameTitle() {
        service.putBlock(testTitle);
        try {
            service.putBlock(testTitle);
            fail("Нет ошибки при создании блока с повторяющимся названием");
        } catch (Exception _) {
        }
        assertEquals(1, repository.findAll().size());
    }

    //попытка добавить null
    @Test
    public void putBlockNullTitle() {
        try {
            service.putBlock(null);
            fail("Нет ошибки при создании блока с null названием");
        } catch (Exception _) {
        }
        assertEquals(0, repository.findAll().size());
    }
}