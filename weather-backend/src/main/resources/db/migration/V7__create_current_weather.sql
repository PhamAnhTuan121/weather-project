CREATE TABLE current_weather
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    city_id BIGINT NOT NULL,

    station_id BIGINT NOT NULL,

    temperature DECIMAL(5,2) NOT NULL,

    humidity INT NOT NULL,

    pressure INT NOT NULL,

    wind_speed DECIMAL(5,2),

    wind_direction VARCHAR(30),

    visibility INT,

    cloud INT,

    uv_index DECIMAL(4,2),

    description VARCHAR(255),

    icon VARCHAR(30),

    observation_time DATETIME NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_current_weather_city
        FOREIGN KEY(city_id)
            REFERENCES cities(id),

    CONSTRAINT fk_current_weather_station
        FOREIGN KEY(station_id)
            REFERENCES weather_stations(id)
);

ALTER TABLE current_weather
    ADD COLUMN feels_like DECIMAL(5,2);