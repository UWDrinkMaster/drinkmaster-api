CREATE TABLE drink_ingredient (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    drink_id BIGINT,
                                    ingredient_id BIGINT,
                                    quantity DOUBLE DEFAULT 0.0,
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (drink_id) REFERENCES drink(id),
                                    FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);
