-- Доступ ролей к изменению показателей
-- ADMIN обходит проверку в коде. DIRECTOR в таблицу не вносится (только просмотр).

CREATE TABLE t_metric_access
(
    f_id BIGSERIAL PRIMARY KEY,
    f_role_id BIGINT NOT NULL,
    f_metric_id BIGINT NOT NULL,
    CONSTRAINT fk_metric_access_role FOREIGN KEY (f_role_id) REFERENCES t_role (id),
    CONSTRAINT fk_metric_access_metric FOREIGN KEY (f_metric_id) REFERENCES t_metric (id),
    CONSTRAINT uq_metric_access_role_metric UNIQUE (f_role_id, f_metric_id)
);

CREATE INDEX idx_metric_access_role ON t_metric_access (f_role_id);
CREATE INDEX idx_metric_access_metric ON t_metric_access (f_metric_id);

-- HEALTH_ENG
INSERT INTO t_metric_access (f_role_id, f_metric_id)
SELECT r.id, m.id
FROM t_role r
         CROSS JOIN t_metric m
WHERE r.f_name = 'HEALTH_ENG'
  AND m.f_metric_name IN ('Травматизм', 'Замечания по ОТ');

-- SAFETY_ENG
INSERT INTO t_metric_access (f_role_id, f_metric_id)
SELECT r.id, m.id
FROM t_role r
         CROSS JOIN t_metric m
WHERE r.f_name = 'SAFETY_ENG'
  AND m.f_metric_name IN ('Браки по БД', 'Отказы тех средств');

-- ZAM_BD
INSERT INTO t_metric_access (f_role_id, f_metric_id)
SELECT r.id, m.id
FROM t_role r
         CROSS JOIN t_metric m
WHERE r.f_name = 'ZAM_BD'
  AND m.f_metric_name IN ('Травматизм', 'Замечания по ОТ', 'Браки по БД', 'Отказы тех средств');

-- ZAM_OPER
INSERT INTO t_metric_access (f_role_id, f_metric_id)
SELECT r.id, m.id
FROM t_role r
         CROSS JOIN t_metric m
WHERE r.f_name = 'ZAM_OPER'
  AND m.f_metric_name IN ('Процент выполнения графика', 'Рабочий парк вагонов',
                          'Отправлено грузовых вагонов', 'Расформировано');

-- ECONOMIST
INSERT INTO t_metric_access (f_role_id, f_metric_id)
SELECT r.id, m.id FROM t_role r CROSS JOIN t_metric m
WHERE r.f_name = 'ECONOMIST'
  AND m.f_metric_name IN ('Простой транзитных вагонов с/п', 'Простой транзитных вагонов б/п',
                          'Доходы', 'Запасы ТМЦ',
                          'Производительность труда', 'Расходы', 'Себестоимость',
                          'Прибыль', 'Темп роста производительности труда');

-- NORM_ENG
INSERT INTO t_metric_access (f_role_id, f_metric_id)
SELECT r.id, m.id
FROM t_role r
         CROSS JOIN t_metric m
WHERE r.f_name = 'NORM_ENG'
  AND m.f_metric_name IN ('Фонд оплаты труда', 'Средняя зарплата', 'Темп роста зарплаты');

-- ZAM_HR
INSERT INTO t_metric_access (f_role_id, f_metric_id)
SELECT r.id, m.id
FROM t_role r
         CROSS JOIN t_metric m
WHERE r.f_name = 'ZAM_HR'
  AND m.f_metric_name IN ('Численность работников', 'Текучесть кадров');