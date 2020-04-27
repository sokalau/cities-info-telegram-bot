package com.sokolov.touristtelegrambot.service.impl;

import com.sokolov.touristtelegrambot.entity.CityInfo;
import com.sokolov.touristtelegrambot.repository.CityInfoRepository;
import com.sokolov.touristtelegrambot.service.CityInfoService;
import com.sokolov.touristtelegrambot.service.TranslateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    @Transactional
    public String removeIfExists(String name) {
        String message = NOT_EXISTS;

        if (StringUtils.isNotBlank(name)) {
            String preparedName = prepareName(name);
            if (repository.existsByName(preparedName)) {
                repository.deleteByName(preparedName);
                message = DELETED_MESSAGE;
            }
        }

        return message;
    }

    @Override
    public String addOrReplace(String name, String description) {
        String message = INVALID_PARAMS_MESSAGE;

        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(description)) {
            String preparedName = prepareName(name);
            CityInfo cityInfo = repository.findByName(preparedName);

            if (cityInfo != null) {
                cityInfo.setDescription(description);
                repository.save(cityInfo);
                message = String.format(REPLACED_MESSAGE, preparedName, description);
            } else {
                CityInfo newCityInfo = new CityInfo(preparedName, description);
                repository.save(newCityInfo);
                message = String.format(ADDED_MESSAGE, preparedName, description);
            }
        }

        return message;
    }

    @Override
    public CityInfo get(String name) {
        CityInfo cityInfo = null;

        if (StringUtils.isNotBlank(name)) {
            String preparedName = prepareName(name);
            cityInfo = repository.findByName(preparedName);
        }

        return cityInfo;
    }

    private String prepareName(String name) {
        String normalizedName = name.trim().toLowerCase();
        return translateService.translate(normalizedName, DefaultTranslateService.DEFAULT_LANGUAGE);
    }
}
