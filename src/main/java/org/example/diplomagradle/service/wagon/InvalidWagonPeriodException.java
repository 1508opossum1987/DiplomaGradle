package org.example.diplomagradle.service.wagon;

import java.time.LocalDate;

public class InvalidWagonPeriodException extends RuntimeException {
    public InvalidWagonPeriodException(String message) {
        super(message);
    }
}
