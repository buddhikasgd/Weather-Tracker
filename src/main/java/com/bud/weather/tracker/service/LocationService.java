package com.bud.weather.tracker.service;

import com.bud.weather.tracker.model.Location;

import java.util.List;

public interface LocationService {
    void addLocation(Location location);
    List<Location> findByCountryAndCity(String country, String city);
}
