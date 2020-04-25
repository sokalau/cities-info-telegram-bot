package com.sokolov.touristtelegrambot.integration;

import com.sokolov.touristtelegrambot.model.CityDetails;

public interface GeoDBCitiesIntegration {
    CityDetails getCityDetails(String name);
}
