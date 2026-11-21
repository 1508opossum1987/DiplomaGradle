package org.example.diplomagradle.datamodel.repository;

import org.example.diplomagradle.datamodel.entity.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//интерфейс репозиторий сущности Role
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName (String name);
}
