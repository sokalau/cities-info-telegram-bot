package com.sokolov.touristtelegrambot.integration.impl;

import com.sokolov.touristtelegrambot.integration.GeoDBCitiesIntegration;
import com.sokolov.touristtelegrambot.model.CityDetails;
import org.springframework.stereotype.Service;

@Service
public class DefaultGeoDBCitiesIntegration implements GeoDBCitiesIntegration {
    @Override
    public CityDetails getCityDetails(String name) {
        return new CityDetails();
    }
}
