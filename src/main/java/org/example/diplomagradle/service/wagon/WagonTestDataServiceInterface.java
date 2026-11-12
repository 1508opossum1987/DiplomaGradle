package org.example.diplomagradle.service.wagon;

//интерфейс с методами заполнения таблиц t_wagons
public interface WagonTestDataServiceInterface {
    //генерация тестовых данных в разрешенный период прошлый и текущий месяц до текущей даты
    void generateForMonth(Integer year, Integer month);

    //генерация данных в период с января 2024 года по сентябрь 2026 года
    void generateHistory();
}
