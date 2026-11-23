package org.example.diplomagradle.service.user;

import org.example.diplomagradle.datamodel.entity.users.Role;
import org.example.diplomagradle.datamodel.entity.users.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

//интерфейс для работы с сущностью User
public interface UserServiceInterface {
    //получить всех пользователей
    public List<User> getAllUsers();

    //получить пользователя по name
    public User getUserByUsername(String username);

    //добавить нового пользователя
    public void addNewUser(UserValues values);

    //удалить пользователя
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserByUsername(String username);

    //забанить-разбанить пользователя по имени
    public void changeEnabledUserByUsername(String username);

    //изменить данные пользователя по имени
    public void changeUserByUsername(String username, UserValues values);

    //регистрация пользователей
    void registerUser(RegisterUserValues values);
}
