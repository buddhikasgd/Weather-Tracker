package com.bud.weather.api.service.external;

import com.bud.weather.api.dto.external.openweather.WeatherData;
import com.bud.weather.api.service.external.impl.OpenWeatherServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenWeatherServiceImplTest {
    private static OpenWeatherService weatherService;
    private static RestTemplate restTemplate;
    @BeforeAll
    static void setup() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q={city},{country}&appid=b2e94810d748ed12518632c57f5e3835";
        restTemplate = new RestTemplate();
        weatherService = new OpenWeatherServiceImpl(url, restTemplate);
    }

    @Test
    void returnWeatherDataWhenCountryAndCityAreValid() {
        String country = "uk";
        String city = "London";
        WeatherData weatherData = weatherService.retrieveWeatherData(country, city);

        assertNotNull(weatherData);
        assertEquals( weatherData.getTimezone(), 3600);
        assertEquals( weatherData.getName(), "London");
        assertEquals( weatherData.getSys().getCountry(), "GB");
        assertEquals( weatherData.getCoord().getLon(), -0.1257);
        assertEquals( weatherData.getCoord().getLat(), 51.5085);
    }

}
