package com.bud.weather.api.service;

import com.bud.weather.api.model.Location;

import java.util.List;

public interface LocationService {
    void addLocation(Location location);
    List<Location> findByCountryAndCity(String country, String city);
}
