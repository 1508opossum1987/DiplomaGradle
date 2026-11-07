package org.example.diplomagradle.datamodel.entity.wagon;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

//сущность вагон
@Entity
@Table(name = "t_wagon")
public class Wagon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //номер вагона от 20000000 до 99999999
    @Min(20000000)
    @Max(99999999)
    @Column(name = "f_number", nullable = false, unique = true)
    private Long number;

    //категория вагона - из списка
    @Enumerated(EnumType.STRING)
    @Column(name = "f_category", nullable = false)
    private WagonCategory category;

    //последняя операция с вагоном
    @Enumerated(EnumType.STRING)
    @Column(name = "f_last_operation", nullable = false)
    private WagonOperationType lastOperation;

    //время последней операции
    @Column (name="f_last_operation_at", nullable = false)
    private LocalDateTime lastOperationAt;

    //дата прибытия
    @Column (name="f_arrival_at", nullable = false)
    private LocalDateTime arrivalAt;

    //дата отправления
    @Column (name="f_departure_at")
    private LocalDateTime departureAt;

    //признак выполнения грузовых операций
    @Enumerated(EnumType.STRING)
    @Column(name = "f_loading", nullable = false)
    private LoadingType loading;

    public Wagon(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public WagonCategory getCategory() {
        return category;
    }

    public void setCategory(WagonCategory category) {
        this.category = category;
    }

    public WagonOperationType getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(WagonOperationType lastOperation) {
        this.lastOperation = lastOperation;
    }

    public LocalDateTime getLastOperationAt() {
        return lastOperationAt;
    }

    public void setLastOperationAt(LocalDateTime lastOperationAt) {
        this.lastOperationAt = lastOperationAt;
    }

    public LocalDateTime getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(LocalDateTime arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public LocalDateTime getDepartureAt() {
        return departureAt;
    }

    public void setDepartureAt(LocalDateTime departureAt) {
        this.departureAt = departureAt;
    }

    public LoadingType getLoading() {
        return loading;
    }

    public void setLoading(LoadingType loading) {
        this.loading = loading;
    }
}
