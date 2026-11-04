package org.example.diplomagradle.api.metricValieApi;

import org.example.diplomagradle.api.messages.StringMessage;
import org.example.diplomagradle.datamodel.entity.MetricValue;
import org.example.diplomagradle.service.metricValue.MetricValueServiceInterface;
import org.example.diplomagradle.service.metricValue.MetricValueValues;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/metric-value")
public class MetricValueApiController {
    private final MetricValueServiceInterface metricValueServiceInterface;

    public MetricValueApiController(MetricValueServiceInterface metricValueServiceInterface){
        this.metricValueServiceInterface = metricValueServiceInterface;
    }

    //добавление записи всех значений показателя по id Metric
    @PostMapping ()
    @ResponseStatus(HttpStatus.CREATED)
    public StringMessage addMetricValueByIdMetricApi (@RequestBody MetricValueValues values){
        metricValueServiceInterface
                .addMetricValue(values);
        return (new StringMessage("Success"));
    }

    //получение всех записей значений показателей
    @GetMapping
    public List<MetricValue> getAllMetricValuesApi(){
        return metricValueServiceInterface.getAllMetricValues();
    }

    //получение таблицы всех значений показателей по id записи MetricValue
    @GetMapping ("/metric-value-id/{metricValueId}")
    public List<MetricValue> getAllMetricValuesByIdMetricValueApi(@PathVariable Long metricValueId){
        return metricValueServiceInterface.getAllMetricValuesByIdMetricValue(metricValueId);
    }

    //получение списка значений показателей по id показателя
    @GetMapping ("/metric-id/{idMetric}")
    public List<MetricValue> getAllMetricValuesByMetricIdApi(@PathVariable Long idMetric){
        return metricValueServiceInterface.getAllMetricValuesByMetricId(idMetric);
    }

    //получение списка значений показателей по названию показателя
    @GetMapping ("/metric-name/{metricName}")
    public List<MetricValue> getAllMetricValuesByMetricNameApi(@PathVariable String metricName){
        return metricValueServiceInterface.getAllMetricValuesByMetricName(metricName);
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
                                                           @RequestParam BigDecimal newPlan, @RequestParam BigDecimal newFact, @RequestParam String updatedBy){
        metricValueServiceInterface.putAllMetricValuesByMetricNameMonthYear(metricName, year,
                month, newPlan, newFact, updatedBy);
        return (new StringMessage("Success"));
    }

    //обновление всех значений показателя с поиском по id записи, году и месяцу
    @PutMapping("/metric-value-id/{metricValueId}")
    public StringMessage putAllMetricValuesByMetricValueIdMonthYearApi(@PathVariable Long metricValueId, @RequestParam Integer year, @RequestParam Integer month,
                                                              @RequestParam BigDecimal newPlan, @RequestParam BigDecimal newFact, @RequestParam String updatedBy){
        metricValueServiceInterface.putAllMetricValuesByMetricValueIdMonthYear(metricValueId, year, month,
        newPlan, newFact, updatedBy);
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
