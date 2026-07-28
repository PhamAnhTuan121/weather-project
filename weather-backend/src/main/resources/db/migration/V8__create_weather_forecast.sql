CREATE TABLE weather_forecasts (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,

                                   city_id BIGINT NOT NULL,
                                   station_id BIGINT NOT NULL,

                                   forecast_time DATETIME NOT NULL,

                                   temperature DECIMAL(5,2) NOT NULL,
                                   feels_like DECIMAL(5,2),

                                   humidity INT,
                                   pressure INT,

                                   wind_speed DECIMAL(5,2),
                                   wind_direction VARCHAR(30),

                                   cloud INT,

                                   description VARCHAR(255),
                                   icon VARCHAR(30),
                                   visibility INTEGER,
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                       ON UPDATE CURRENT_TIMESTAMP,

                                   CONSTRAINT fk_forecast_city
                                       FOREIGN KEY(city_id)
                                           REFERENCES cities(id),

                                   CONSTRAINT fk_forecast_station
                                       FOREIGN KEY(station_id)
                                           REFERENCES weather_stations(id),

                                   INDEX idx_forecast_station_time(station_id, forecast_time),

                                   INDEX idx_forecast_city_time(city_id, forecast_time)
);