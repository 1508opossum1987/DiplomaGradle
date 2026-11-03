package org.example.diplomagradle.service.metric;

//проверка наличия показателей
public class EmptyMetricsException extends RuntimeException {
    public EmptyMetricsException() {
        super("The metrics is empty");
    }
}
