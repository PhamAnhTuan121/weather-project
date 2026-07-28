CREATE TABLE weather_stations (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,

                                  station_code VARCHAR(50) NOT NULL UNIQUE,

                                  station_name VARCHAR(255) NOT NULL,

                                  provider VARCHAR(50) NOT NULL,

                                  city_id BIGINT NOT NULL,

                                  latitude DECIMAL(10,7),

                                  longitude DECIMAL(10,7),

                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                      ON UPDATE CURRENT_TIMESTAMP,
                                  status ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
                                  CONSTRAINT fk_station_city
                                      FOREIGN KEY (city_id)
                                          REFERENCES cities(id)
);