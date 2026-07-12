CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,

                       username VARCHAR(50) NOT NULL UNIQUE,

                       email VARCHAR(100) NOT NULL UNIQUE,

                       password VARCHAR(255) NOT NULL,

                       full_name VARCHAR(100),

                       enabled BOOLEAN NOT NULL DEFAULT TRUE,

                       role_id BIGINT NOT NULL,

                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                           ON UPDATE CURRENT_TIMESTAMP,

                       CONSTRAINT fk_user_role
                           FOREIGN KEY (role_id)
                               REFERENCES roles(id)
);