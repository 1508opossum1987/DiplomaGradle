package org.example.diplomagradle.service.metricValue.metricDataService;

public interface MetricTestDataServiceInterface {
    //помесячная генерация показателей
    public void generateForMonth(Integer year, Integer month);
    //генерация ручных показателей
    public void generateAllManual();
}
