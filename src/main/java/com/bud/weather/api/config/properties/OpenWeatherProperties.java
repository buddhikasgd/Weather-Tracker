package com.bud.weather.api.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:open-weather.properties")
@ConfigurationProperties
@Getter @Setter @NoArgsConstructor
public class OpenWeatherProperties {
    private String apiBaseUrl;
    private String apiWeatherUrl;
}
