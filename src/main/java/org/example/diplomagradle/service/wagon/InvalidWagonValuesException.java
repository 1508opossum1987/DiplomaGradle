package org.example.diplomagradle.service.wagon;

public class InvalidWagonValuesException extends RuntimeException {
    public InvalidWagonValuesException(WagonValues wagonValues, String message) {
        super("Invalid wagon values: "+ wagonValues + ".Reason: " +message);
    }

    public InvalidWagonValuesException(String message) {
        super("Invalid wagon values. Reason: " +message);
    }
}
