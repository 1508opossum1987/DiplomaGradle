package org.example.diplomagradle.api.page.controller;

import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.datamodel.entity.MetricValue;
import org.example.diplomagradle.datamodel.entity.MetricsType;
import org.example.diplomagradle.service.block.BlockHasMetricsException;
import org.example.diplomagradle.service.block.BlockServiceInterface;
import org.example.diplomagradle.service.block.DuplicateTitleBlockException;
import org.example.diplomagradle.service.metric.DuplicateNameMetricException;
import org.example.diplomagradle.service.metric.EmptyMetricsException;
import org.example.diplomagradle.service.metric.MetricServiceInterface;
import org.example.diplomagradle.service.metric.MetricValues;
import org.example.diplomagradle.service.metricValue.*;
import org.example.diplomagradle.service.metric.MetricHasMetricValuesException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class AdminPageController {

    private final BlockServiceInterface blockServiceInterface;
    private final MetricServiceInterface metricServiceInterface;
    private final MetricValueServiceInterface metricValueServiceInterface;

    public AdminPageController(BlockServiceInterface blockServiceInterface,
                               MetricServiceInterface metricServiceInterface,
                               MetricValueServiceInterface metricValueServiceInterface) {
        this.blockServiceInterface = blockServiceInterface;
        this.metricServiceInterface = metricServiceInterface;
        this.metricValueServiceInterface = metricValueServiceInterface;
    }

    //получить страницу админа
    @GetMapping("/admin")
    public String adminPage() {
        return "admin/admin";
    }

    //получить страницу инфоцентра админа
    @GetMapping("/admin/info-center")
    public String infoCenterPage(Model model) {
        List<Block> blocks = blockServiceInterface.getAllBlocks();
        List<BlockWithMetrics> rows = new ArrayList<>();
        for (Block block : blocks) {
            List<Metric> metrics;
            try {
                metrics = metricServiceInterface.getMetricByTitleBlock(block.getTitle());
            } catch (EmptyMetricsException ex) {
                metrics = List.of();
            }
            rows.add(new BlockWithMetrics(block, metrics));
        }
        model.addAttribute("blocks", rows);
        return "admin/admin-info-center";
    }

    //создать блок
    @PostMapping("/admin/info-center/block")
    public String createBlock(@RequestParam String title,
                              RedirectAttributes redirectAttributes) {
        try {
            blockServiceInterface.putBlock(title);
            redirectAttributes.addFlashAttribute("success", "Блок создан");
        } catch (DuplicateTitleBlockException ex) {
            redirectAttributes.addFlashAttribute("error", "Блок с таким названием уже существует");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании блока: " + ex.getMessage());
        }
        return "redirect:/admin/info-center";
    }

    //удалить блок
    @PostMapping("/admin/info-center/block/{id}/delete")
    public String deleteBlock(@PathVariable Long id,
                              RedirectAttributes redirectAttributes) {
        try {
            blockServiceInterface.deleteBlockById(id);
            redirectAttributes.addFlashAttribute("success", "Блок удалён");
        } catch (BlockHasMetricsException ex) {
            redirectAttributes.addFlashAttribute("error", "Нельзя удалить блок: в нём есть метрики");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении блока: " + ex.getMessage());
        }
        return "redirect:/admin/info-center";
    }

    //получить страницу формы создания новой метрики
    @GetMapping("/admin/info-center/block/{blockId}/metric/new")
    public String newMetricForm(@PathVariable Long blockId, Model model) {
        Block block = blockServiceInterface.getBlockById(blockId);
        model.addAttribute("block", block);
        model.addAttribute("types", MetricsType.values());
        return "admin/admin-metric-form";
    }

    //создать метрику
    @PostMapping("/admin/info-center/block/{blockId}/metric")
    public String createMetric(@PathVariable Long blockId,
                               @RequestParam String metricName,
                               @RequestParam String unit,
                               @RequestParam MetricsType type,
                               @RequestParam(required = false) BigDecimal minValue,
                               @RequestParam(required = false) BigDecimal maxValue,
                               RedirectAttributes redirectAttributes) {
        try {
            MetricValues values = new MetricValues(metricName, unit, type, blockId, null);
            metricServiceInterface.addMetric(values);
            if (minValue != null || maxValue != null) {
                Metric created = metricServiceInterface.getMetricByName(metricName);
                metricServiceInterface.patchMetricMinMaxById(created.getId(), minValue, maxValue);
            }
            redirectAttributes.addFlashAttribute("success", "Показатель создан");
        } catch (DuplicateNameMetricException ex) {
            redirectAttributes.addFlashAttribute("error", "Показатель с таким названием уже существует");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании показателя: " + ex.getMessage());
        }
        return "redirect:/admin/info-center";
    }

    //получить форму редактирования метрики
    @GetMapping("/admin/info-center/metric/{id}/edit")
    public String editMetricForm(@PathVariable Long id, Model model) {
        Metric metric = metricServiceInterface.getMetricById(id);
        model.addAttribute("metric", metric);
        return "admin/admin-metric-edit";
    }

    //редактировать метрику
    @PostMapping("/admin/info-center/metric/{id}/edit")
    public String editMetric(@PathVariable Long id,
                             @RequestParam String metricName,
                             @RequestParam String unit,
                             @RequestParam(required = false) BigDecimal minValue,
                             @RequestParam(required = false) BigDecimal maxValue,
                             RedirectAttributes redirectAttributes) {
        try {
            Metric current = metricServiceInterface.getMetricById(id);
            if (!metricName.equals(current.getMetricName())) {
                metricServiceInterface.patchMetricNameById(id, metricName);
            }
            if (!unit.equals(current.getUnit())) {
                metricServiceInterface.patchMetricUnitById(id, unit);
            }
            metricServiceInterface.patchMetricMinMaxById(id, minValue, maxValue);
            redirectAttributes.addFlashAttribute("success", "Показатель обновлён");
        } catch (DuplicateNameMetricException ex) {
            redirectAttributes.addFlashAttribute("error", "Показатель с таким названием уже существует");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении показателя: " + ex.getMessage());
        }
        return "redirect:/admin/info-center";
    }

    //удалить метрику
    @PostMapping("/admin/info-center/metric/{id}/delete")
    public String deleteMetric(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        try {
            metricServiceInterface.deleteMetricById(id);
            redirectAttributes.addFlashAttribute("success", "Показатель удалён");
        } catch (MetricHasMetricValuesException ex) {
            redirectAttributes.addFlashAttribute("error", "Нельзя удалить показатель: у него есть значения");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении показателя: " + ex.getMessage());
        }
        return "redirect:/admin/info-center";
    }

    //получение значений метрик
    @GetMapping("/admin/info-center/metric/{id}/values")
    public String metricValuesPage(@PathVariable Long id, Model model) {
        Metric metric = metricServiceInterface.getMetricById(id);
        List<MetricValue> values;
        try {
            values = metricValueServiceInterface.getAllMetricValuesByMetricId(id).stream()
                    .sorted(Comparator.comparing(MetricValue::getYear).reversed()
                            .thenComparing(Comparator.comparing(MetricValue::getMonth).reversed()))
                    .toList();
        } catch (EmptyMetricValuesException ex) {
            values = List.of();
        }
        model.addAttribute("metric", metric);
        model.addAttribute("values", values);
        return "admin/admin-metric-values";
    }

    //добавление значения метрики
    @PostMapping("/admin/info-center/metric/{id}/values")
    public String createMetricValue(@PathVariable Long id,
                                    @RequestParam Integer year,
                                    @RequestParam Integer month,
                                    @RequestParam(required = false) BigDecimal plan,
                                    @RequestParam(required = false) BigDecimal fact,
                                    RedirectAttributes redirectAttributes) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            MetricValueValues values = new MetricValueValues(id, month, year, plan, fact, username);
            metricValueServiceInterface.addMetricValue(values);
            redirectAttributes.addFlashAttribute("success", "Значение добавлено");
        } catch (DuplicateMetricValueException ex) {
            redirectAttributes.addFlashAttribute("error", "Значение за этот месяц уже существует");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка: " + ex.getMessage());
        }
        return "redirect:/admin/info-center/metric/" + id + "/values";
    }

    //вспомогательный класс-record для работы с блоками и метриками
    public record BlockWithMetrics(Block block, List<Metric> metrics) {}

    //получить форму редактирования значения показателя
    @GetMapping("/admin/info-center/metric/{metricId}/values/{valueId}/edit")
    public String editMetricValueForm(@PathVariable("metricId") Long metricId,
                                      @PathVariable("valueId") Long valueId,
                                      Model model) {
        MetricValue value = metricValueServiceInterface.getAllMetricValuesByMetricId(metricId)
                .stream()
                .filter(v -> v.getId().equals(valueId))
                .findFirst()
                .orElseThrow(() -> new InvalidIdMetricValueException(valueId));
        Metric metric = metricServiceInterface.getMetricById(metricId);
        model.addAttribute("metric", metric);
        model.addAttribute("value", value);
        return "admin/admin-metric-value-edit";
    }

    //редактировать значение метрики
    @PostMapping("/admin/info-center/metric/{metricId}/values/{valueId}/edit")
    public String editMetricValue(@PathVariable("metricId") Long metricId,
                                  @PathVariable("valueId") Long valueId,
                                  @RequestParam BigDecimal plan,
                                  @RequestParam BigDecimal fact,
                                  @RequestParam Integer year,
                                  @RequestParam Integer month,
                                  RedirectAttributes redirectAttributes) {
        try {
            metricValueServiceInterface.patchPlanFactByMetricValueIdMonthYear(
                    valueId, year, month, plan, fact);
            redirectAttributes.addFlashAttribute("success", "Значение обновлено");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка: " + ex.getMessage());
        }
        return "redirect:/admin/info-center/metric/" + metricId + "/values";
    }

    //удалить значение метрики
    @PostMapping("/admin/info-center/metric/{metricId}/values/{valueId}/delete")
    public String deleteMetricValue(@PathVariable("metricId") Long metricId,
                                    @PathVariable("valueId") Long valueId,
                                    RedirectAttributes redirectAttributes) {
        try {
            metricValueServiceInterface.deleteMetricValueById(valueId);
            redirectAttributes.addFlashAttribute("success", "Значение удалено");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка: " + ex.getMessage());
        }
        return "redirect:/admin/info-center/metric/" + metricId + "/values";
    }
}