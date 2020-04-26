package com.sokolov.touristtelegrambot.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Update implements Serializable {
    @JsonProperty("update_id")
    private Long id;

    private Message message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
