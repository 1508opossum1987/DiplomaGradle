package org.example.diplomagradle.datamodel.entity;

import jakarta.persistence.*;
import org.example.diplomagradle.datamodel.entity.users.Role;

//сущность доступа к показателям
@Entity
@Table(name = "t_metric_access")
public class MetricAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //связь с ролью
    @ManyToOne
    @JoinColumn(name = "f_role_id", nullable = false)
    private Role role;

    //связь с метрикой
    @ManyToOne
    @JoinColumn(name = "f_metric_id", nullable = false)
    private Metric metric;

    public MetricAccess() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
