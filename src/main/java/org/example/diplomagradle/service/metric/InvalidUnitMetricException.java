package org.example.diplomagradle.service.metric;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//неверное значение единицы измерения покзателя
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUnitMetricException extends RuntimeException {
    public InvalidUnitMetricException(String newUnit) {
        super("This unit: "+newUnit+ " is invalid");
    }
}
