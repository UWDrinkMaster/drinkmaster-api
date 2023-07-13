CREATE TABLE admin (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      machine_id BIGINT,
                      isEnabled BOOLEAN DEFAULT true,
                      imageURL VARCHAR(255),
                      signedInAt TIMESTAMP,
                      createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (machine_id) REFERENCES machine(id)
);