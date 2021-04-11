package com.bud.weather.api.service.impl;

import com.bud.weather.api.dto.WeatherDto;
import com.bud.weather.api.dto.external.openweather.OpenWeather;
import com.bud.weather.api.dto.external.openweather.WeatherData;
import com.bud.weather.api.model.Weather;
import com.bud.weather.api.repository.WeatherRepository;
import com.bud.weather.api.service.WeatherService;
import com.bud.weather.api.service.external.OpenWeatherService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OpenWeatherServiceImplTest {
    private WeatherService weatherService;
    private OpenWeatherService openWeatherService;
    private RestTemplate restTemplate;
    private WeatherRepository weatherRepository;

    @Before
    public void setUp(){
        restTemplate = new RestTemplate();
        weatherRepository = Mockito.mock(WeatherRepository.class);
        openWeatherService = Mockito.mock(OpenWeatherService.class);
        weatherService = new WeatherServiceImpl(openWeatherService, weatherRepository);
    }

    @Test
    public void getCurrentWeatherByCountryAndCity() {
        String country = "gb";
        String city = "London";

        Mockito.when(weatherRepository.save(Mockito.any())).thenReturn(getSavedWeatherEntity(country, city));
        Mockito.when(weatherRepository.findByCountryAndCity(country, city))
                .thenReturn(Collections.singletonList(getSavedWeatherEntity(country, city)));
        Mockito.when(openWeatherService.retrieveWeatherData(country, city)).thenReturn(getWeatherDataDto(country, city));

        WeatherDto weatherDto = weatherService.getCurrentWeatherByCountryAndCity(country, city);
        assertNotNull(weatherDto);
        assertNotNull(weatherDto.getWeatherData());
        assertEquals(weatherDto.getWeatherData(), "Open Clouds");
    }

    private Weather getSavedWeatherEntity(String country, String city) {
        Weather weather = new Weather();
        weather.setId(1L);
        weather.setCity(country.toUpperCase());
        weather.setCountry(city.toUpperCase());
        weather.setDescription("Open Clouds");
        return weather;
    }

    private WeatherData getWeatherDataDto(String country, String city) {
        WeatherData weatherDataDto = new WeatherData();
        List<OpenWeather> openWeatherList = new ArrayList<>();
        OpenWeather openWeather = new OpenWeather();
        openWeather.setDescription("Open Clouds");
        openWeatherList.add(openWeather);
        weatherDataDto.setOpenWeather(openWeatherList);
        return weatherDataDto;
    }
}
