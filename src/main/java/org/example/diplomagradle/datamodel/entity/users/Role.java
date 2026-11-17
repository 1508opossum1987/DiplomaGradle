package org.example.diplomagradle.datamodel.entity.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Audited;

import java.util.Set;

//сущность роли - справочник
@Entity
@Table(name = "t_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //название роли
    @Column(name = "f_name", nullable = false, unique = true)
    private String name;

    //связь с сущностью User
    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Set<User> users;

    public Role(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
