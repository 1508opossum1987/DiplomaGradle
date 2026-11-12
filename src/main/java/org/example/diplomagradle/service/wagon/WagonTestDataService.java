package org.example.diplomagradle.service.wagon;

import org.example.diplomagradle.datamodel.entity.wagon.LoadingType;
import org.example.diplomagradle.datamodel.entity.wagon.Wagon;
import org.example.diplomagradle.datamodel.entity.wagon.WagonCategory;
import org.example.diplomagradle.datamodel.entity.wagon.WagonOperationType;
import org.example.diplomagradle.datamodel.repository.WagonRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;

public class WagonTestDataService implements WagonTestDataServiceInterface{
    private final WagonRepository wagonRepository;

    private final JdbcTemplate jdbcTemplate;

    private final Random random = new Random();

    public WagonTestDataService (WagonRepository wagonRepository,
                                 JdbcTemplate jdbcTemplate){
        this.wagonRepository = wagonRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    //генерация за разрешенный месяц (текущий до вчера или предыдущий)
    @Override
    public void generateForMonth(Integer year, Integer month) {
        if (year == null || year < 2024) {
            throw new InvalidWagonPeriodException("This year: " + year + " isn't correct");
        }
        if (month == null || month < 1 || month > 12) {
            throw new InvalidWagonPeriodException("This month: " + month + " isn't correct");
        }

        YearMonth ym = YearMonth.of(year, month);
        YearMonth current = YearMonth.now();
        if (!ym.equals(current) && !ym.equals(current.minusMonths(1))) {
            throw new InvalidWagonPeriodException("only current or previous month allowed: " + ym);
        }

        LocalDate today = LocalDate.now();
        int daysInMonth = ym.lengthOfMonth();

        //деление на 5000 записей в БД
        List<Wagon> batch = new ArrayList<>(5000);

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = ym.atDay(day);
            if (!date.isBefore(today)) {
                break;
            }
            int wagonsPerDay = 4000 + random.nextInt(1501);
            for (int i = 0; i < wagonsPerDay; i++) {
                WagonValues values = randomWagonValues(ym, date);
                batch.add(buildWagon(values));
                if (batch.size() >= 5000) {
                    flushBatch(batch);
                }
            }
        }

        if (!batch.isEmpty()) {
            flushBatch(batch);
        }
    }

    //генерация истории 2024-01 по 2026-09
    @Override
    public void generateHistory() {
        YearMonth start = YearMonth.of(2024, 1);
        YearMonth end = YearMonth.of(2026, 9);

        YearMonth current = start;
        while (!current.isAfter(end)) {
            int daysInMonth = current.lengthOfMonth();
            List<Wagon> batch = new ArrayList<>(5000);

            for (int day = 1; day <= daysInMonth; day++) {
                LocalDate date = current.atDay(day);
                int wagonsPerDay = 4000 + random.nextInt(1501);
                for (int i = 0; i < wagonsPerDay; i++) {
                    WagonValues values = randomWagonValues(current, date);
                    batch.add(buildWagon(values));
                    if (batch.size() >= 5000) {
                        flushBatch(batch);
                    }
                }
            }

            if (!batch.isEmpty()) {
                flushBatch(batch);
            }

            current = current.plusMonths(1);
        }
    }

    //создание частей БД на все месяцы отправления и сохранение в БД
    private void flushBatch(List<Wagon> batch) {
        Set<YearMonth> departureMonths = new HashSet<>();
        for (Wagon wagon : batch) {
            if (wagon.getDepartureAt() != null) {
                departureMonths.add(YearMonth.from(wagon.getDepartureAt()));
            }
        }
        for (YearMonth m : departureMonths) {
            ensurePartition(m);
        }
        wagonRepository.saveAll(batch);
        batch.clear();
    }

    //генерация случайных значений вагона за конкретный день месяца
    private WagonValues randomWagonValues(YearMonth ym, LocalDate day) {
        Long number = 20000000L + random.nextInt(80000000);

        WagonCategory category = WagonCategory.values()[random.nextInt(WagonCategory.values().length)];

        LocalTime arrivalTime = LocalTime.of(random.nextInt(24), random.nextInt(60));
        LocalDateTime arrivalAt = LocalDateTime.of(day, arrivalTime);

        LoadingType loading;
        if (category == WagonCategory.LOCAL) {
            loading = LoadingType.values()[random.nextInt(LoadingType.values().length)];
        } else {
            loading = LoadingType.NONE;//у неместных не может быть иного значения
        }

        boolean departed = random.nextInt(100) < 99; //99 % вагонов отправляются
        LocalDateTime departureAt = null;
        LocalDateTime lastOperationAt;
        WagonOperationType lastOperation;

        if (departed) {
            boolean sameDay = random.nextInt(100) < 90; //90% отправляются в эти же сутки

            LocalDateTime departureCandidate;
            if (sameDay) {
                departureCandidate = arrivalAt.plusMinutes(1 + random.nextInt(180));//+180 минут от прибытия задел
            } else {
                int addDays = 1 + random.nextInt(3);
                LocalDate departureDate = day.plusDays(addDays);
                LocalTime departureTime = LocalTime.of(random.nextInt(24), random.nextInt(60));
                departureCandidate = LocalDateTime.of(departureDate, departureTime);
                if (!departureCandidate.isAfter(arrivalAt)) {
                    departureCandidate = arrivalAt.plusMinutes(1);
                }
            }

            departureAt = departureCandidate;
            lastOperationAt = departureAt;
            lastOperation = WagonOperationType.DEPARTURE;
        } else {
            lastOperation = WagonOperationType.values()[random.nextInt(WagonOperationType.values().length)];
            if (lastOperation == WagonOperationType.DEPARTURE) {
                lastOperation = WagonOperationType.PROCESSING;
            }
            LocalTime opTime = LocalTime.of(random.nextInt(24), random.nextInt(60));
            lastOperationAt = LocalDateTime.of(day, opTime);
            if (lastOperationAt.isBefore(arrivalAt)) {
                lastOperationAt = arrivalAt;
            }
        }

        return new WagonValues(number, category, lastOperation, lastOperationAt,
                arrivalAt, departureAt, loading);
    }

    //создание сущности Wagon из значений
    private Wagon buildWagon(WagonValues values) {
        Wagon wagon = new Wagon();
        wagon.setNumber(values.number());
        wagon.setCategory(values.category());
        wagon.setLastOperation(values.lastOperation());
        wagon.setLastOperationAt(values.lastOperationAt());
        wagon.setArrivalAt(values.arrivalAt());
        wagon.setDepartureAt(values.departureAt());
        wagon.setLoading(values.loading());
        return wagon;
    }

    //внутренний метод дял проверки создания части БД на нужный месяц
    //аналог метода в WagonService
    private void ensurePartition(YearMonth yearMonth) {
        String partitionName = String.format("t_wagon_%04d_%02d",
                yearMonth.getYear(), yearMonth.getMonthValue());

        Boolean exists = jdbcTemplate.queryForObject(
                "SELECT to_regclass(?) IS NOT NULL",
                Boolean.class,
                partitionName);

        if (Boolean.TRUE.equals(exists)) {
            return;
        }

        String start = yearMonth.atDay(1).toString();
        String end = yearMonth.plusMonths(1).atDay(1).toString();

        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s PARTITION OF t_wagon FOR VALUES FROM ('%s') TO ('%s')",
                partitionName, start, end);

        jdbcTemplate.execute(sql);
    }
}
