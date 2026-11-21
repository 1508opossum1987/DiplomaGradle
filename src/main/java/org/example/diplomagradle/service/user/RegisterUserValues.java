package org.example.diplomagradle.service.user;

//класс для ввода формы регистрации пользователем без роли и активности
public record RegisterUserValues(String username, String password,
                                 String name, String email) {
    public RegisterUserValues {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidUserValuesException(null, "username is null or empty");
        }
        if (username.length() < 4 || username.length() > 20) {
            throw new InvalidUserValuesException(null, "username length must be between 4 and 20");
        }
        if (!username.matches("^[a-zA-Z0-9_.-]+$")) {
            throw new InvalidUserValuesException(null, "username contents invalid symbols");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidUserValuesException(null, "password is null or empty");
        }
        if (password.length() < 4 || password.length() > 20) {
            throw new InvalidUserValuesException(null, "password length must be between 4 and 20");
        }
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d).+$")) {
            throw new InvalidUserValuesException(null, "password contents invalid symbols");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidUserValuesException(null, "name is null or empty");
        }
        if (name.length() < 4 || name.length() > 20) {
            throw new InvalidUserValuesException(null, "name length must be between 4 and 20");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidUserValuesException(null, "email is null or empty");
        }
        if (email.length() > 40) {
            throw new InvalidUserValuesException(null, "email length must be at most 40");
        }
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new InvalidUserValuesException(null, "email format is invalid");
        }
    }
}
