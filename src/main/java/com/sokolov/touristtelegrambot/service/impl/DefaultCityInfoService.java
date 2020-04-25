package com.sokolov.touristtelegrambot.service.impl;

import com.sokolov.touristtelegrambot.entity.CityInfo;
import com.sokolov.touristtelegrambot.repository.CityInfoRepository;
import com.sokolov.touristtelegrambot.service.CityInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class DefaultCityInfoService implements CityInfoService {
    private final CityInfoRepository repository;

    public DefaultCityInfoService(CityInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void removeIfExists(String name) {
        repository.deleteByName(name);
    }

    @Override
    public String addOrReplace(String name, String description) {
        String message = "Name cannot be blank.";

        if (StringUtils.isNotBlank(name)) {
            CityInfo cityInfo = repository.findByName(name);

            if (cityInfo != null) {
                if (StringUtils.isNoneBlank(description)) {
                    cityInfo.setDescription(description);
                    repository.save(cityInfo);
                    message = "Old description has been replaced to -> " + description;
                }
            } else {
                if (StringUtils.isNoneBlank(description)) {
                    CityInfo newCityInfo = new CityInfo(name, description);
                    repository.save(newCityInfo);
                    message = "New description has been added -> " + description;
                }
            }
        }

        return message;
    }

    @Override
    public CityInfo get(String name) {
        return repository.findByName(name);
    }
}
