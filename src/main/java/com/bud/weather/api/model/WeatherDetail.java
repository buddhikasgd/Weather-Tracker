package com.bud.weather.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "WEATHER_DETAILS")
@Getter @Setter @NoArgsConstructor
public class WeatherDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @OneToOne(mappedBy = "weatherDetail")
    private Weather weather;

    @Column(name="TEMPERATURE")
    private Double temperature;

    @Column(name="FEELS_LIKE")
    private Double feelsLike;

    @Column(name="MIN_TEMPERATURE")
    private Double minTemperature;

    @Column(name="MAX_TEMPERATURE")
    private Double maxTemperature;

    @Column(name="PRESSURE")
    private Integer pressure;

    @Column(name="HUMIDITY")
    private Integer humidity;

    @Column(name="VISIBILITY")
    private Integer visibility;

    @Column(name="WIND_SPEED")
    private Double windSpeed;

    @Column(name="WIND_DIRECTION")
    private Double windDirection;

    @Column(name="CLOUDINESS")
    private Integer cloudiness;

    @Column(name="SUNRISE_TIME")
    private Long sunrise;

    @Column(name="SUNSET_TIME")
    private Long sunset;

}
