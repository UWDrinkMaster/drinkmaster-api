DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username                 VARCHAR(255) NOT NULL,
    phone_number             VARCHAR(255) UNIQUE,
    email                    VARCHAR(255) UNIQUE,
    password                 VARCHAR(255),
    is_enabled               BOOLEAN            DEFAULT true COMMENT 'Flag indicating if the user account is enabled',
    image_url                VARCHAR(255) COMMENT 'URL of the profile picture of the user',
    date_of_birth            DATE               DEFAULT NULL,
    last_sobriety_test_score DOUBLE             DEFAULT 0.0,
    last_sobriety_test_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    signed_in_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
