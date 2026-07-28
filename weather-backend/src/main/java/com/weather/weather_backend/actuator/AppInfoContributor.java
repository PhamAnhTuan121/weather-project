package com.weather.weather_backend.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class AppInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {

        builder.withDetail(
                "app",
                java.util.Map.of(
                        "name", "Weather Backend",
                        "version", "1.0.0",
                        "description", "Weather Monitoring Backend API",
                        "java", System.getProperty("java.version")
                )
        );
    }

}