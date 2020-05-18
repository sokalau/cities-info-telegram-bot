package com.sokolov.touristtelegrambot.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Message implements Serializable {
    @JsonProperty("message_id")
    private Long id;

    private User from;
    private Chat chat;
    private Long date;
    private String text;
}
