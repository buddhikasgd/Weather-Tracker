package com.bud.weather.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class GeoLocation {
    @Column(name="LONGITUDE")
    private Double longitude;

    @Column(name="LATITUDE")
    private Double latitude;
}
