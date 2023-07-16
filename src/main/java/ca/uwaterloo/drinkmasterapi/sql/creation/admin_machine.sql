DROP TABLE IF EXISTS `admin_machine`;

CREATE TABLE `admin_machine`
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    admin_id   BIGINT,
    machine_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES `admin` (id),
    FOREIGN KEY (machine_id) REFERENCES `machine` (id)
);
