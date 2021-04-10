package com.bud.weather.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class WeatherTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherTrackerApplication.class, args);
	}

}
