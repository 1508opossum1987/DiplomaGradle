package org.example.diplomagradle.datamodel.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;

import java.util.Set;

//сущность с названиями блоков показателей
@Entity
@Table(name = "t_block")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //название
    @Column(name = "f_title", nullable = false, unique = true)
    private String title;

    //связь с сущностью Metric
    @OneToMany(mappedBy = "block")
    private Set<Metric> metrics;

    public Block() {}

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

    public Set<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(Set<Metric> metrics) {
        this.metrics = metrics;
    }
}
