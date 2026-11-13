package org.example.diplomagradle.service.metricValue.metricDataService;

import org.example.diplomagradle.service.wagon.wagonMetric.WagonMetricServiceInterface;

import java.time.YearMonth;

//управление общей генерацией всех показателей
public class TestDataService implements TestDataServiceInterface{
    private final WagonMetricServiceInterface wagonMetricServiceInterface;
    private final MetricTestDataServiceInterface metricTestDataServiceInterface;

    public TestDataService(WagonMetricServiceInterface wagonMetricServiceInterface,
                       MetricTestDataServiceInterface metricTestDataServiceInterface){
        this.wagonMetricServiceInterface = wagonMetricServiceInterface;
        this.metricTestDataServiceInterface = metricTestDataServiceInterface;
    }

    //общий метод для генерации значений показателей
    @Override
    public void generateAll() {
        //для расчетных показателей, зависящих от вагонов - в цикле
        YearMonth start = YearMonth.of(2024, 1);
        YearMonth end = YearMonth.of(2026, 9);

        YearMonth current = start;
        while (!current.isAfter(end)) {
            wagonMetricServiceInterface.calculateAndSaveForMonth(
                    current.getYear(), current.getMonthValue());
            current = current.plusMonths(1);
        }

        //для ручных показателей - этот метод сам по себе цикл
        metricTestDataServiceInterface.generateAllManual();
    }
}
