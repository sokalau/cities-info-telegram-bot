package com.sokolov.touristtelegrambot.config;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {
    @Value("${bot.token}")
    private String token;

    @Bean
    public TelegramBot configureTelegramBot() {
        return new TelegramBot(token);
    }
}
