package org.example.diplomagradle.service.metric;

public class InvalidMetricNameException extends RuntimeException {
    public InvalidMetricNameException(String name) {

        super("This name: "+name+"isn't valid");
    }
}
