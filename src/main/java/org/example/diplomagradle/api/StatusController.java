package org.example.diplomagradle.api;

import org.example.diplomagradle.api.messages.StringMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//вспомогательный класс для проверки работы сервера
@RestController
@RequestMapping("api")
public class StatusController {
    @GetMapping
    public StringMessage status (){
        return new StringMessage("Server is running");
    }

    @GetMapping ("ping")
    public StringMessage ping(){
        return new StringMessage("pong");
    }
}
