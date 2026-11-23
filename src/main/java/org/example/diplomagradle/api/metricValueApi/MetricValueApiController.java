package org.example.diplomagradle.api.metricValueApi;

import org.example.diplomagradle.api.messages.StringMessage;
import org.example.diplomagradle.datamodel.entity.MetricValue;
import org.example.diplomagradle.security.AccessCheckerInterface;
import org.example.diplomagradle.service.metricValue.MetricValueServiceInterface;
import org.example.diplomagradle.service.metricValue.MetricValueValues;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/metric-value")
public class MetricValueApiController {
    private final MetricValueServiceInterface metricValueServiceInterface;

    private final AccessCheckerInterface accessCheckerInterface;

    public MetricValueApiController(MetricValueServiceInterface metricValueServiceInterface,
                                    AccessCheckerInterface accessCheckerInterface){
        this.metricValueServiceInterface = metricValueServiceInterface;
        this.accessCheckerInterface = accessCheckerInterface;
    }

    //добавление записи всех значений показателя по id Metric
    @PostMapping ()
    @ResponseStatus(HttpStatus.CREATED)
    public StringMessage addMetricValueByIdMetricApi (@RequestBody MetricValueValues values){
        metricValueServiceInterface
                .addMetricValue(values);
        return (new StringMessage("Success"));
    }

    //получение всех записей значений показателей (только по доступным метрикам)
    @GetMapping
    public List<MetricValue> getAllMetricValuesApi(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return metricValueServiceInterface.getAllMetricValues().stream()
                .filter(mv -> accessCheckerInterface.canViewMetric(auth, mv.getMetric().getId()))
                .toList();
    }

    //получение таблицы всех значений показателей по id записи MetricValue
    @GetMapping ("/metric-value-id/{metricValueId}")
    @PreAuthorize("@accessChecker.canEditByMetricValueId(authentication, #metricValueId)")
    public List<MetricValue> getAllMetricValuesByIdMetricValueApi(@PathVariable Long metricValueId){
        return metricValueServiceInterface.getAllMetricValuesByIdMetricValue(metricValueId);
    }

    //получение списка значений показателей по id показателя
    @GetMapping ("/metric-id/{idMetric}")
    @PreAuthorize("@accessChecker.canViewMetric(authentication, #idMetric)")
    public List<MetricValue> getAllMetricValuesByMetricIdApi(@PathVariable Long idMetric){
        return metricValueServiceInterface.getAllMetricValuesByMetricId(idMetric);
    }

    //получение списка значений показателей по названию показателя
    @GetMapping ("/metric-name/{metricName}")
    public List<MetricValue> getAllMetricValuesByMetricNameApi(@PathVariable String metricName){
        List<MetricValue> values = metricValueServiceInterface.getAllMetricValuesByMetricName(metricName);
        if (values.isEmpty()) {
            return values;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long metricId = values.getFirst().getMetric().getId();
        if (!accessCheckerInterface.canViewMetric(auth, metricId)) {
            throw new AccessDeniedException("You haven't access!");
        }
        return values;
    }

    //удаление всех значений показателя по id самого показателя Metric
    @DeleteMapping ("/metric-id/{idMetric}")
    public StringMessage deleteAllMetricValuesByMetricIdApi(@PathVariable Long idMetric){
        metricValueServiceInterface.deleteAllMetricValuesByMetricId(idMetric);
        return (new StringMessage("Success"));
    }

    //удаление всех значений показателя по name самого показателя Metric
    @DeleteMapping ("/metric-name/{metricName}")
    public StringMessage deleteAllMetricValuesByMetricNameApi(@PathVariable String metricName){
        metricValueServiceInterface.deleteAllMetricValuesByMetricName(metricName);
        return (new StringMessage("Success"));
    }

    //обновление всех значений показателя с поиском по названию показателя, году и месяцу
    @PutMapping ("/metric-name/{metricName}")
    public StringMessage putAllMetricValuesByMetricNameMonthYearApi(@PathVariable String metricName, @RequestParam Integer year, @RequestParam Integer month,
                                                                    @RequestParam BigDecimal newPlan, @RequestParam BigDecimal newFact){
        metricValueServiceInterface.putAllMetricValuesByMetricNameMonthYear(metricName, year,
                month, newPlan, newFact);
        return (new StringMessage("Success"));
    }

    //обновление всех значений показателя с поиском по id записи, году и месяцу
    @PutMapping("/metric-value-id/{metricValueId}")
    public StringMessage putAllMetricValuesByMetricValueIdMonthYearApi(@PathVariable Long metricValueId, @RequestParam Integer year, @RequestParam Integer month,
                                                                       @RequestParam BigDecimal newPlan, @RequestParam BigDecimal newFact){
        metricValueServiceInterface.putAllMetricValuesByMetricValueIdMonthYear(metricValueId, year, month,
                newPlan, newFact);
        return (new StringMessage("Success"));
    }

    //обновление планового значения показателя с поиском по названию показателя, году и месяцу
    @PatchMapping("/plan/{metricName}")
    public StringMessage patchPlanByMetricNameMonthYearApi(@PathVariable String metricName, @RequestParam Integer year, @RequestParam Integer month, @RequestParam BigDecimal newPlan){
        metricValueServiceInterface.patchPlanByMetricNameMonthYear(metricName, year, month, newPlan);
        return (new StringMessage("Success"));
    }

    //обновление фактического значения показателя с поиском по названию показателя, году и месяцу
    @PatchMapping("/fact/{metricName}")
    public StringMessage patchFactByMetricNameMonthYearApi(@PathVariable String metricName, @RequestParam Integer year, @RequestParam Integer month, @RequestParam BigDecimal newFact){
        metricValueServiceInterface.patchFactByMetricNameMonthYear(metricName, year, month, newFact);
        return (new StringMessage("Success"));
    }

    //обновление планового и фактического значения показателя с поиском по id записи, году и месяцу
    @PatchMapping("/metric-value-id/{metricValueId}")
    public StringMessage patchPlanFactByMetricValueIdMonthYearApi(@PathVariable Long metricValueId, @RequestParam Integer year, @RequestParam Integer month, @RequestParam BigDecimal newPlan, @RequestParam BigDecimal newFact){
        metricValueServiceInterface.patchPlanFactByMetricValueIdMonthYear(metricValueId, year, month, newPlan, newFact);
        return (new StringMessage("Success"));
    }
}