package com.sokolov.touristtelegrambot.service.impl;

import com.sokolov.touristtelegrambot.dto.geodbcities.CityDetails;
import com.sokolov.touristtelegrambot.dto.geodbcities.CityDetailsResponse;
import com.sokolov.touristtelegrambot.integration.GeoDBCitiesIntegration;
import com.sokolov.touristtelegrambot.service.GeoDBCitiesService;
import com.sokolov.touristtelegrambot.service.TranslateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class DefaultGeoDBCitiesService implements GeoDBCitiesService {
    private static final String CITY = "city";
    private static final String COUNTRY = "country";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String DESCRIPTION_FORMAT = "%s: %s\n%s: %s\n%s: %s\n%s: %s";

    private final GeoDBCitiesIntegration geoDBCitiesIntegration;
    private final TranslateService translateService;

    public DefaultGeoDBCitiesService(GeoDBCitiesIntegration geoDBCitiesIntegration,
                                     TranslateService translateService) {
        this.geoDBCitiesIntegration = geoDBCitiesIntegration;
        this.translateService = translateService;
    }

    @Override
    public String getDescription(String cityName, String language) {
        String result = EMPTY;

        CityDetailsResponse cityDetailsResponse = geoDBCitiesIntegration.getCityDetails(cityName);
        if (cityDetailsResponse != null) {
            result = parseCityDetails(cityDetailsResponse, language);
        }

        return result;
    }

    private String parseCityDetails(CityDetailsResponse cityDetailsResponse, String language) {
        String result = EMPTY;
        CityDetails[] data = cityDetailsResponse.getData();

        if (data.length > 0) {
            CityDetails cityDetails = cityDetailsResponse.getData()[0];

            String cityName = StringUtils.capitalize(translateService.translate(CITY, language));
            String city = StringUtils.capitalize(translateService.translate(cityDetails.getCity(), language));

            String countryName = StringUtils.capitalize(translateService.translate(COUNTRY, language));
            String country = StringUtils.capitalize(translateService.translate(cityDetails.getCountry(), language));

            String latitudeName = StringUtils.capitalize(translateService.translate(LATITUDE, language));
            String latitude = cityDetails.getLatitude().toString();

            String longitudeName = StringUtils.capitalize(translateService.translate(LONGITUDE, language));
            String longitude = cityDetails.getLongitude().toString();

            result = String.format(DESCRIPTION_FORMAT, cityName, city, countryName, country,
                    latitudeName, latitude, longitudeName, longitude);
        }

        return result;
    }
}
