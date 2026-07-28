CREATE TABLE weather_history
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    city_id BIGINT NOT NULL,

    station_id BIGINT NOT NULL,

    observation_time DATETIME NOT NULL,

    temperature DECIMAL(5,2),

    feels_like DECIMAL(5,2),

    humidity INT,

    pressure INT,

    wind_speed DECIMAL(5,2),

    wind_direction VARCHAR(30),

    visibility INT,

    cloud INT,

    uv_index DECIMAL(5,2),

    description VARCHAR(255),

    icon VARCHAR(30),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_history_city
        FOREIGN KEY(city_id)
            REFERENCES cities(id),

    CONSTRAINT fk_history_station
        FOREIGN KEY(station_id)
            REFERENCES weather_stations(id)
);