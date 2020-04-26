package com.sokolov.touristtelegrambot.dto.yandextranslate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetectLanguageResponse {
    private Integer code;

    @JsonProperty(value = "lang")
    private String language;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
