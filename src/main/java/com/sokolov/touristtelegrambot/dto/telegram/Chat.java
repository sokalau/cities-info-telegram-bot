package com.sokolov.touristtelegrambot.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Chat implements Serializable {
    private Long id;

    @JsonProperty("fist_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String username;

    private String type;
}
