package com.sokolov.touristtelegrambot.dto.geodbcities;

import java.io.Serializable;

public class CityDetailsResponse implements Serializable {
    private CityDetails[] data;

    public CityDetails[] getData() {
        return data;
    }

    public void setData(CityDetails[] data) {
        this.data = data;
    }
}
