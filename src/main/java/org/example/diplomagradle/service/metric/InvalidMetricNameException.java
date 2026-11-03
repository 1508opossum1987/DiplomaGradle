package org.example.diplomagradle.service.metric;

//неправильное имя показателя
public class InvalidMetricNameException extends RuntimeException {
    public InvalidMetricNameException(String name) {
        super("This name: "+name+" isn't valid");
    }
}
