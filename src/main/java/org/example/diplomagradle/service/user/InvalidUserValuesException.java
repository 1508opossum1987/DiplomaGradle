package org.example.diplomagradle.service.user;

public class InvalidUserValuesException extends RuntimeException {
    public InvalidUserValuesException(UserValues values, String reason){
        super("Invalid user values "+values+ ". Reason: "+reason);
    }
}
