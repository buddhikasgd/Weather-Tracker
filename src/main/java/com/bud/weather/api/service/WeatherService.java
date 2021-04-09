package com.bud.weather.api.service;

import com.bud.weather.api.dto.WeatherDto;

public interface WeatherService {
    WeatherDto getCurrentWeatherByCountryAndCity(String country, String city);
}
