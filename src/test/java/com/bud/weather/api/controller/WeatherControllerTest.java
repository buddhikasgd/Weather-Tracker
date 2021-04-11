package com.bud.weather.api.controller;

import com.bud.weather.api.dto.WeatherDto;
import com.bud.weather.api.model.Location;
import com.bud.weather.api.service.LocationService;
import com.bud.weather.api.service.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WeatherControllerTest {

    private WeatherController weatherController;
    private WeatherService weatherService;
    private LocationService locationService;

    @Before
    public void setUp(){
        locationService = Mockito.mock(LocationService.class);
        weatherService = Mockito.mock(WeatherService.class);
        weatherController = new WeatherController(locationService, weatherService);
    }

    @Test
    public void getCurrentWeatherByCountryAndCity() {
        String country = "gb";
        String city = "London";
        String apiKey = "WTR-KEY-1";

        Mockito.when(weatherService.getCurrentWeatherByCountryAndCity(country, city)).thenReturn(getWeatherDto());
        Mockito.when(locationService.findByCountryAndCity(country, city))
                .thenReturn(Collections.singletonList(getSavedLocation(country, city)));

        ResponseEntity<WeatherDto> weatherDto = weatherController.getCurrentWeatherByCountryAndCity(country, city, apiKey);
        assertNotNull(weatherDto);
        assertEquals(weatherDto.getStatusCode(), HttpStatus.OK);
        assertEquals(weatherDto.getBody().getWeatherData(), "Open Clouds");
    }

    private Location getSavedLocation(String country, String city) {
        Location location = new Location();
        location.setId(1L);
        location.setCity(country.toUpperCase());
        location.setCountry(city.toUpperCase());
        return location;
    }

    private WeatherDto getWeatherDto(){
        WeatherDto weatherDto = new WeatherDto();;
        weatherDto.setWeatherData("Open Clouds");
        return weatherDto;
    }

}
