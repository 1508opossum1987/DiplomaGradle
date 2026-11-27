package org.example.diplomagradle.service.metric;

import org.example.diplomagradle.datamodel.entity.Block;
import org.example.diplomagradle.datamodel.entity.Metric;
import org.example.diplomagradle.datamodel.entity.MetricsType;
import org.example.diplomagradle.datamodel.repository.MetricRepository;
import org.example.diplomagradle.datamodel.repository.MetricValueRepository;
import org.example.diplomagradle.service.block.BlockServiceInterface;
import org.example.diplomagradle.service.block.InvalidIdBlockException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MetricServiceTest {
    private MetricRepository metricRepositoryMock;
    private BlockServiceInterface blockServiceInterfaceMock;
    private MetricValueRepository metricValueRepositoryMock;
    private MetricService metricServiceMock;

    @BeforeEach
    void setUp() {
        metricRepositoryMock = Mockito.mock(MetricRepository.class, invocation -> {
            String invocationError = String.format(
                    "method: %s, arguments: %s",
                    invocation.getMethod().getName(),
                    Arrays.toString(invocation.getArguments())
            );
            throw new UnsupportedOperationException(invocationError);
        });

        blockServiceInterfaceMock = Mockito.mock(BlockServiceInterface.class, invocation -> {
            String invocationError = String.format(
                    "method: %s, arguments: %s",
                    invocation.getMethod().getName(),
                    Arrays.toString(invocation.getArguments())
            );
            throw new UnsupportedOperationException(invocationError);
        });

        metricValueRepositoryMock = Mockito.mock(MetricValueRepository.class, invocation -> {
            String invocationError = String.format(
                    "method: %s, arguments: %s",
                    invocation.getMethod().getName(),
                    Arrays.toString(invocation.getArguments())
            );
            throw new UnsupportedOperationException(invocationError);
        });

        metricServiceMock = new MetricService(metricRepositoryMock, blockServiceInterfaceMock, metricValueRepositoryMock);
    }

    //удачное добавление метрик
    @Test
    void addMetricSuccess() {
        MetricValues values = new MetricValues("Травматизм", "случай", MetricsType.MANUAL, 1L, null);
        Mockito.doReturn(Optional.empty())
                .when(metricRepositoryMock)
                .findByMetricName("Травматизм");
        Block block = new Block(1L);
        Mockito.doReturn(block)
                .when(blockServiceInterfaceMock)
                .getBlockById(1L);
        Mockito.doReturn(new Metric())
                .when(metricRepositoryMock)
                .save(Mockito.any(Metric.class));
        metricServiceMock.addMetric(values);
        ArgumentCaptor<Metric> captor = ArgumentCaptor.forClass(Metric.class);
        Mockito.verify(metricRepositoryMock).save(captor.capture());
        Metric saved = captor.getValue();
        assertEquals("Травматизм", saved.getMetricName());
        assertEquals("случай", saved.getUnit());
        assertEquals(MetricsType.MANUAL, saved.getType());
        assertSame(block, saved.getBlock());
    }

    //попытка передачи значений null
    @Test
    void addMetricNullValues() {
        assertThrows(
                InvalidMetricValuesException.class,
                () -> metricServiceMock.addMetric(null)
        );
    }

    //попытка передать имя дубликат
    @Test
    void MetricSameName() {
        MetricValues values = new MetricValues("Травматизм", "случай", MetricsType.MANUAL, 1L, null);
        Mockito.doReturn(Optional.of(new Metric()))
                .when(metricRepositoryMock)
                .findByMetricName("Травматизм");
        DuplicateNameMetricException actualException = assertThrows(
                DuplicateNameMetricException.class,
                () -> metricServiceMock.addMetric(values)
        );
        Mockito.verify(metricRepositoryMock, Mockito.never()).save(Mockito.any(Metric.class));
        assertEquals("This name: Травматизм is already exists, please choice other name",
                actualException.getMessage());
    }

    //попытка передать по несуществующему блоку
    @Test
    void addMetricBlockNotFound() {
        MetricValues values = new MetricValues("Травматизм", "случай", MetricsType.MANUAL, 1L, null);
        Mockito.doReturn(Optional.empty())
                .when(metricRepositoryMock)
                .findByMetricName("Травматизм");
        Mockito.doThrow(new InvalidIdBlockException(1L))
                .when(blockServiceInterfaceMock)
                .getBlockById(1L);
        InvalidIdBlockException actualException = assertThrows(
                InvalidIdBlockException.class,
                () -> metricServiceMock.addMetric(values)
        );
        Mockito.verify(metricRepositoryMock, Mockito.never()).save(Mockito.any(Metric.class));
        assertEquals("This id: 1 isn't found", actualException.getMessage());
    }

    @Test
    void getAllMetrics() {
    }

    @Test
    void getMetricById() {
    }

    @Test
    void getMetricByName() {
    }

    @Test
    void deleteMetricById() {
    }

    @Test
    void deleteAllMetrics() {
    }

    @Test
    void patchMetricNameByName() {
    }

    @Test
    void patchMetricNameById() {
    }

    @Test
    void patchMetricUnitByName() {
    }

    @Test
    void patchMetricUnitById() {
    }

    @Test
    void getMetricByTitleBlock() {
    }

    @Test
    void patchMetricMinMaxById() {
    }
}