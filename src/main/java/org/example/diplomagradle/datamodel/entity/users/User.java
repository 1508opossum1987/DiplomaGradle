package org.example.diplomagradle.datamodel.entity.users;

import jakarta.persistence.*;

//сущность пользователей
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //логин
    @Column(name = "t_username", nullable = false)
    private String username;

    //пароль
    @Column(name = "f_password", nullable = false, unique = true)
    private String password;

    //активность
    @Column(name = "f_enabled", nullable = false, columnDefinition = "boolean default true")
    private Boolean enabled = true;

    //связь с Role
    @ManyToOne
    @JoinColumn(name = "f_role_id", nullable = false)
    private Role role;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
