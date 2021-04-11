package com.bud.weather.api.service.impl;

import com.bud.weather.api.dto.WeatherDto;
import com.bud.weather.api.dto.external.openweather.WeatherData;
import com.bud.weather.api.mapper.OpenWeatherMapper;
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
    private OpenWeatherService openWeatherService;
    private WeatherRepository weatherRepository;
    private OpenWeatherMapper openWeatherMapper = new OpenWeatherMapper();

    @Autowired
    public WeatherServiceImpl(OpenWeatherService openWeatherService, WeatherRepository weatherRepository) {
        this.openWeatherService = openWeatherService;
        this.weatherRepository = weatherRepository;
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "handleServiceOutage", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public WeatherDto getCurrentWeatherByCountryAndCity(String country, String city) {
        WeatherData weatherData = this.openWeatherService.retrieveWeatherData(country, city);
        Weather weatherEntity = createWeatherEntity(weatherData);
        return saveAndReturnWeatherDto(weatherEntity, country, city);
    }

    private WeatherDto saveAndReturnWeatherDto(Weather weatherEntity, String country, String city) {
        if (weatherEntity != null) {
            List<Weather> storedWeatherData = this.weatherRepository.findByCountryAndCity(country.toUpperCase(), city.toUpperCase());
            if (!CollectionUtils.isEmpty(storedWeatherData)) {
                weatherEntity.setId(storedWeatherData.get(0).getId());
                weatherEntity.getWeatherDetail().setId(storedWeatherData.get(0).getWeatherDetail().getId());
            }
            Weather updatedWeather = this.weatherRepository.save(weatherEntity);
            return new WeatherDto(updatedWeather.getDescription());
        }
        return null;
    }

    public WeatherDto handleServiceOutage(String country, String city) {
        logger.error("Unable to call open weather service!!");
        List<Weather> storedWeatherData = this.weatherRepository.findByCountryAndCity(country.toUpperCase(), city.toUpperCase());
        if (!CollectionUtils.isEmpty(storedWeatherData)) {
            logger.debug("Returning stored data from database until service resumes!!");
            return new WeatherDto(storedWeatherData.get(0).getDescription());
        }
        logger.debug("Unable to find weather data in database for the given location! Country {} / City {}", country, city);
        return null;
    }

    private Weather createWeatherEntity(WeatherData weatherData) {
        return weatherData != null ? openWeatherMapper.map(weatherData, Weather.class) : null;
    }
}
