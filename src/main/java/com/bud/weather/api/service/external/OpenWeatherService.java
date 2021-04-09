package com.bud.weather.api.service.external;

import com.bud.weather.api.dto.external.openweather.WeatherData;

public interface OpenWeatherService {
    WeatherData retrieveWeatherData(String country, String city);
}
