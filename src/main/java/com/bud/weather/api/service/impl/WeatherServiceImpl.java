package com.bud.weather.api.service.impl;

import com.bud.weather.api.dto.WeatherDto;
import com.bud.weather.api.dto.external.openweather.WeatherData;
import com.bud.weather.api.model.Weather;
import com.bud.weather.api.repository.WeatherRepository;
import com.bud.weather.api.service.WeatherService;
import com.bud.weather.api.service.external.OpenWeatherService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {
    private final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Autowired
    private OpenWeatherService openWeatherService;

    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "handleServiceOutage", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public WeatherDto getCurrentWeatherByCountryAndCity(String country, String city) {
        WeatherData weatherData = openWeatherService.retrieveWeatherData(country, city);
        Weather weatherEntity = createWeatherEntity(country, city, weatherData);
        if (weatherEntity != null) {
            Weather updatedWeather = weatherRepository.save(weatherEntity);
            return new WeatherDto(updatedWeather.getDescription());
        }
        return null;
    }

    public WeatherDto handleServiceOutage(String country, String city) {
        logger.error("Unable to call open weather service!!");
        List<Weather> storedWeatherData = weatherRepository.findByCountryAndCity(country.toUpperCase(), city.toUpperCase());
        if (!CollectionUtils.isEmpty(storedWeatherData)) {
            logger.debug("Returning stored data from database until service resumes!!");
            return new WeatherDto(storedWeatherData.get(0).getDescription());
        }
        logger.debug("Unable to find weather data in database for the given location! Country {} / City {}", country, city);
        return null;
    }

    private Weather createWeatherEntity(String country, String city, WeatherData weatherData) {
        if (weatherData != null && !CollectionUtils.isEmpty(weatherData.getWeather())) {
            String description = weatherData.getWeather().get(0).getDescription();
            return new Weather(country.toUpperCase(), city.toUpperCase(), description);
        }
        return null;
    }
}
