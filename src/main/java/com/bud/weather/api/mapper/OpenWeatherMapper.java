package com.bud.weather.api.mapper;

import com.bud.weather.api.dto.external.openweather.WeatherData;
import com.bud.weather.api.model.Weather;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

public class OpenWeatherMapper extends ConfigurableMapper {
    protected void configure(MapperFactory factory) {
        factory.classMap(Weather.class, WeatherData.class)
                .field("country", "sys.country")
                .field("city", "name")
                .field("condition", "openWeather[0].main")
                .field("description", "openWeather[0].description")
                .field("geoLocation.longitude", "coord.lon")
                .field("geoLocation.latitude", "coord.lat")
                .field("weatherDetail.temperature", "main.temp")
                .field("weatherDetail.feelsLike", "main.feelsLike")
                .field("weatherDetail.minTemperature", "main.tempMin")
                .field("weatherDetail.maxTemperature", "main.tempMax")
                .field("weatherDetail.pressure", "main.pressure")
                .field("weatherDetail.humidity", "main.humidity")
                .field("weatherDetail.visibility", "visibility")
                .field("weatherDetail.windSpeed", "wind.speed")
                .field("weatherDetail.windDirection", "wind.deg")
                .field("weatherDetail.cloudiness", "clouds.all")
                .field("weatherDetail.sunrise", "sys.sunrise")
                .field("weatherDetail.sunset", "sys.sunset")
                .byDefault()
                .register();
    }
}
