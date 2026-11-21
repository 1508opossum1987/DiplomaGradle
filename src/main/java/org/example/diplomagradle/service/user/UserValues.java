package org.example.diplomagradle.service.user;

import org.example.diplomagradle.datamodel.entity.users.Role;

//класс хранилище данных о User
public record UserValues(String username, String password,
                         Boolean enabled, Role role,
                         String name, String email) {
    public UserValues {
        //проверки имени пользователя
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidUserValuesException(this, "username is null or empty");
        }
        if (username.length() < 4 || username.length() > 20) {
            throw new InvalidUserValuesException(this, "username length must be between 4 and 20");
        }
        if (!username.matches("^[a-zA-Z0-9_.-]+$")) {//буквы, цифры, подчеркивания, точка, дефис
            throw new InvalidUserValuesException(this, "username contents invalid symbols");
        }

        //проверки пароля
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidUserValuesException(this, "password is null or empty");
        }
        if (password.length() < 4 || password.length() > 20) {
            throw new InvalidUserValuesException(this, "password length must be between 4 and 20");
        }
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d).+$")) {//хотя бы одна буква и цифра
            throw new InvalidUserValuesException(this, "username contents invalid symbols");
        }

        //проверка enabled
        if (enabled == null) {
            throw new InvalidUserValuesException(this, "enabled is null");
        }

        //проверка role
        if (role == null) {
            throw new InvalidUserValuesException(this, "role is null");
        }

        //проверка name
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidUserValuesException(this, "name is null or empty");
        }
        if (name.length() < 4 || name.length() > 20) {
            throw new InvalidUserValuesException(this, "name length must be between 4 and 20");
        }

        //проверка email
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidUserValuesException(this, "email is null or empty");
        }
        if (email.length() > 40) {
            throw new InvalidUserValuesException(this, "email length must be at most 40");
        }
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {//для email
            throw new InvalidUserValuesException(this, "email format is invalid");
        }

    }
}
