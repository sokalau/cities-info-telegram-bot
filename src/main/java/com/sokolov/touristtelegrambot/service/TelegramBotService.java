package com.sokolov.touristtelegrambot.service;

import com.sokolov.touristtelegrambot.model.Update;

public interface TelegramBotService {
    void reply(Update update);
}
