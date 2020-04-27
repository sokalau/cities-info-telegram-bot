package com.sokolov.touristtelegrambot.service.impl;

import com.sokolov.touristtelegrambot.entity.CityInfo;
import com.sokolov.touristtelegrambot.repository.CityInfoRepository;
import com.sokolov.touristtelegrambot.service.CityInfoService;
import com.sokolov.touristtelegrambot.service.TranslateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class DefaultCityInfoService implements CityInfoService {
    private static final String REPLACED_MESSAGE = "Description for '%s' has been replaced to '%s'.";
    private static final String ADDED_MESSAGE = "New city '%s' has been added with '%s' description.";
    private static final String INVALID_PARAMS_MESSAGE = "Invalid name and/or description parameters.";
    private static final String NOT_EXISTS = "There is no such city information.";
    private static final String DELETED_MESSAGE = "City information has been successfully deleted.";

    private final CityInfoRepository repository;
    private final TranslateService translateService;

    public DefaultCityInfoService(CityInfoRepository repository, TranslateService translateService) {
        this.repository = repository;
        this.translateService = translateService;
    }

    @Override
    public String removeIfExists(String name) {
        String message = NOT_EXISTS;

        if (repository.existsByName(name)){
            repository.deleteByName(name);
            message = DELETED_MESSAGE;
        }

        return message;
    }

    @Override
    public String addOrReplace(String name, String description) {
        String message = INVALID_PARAMS_MESSAGE;

        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(description)) {
            String normalizedName = name.trim().toLowerCase();
            String translatedName = translateService.translate(
                    normalizedName, DefaultTranslateService.DEFAULT_LANGUAGE);
            CityInfo cityInfo = repository.findByName(translatedName);

            if (cityInfo != null) {
                cityInfo.setDescription(description);
                repository.save(cityInfo);
                message = String.format(REPLACED_MESSAGE, translatedName, description);
            } else {
                CityInfo newCityInfo = new CityInfo(translatedName, description);
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
            String normalizedName = name.trim().toLowerCase();
            String translatedName = translateService.translate(
                    normalizedName, DefaultTranslateService.DEFAULT_LANGUAGE);
            cityInfo = repository.findByName(translatedName);
        }

        return cityInfo;
    }
}
