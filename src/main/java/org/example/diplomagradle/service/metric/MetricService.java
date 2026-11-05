package org.example.diplomagradle.service.metric;

import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.datamodel.repository.MetricRepository;
import org.example.diplomagradle.datamodel.repository.MetricValueRepository;
import org.example.diplomagradle.service.block.BlockServiceInterface;
import org.example.diplomagradle.service.block.EmptyBlocksException;

import java.util.List;
import java.util.Optional;

//операции с сущностью Metric - показателями
public class MetricService implements MetricServiceInterface {
    private final MetricRepository metricRepository;

    private final MetricValueRepository metricValueRepository;

    private final BlockServiceInterface blockServiceInterface;

    public MetricService(MetricRepository metricRepository,
                         BlockServiceInterface blockServiceInterface,
                         MetricValueRepository metricValueRepository) {
        this.metricRepository = metricRepository;
        this.blockServiceInterface = blockServiceInterface;
        this.metricValueRepository = metricValueRepository;
    }

    //добавить показатель
    @Override
    public void addMetric(MetricValues values) {
        if (values == null){
            throw new InvalidMetricValuesException(null, "values is empty");
        }

        if(metricRepository.findByMetricName(values.metricName()).isPresent()){
            throw new DuplicateNameMetricException(values.metricName());
        }

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
    @Override
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
        if (metricValueRepository.existsByMetricId(id)) {
            throw new MetricHasMetricValuesException(id);
        }
        metricRepository.delete(metric);
    }

    //удалить все показатели
    @Override
    public void deleteAllMetrics() {
        if (metricRepository.findAll().isEmpty()){
            throw new EmptyMetricsException();
        }
        if (!(metricValueRepository.count() == 0)) {
            throw new MetricHasMetricValuesException();
        }
        metricRepository.deleteAll();
    }

    //изменить название показателя с поиском по названию
    @Override
    public void patchMetricNameByName(String oldName, String newName) {
        if(oldName == null || oldName.trim().isEmpty()){
            throw new InvalidMetricNameException(oldName);
        }

        if(newName == null || newName.trim().isEmpty()){
            throw new InvalidMetricNameException(newName);
        }

        if (newName.equals(oldName)){
            throw new DuplicateNameMetricException(newName);
        }

        if(metricRepository.findByMetricName(newName).isPresent()){
            throw new DuplicateNameMetricException(newName);
        }

        Metric metric = getMetricByName(oldName);

        metric.setMetricName(newName);
        metricRepository.save(metric);
    }

    //изменить название показателя с поиском по id
    @Override
    public void patchMetricNameById(Long id, String newName) {
        Metric metric = getMetricById(id);
        if(newName == null || newName.trim().isEmpty()){
            throw new InvalidMetricNameException(newName);
        }

        if (newName.equals(metric.getMetricName())) {
            throw new DuplicateNameMetricException(newName);
        }

        if (metricRepository.findByMetricName(newName).isPresent()) {
            throw new DuplicateNameMetricException(newName);
        }

        metric.setMetricName(newName);
        metricRepository.save(metric);
    }

    //изменить единицы измерения показателя с поиском по названию
    @Override
    public void patchMetricUnitByName(String name, String newUnit) {
        if(name == null || name.trim().isEmpty()){
            throw new InvalidMetricNameException(name);
        }

        if (newUnit == null || newUnit.trim().isEmpty()) {
            throw new InvalidUnitMetricException(newUnit);
        }

        Metric metric = getMetricByName(name);

        metric.setUnit(newUnit);
        metricRepository.save(metric);
    }

    //изменить тип показателя с поиском по id
    @Override
    public void patchMetricUnitById(Long id, String newUnit) {
        if (newUnit == null || newUnit.trim().isEmpty()) {
            throw new InvalidUnitMetricException(newUnit);
        }

        Metric metric = getMetricById(id);

        metric.setUnit(newUnit);
        metricRepository.save(metric);
    }

    //получить показатели по названию блока показателей Block
    @Override
    public List<Metric> getMetricByTitleBlock(String blockTitle){
        Block block = blockServiceInterface.getBlockByTitle(blockTitle);
        List<Metric> metrics = metricRepository.findByBlockId(block.getId());
        if (metrics.isEmpty()) {
            throw new EmptyMetricsException();
        }
        return metrics;
    }


}
