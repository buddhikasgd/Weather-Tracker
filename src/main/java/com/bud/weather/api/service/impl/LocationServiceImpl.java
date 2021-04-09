package com.bud.weather.api.service.impl;

import com.bud.weather.api.model.Location;
import com.bud.weather.api.repository.LocationRepository;
import com.bud.weather.api.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void addLocation(Location location) {
        locationRepository.save(location);
    }

    @Override
    public List<Location> findByCountryAndCity(String country, String city) {
        return locationRepository.findByCountryAndCity(country, city);
    }
}
