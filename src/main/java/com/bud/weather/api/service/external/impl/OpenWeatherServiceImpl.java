package com.bud.weather.api.service.external.impl;

import com.bud.weather.api.dto.external.openweather.WeatherData;
import com.bud.weather.api.service.external.OpenWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class OpenWeatherServiceImpl implements OpenWeatherService {
    private final Logger logger = LoggerFactory.getLogger(OpenWeatherServiceImpl.class);

    private String weatherServiceUrl;

    private RestTemplate restTemplate;

    public OpenWeatherServiceImpl(String weatherServiceUrl, RestTemplate restTemplate) {
        this.weatherServiceUrl = weatherServiceUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherData retrieveWeatherData(String country, String city) {
        logger.info("Calling weather api - country : {} and city: {}", country, city);
        String apiUrl = this.weatherServiceUrl.replace("{city}", city).replace("{country}", country);
        logger.debug("API Url : {}", apiUrl);
        return restTemplate.getForObject(apiUrl, WeatherData.class);
    }
}
