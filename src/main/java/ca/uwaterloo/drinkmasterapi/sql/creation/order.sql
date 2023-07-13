CREATE TABLE `order` (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         machineId BIGINT NOT NULL,
                         userId BIGINT NOT NULL,
                         drinkId BIGINT NOT NULL,
                         quantity INT NOT NULL,
                         status VARCHAR(255) NOT NULL,
                         createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (machineId) REFERENCES `machine` (id),
                         FOREIGN KEY (userId) REFERENCES `user` (id),
                         FOREIGN KEY (drinkId) REFERENCES `drink` (id)
);