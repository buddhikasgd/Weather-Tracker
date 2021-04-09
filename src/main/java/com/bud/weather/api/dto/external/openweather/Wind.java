package com.bud.weather.api.dto.external.openweather;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "speed",
    "deg"
})
@Getter @Setter @NoArgsConstructor
public class Wind implements Serializable
{
    private final static long serialVersionUID = -6392867944182896171L;
    @JsonProperty("speed")
    private double speed;
    @JsonProperty("deg")
    private long deg;
}
