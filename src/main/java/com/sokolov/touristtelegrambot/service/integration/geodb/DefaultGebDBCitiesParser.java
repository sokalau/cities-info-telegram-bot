package com.sokolov.touristtelegrambot.service.integration.geodb;

import com.sokolov.touristtelegrambot.dto.geodbcities.CityDetails;
import com.sokolov.touristtelegrambot.dto.geodbcities.CityDetailsResponse;
import com.sokolov.touristtelegrambot.service.integration.yandex.YandexTranslateIntegration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class DefaultGebDBCitiesParser implements GebDBCitiesParser {
    private static final String CITY = "city";
    private static final String COUNTRY = "country";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String DESCRIPTION_FORMAT = "%s: %s\n%s: %s\n%s: %s\n%s: %s";

    private final YandexTranslateIntegration integration;

    @Autowired
    public DefaultGebDBCitiesParser(YandexTranslateIntegration integration) {
        this.integration = integration;
    }

    @Override
    public String parse(CityDetailsResponse response, String language) {
        var description = EMPTY;
        var data = response.getData();

        if (data.length > 0) {
            var cityDetails = response.getData()[0];

            var cityName = StringUtils.capitalize(integration.translate(CITY, language));
            var city = StringUtils.capitalize(integration.translate(cityDetails.getCity(), language));

            var countryName = StringUtils.capitalize(integration.translate(COUNTRY, language));
            var country = StringUtils.capitalize(integration.translate(cityDetails.getCountry(), language));

            var latitudeName = StringUtils.capitalize(integration.translate(LATITUDE, language));
            var latitude = cityDetails.getLatitude().toString();

            var longitudeName = StringUtils.capitalize(integration.translate(LONGITUDE, language));
            var longitude = cityDetails.getLongitude().toString();

            description = String.format(DESCRIPTION_FORMAT, cityName, city, countryName, country,
                    latitudeName, latitude, longitudeName, longitude);
        }

        return description;
    }
}
