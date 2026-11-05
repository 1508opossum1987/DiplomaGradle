ALTER TABLE t_metric ADD COLUMN f_min_value NUMERIC(10,1);
ALTER TABLE t_metric ADD COLUMN f_max_value NUMERIC(10,1);

UPDATE t_metric SET f_min_value = 0.0,   f_max_value = 2.0  WHERE f_metric_name = 'Травматизм';
UPDATE t_metric SET f_min_value = 0.0,   f_max_value = 20.0  WHERE f_metric_name = 'Замечания по ОТ';
UPDATE t_metric SET f_min_value = 0.0,   f_max_value = 5.0  WHERE f_metric_name = 'Браки по БД';
UPDATE t_metric SET f_min_value = 0.0,   f_max_value = 5.0  WHERE f_metric_name = 'Отказы тех средств';
UPDATE t_metric SET f_min_value = 1.0,   f_max_value = null  WHERE f_metric_name = 'Простой транзитных вагонов с/п';
UPDATE t_metric SET f_min_value = 1.0,   f_max_value = null  WHERE f_metric_name = 'Простой транзитных вагонов б/п';
UPDATE t_metric SET f_min_value = 1.0,   f_max_value = null  WHERE f_metric_name = 'Простой местных вагонов';
UPDATE t_metric SET f_min_value = 50.0,   f_max_value = 100.0  WHERE f_metric_name = 'Процент выполнения графика';
UPDATE t_metric SET f_min_value = 100.0,   f_max_value = 5000.0  WHERE f_metric_name = 'Рабочий парк вагонов';
UPDATE t_metric SET f_min_value = 100000.0,   f_max_value = 250000.0  WHERE f_metric_name = 'Отправлено грузовых вагонов';
UPDATE t_metric SET f_min_value = 70000.0,   f_max_value = 120000.0  WHERE f_metric_name = 'Расформировано';
UPDATE t_metric SET f_min_value = 5000000.0,   f_max_value = 30000000.0  WHERE f_metric_name = 'Доходы';
UPDATE t_metric SET f_min_value = 0.0,   f_max_value = null  WHERE f_metric_name = 'Погрузка';
UPDATE t_metric SET f_min_value = 0.0,   f_max_value = null  WHERE f_metric_name = 'Выгрузка';
UPDATE t_metric SET f_min_value = 500000.0,   f_max_value = 1000000.0  WHERE f_metric_name = 'Запасы ТМЦ';
UPDATE t_metric SET f_min_value = 50.0,   f_max_value = 400.0  WHERE f_metric_name = 'Производительность труда';
UPDATE t_metric SET f_min_value = 24000000.0,   f_max_value = 30000000.0  WHERE f_metric_name = 'Фонд оплаты труда';
UPDATE t_metric SET f_min_value = 5000000.0,   f_max_value = 30000000.0  WHERE f_metric_name = 'Расходы';
UPDATE t_metric SET f_min_value = 5000000.0,   f_max_value = 30000000.0  WHERE f_metric_name = 'Себестоимость';
UPDATE t_metric SET f_min_value = -1000000.0,   f_max_value = 3000000.0  WHERE f_metric_name = 'Прибыль';
UPDATE t_metric SET f_min_value = -20.0,   f_max_value = 20.0  WHERE f_metric_name = 'Темп роста производительности труда';
UPDATE t_metric SET f_min_value = 280.0,   f_max_value = 330.0  WHERE f_metric_name = 'Численность работников';
UPDATE t_metric SET f_min_value = 70000.0,   f_max_value = 120000.0  WHERE f_metric_name = 'Средняя зарплата';
UPDATE t_metric SET f_min_value = -20.0,   f_max_value = 20.0  WHERE f_metric_name = 'Темп роста зарплаты';
UPDATE t_metric SET f_min_value = -10.0, f_max_value = 10.0 WHERE f_metric_name = 'Текучесть кадров';

