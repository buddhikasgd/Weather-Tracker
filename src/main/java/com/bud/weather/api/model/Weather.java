package com.bud.weather.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "WEATHER")
@Getter @Setter @NoArgsConstructor
public class Weather implements Serializable {
    private static final long serialVersionUID = -3062319338277678024L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name="COUNTRY", length = 30)
    private String country;

    @Column(name="CITY", length = 50)
    private String city;

    @Column(name="DESCRIPTION", length = 100)
    private String description;

    public Weather(String country, String city, String description) {
        this.country = country;
        this.city = city;
        this.description = description;
    }
}
