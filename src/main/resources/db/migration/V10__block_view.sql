-- Доступ ролей (переход со страницы /info-center в блок), здесь просмотр
-- ADMIN обходит проверку в коде

CREATE TABLE t_block_view
(
    f_id       BIGSERIAL PRIMARY KEY,
    f_role_id  BIGINT NOT NULL,
    f_block_id BIGINT NOT NULL,
    CONSTRAINT fk_block_view_role FOREIGN KEY (f_role_id) REFERENCES t_role (id),
    CONSTRAINT fk_block_view_block FOREIGN KEY (f_block_id) REFERENCES t_block (id),
    CONSTRAINT uq_block_view_role_block UNIQUE (f_role_id, f_block_id)
);

CREATE INDEX idx_block_view_role ON t_block_view (f_role_id);
CREATE INDEX idx_block_view_block ON t_block_view (f_block_id);

-- DIRECTOR
INSERT INTO t_block_view (f_role_id, f_block_id)
SELECT r.id, b.id
FROM t_role r
         CROSS JOIN t_block b
WHERE r.f_name = 'DIRECTOR';

-- HEALTH_ENG
INSERT INTO t_block_view (f_role_id, f_block_id)
SELECT r.id, b.id
FROM t_role r
         CROSS JOIN t_block b
WHERE r.f_name = 'HEALTH_ENG'
  AND b.f_title = 'Безопасность';

-- SAFETY_ENG
INSERT INTO t_block_view (f_role_id, f_block_id)
SELECT r.id, b.id
FROM t_role r
         CROSS JOIN t_block b
WHERE r.f_name = 'SAFETY_ENG'
  AND b.f_title = 'Безопасность';

-- ZAM_BD
INSERT INTO t_block_view (f_role_id, f_block_id)
SELECT r.id, b.id
FROM t_role r
         CROSS JOIN t_block b
WHERE r.f_name = 'ZAM_BD'
  AND b.f_title = 'Безопасность';

-- ZAM_OPER
INSERT INTO t_block_view (f_role_id, f_block_id)
SELECT r.id, b.id
FROM t_role r
         CROSS JOIN t_block b
WHERE r.f_name = 'ZAM_OPER'
  AND b.f_title IN ('Качество', 'Производство');

-- ECONOMIST
INSERT INTO t_block_view (f_role_id, f_block_id)
SELECT r.id, b.id
FROM t_role r
         CROSS JOIN t_block b
WHERE r.f_name = 'ECONOMIST'
  AND b.f_title IN ('Качество', 'Производство', 'Экономика');

-- NORM_ENG
INSERT INTO t_block_view (f_role_id, f_block_id)
SELECT r.id, b.id
FROM t_role r
         CROSS JOIN t_block b
WHERE r.f_name = 'NORM_ENG'
  AND b.f_title IN ('Экономика', 'Корпоративная культура');

-- ZAM_HR
INSERT INTO t_block_view (f_role_id, f_block_id)
SELECT r.id, b.id
FROM t_role r
         CROSS JOIN t_block b
WHERE r.f_name = 'ZAM_HR'
  AND b.f_title = 'Корпоративная культура';