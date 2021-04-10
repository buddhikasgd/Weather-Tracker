package com.bud.weather.api.repository;

import com.bud.weather.api.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository  extends JpaRepository<Weather, Long> {
    List<Weather> findByCountryAndCity(String country, String city);
}
