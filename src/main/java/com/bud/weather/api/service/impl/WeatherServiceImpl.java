package com.bud.weather.api.service.impl;

import com.bud.weather.api.dto.WeatherDto;
import com.bud.weather.api.dto.external.openweather.WeatherData;
import com.bud.weather.api.model.Weather;
import com.bud.weather.api.repository.WeatherRepository;
import com.bud.weather.api.service.WeatherService;
import com.bud.weather.api.service.external.OpenWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private OpenWeatherService openWeatherService;

    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    @Transactional
    public WeatherDto getCurrentWeatherByCountryAndCity(String country, String city) {
        WeatherData weatherData = openWeatherService.retrieveWeatherData(country, city);
        Weather weatherEntity = createWeatherEntity(country, city, weatherData);
        if (weatherEntity != null) {
            Weather updatedWeather = weatherRepository.save(weatherEntity);
            if (updatedWeather != null) {
                return new WeatherDto(updatedWeather.getDescription());
            }
        }
        return null;
    }

    private Weather createWeatherEntity(String country, String city, WeatherData weatherData) {
        if (weatherData != null && !CollectionUtils.isEmpty(weatherData.getWeather())) {
            String description = weatherData.getWeather().get(0).getDescription();
            return new Weather(country, city, description);
        }
        return null;
    }
}
