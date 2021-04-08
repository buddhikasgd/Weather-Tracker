package com.bud.weather.tracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LOCATION")
@Getter @Setter @NoArgsConstructor
public class Location implements Serializable {
    private static final long serialVersionUID = -5071294176205327404L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name="COUNTRY", length = 30)
    private String country;

    @Column(name="CITY", length = 50)
    private String city;

    public Location(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
