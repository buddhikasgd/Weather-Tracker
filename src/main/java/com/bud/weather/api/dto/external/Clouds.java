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
    "all"
})
@Getter @Setter @NoArgsConstructor
public class Clouds implements Serializable
{
    private final static long serialVersionUID = 1135709519919732009L;
    @JsonProperty("all")
    private long all;
}
