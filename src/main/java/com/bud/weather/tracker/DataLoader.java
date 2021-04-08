package com.bud.weather.tracker;

import com.bud.weather.tracker.dto.LocationDataDto;
import com.bud.weather.tracker.model.Location;
import com.bud.weather.tracker.service.LocationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;


/**
 * This loads location data from city.list.json which is used by Application on start.
 *
 */
@Component
public class DataLoader implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private LocationService locationService;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        File countryAndCityData = new File(DataLoader.class.getResource("/static/city.list.json").getFile());
        List<LocationDataDto> LocationDataDtoList = mapper.readValue(countryAndCityData, new TypeReference<List<LocationDataDto>>() {});
        for (LocationDataDto locationDataDto : LocationDataDtoList) {
            logger.info("Loading country and city data {} - {}", locationDataDto.getCountry(), locationDataDto.getName());
            locationService.addLocation(new Location(locationDataDto.getCountry(), locationDataDto.getName()));
        }
    }
}