package com.weather.weather_backend.redis.constant;

public final class RedisKey {

    private RedisKey() {
    }

    public static final String CURRENT_WEATHER = "weather:current:";

    public static final String FORECAST = "weather:forecast:";

    public static final String OTP = "otp:";

    public static final String USER = "user:";

}