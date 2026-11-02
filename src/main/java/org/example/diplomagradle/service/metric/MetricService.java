package org.example.diplomagradle.service.metric;

import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.datamodel.repository.MetricRepository;
import org.example.diplomagradle.service.block.BlockServiceInterface;

import java.util.List;
import java.util.Optional;

//операции с сущностью Metric - показателями
public class MetricService implements MetricServiceInterface {
    private final MetricRepository metricRepository;

    private final BlockServiceInterface blockServiceInterface;

    public MetricService(MetricRepository metricRepository,
                         BlockServiceInterface blockServiceInterface) {
        this.metricRepository = metricRepository;
        this.blockServiceInterface = blockServiceInterface;
    }

    //добавить показатель
    @Override
    public void addMertic(MetricValues values) {
        Block block = blockServiceInterface.getBlockById(values.blockId());

        Metric metric = new Metric();
        metric.setMetricName(values.metricName());
        metric.setUnit(values.unit());
        metric.setType(values.type());
        metric.setBlock(block);

        metricRepository.save(metric);
    }

    //получить все показатели
    @Override
    public List<Metric> getAllMetrics() {
        return metricRepository.findAll();
    }

    //получить показатель по id
    @Override
    public Metric getMetricById(Long id) {
        Optional<Metric> metrics = metricRepository.findById(id);
        if(metrics.isEmpty()){
            throw new InvalidIdMetricException(id);
        }
        return metrics.get();
    }

    //получить показатель по названию
    public Metric getMetricByName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new InvalidMetricNameException(name);
        }
        Optional<Metric> metrics = metricRepository.findByMetricName(name);
        if(metrics.isEmpty()){
            throw new InvalidMetricNameException(name);
        } else
            return metrics.get();
    }

    //удалить показатель по id
    @Override
    public void deleteMetricById(Long id) {
        Metric metric = getMetricById(id);
        metricRepository.delete(metric);
    }

    //удалить все показатели
    @Override
    public void deleteAllMetrics() {
        metricRepository.deleteAll();
    }

    //изменить название показателя с поиском по названию
    @Override
    public void patchMetricNameByName(String oldName, String newName) {
        Metric metric = getMetricByName(oldName);
        metric.setMetricName(newName);
        metricRepository.save(metric);
    }

    //изменить название показателя с поиском по id
    @Override
    public void patchMetricNameById(Long id, String newName) {
        Metric metric = getMetricById(id);
        metric.setMetricName(newName);
        metricRepository.save(metric);
    }

    //изменить тип показателя с поиском по названию
    @Override
    public void patchMetricUnitByName(String name, String newUnit) {
        Metric metric = getMetricByName(name);
        metric.setUnit(newUnit);
        metricRepository.save(metric);
    }

    //изменить тип показателя с поиском по id
    @Override
    public void patchMetricUnitById(Long id, String newUnit) {
        Metric metric = getMetricById(id);
        metric.setUnit(newUnit);
        metricRepository.save(metric);
    }
}
