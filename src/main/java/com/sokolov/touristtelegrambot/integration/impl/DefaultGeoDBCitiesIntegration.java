package com.sokolov.touristtelegrambot.integration.impl;

import com.sokolov.touristtelegrambot.dto.geodbcities.CityDetailsResponse;
import com.sokolov.touristtelegrambot.integration.GeoDBCitiesIntegration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DefaultGeoDBCitiesIntegration implements GeoDBCitiesIntegration {
    private static final String GET_CITY_DETAILS_URL = "http://geodb-free-service.wirefreethought.com/v1/geo/cities?namePrefix=%s&limit=1&offset=0&hateoasMode=false";

    private final RestTemplate restTemplate;

    public DefaultGeoDBCitiesIntegration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CityDetailsResponse getCityDetails(String name) {
        CityDetailsResponse response = new CityDetailsResponse();

        if (StringUtils.isNoneBlank(name)) {
            String url = String.format(GET_CITY_DETAILS_URL, name);
            ResponseEntity<CityDetailsResponse> responseEntity = restTemplate.getForEntity(
                    url, CityDetailsResponse.class);
            response = responseEntity.getBody();
        }

        return response;
    }
}
