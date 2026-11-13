package org.example.diplomagradle.api.metricTestDataApi;

import org.example.diplomagradle.api.messages.StringMessage;
import org.example.diplomagradle.service.metricValue.metricDataService.MetricTestDataServiceInterface;
import org.example.diplomagradle.service.metricValue.metricDataService.TestDataServiceInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/metric-test-data")
public class MetricTestDataApiController {
    private final TestDataServiceInterface testDataServiceInterface;

    private final MetricTestDataServiceInterface metricTestDataServiceInterface;


    public MetricTestDataApiController(TestDataServiceInterface testDataServiceInterface,
                                       MetricTestDataServiceInterface metricTestDataServiceInterface) {
        this.testDataServiceInterface = testDataServiceInterface;
        this.metricTestDataServiceInterface = metricTestDataServiceInterface;
    }

    //сгенерировать основную массу показателей
    @PostMapping("/generate-all")
    public StringMessage generateAllApi() {
        testDataServiceInterface.generateAll();
        return new StringMessage("Success");
    }

    @PostMapping("/generate")
    public StringMessage generateForMonthApi(@RequestParam Integer year, @RequestParam Integer month) {
        metricTestDataServiceInterface.generateForMonth(year, month);
        return new StringMessage("Success");
    }
}
