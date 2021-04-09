package com.bud.weather.api.controller;

import com.bud.weather.api.dto.WeatherDto;
import com.bud.weather.api.exception.LocationNotFoundException;
import com.bud.weather.api.exception.ValidationException;
import com.bud.weather.api.model.Location;
import com.bud.weather.api.service.LocationService;
import com.bud.weather.api.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/api/v1/current-weather")
@Validated
public class WeatherController {
    public static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private LocationService locationService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping(value = "/countries/{country}/cities/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherDto> getCurrentWeatherByCountryAndCity(
            @PathVariable("country") @Valid @NotBlank @Pattern(regexp = "^[A-Za-z]{2}$", message = "Invalid country") @Size(max = 30, message = "max length is 30 characters") String country,
            @PathVariable("city") @Valid @NotBlank @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid city") @Size(max = 50, message = "max length is 50 characters")  String city) {
        logger.info("Request - country: {} / city : {}", country, city);
        if (StringUtils.hasLength(country) && StringUtils.hasLength(city)) {
            List<Location> locationList = locationService.findByCountryAndCity(country, city);
            if (!CollectionUtils.isEmpty(locationList)) {
                WeatherDto weatherDto = weatherService.getCurrentWeatherByCountryAndCity(country, city);
                if (weatherDto != null) {
                    return new ResponseEntity(weatherDto, HttpStatus.OK);
                }
            }
            throw new LocationNotFoundException(String.format("Not found in database - country: %s / city : %s", country, city));
        } else {
            logger.info("Invalid inputs - country: {} / city : {}", country, city);
            throw new ValidationException(String.format("Invalid inputs - country: %s / city : %s", country, city));
        }
    }

}
