package org.example.diplomagradle.api.metricApi;

import org.example.diplomagradle.api.messages.StringMessage;
import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.security.AccessCheckerInterface;
import org.example.diplomagradle.service.metric.MetricServiceInterface;
import org.example.diplomagradle.service.metric.MetricValues;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/metric")
public class MetricApiController {
    private final MetricServiceInterface metricServiceInterface;

    private final AccessCheckerInterface accessCheckerInterface;

    public MetricApiController(MetricServiceInterface metricServiceInterface,
                               AccessCheckerInterface accessCheckerInterface) {
        this.metricServiceInterface = metricServiceInterface;
        this.accessCheckerInterface = accessCheckerInterface;
    }

    //добавление показателей
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StringMessage addMetricApi(@RequestBody MetricValues values) {
        metricServiceInterface.addMetric(values);
        return (new StringMessage("Success"));
    }

    //получение всех покзателей
    @GetMapping
    public List<Metric> getAllMetricsApi() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return metricServiceInterface.getAllMetrics().stream()
                .filter(metric -> accessCheckerInterface.canViewMetric(auth, metric.getId()))
                .toList();
    }

    //получение показателя по id
    @GetMapping("/id/{id}")
    @PreAuthorize("@accessChecker.canViewMetric(authentication, #id)")
    public Metric getMetricByIdApi(@PathVariable Long id) {
        return metricServiceInterface.getMetricById(id);
    }

    //получение показателя по названию
    @GetMapping("/name/{name}")
    public Metric getMetricByNameApi(@PathVariable String name) {
        Metric metric = metricServiceInterface.getMetricByName(name);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!accessCheckerInterface.canViewMetric(auth, metric.getId())) {
            throw new AccessDeniedException("You haven't access!");
        }
        return metric;
    }

    //удаление показателя по id
    @DeleteMapping("/{id}")
    public StringMessage deleteMetricByIdApi(@PathVariable Long id) {
        metricServiceInterface.deleteMetricById(id);
        return (new StringMessage("Success"));
    }

    //удаление всех показателей
    @DeleteMapping
    public StringMessage deleteAllMetricsApi() {
        metricServiceInterface.deleteAllMetrics();
        return (new StringMessage("Success"));
    }

    //изменение названия показателя с поиском по его старому названию
    @PatchMapping("/name/by-name/{oldName}")
    public StringMessage patchMetricNameByNameApi(@PathVariable String oldName, @RequestParam String newName) {
        metricServiceInterface.patchMetricNameByName(oldName, newName);
        return (new StringMessage("Success"));
    }

    //изменение названия показателя с поиском по id
    @PatchMapping("/name/by-id/{id}")
    public StringMessage patchMetricNameByIdApi(@PathVariable Long id, @RequestParam String newName) {
        metricServiceInterface.patchMetricNameById(id, newName);
        return (new StringMessage("Success"));
    }

    //изменение единицы измерения показателя с поиском по названию
    @PatchMapping("/unit/by-name/{name}")
    public StringMessage patchMetricUnitByNameApi (@PathVariable String name, @RequestParam String newUnit) {
        metricServiceInterface.patchMetricUnitByName(name, newUnit);
        return (new StringMessage("Success"));
    }

    @PatchMapping("/unit/by-id/{id}")
    public StringMessage patchMetricUnitByIdApi (@PathVariable Long id, @RequestParam String newUnit) {
        metricServiceInterface.patchMetricUnitById(id, newUnit);
        return (new StringMessage("Success"));
    }
}
