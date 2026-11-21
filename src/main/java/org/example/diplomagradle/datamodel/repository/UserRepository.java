package org.example.diplomagradle.datamodel.repository;

import org.example.diplomagradle.datamodel.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//интерфейс репозиторий для сущности User
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername (String username);
    Optional<User> findUserByEmail(String email);
}
