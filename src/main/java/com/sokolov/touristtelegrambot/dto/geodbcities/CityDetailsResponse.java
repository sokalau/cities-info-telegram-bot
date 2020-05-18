package com.sokolov.touristtelegrambot.dto.geodbcities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CityDetailsResponse implements Serializable {
    private CityDetails[] data;
}
