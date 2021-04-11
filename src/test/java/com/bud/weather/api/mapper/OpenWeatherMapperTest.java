package com.bud.weather.api.mapper;

import com.bud.weather.api.dto.external.openweather.WeatherData;
import com.bud.weather.api.model.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OpenWeatherMapperTest {
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private OpenWeatherMapper openWeatherMapper = new OpenWeatherMapper();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void mapOpenWeatherDataToWeatherEntity() throws Exception {
        WeatherData weatherData = getOpenWeatherData();
        Weather weatherEntity = openWeatherMapper.map(weatherData, Weather.class);

        assertNotNull(weatherEntity);
        assertEquals( weatherEntity.getCity(), "London");
        assertEquals( weatherEntity.getCountry(), "GB");
        assertEquals( weatherEntity.getCondition(), "Clouds");
        assertEquals( weatherEntity.getDescription(), "broken clouds");
        assertEquals( weatherEntity.getGeoLocation().getLongitude(), -0.1257);
        assertEquals( weatherEntity.getGeoLocation().getLatitude(), 51.5085);
        assertEquals( weatherEntity.getWeatherDetail().getTemperature(), 284.18);
        assertEquals( weatherEntity.getWeatherDetail().getFeelsLike(), 282.62);
        assertEquals( weatherEntity.getWeatherDetail().getMinTemperature(), 282.15);
        assertEquals( weatherEntity.getWeatherDetail().getMaxTemperature(), 285.93);
        assertEquals( weatherEntity.getWeatherDetail().getPressure(), 1022);
        assertEquals( weatherEntity.getWeatherDetail().getHumidity(), 49);
        assertEquals( weatherEntity.getWeatherDetail().getVisibility(), 10000);
        assertEquals( weatherEntity.getWeatherDetail().getWindSpeed(), 6.17);
        assertEquals( weatherEntity.getWeatherDetail().getWindDirection(), 230);
        assertEquals( weatherEntity.getWeatherDetail().getCloudiness(), 75);
        assertEquals( weatherEntity.getWeatherDetail().getSunrise(), DATE_TIME_FORMAT.parse("2021-04-08 15:19:40").getTime() / 1000);
        assertEquals( weatherEntity.getWeatherDetail().getSunset(), DATE_TIME_FORMAT.parse("2021-04-09 04:44:33").getTime() / 1000);

    }

    private WeatherData getOpenWeatherData() throws JsonProcessingException {
        String openWeatherResponse = "{\n" +
                "    \"coord\": {\n" +
                "        \"lon\": -0.1257,\n" +
                "        \"lat\": 51.5085\n" +
                "    },\n" +
                "    \"weather\": [\n" +
                "        {\n" +
                "            \"id\": 803,\n" +
                "            \"main\": \"Clouds\",\n" +
                "            \"description\": \"broken clouds\",\n" +
                "            \"icon\": \"04d\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"base\": \"stations\",\n" +
                "    \"main\": {\n" +
                "        \"temp\": 284.18,\n" +
                "        \"feels_like\": 282.62,\n" +
                "        \"temp_min\": 282.15,\n" +
                "        \"temp_max\": 285.93,\n" +
                "        \"pressure\": 1022,\n" +
                "        \"humidity\": 49\n" +
                "    },\n" +
                "    \"visibility\": 10000,\n" +
                "    \"wind\": {\n" +
                "        \"speed\": 6.17,\n" +
                "        \"deg\": 230\n" +
                "    },\n" +
                "    \"clouds\": {\n" +
                "        \"all\": 75\n" +
                "    },\n" +
                "    \"dt\": 1617883046,\n" +
                "    \"sys\": {\n" +
                "        \"type\": 1,\n" +
                "        \"id\": 1414,\n" +
                "        \"country\": \"GB\",\n" +
                "        \"sunrise\": 1617859180,\n" +
                "        \"sunset\": 1617907473\n" +
                "    },\n" +
                "    \"timezone\": 3600,\n" +
                "    \"id\": 2643743,\n" +
                "    \"name\": \"London\",\n" +
                "    \"cod\": 200\n" +
                "}\n";
       return objectMapper.readValue(openWeatherResponse, WeatherData.class);
    }

}
