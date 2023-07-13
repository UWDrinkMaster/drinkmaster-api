CREATE TABLE `user_allergy`
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId     BIGINT,
    allergyId  BIGINT,
    createdAt  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES `user` (id),
    FOREIGN KEY (allergyId) REFERENCES `allergy` (id)
);
