package org.example.diplomagradle.service.wagon;

import jakarta.transaction.Transactional;
import org.example.diplomagradle.datamodel.entity.wagon.Wagon;
import org.example.diplomagradle.datamodel.repository.WagonRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public class WagonService implements WagonServiceInterface {
    private final WagonRepository wagonRepository;

    private final JdbcTemplate jdbcTemplate;

    public WagonService(WagonRepository wagonRepository,
                        JdbcTemplate jdbcTemplate) {
        this.wagonRepository = wagonRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    //получить все вагоны из репозитория
    @Override
    public List<Wagon> getAllWagons() {
        return wagonRepository.findAll();
    }

    //добавить вагон
    @Override
    public void addWagon(WagonValues wagonValues) {
        if (wagonValues == null) {
            throw new InvalidWagonValuesException(null, "Invalid WagonValues");
        }

        if (wagonRepository.existsByNumber(wagonValues.number())) {
            throw new DuplicateWagonNumberException(wagonValues.number());
        }

        if (wagonValues.departureAt() != null) {
            LocalDate d = wagonValues.departureAt().toLocalDate();
            LocalDate today = LocalDate.now();

            if (!d.isBefore(today)) {
                throw new InvalidWagonPeriodException("departureAt must be before today: " + d);
            }

            YearMonth current = YearMonth.now();
            YearMonth target = YearMonth.from(d);
            if (!target.equals(current) && !target.equals(current.minusMonths(1))) {
                throw new InvalidWagonPeriodException("only current or previous month allowed: " + target);
            }

            ensurePartition(target);
        }

        Wagon wagon = new Wagon();
        wagon.setNumber(wagonValues.number());
        wagon.setCategory(wagonValues.category());
        wagon.setLastOperation(wagonValues.lastOperation());
        wagon.setLastOperationAt(wagonValues.lastOperationAt());
        wagon.setArrivalAt(wagonValues.arrivalAt());
        wagon.setDepartureAt(wagonValues.departureAt());
        wagon.setLoading(wagonValues.loading());

        wagonRepository.save(wagon);
    }

    //внутренний метод для проверки наличия и/или создания новых частей базы данных вагонов
    private void ensurePartition(YearMonth yearMonth) {
        //формирование наименования части таблицы определенным образом из года и месяца
        String partitionName = String.format("t_wagon_%04d_%02d",
                yearMonth.getYear(), yearMonth.getMonthValue());

        //проверка наличия такой записи в наименовании части таблицы
        //через функцию postgreSQL
        Boolean exists = jdbcTemplate.queryForObject(
                "SELECT to_regclass(?) IS NOT NULL",
                Boolean.class,
                partitionName);

        //если есть - выход
        if (Boolean.TRUE.equals(exists)) {
            return;
        }

        //задание дат начали и конца для проверки
        //для той же логики - возможности редактирования данных за
        //текущий или предыдущий месяц
        String start = yearMonth.atDay(1).toString();
        String end = yearMonth.plusMonths(1).atDay(1).toString();

        //формирование строки скрипта в SQL-запросе
        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s PARTITION OF t_wagon FOR VALUES FROM ('%s') TO ('%s')",
                partitionName, start, end);

        //передача sql-запроса через Bean
        jdbcTemplate.execute(sql);
    }

    //получить вагон по id
    @Override
    public Wagon getWagonById(Long wagonId) {
        if (wagonId == null || wagonId <= 0) {
            throw new InvalidWagonIdException(wagonId);
        }
        Optional<Wagon> wagon = wagonRepository.findById(wagonId);
        if (wagon.isEmpty()) {
            throw new InvalidWagonIdException(wagonId);
        }
        return wagon.get();
    }

    //получить вагон по номеру
    @Override
    public Wagon getWagonByNumber(Long wagonNumber) {
        if (wagonNumber == null || wagonNumber < 20000000 || wagonNumber > 99999999) {
            throw new InvalidWagonNumberException(wagonNumber);
        }
        Optional<Wagon> wagon = wagonRepository.findByNumber(wagonNumber);
        if (wagon.isEmpty()) {
            throw new InvalidWagonNumberException(wagonNumber);
        }
        return wagon.get();
    }

    //получить список вагонов за месяц
    @Override
    public List<Wagon> getWagonsByMonth(Integer year, Integer month) {
        if (year == null || year < 2024) {
            throw new InvalidWagonPeriodException("This year: " + year + "isn't correct");
        }
        if (month == null || month < 1 || month > 12) {
            throw new InvalidWagonPeriodException("This month: " + month + "isn't correct");
        }

        YearMonth ym = YearMonth.of(year, month);

        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.plusMonths(1).atDay(1).atStartOfDay();

        List<Wagon> wagons = wagonRepository.findByDepartureAtBetween(start, end);

        if (wagons.isEmpty()) {
            throw new EmptyWagonsException();
        }

        return wagons;
    }

    //удалить вагон по номеру
    @Transactional
    @Override
    public void deleteWagonByNumber(Long wagonNumber) {
        if (wagonNumber == null || wagonNumber < 20000000 || wagonNumber > 99999999) {
            throw new InvalidWagonNumberException(wagonNumber);
        }

        wagonRepository.deleteByNumber(wagonNumber);
    }

    //удалить вагон по id
    @Transactional
    @Override
    public void deleteWagonById(Long wagonId) {
        if (wagonId == null || wagonId <= 0) {
            throw new InvalidWagonIdException(wagonId);
        }

        wagonRepository.deleteById(wagonId);
    }
}
