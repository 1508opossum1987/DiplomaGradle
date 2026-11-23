package org.example.diplomagradle.service.wagon;

import org.example.diplomagradle.datamodel.entity.wagon.Wagon;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

//интерфейс для обработки задач с сущностью Wagon
public interface WagonServiceInterface {
    //получить весь список вагонов
    public List<Wagon> getAllWagons();

    //добавить вагон
    public void addWagon(WagonValues wagonValues);

    //получить вагон по id
    public Wagon getWagonById(Long wagonId);

    //получить вагон по номеру
    public Wagon getWagonByNumber(Long wagonNumber);

    //получить вагоны помесячно
    public List<Wagon> getWagonsByMonth(Integer year, Integer month);

    //удалить вагон по номеру
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteWagonByNumber(Long wagonNumber);

    //удалить вагон по id
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteWagonById(Long wagonId);
}
