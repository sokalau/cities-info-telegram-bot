package com.sokolov.touristtelegrambot.controller;

import com.sokolov.touristtelegrambot.entity.CityInfo;
import com.sokolov.touristtelegrambot.service.CityInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/cities")
public class CityInfoController {
    private static final String CITY_INFO = "cityInfo";
    private static final String MESSAGE = "message";

    private final CityInfoService cityInfoService;

    public CityInfoController(CityInfoService cityInfoService) {
        this.cityInfoService = cityInfoService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCityInfo(String name, String description) {
        Map<String, Object> body = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = cityInfoService.addOrReplace(name, description);
        body.put(MESSAGE, message);
        return new ResponseEntity<>(body, status);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<?> getCityInfo(@PathVariable String name) {
        Map<String, Object> body = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        CityInfo cityInfo = cityInfoService.get(name);
        if (cityInfo != null) {
            body.put(CITY_INFO, cityInfo);
        } else {
            body.put(MESSAGE, "There is no city information.");
        }

        return new ResponseEntity<>(body, status);
    }

    @DeleteMapping(value = "/remove/{name}")
    public ResponseEntity<?> removeCityInfo(@PathVariable String name) {
        Map<String, Object> body = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        cityInfoService.removeIfExists(name);
        return new ResponseEntity<>(body, status);
    }
}
