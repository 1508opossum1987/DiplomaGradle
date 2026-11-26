package org.example.diplomagradle.api.page.controller;

import org.example.diplomagradle.datamodel.entity.users.Role;
import org.example.diplomagradle.datamodel.entity.users.User;
import org.example.diplomagradle.service.role.EmptyRolesException;
import org.example.diplomagradle.service.role.InvalidRoleNameException;
import org.example.diplomagradle.service.role.RoleServiceInterface;
import org.example.diplomagradle.service.user.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
public class AdminPageUserController {
    private final UserServiceInterface userServiceInterface;

    private final RoleServiceInterface roleServiceInterface;

    public AdminPageUserController(UserServiceInterface userServiceInterface,
                                   RoleServiceInterface roleServiceInterface) {
        this.userServiceInterface = userServiceInterface;
        this.roleServiceInterface = roleServiceInterface;
    }

    //получить страницу пользователей
    @GetMapping("/admin/users")
    public String usersPage(Model model) {
        List<User> users = userServiceInterface.getAllUsers();
        try {
            users = userServiceInterface.getAllUsers().stream()
                    .sorted(Comparator.comparing(User::getId))
                    .toList();
        } catch (EmptyUsersException ex) {
            users = List.of();
        }
        model.addAttribute("users", users);
        model.addAttribute("roles", roles());
        return "admin/admin-users";
    }

    //создать пользователя
    @PostMapping("/admin/users")
    public String createUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String roleName,
                             @RequestParam Boolean enabled,
                             RedirectAttributes redirectAttributes) {
        try {
            Role role = roleServiceInterface.getRoleByName(roleName);
            UserValues values = new UserValues(username, password, enabled, role, name, email);
            userServiceInterface.addNewUser(values);
            redirectAttributes.addFlashAttribute("success", "Пользователь создан");
        } catch (DuplicateUserNameException ex) {
            redirectAttributes.addFlashAttribute("error", "Пользователь с таким логином уже существует");
        } catch (DuplicateEmailException ex) {
            redirectAttributes.addFlashAttribute("error", "Пользователь с такой почтой уже существует");
        } catch (InvalidRoleNameException ex) {
            redirectAttributes.addFlashAttribute("error", "Такой роли нет");
        } catch (InvalidUserValuesException ex) {
            redirectAttributes.addFlashAttribute("error", "Проверьте логин, пароль, имя и email");
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Пользователь с таким именем уже существует");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании пользователя: " + ex.getMessage());
        }
        return "redirect:/admin/users";
    }

    //получить форму редактирования пользователя
    @GetMapping("/admin/users/{username}/edit")
    public String editUserForm(@PathVariable String username,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        try {
            User user = userServiceInterface.getUserByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("roles", roles());
            return "admin/admin-user-edit";
        } catch (InvalidUserNameException ex) {
            redirectAttributes.addFlashAttribute("error", "Пользователь не найден");
            return "redirect:/admin/users";
        }
    }

    //редактировать пользователя
    @PostMapping("/admin/users/{username}/edit")
    public String editUser(@PathVariable String username,
                           @RequestParam String usernameNew,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String roleName,
                           @RequestParam Boolean enabled,
                           RedirectAttributes redirectAttributes) {
        try {
            Role role = roleServiceInterface.getRoleByName(roleName);
            UserValues values = new UserValues(usernameNew, password, enabled, role, name, email);
            userServiceInterface.changeUserByUsername(username, values);
            redirectAttributes.addFlashAttribute("success", "Пользователь обновлён");
        } catch (InvalidUserNameException ex) {
            redirectAttributes.addFlashAttribute("error", "Пользователь не найден");
        } catch (DuplicateUserNameException ex) {
            redirectAttributes.addFlashAttribute("error", "Пользователь с таким логином уже существует");
        } catch (DuplicateEmailException ex) {
            redirectAttributes.addFlashAttribute("error", "Пользователь с такой почтой уже существует");
        } catch (InvalidRoleNameException ex) {
            redirectAttributes.addFlashAttribute("error", "Такой роли нет");
        } catch (InvalidUserValuesException ex) {
            redirectAttributes.addFlashAttribute("error", "Проверьте логин, пароль, имя и email");
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Пользователь с таким именем уже существует");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении пользователя: " + ex.getMessage());
        }
        return "redirect:/admin/users";
    }

    //удалить пользователя
    @PostMapping("/admin/users/{username}/delete")
    public String deleteUser(@PathVariable String username,
                             RedirectAttributes redirectAttributes) {

        try {
            userServiceInterface.deleteUserByUsername(username);
            redirectAttributes.addFlashAttribute("success", "Пользователь удалён");
        } catch (InvalidUserNameException ex) {
            redirectAttributes.addFlashAttribute("error", "Пользователь не найден");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении пользователя: " + ex.getMessage());
        }
        return "redirect:/admin/users";
    }

    //забанить-разбанить пользователя
    @PostMapping("/admin/users/{username}/enabled")
    public String changeEnabled(@PathVariable String username,
                                RedirectAttributes redirectAttributes) {
        try {
            userServiceInterface.changeEnabledUserByUsername(username);
            redirectAttributes.addFlashAttribute("success", "Статус пользователя изменён");
        } catch (InvalidUserNameException ex) {
            redirectAttributes.addFlashAttribute("error", "Пользователь не найден");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при изменении статуса: " + ex.getMessage());
        }
        return "redirect:/admin/users";
    }

    private List<Role> roles() {
        try {
            return roleServiceInterface.getAllRoles().stream()
                    .sorted(Comparator.comparing(Role::getName))
                    .toList();
        } catch (EmptyRolesException ex) {
            return List.of();
        }
    }
}
