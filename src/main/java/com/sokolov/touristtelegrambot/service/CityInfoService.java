package com.sokolov.touristtelegrambot.service;

import com.sokolov.touristtelegrambot.entity.CityInfo;

public interface CityInfoService {
    String removeIfExists(String name);

    String addOrReplace(String name, String description);

    CityInfo get(String name);
}
