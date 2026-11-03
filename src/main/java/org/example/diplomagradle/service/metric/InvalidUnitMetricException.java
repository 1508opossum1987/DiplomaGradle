package org.example.diplomagradle.service.metric;

//неверное значение единицы измерения покзателя
public class InvalidUnitMetricException extends RuntimeException {
    public InvalidUnitMetricException(String newUnit) {
        super("This unit: "+newUnit+ " is invalid");
    }
}
