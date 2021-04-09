package com.bud.weather.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
public class WeatherDto implements Serializable {
  private static final long serialVersionUID = -8650926347032329959L;
  private String weatherData;

  public WeatherDto(String weatherData) {
    this.weatherData = weatherData;
  }
}
