package org.example.diplomagradle.api.wagonApi;

import org.example.diplomagradle.api.messages.StringMessage;
import org.example.diplomagradle.datamodel.entity.wagon.Wagon;
import org.example.diplomagradle.service.wagon.WagonServiceInterface;
import org.example.diplomagradle.service.wagon.WagonTestDataServiceInterface;
import org.example.diplomagradle.service.wagon.WagonValues;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/wagon")
public class WagonApiController {
    private final WagonServiceInterface wagonServiceInterface;

    private final WagonTestDataServiceInterface wagonTestDataServiceInterface;

    public WagonApiController(WagonServiceInterface wagonServiceInterface,
                              WagonTestDataServiceInterface wagonTestDataServiceInterface){
        this.wagonServiceInterface = wagonServiceInterface;
        this.wagonTestDataServiceInterface = wagonTestDataServiceInterface;
    }

    //получить все вагоны
    @GetMapping
    public List<Wagon> getAllWagonsApi() {
        return wagonServiceInterface.getAllWagons();
    }

    //добавить вагон
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StringMessage addWagonApi(@RequestBody WagonValues wagonValues) {
        wagonServiceInterface.addWagon(wagonValues);
        return new StringMessage("Success");
    }

    //получить вагон по id
    @GetMapping("/{wagonId}")
    public Wagon getWagonByIdApi(@PathVariable Long wagonId) {
        return wagonServiceInterface.getWagonById(wagonId);
    }

    //получить вагон по номеру
    @GetMapping("/number/{wagonNumber}")
    public Wagon getWagonByNumberApi(@PathVariable Long wagonNumber) {
        return wagonServiceInterface.getWagonByNumber(wagonNumber);
    }

    //получить вагоны за месяц
    @GetMapping("/month")
    public List<Wagon> getWagonsByMonthApi(@RequestParam Integer year, @RequestParam Integer month) {
        return wagonServiceInterface.getWagonsByMonth(year, month);
    }

    //удалить вагон по id
    @DeleteMapping("/{wagonId}")
    public StringMessage deleteWagonByIdApi(@PathVariable Long wagonId) {
        wagonServiceInterface.deleteWagonById(wagonId);
        return new StringMessage("Success");
    }

    //удалить вагон по номеру
    @DeleteMapping("/number/{wagonNumber}")
    public StringMessage deleteWagonByNumberApi(@PathVariable Long wagonNumber) {
        wagonServiceInterface.deleteWagonByNumber(wagonNumber);
        return new StringMessage("Success");
    }

    //генерация тестовых данных за разрешенный месяц
    @PostMapping("/generate")
    public StringMessage generateForMonthApi(@RequestParam Integer year, @RequestParam Integer month) {
        wagonTestDataServiceInterface.generateForMonth(year, month);
        return new StringMessage("Success");
    }

    //генерация данных за 2024-01 - 2026-09
    @PostMapping("/generate-history")
    public StringMessage generateHistoryApi() {
        wagonTestDataServiceInterface.generateHistory();
        return new StringMessage("Success");
    }
}
