package com.sokolov.touristtelegrambot.dto.yandextranslate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranslateTextResponse {
    private Integer code;

    @JsonProperty(value = "lang")
    private String language;

    private String[] text;

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

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }
}
