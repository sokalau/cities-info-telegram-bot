package com.sokolov.touristtelegrambot.dto.yandextranslate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslateTextResponse {
    private Integer code;

    @JsonProperty(value = "lang")
    private String language;

    private String[] text;
}
