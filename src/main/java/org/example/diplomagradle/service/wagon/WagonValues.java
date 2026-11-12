package org.example.diplomagradle.service.wagon;

import org.example.diplomagradle.datamodel.entity.wagon.LoadingType;
import org.example.diplomagradle.datamodel.entity.wagon.WagonCategory;
import org.example.diplomagradle.datamodel.entity.wagon.WagonOperationType;

import java.time.LocalDateTime;

public record WagonValues(Long number, WagonCategory category, WagonOperationType lastOperation,
                          LocalDateTime lastOperationAt, LocalDateTime arrivalAt,
                          LocalDateTime departureAt, LoadingType loading) {
    public WagonValues {
        //проверка диапазона номера и его наличия
        if (number == null || number < 20000000 || number > 99999999) {
            throw new InvalidWagonValuesException(this, "number is null or out of range 20000000-99999999");
        }
        //проверка наличия категории
        if (category == null) {
            throw new InvalidWagonValuesException(this, "category is null");
        }
        //проверка наличия последней операции
        if (lastOperation == null) {
            throw new InvalidWagonValuesException(this, "lastOperation is null");
        }
        //проверка наличия даты последней операции
        if (lastOperationAt == null) {
            throw new InvalidWagonValuesException(this, "lastOperationAt is null");
        }
        //проверка наличия даты прибытия
        if (arrivalAt == null) {
            throw new InvalidWagonValuesException(this, "arrivalAt is null");
        }
        //проверка наличия даты прибытия на диапазон
        if (arrivalAt.isBefore(LocalDateTime.of(2024, 1, 1, 0, 0))) {
            throw new InvalidWagonValuesException(this, "arrivalAt is before 2024-01-01");
        }
        //проверка наличия типа грузовых операций
        if (loading == null) {
            throw new InvalidWagonValuesException(this, "loading is null");
        }
        //прибытие должно быть раньше отправления
        if (departureAt != null && !arrivalAt.isBefore(departureAt)) {
            throw new InvalidWagonValuesException(this, "arrivalAt must be before departureAt");
        }
        //последняя операция должна быть позже прибытия
        if (lastOperationAt.isBefore(arrivalAt)) {
            throw new InvalidWagonValuesException(this, "lastOperationAt must be >= arrivalAt");
        }
        //последняя операция - отправление?
        boolean isDeparture = lastOperation == WagonOperationType.DEPARTURE;
        //еcли вагон отправился, то дата отправления должна быть
        if (isDeparture && departureAt == null) {
            throw new InvalidWagonValuesException(this, "departureAt is required when lastOperation is DEPARTURE");
        }
        //если вагон отправился, то время отправления равно времени операции
        if (isDeparture && !lastOperationAt.equals(departureAt)) {
            throw new InvalidWagonValuesException(this, "lastOperationAt must equal departureAt when lastOperation is DEPARTURE");
        }
        //если последняя операция не отправление, но время отправления есть
        if (!isDeparture && departureAt != null) {
            throw new InvalidWagonValuesException(this, "departureAt must be null when lastOperation is not DEPARTURE");
        }

        //если категория вагона - не местный, то у него не может быть погрузки или выгрузки
        if (category != WagonCategory.LOCAL && loading != LoadingType.NONE) {
            throw new InvalidWagonValuesException(this, "loading must be NONE for non-LOCAL wagons");
        }
    }

}
