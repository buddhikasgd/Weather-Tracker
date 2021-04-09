package com.bud.weather.api.dto.external;

import java.io.Serializable;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "lon",
    "lat"
})
@Getter @Setter @NoArgsConstructor
public class Coord implements Serializable
{
    private final static long serialVersionUID = -1974465267890054031L;
    @JsonProperty("lon")
    private double lon;
    @JsonProperty("lat")
    private double lat;
}
