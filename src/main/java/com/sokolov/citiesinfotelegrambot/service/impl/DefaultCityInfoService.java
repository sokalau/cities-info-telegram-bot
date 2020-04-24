package com.sokolov.citiesinfotelegrambot.service.impl;

import com.sokolov.citiesinfotelegrambot.service.CityInfoService;
import org.springframework.stereotype.Service;

@Service
public class DefaultCityInfoService implements CityInfoService {
    @Override
    public void removeIfExists(String name) {
    }

    @Override
    public void addOrReplace(String name, String description) {
    }

    @Override
    public void get(String name) {
    }
}
