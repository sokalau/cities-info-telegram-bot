package com.sokolov.citiesinfotelegrambot.service;

import com.sokolov.citiesinfotelegrambot.model.Update;

public interface TelegramBotService {
    void reply(Update update);
}
