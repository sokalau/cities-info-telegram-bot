package com.sokolov.citiesinfotelegrambot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Chat implements Serializable {
    private Long id;

    @JsonProperty("fist_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String username;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
