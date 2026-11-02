package org.example.diplomagradle.datamodel.entity;

import jakarta.persistence.*;

//сущность с названиями показателей
@Entity
@Table(name = "t_metric")
public class Metric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //название
    @Column(name = "f_metric_name", nullable = false, unique = true)
    private String metricName;

    //единица измерения показателя
    @Column(name = "f_unit", nullable = false)
    private String unit;

    //тип показателя: ручной или расчетный
    @Enumerated(EnumType.STRING)
    @Column(name = "f_type", nullable = false)
    private MetricsType type;

    //связь с сущностью Block
    @ManyToOne
    @JoinColumn(name = "f_block_id", nullable = false)
    private Block block;

    public Metric() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public MetricsType getType() {
        return type;
    }

    public void setType(MetricsType type) {
        this.type = type;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
