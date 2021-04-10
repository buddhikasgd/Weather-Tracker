package com.bud.weather.api.controller;

import com.bud.weather.api.dto.WeatherDto;
import com.bud.weather.api.exception.LocationNotFoundException;
import com.bud.weather.api.exception.ServiceOutageException;
import com.bud.weather.api.exception.ValidationException;
import com.bud.weather.api.model.Location;
import com.bud.weather.api.service.LocationService;
import com.bud.weather.api.service.WeatherService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static com.bud.weather.api.util.ErrorConstant.COUNTRY_OR_CITY_NOT_FOUND;
import static com.bud.weather.api.util.ErrorConstant.INVALID_COUNTRY_OR_CITY;

@RestController
@RequestMapping("/api/v1/current-weather")
@Validated
@Api(value="Weather tracker")
public class WeatherController {
    public static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private LocationService locationService;

    @Autowired
    private WeatherService weatherService;

    @ApiOperation(value = "Retrieve current weather by location", response = WeatherDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "Country or city is not found"),
            @ApiResponse(code = 422, message = "Invalid country or city")
    }
    )
    @GetMapping(value = "/countries/{country}/cities/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherDto> getCurrentWeatherByCountryAndCity(
            @PathVariable("country") @ApiParam(value = "country", example = "AU") @Valid @NotBlank @Pattern(regexp = "^[A-Za-z]{2}$", message = "Invalid country") @Size(max = 30, message = "max length is 30 characters") String country,
            @PathVariable("city") @ApiParam(value = "city", example = "sydney") @Valid @NotBlank @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid city") @Size(max = 50, message = "max length is 50 characters")  String city,
            @RequestHeader(value = "X-api-key") @ApiParam(value = "api key", example = "WTR-KEY-1") String apiKey) {
        logger.info("Request - country: {} / city : {}", country, city);
        if (StringUtils.hasLength(country) && StringUtils.hasLength(city)) {
            List<Location> locationList = locationService.findByCountryAndCity(country, city);
            if (!CollectionUtils.isEmpty(locationList)) {
                WeatherDto weatherDto = weatherService.getCurrentWeatherByCountryAndCity(country, city);
                if (weatherDto != null) {
                    return new ResponseEntity(weatherDto, HttpStatus.OK);
                } else {
                    throw new ServiceOutageException("Service is unavailable. Please try again later");
                }
            } else {
                logger.error("Not found in database - country: {} / city : {}", country, city);
                throw new LocationNotFoundException(COUNTRY_OR_CITY_NOT_FOUND);
            }
        } else {
            logger.error("Invalid inputs - country: {} / city : {}", country, city);
            throw new ValidationException(INVALID_COUNTRY_OR_CITY);
        }
    }

}
