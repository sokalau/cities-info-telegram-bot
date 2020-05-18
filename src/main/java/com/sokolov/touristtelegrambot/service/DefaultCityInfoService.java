package com.sokolov.touristtelegrambot.service;

import com.sokolov.touristtelegrambot.entity.CityInfo;
import com.sokolov.touristtelegrambot.repository.CityInfoRepository;
import com.sokolov.touristtelegrambot.service.integration.yandex.YandexTranslateIntegration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.sokolov.touristtelegrambot.service.integration.yandex.DefaultYandexTranslateIntegration.DETECT_LANGUAGE_URL;

@Service
public class DefaultCityInfoService implements CityInfoService {
    private static final String REPLACED_MESSAGE = "Description for '%s' has been replaced to '%s'.";
    private static final String ADDED_MESSAGE = "New city '%s' has been added with '%s' description.";
    private static final String INVALID_PARAMS_MESSAGE = "Invalid name and/or description parameters.";
    private static final String NOT_EXISTS = "There is no such city information.";
    private static final String DELETED_MESSAGE = "City information has been successfully deleted.";

    private final CityInfoRepository repository;
    private final YandexTranslateIntegration integration;

    @Autowired
    public DefaultCityInfoService(CityInfoRepository repository, YandexTranslateIntegration integration) {
        this.repository = repository;
        this.integration = integration;
    }

    @Override
    @Transactional
    public String removeIfExists(String name) {
        var message = NOT_EXISTS;

        if (StringUtils.isNotBlank(name)) {
            var translatedName = translateName(name);
            if (repository.existsByName(translatedName)) {
                repository.deleteByName(translatedName);
                message = DELETED_MESSAGE;
            }
        }

        return message;
    }

    @Override
    public String addOrReplace(String name, String description) {
        var message = INVALID_PARAMS_MESSAGE;

        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(description)) {
            var translatedName = translateName(name);
            var cityInfo = repository.findByName(translatedName);

            if (cityInfo != null) {
                cityInfo.setDescription(description);
                repository.save(cityInfo);
                message = String.format(REPLACED_MESSAGE, translatedName, description);
            } else {
                var newCityInfo = new CityInfo(translatedName, description);
                repository.save(newCityInfo);
                message = String.format(ADDED_MESSAGE, translatedName, description);
            }
        }

        return message;
    }

    @Override
    public CityInfo get(String name) {
        CityInfo cityInfo = null;

        if (StringUtils.isNotBlank(name)) {
            var translatedName = translateName(name);
            cityInfo = repository.findByName(translatedName);
        }

        return cityInfo;
    }

    private String translateName(String name) {
        return integration.translate(name.trim().toLowerCase(), DETECT_LANGUAGE_URL);
    }
}
