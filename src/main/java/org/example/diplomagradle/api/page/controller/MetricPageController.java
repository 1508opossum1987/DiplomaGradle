package org.example.diplomagradle.api.page.controller;

import org.example.diplomagradle.api.page.dto.MetricValueEditDto;
import org.example.diplomagradle.api.page.dto.MetricValuePageDto;
import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.service.metric.MetricServiceInterface;
import org.example.diplomagradle.service.metricValue.MetricValueServiceInterface;
import org.example.diplomagradle.service.page.PageServiceInterface;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class MetricPageController {
    private final PageServiceInterface pageServiceInterface;

    private final MetricServiceInterface metricServiceInterface;

    private final MetricValueServiceInterface metricValueServiceInterface;

    public MetricPageController(PageServiceInterface pageServiceInterface,
                                MetricServiceInterface metricServiceInterface,
                                MetricValueServiceInterface metricValueServiceInterface) {
        this.pageServiceInterface = pageServiceInterface;
        this.metricServiceInterface = metricServiceInterface;
        this.metricValueServiceInterface = metricValueServiceInterface;
    }

    @GetMapping("/metric/{metricId}")
    @PreAuthorize("@accessChecker.canViewMetric(authentication, #metricId)")
    public String metricPage(@PathVariable Long metricId, Model model) {
        List<MetricValuePageDto> values = pageServiceInterface.getMetricValues(metricId);

        Metric metric = metricServiceInterface.getMetricById(metricId);
        model.addAttribute("values", values);
        model.addAttribute("metricId", metricId);
        model.addAttribute("metric", metric);

        return "metric";
    }

    @GetMapping("/metric/{metricId}/edit/{valueId}")
    @PreAuthorize("@accessChecker.canEditByMetricValueId(authentication, #valueId)")
    public String editMetricValuePage(@PathVariable Long metricId,
                                      @PathVariable Long valueId,
                                      Model model) {
        MetricValueEditDto value = pageServiceInterface.getEditData(valueId);

        if (!value.metricId().equals(metricId)) {
            throw new RuntimeException("MetricValue " + valueId + " does not belong to metric " + metricId);
        }

        Metric metric = metricServiceInterface.getMetricById(metricId);

        model.addAttribute("value", value);
        model.addAttribute("metricName", metric.getMetricName());
        model.addAttribute("unit", metric.getUnit());

        return "metric-edit";
    }

    @PostMapping("/metric/{metricId}/edit/{valueId}")
    public String editMetricValue(@PathVariable Long metricId,
                                  @PathVariable Long valueId,
                                  @RequestParam BigDecimal plan,
                                  @RequestParam BigDecimal fact,
                                  @RequestParam Integer year,
                                  @RequestParam Integer month) {
        metricValueServiceInterface.patchPlanFactByMetricValueIdMonthYear(valueId, year, month, plan, fact);
        return "redirect:/metric/" + metricId;
    }

}
