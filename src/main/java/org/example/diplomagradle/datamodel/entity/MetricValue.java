package org.example.diplomagradle.datamodel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.DialectOverride;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//сущность - значение показателя
@Entity
//уникальное ограничение - на один показатель за один месяц и год - одна запись
@Table(name="t_metric_value", uniqueConstraints = @UniqueConstraint(
        name="uk_metric_value_metric_month_year",
        columnNames = {"f_metric_id", "f_month", "f_year"})
)
@Check(constraints = "f_month BETWEEN 1 AND 12 AND f_year >= 2024")
public class MetricValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //связь с наименованием показателя
    @ManyToOne
    @JoinColumn(name="f_metric_id", nullable = false)
    private Metric metric;

    //месяц
    @Min(1)
    @Max(12)
    @Column(name="f_month", nullable = false)
    private Integer month;

    //год
    @Min(2024)
    @Column(name="f_year", nullable = false)
    private Integer year;

    //плановое значение показателя
    @Column(name="f_plan", precision = 10, scale = 1)
    private BigDecimal plan;

    //фактическое значение показателя
    @Column(name="f_fact", precision = 10, scale = 1)
    private BigDecimal fact;

    //кто последний изменил показатель
    //TODO - здесь впоследствии планируется подброс сделать User-ов
    @Column(name="f_updated_by")
    private String updatedBy;

    //дата последнего изменения показателя
    //TODO - здесь впоследствии планируется подброс сделать User-ов
    @JsonFormat(pattern = "dd:MM:yyyy HH:mm")
    @Column(name="f_updated_at")
    private LocalDateTime updatedAt;


    public MetricValue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getPlan() {
        return plan;
    }

    public void setPlan(BigDecimal plan) {
        this.plan = plan;
    }

    public BigDecimal getFact() {
        return fact;
    }

    public void setFact(BigDecimal fact) {
        this.fact = fact;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
