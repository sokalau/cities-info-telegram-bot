package com.sokolov.citiesinfotelegrambot.service;

import org.springframework.stereotype.Service;

@Service
public interface CityInfoService {
    void removeIfExists(String name);

    void addOrReplace(String name, String description);

    void get(String name);
}
