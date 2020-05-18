package com.sokolov.touristtelegrambot.service.integration.geodb;

import com.sokolov.touristtelegrambot.dto.geodbcities.CityDetailsResponse;

public interface GebDBCitiesParser {
    String parse(CityDetailsResponse response, String language);
}
