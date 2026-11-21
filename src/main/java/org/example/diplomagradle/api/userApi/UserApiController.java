package org.example.diplomagradle.api.userApi;

import org.example.diplomagradle.api.messages.StringMessage;
import org.example.diplomagradle.datamodel.entity.users.Role;
import org.example.diplomagradle.datamodel.entity.users.User;
import org.example.diplomagradle.service.role.RoleServiceInterface;
import org.example.diplomagradle.service.user.RegisterUserValues;
import org.example.diplomagradle.service.user.UserServiceInterface;
import org.example.diplomagradle.service.user.UserValues;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserApiController {
    private final UserServiceInterface userServiceInterface;

    public UserApiController(UserServiceInterface userServiceInterface) {
        this.userServiceInterface = userServiceInterface;
    }

    //добавление пользователя
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public StringMessage addUserApi(@RequestBody UserValues values) {
        userServiceInterface.addNewUser(values);
        return (new StringMessage("Success"));
    }

    //регистрация нового пользователя с ролью USER
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public StringMessage registerUserApi(@RequestBody RegisterUserValues values) {
        userServiceInterface.registerUser(values);
        return (new StringMessage("Success"));
    }

    //получение всех пользователей
    @GetMapping
    public List<User> getAllUsersApi() {
        return userServiceInterface.getAllUsers();
    }

    //получение пользователя по username
    @GetMapping("/username/{username}")
    public User getUserByUsernameApi(@PathVariable String username) {
        return userServiceInterface.getUserByUsername(username);
    }

    //удаление пользователя по username
    @DeleteMapping("/username/{username}")
    public StringMessage deleteUserByUsernameApi(@PathVariable String username) {
        userServiceInterface.deleteUserByUsername(username);
        return (new StringMessage("Success"));
    }

    //забанить-разбанить пользователя по username
    @PatchMapping("/username/{username}/enabled")
    public StringMessage changeEnabledUserByUsernameApi(@PathVariable String username) {
        userServiceInterface.changeEnabledUserByUsername(username);
        return (new StringMessage("Success"));
    }

    //изменение данных пользователя по username
    @PutMapping("/username/{username}")
    public StringMessage changeUserByUsernameApi(@PathVariable String username,
                                                 @RequestBody UserValues values) {
        userServiceInterface.changeUserByUsername(username, values);
        return (new StringMessage("Success"));
    }

}
