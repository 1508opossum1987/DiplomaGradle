package org.example.diplomagradle.service.role;

import org.example.diplomagradle.datamodel.entity.users.Role;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

//интерфейс для работы с Role
public interface RoleServiceInterface {
    //показать все роли
    public List<Role> getAllRoles();

    //получить роль по name
    public Role getRoleByName(String name);

    //добавить новую роль
    public void addNewRole(String name);

    //удалить роль
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRoleByName(String name);
}
