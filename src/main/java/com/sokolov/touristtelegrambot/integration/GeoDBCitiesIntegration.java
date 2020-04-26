package com.sokolov.touristtelegrambot.integration;

import com.sokolov.touristtelegrambot.dto.geodbcities.CityDetailsResponse;

public interface GeoDBCitiesIntegration {
    CityDetailsResponse getCityDetails(String name);
}
