package com.bud.weather.api.repository;

import com.bud.weather.api.model.GeoLocation;
import com.bud.weather.api.model.Weather;
import com.bud.weather.api.model.WeatherDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OpenWeatherRepositoryTest {
    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    void saveAndRetrieveWeatherDto() throws ParseException {
        String country = "gb";
        String city = "London";
        String description = "broken clouds";
        Weather weatherEntity = createWeatherEntity(country, city, description);

        Weather savedEntity = weatherRepository.save(weatherEntity);

        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getId());
        assertEquals(savedEntity.getCity(), city);
        assertEquals(savedEntity.getCountry(), country);
        assertEquals(savedEntity.getDescription(), description);
        assertEquals(savedEntity.getCondition(), "Clouds");

        assertEquals(savedEntity.getGeoLocation().getLatitude(), 51.5085);
        assertEquals(savedEntity.getGeoLocation().getLongitude(), -0.1257);

        assertEquals(savedEntity.getWeatherDetail().getCloudiness(), 75);
        assertEquals(savedEntity.getWeatherDetail().getHumidity(), 49);
        assertEquals(savedEntity.getWeatherDetail().getPressure(), 1022);
        assertEquals(savedEntity.getWeatherDetail().getFeelsLike(), 282.62);
        assertEquals(savedEntity.getWeatherDetail().getTemperature(), 284.18);
        assertEquals(savedEntity.getWeatherDetail().getMaxTemperature(), 285.93);
        assertEquals(savedEntity.getWeatherDetail().getMinTemperature(), 282.15);
        assertEquals(savedEntity.getWeatherDetail().getVisibility(), 10000);
        assertEquals(savedEntity.getWeatherDetail().getSunrise() , DATE_TIME_FORMAT.parse("2021-04-08 15:19:40").getTime() / 1000);
        assertEquals(savedEntity.getWeatherDetail().getSunset(), DATE_TIME_FORMAT.parse("2021-04-09 04:44:33").getTime() / 1000);
    }

    private Weather createWeatherEntity(String country, String city, String description) {
        Weather weather = new Weather();
        weather.setCountry(country);
        weather.setCity(city);
        weather.setDescription(description);
        weather.setCondition("Clouds");

        GeoLocation geoLocation = new GeoLocation();
        geoLocation.setLongitude(-0.1257);
        geoLocation.setLatitude(51.5085);
        weather.setGeoLocation(geoLocation);

        WeatherDetail weatherDetail = new WeatherDetail();
        weatherDetail.setCloudiness(75);
        weatherDetail.setHumidity(49);
        weatherDetail.setPressure(1022);
        weatherDetail.setFeelsLike(282.62);
        weatherDetail.setTemperature(284.18);
        weatherDetail.setMaxTemperature(285.93);
        weatherDetail.setMinTemperature(282.15);
        weatherDetail.setSunrise(new Integer(1617859180).longValue());
        weatherDetail.setSunset(new Integer(1617907473).longValue());
        weatherDetail.setVisibility(10000);

        weatherDetail.setWeather(weather);
        weather.setWeatherDetail(weatherDetail);

        return weather;
    }

}
