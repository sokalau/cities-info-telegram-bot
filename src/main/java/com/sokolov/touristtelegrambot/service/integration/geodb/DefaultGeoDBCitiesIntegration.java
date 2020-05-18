package com.sokolov.touristtelegrambot.service.integration.geodb;

import com.sokolov.touristtelegrambot.dto.geodbcities.CityDetailsResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class DefaultGeoDBCitiesIntegration implements GeoDBCitiesIntegration {
    private static final String GET_CITY_DETAILS_URL
            = "http://geodb-free-service.wirefreethought.com/v1/geo/cities?namePrefix=%s&limit=1&offset=0&hateoasMode=false";

    private final RestTemplate restTemplate;
    private final GebDBCitiesParser parser;

    @Autowired
    public DefaultGeoDBCitiesIntegration(RestTemplate restTemplate, GebDBCitiesParser parser) {
        this.restTemplate = restTemplate;
        this.parser = parser;
    }

    @Override
    public String getDescription(String cityName, String language) {
        var description = EMPTY;

        if (StringUtils.isNoneBlank(cityName)) {
            var url = String.format(GET_CITY_DETAILS_URL, cityName);
            var response = restTemplate.getForObject(url, CityDetailsResponse.class);

            if (response != null) {
                description = parser.parse(response, language);
            }
        }

        return description;
    }
}
