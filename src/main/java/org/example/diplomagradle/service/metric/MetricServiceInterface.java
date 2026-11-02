package org.example.diplomagradle.service.metric;

import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.datamodel.entity.Metric;

import java.util.List;

//интерфейс для обработки задач с сущностью Metric
public interface MetricServiceInterface {
    //добавить показатель
    public void addMertic(MetricValues values);

    //получить все показатели
    public List<Metric> getAllMetrics();

    //получить показатель по id
    public Metric getMetricById(Long id);

    //получить показатель по названию
    public Metric getMetricByName(String name);

    //удалить показатель по номеру (id)
    public void deleteMetricById(Long id);

    //удалить все показатели
    public void deleteAllMetrics();

    //изменить название показателя по названию
    public void patchMetricNameByName(String oldName, String newName);

    //изменить название показателя по id
    public void patchMetricNameById(Long id, String newName);

    //изменить единицу измерения показателя по названию
    public void patchMetricUnitByName(String name, String newUnit);

    //изменить единицу измерения показателя по id
    public void patchMetricUnitById(Long id, String newUnit);
}
