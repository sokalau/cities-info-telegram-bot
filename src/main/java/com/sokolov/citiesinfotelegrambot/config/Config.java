package com.sokolov.citiesinfotelegrambot.config;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${bot.token}")
    private String token;

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(token);
    }
}
