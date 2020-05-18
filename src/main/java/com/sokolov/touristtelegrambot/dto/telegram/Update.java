package com.sokolov.touristtelegrambot.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Update implements Serializable {
    @JsonProperty("update_id")
    private Long id;

    private Message message;
}
