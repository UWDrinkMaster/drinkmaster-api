CREATE TABLE `drink_ingredient` (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    drinkId BIGINT,
                                    ingredientId BIGINT,
                                    quantity DOUBLE DEFAULT 0.0,
                                    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (drinkId) REFERENCES `drink` (id),
                                    FOREIGN KEY (ingredientId) REFERENCES `ingredient` (id)
);
