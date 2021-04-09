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
    "id",
    "main",
    "description",
    "icon"
})
@Getter @Setter @NoArgsConstructor
public class Weather implements Serializable
{
    private final static long serialVersionUID = 2717494992653466918L;
    @JsonProperty("id")
    private long id;
    @JsonProperty("main")
    private String main;
    @JsonProperty("description")
    private String description;
    @JsonProperty("icon")
    private String icon;
}
