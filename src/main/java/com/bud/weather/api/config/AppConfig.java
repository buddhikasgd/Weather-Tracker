package com.bud.weather.api.config;

import com.bud.weather.api.config.properties.OpenWeatherProperties;
import com.bud.weather.api.service.external.OpenWeatherService;
import com.bud.weather.api.service.external.impl.OpenWeatherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "com.bud.weather.api")
public class AppConfig {
    @Autowired
    private OpenWeatherProperties openWeatherProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    OpenWeatherService openWeatherService() {
        return new OpenWeatherServiceImpl(
                 openWeatherProperties.getApiBaseUrl() +
                         openWeatherProperties.getApiWeatherUrl(),
                restTemplate);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
