package com.sokolov.touristtelegrambot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Message implements Serializable {
    @JsonProperty("message_id")
    private Long id;

    private User from;
    private Chat chat;
    private Long date;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
