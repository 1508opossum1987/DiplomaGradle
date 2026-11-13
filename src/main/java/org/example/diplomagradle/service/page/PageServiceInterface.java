package org.example.diplomagradle.service.page;

import org.example.diplomagradle.api.page.dto.BlockPageDto;
import org.example.diplomagradle.api.page.dto.MetricValueEditDto;
import org.example.diplomagradle.api.page.dto.MetricValuePageDto;

import java.util.List;

public interface PageServiceInterface {
    //главная
    List<BlockPageDto> getMainPageData(Integer year, Integer month);

    //страница блоков
    BlockPageDto getBlockPageData(Long blockId, Integer year, Integer month);

    //страница показателей со значениями
    List<MetricValuePageDto> getMetricValues(Long metricId);

    MetricValueEditDto getEditData(Long valueId);
}
