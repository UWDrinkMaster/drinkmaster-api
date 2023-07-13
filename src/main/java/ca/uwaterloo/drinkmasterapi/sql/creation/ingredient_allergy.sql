CREATE TABLE `ingredient_allergy` (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    ingredientId BIGINT,
                                    allergyId BIGINT,
                                    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (ingredientId) REFERENCES `ingredient` (id),
                                    FOREIGN KEY (allergyId) REFERENCES `allergy` (id)
);
