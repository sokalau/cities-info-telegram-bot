package com.sokolov.touristtelegrambot.service;

import com.sokolov.touristtelegrambot.entity.CityInfo;
import org.springframework.stereotype.Service;

@Service
public interface CityInfoService {
    String removeIfExists(String name);

    String addOrReplace(String name, String description);

    CityInfo get(String name);
}
