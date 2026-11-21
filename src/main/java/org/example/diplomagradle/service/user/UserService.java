package org.example.diplomagradle.service.user;

import org.example.diplomagradle.datamodel.entity.users.Role;
import org.example.diplomagradle.datamodel.entity.users.User;
import org.example.diplomagradle.datamodel.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

//сервис для работы с сущностью User
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //получить всех пользователей
    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EmptyUsersException();
        }
        return users;
    }

    //получить пользователя по никнейму
    @Override
    public User getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new EmptyUserNameException();
        }

        Optional<User> user = userRepository.findUserByUsername(username);

        if (user.isEmpty()) {
            throw new InvalidUserNameException(username);
        }

        return user.get();
    }

    //добавить пользователя
    @Override
    public void addNewUser(UserValues values) {
        if (values == null) {
            throw new InvalidUserValuesException(null, "values of users are empty");
        }

        if (userRepository.findUserByUsername(values.username()).isPresent()) {
            throw new DuplicateUserNameException(values.username());
        }

        if (userRepository.findUserByEmail(values.email()).isPresent()) {
            throw new DuplicateEmailException(values.email());
        }

        User user = new User();

        user.setUsername(values.username());
        user.setPassword(passwordEncoder.encode(values.password()));
        user.setRole(values.role());
        user.setEnabled(values.enabled());
        user.setName(values.name());
        user.setEmail(values.email());

        userRepository.save(user);
    }

    //удалить юзера по юзернейму
    @Override
    public void deleteUserByUsername(String username) {
        User user = getUserByUsername(username);
        userRepository.delete(user);
    }

    //забанить-разбанить юзера
    @Override
    public void changeEnabledUserByUsername(String username) {
        User user = getUserByUsername(username);

        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
    }

    //изменить данные существующего юзера
    @Override
    public void changeUserByUsername(String username, UserValues values){
        if (values == null) {
            throw new InvalidUserValuesException(null, "values of users are empty");
        }

        User user = getUserByUsername(username);

        if (values.username() != null && !values.username().equals(username)) {
            if (userRepository.findUserByUsername(values.username()).isPresent()) {
                throw new DuplicateUserNameException(values.username());
            }
            user.setUsername(values.username());
        }

        if (!values.email().equals(user.getEmail())) {
            if (userRepository.findUserByEmail(values.email()).isPresent()) {
                throw new DuplicateEmailException(values.email());
            }
            user.setEmail(values.email());
        }

        user.setPassword(passwordEncoder.encode(values.password()));
        user.setEnabled(values.enabled());
        user.setRole(values.role());
        user.setName(values.name());
        user.setEmail(values.email());

        userRepository.save(user);
    }

    //регистрация нового пользователя
    @Override
    public void registerUser(UserValues values) {
        if (values == null) {
            throw new InvalidUserValuesException(null, "values of users are empty");
        }
        if (userRepository.findUserByUsername(values.username()).isPresent()) {
            throw new DuplicateUserNameException(values.username());
        }
        if (userRepository.findUserByEmail(values.email()).isPresent()) {
            throw new DuplicateEmailException(values.email());
        }

        User user = new User();
        user.setUsername(values.username());
        user.setPassword(passwordEncoder.encode(values.password()));
        user.setEnabled(true);
        user.setName(values.name());
        user.setEmail(values.email());
        user.setRole(values.role()); // сюда контроллер передаст роль USER

        userRepository.save(user);
    }
}
