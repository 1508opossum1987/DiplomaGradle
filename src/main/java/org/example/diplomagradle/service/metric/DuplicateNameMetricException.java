package org.example.diplomagradle.service.metric;

//отлов дубликата имени
public class DuplicateNameMetricException extends RuntimeException {
    public DuplicateNameMetricException(String name) {
        super("This name: "+name+" is already exists, please choice other name");
    }
}
