package com.bud.weather.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bud.weather.api.model.Location;

import java.util.List;

@Repository
public interface LocationRepository  extends JpaRepository<Location, Long> {
    List<Location> findByCountryAndCity(String country, String city);
}
