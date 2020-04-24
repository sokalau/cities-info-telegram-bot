package com.sokolov.citiesinfotelegrambot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.sokolov.citiesinfotelegrambot.model.Message;
import com.sokolov.citiesinfotelegrambot.model.Update;
import com.sokolov.citiesinfotelegrambot.service.TelegramBotService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class DefaultTelegramBotService implements TelegramBotService {
    private final TelegramBot bot;

    public DefaultTelegramBotService(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void reply(Update update) {
        Message message = update.getMessage();
        if (message != null) {
            Long chatId = message.getChat().getId();
            String text = message.getText();

            if (chatId != null && StringUtils.isNoneBlank(text)) {
                String reversed = new StringBuilder(text).reverse().toString();
                bot.execute(new SendMessage(chatId, reversed));
            }
        }
    }
}
