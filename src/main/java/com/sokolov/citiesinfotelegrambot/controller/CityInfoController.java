package com.sokolov.citiesinfotelegrambot.controller;

import com.sokolov.citiesinfotelegrambot.service.CityInfoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cities")
public class CityInfoController {
    private final CityInfoService cityInfoService;

    public CityInfoController(CityInfoService cityInfoService) {
        this.cityInfoService = cityInfoService;
    }

    @PostMapping(value = "/add")
    public void addCityInfo(String name, String description) {
        cityInfoService.addOrReplace(name, description);
    }

    @GetMapping(value = "/{name}")
    public void getCityInfo(@PathVariable String name) {
        cityInfoService.get(name);
    }

    @DeleteMapping(value = "/remove/{name}")
    public void removeCityInfo(@PathVariable String name) {
        cityInfoService.removeIfExists(name);
    }
}
