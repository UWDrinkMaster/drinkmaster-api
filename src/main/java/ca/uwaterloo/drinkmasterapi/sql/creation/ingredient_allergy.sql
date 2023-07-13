CREATE TABLE ingredient_allergy (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    ingredient_id BIGINT,
                                    allergy_id BIGINT,
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (ingredient_id) REFERENCES ingredient(id),
                                    FOREIGN KEY (allergy_id) REFERENCES allergy(id)
);
