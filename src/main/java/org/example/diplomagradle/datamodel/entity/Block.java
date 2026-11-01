package org.example.diplomagradle.datamodel.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;

//сущность с названиями блоком показателей
@Entity
@Table(name = "t_block")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //название
    @Column(name = "f_title", nullable = false)
    private String title;

    public Block() {}

//    public Block(String title) {
//        this.title = title;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
