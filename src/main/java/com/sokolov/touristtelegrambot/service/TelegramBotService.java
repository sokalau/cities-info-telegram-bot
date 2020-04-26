package com.sokolov.touristtelegrambot.service;

import com.sokolov.touristtelegrambot.dto.telegram.Update;

public interface TelegramBotService {
    void reply(Update update);
}
