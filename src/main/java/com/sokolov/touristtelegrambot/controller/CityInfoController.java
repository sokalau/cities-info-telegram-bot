package com.sokolov.touristtelegrambot.controller;

import com.sokolov.touristtelegrambot.entity.CityInfo;
import com.sokolov.touristtelegrambot.service.CityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String NO_INFO_MESSAGE = "There is no city information.";

    private final CityInfoService service;

    @Autowired
    public CityInfoController(CityInfoService service) {
        this.service = service;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCityInfo(String name, String description) {
        Map<String, Object> body = new HashMap<>();
        var status = HttpStatus.OK;
        var message = service.addOrReplace(name, description);
        body.put(MESSAGE, message);
        return new ResponseEntity<>(body, status);
    }

    @GetMapping(value = "/about/{name}")
    public ResponseEntity<?> getCityInfo(@PathVariable String name) {
        Map<String, Object> body = new HashMap<>();
        var status = HttpStatus.OK;

        var cityInfo = service.get(name);
        if (cityInfo != null) {
            body.put(CITY_INFO, cityInfo);
        } else {
            body.put(MESSAGE, NO_INFO_MESSAGE);
        }

        return new ResponseEntity<>(body, status);
    }

    @DeleteMapping(value = "/remove/{name}")
    public ResponseEntity<?> removeCityInfo(@PathVariable String name) {
        Map<String, Object> body = new HashMap<>();
        var status = HttpStatus.OK;
        var message = service.removeIfExists(name);
        body.put(MESSAGE, message);
        return new ResponseEntity<>(body, status);
    }
}
