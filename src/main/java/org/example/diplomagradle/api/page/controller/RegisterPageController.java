package org.example.diplomagradle.api.page.controller;

import org.example.diplomagradle.service.user.RegisterUserValues;
import org.example.diplomagradle.service.user.UserServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//контроллер для обработки регистрации пользователя на странице
@Controller
public class RegisterPageController {
    private final UserServiceInterface userServiceInterface;

    public RegisterPageController(UserServiceInterface userServiceInterface) {
        this.userServiceInterface = userServiceInterface;
    }

    //получить страницу регистрации
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    //передача полей в регистрацию
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email,
                               Model model) {
        try {
            RegisterUserValues values = new RegisterUserValues(username, password, name, email);
            userServiceInterface.registerUser(values);
            return "redirect:/login?registered";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("username", username);
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            return "register";
        }
    }
}
