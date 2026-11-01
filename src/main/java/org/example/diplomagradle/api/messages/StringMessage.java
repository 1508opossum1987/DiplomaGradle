package org.example.diplomagradle.api.messages;

import java.time.LocalDate;
import java.time.LocalDateTime;


//вспомогательный класс для диагностики работы сервера
public class StringMessage {
    private final String message;
    private final LocalDateTime time;

    public StringMessage(String message) {
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
