package org.example.diplomagradle.service.role;

import org.example.diplomagradle.datamodel.entity.users.Role;
import org.example.diplomagradle.datamodel.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

//класс для работы с ролями
public class RoleService implements RoleServiceInterface {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    //получить все роли
    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            throw new EmptyRolesException();
        }
        return roles;
    }

    //получить роль по названию
    @Override
    public Role getRoleByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyRoleNameException();
        }

        Optional<Role> role = roleRepository.findRoleByName(name);

        if (role.isEmpty()) {
            throw new InvalidRoleNameException(name);
        }

        return role.get();
    }

    //добавить роль
    @Override
    public void addNewRole(String name) {
        if (getRoleByName(name) != null) {
            throw new DuplicateRoleNameException(name);
        }

        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
    }

    //удалить роль по имени
    @Override
    public void deleteRoleByName(String name) {
        Role role = getRoleByName(name);

        if (role == null) {
            throw new InvalidRoleNameException(name);
        }

        roleRepository.delete(role);
    }
}
