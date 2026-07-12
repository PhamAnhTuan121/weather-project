CREATE TABLE cities (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,

                        name VARCHAR(100) NOT NULL,

                        latitude DECIMAL(10,7) NOT NULL,

                        longitude DECIMAL(10,7) NOT NULL,

                        region_id BIGINT NOT NULL,

                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                            ON UPDATE CURRENT_TIMESTAMP,

                        CONSTRAINT fk_city_region
                            FOREIGN KEY (region_id)
                                REFERENCES regions(id)
);