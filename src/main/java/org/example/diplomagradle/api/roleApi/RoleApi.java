package org.example.diplomagradle.api.roleApi;

import org.example.diplomagradle.api.messages.StringMessage;
import org.example.diplomagradle.datamodel.entity.users.Role;
import org.example.diplomagradle.service.role.RoleServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role")
public class RoleApi {
    private final RoleServiceInterface roleServiceInterface;

    public RoleApi (RoleServiceInterface roleServiceInterface){
        this.roleServiceInterface = roleServiceInterface;
    }

    //получить все роли
    @GetMapping
    public List<Role> getAllRolesApi(){
        return roleServiceInterface.getAllRoles();
    }

    //получить роль по названию
    @GetMapping("/{name}")
    public Role getRoleByNameApi(@PathVariable String name){
        return roleServiceInterface.getRoleByName(name);
    }

    //добавление новой роли
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public StringMessage addNewRoleApi(String name){
        roleServiceInterface.addNewRole(name);
        return (new StringMessage("Success"));
    }

    //удаление роли по имени
    @DeleteMapping("/{name}")
    public StringMessage deleteRoleByName(String name){
        roleServiceInterface.deleteRoleByName(name);
        return (new StringMessage("Success"));
    }
}
