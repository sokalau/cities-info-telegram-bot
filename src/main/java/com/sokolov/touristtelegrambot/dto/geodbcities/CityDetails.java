package com.sokolov.touristtelegrambot.dto.geodbcities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CityDetails implements Serializable {
    private Integer id;
    private String wikiDataId;
    private String type;
    private String city;
    private String name;
    private String country;
    private String countryCode;
    private String region;
    private String regionCode;
    private Double latitude;
    private Double longitude;
}
