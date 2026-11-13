UPDATE t_metric SET f_min_value = 1, f_max_value = 100 WHERE f_metric_name = 'Простой местных вагонов';
UPDATE t_metric SET f_min_value = 30, f_max_value = 50 WHERE f_metric_name = 'Погрузка';
UPDATE t_metric SET f_min_value = 30, f_max_value = 50 WHERE f_metric_name = 'Выгрузка';
UPDATE t_metric SET f_min_value = 1.0, f_max_value = 10.0 WHERE f_metric_name = 'Простой транзитных вагонов с/п';
UPDATE t_metric SET f_min_value = 1.0, f_max_value = 5.0 WHERE f_metric_name = 'Простой транзитных вагонов б/п';