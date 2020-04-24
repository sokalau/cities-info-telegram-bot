package com.sokolov.citiesinfotelegrambot.controller;

import com.sokolov.citiesinfotelegrambot.model.Update;
import com.sokolov.citiesinfotelegrambot.service.TelegramBotService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HookController {
    private final TelegramBotService telegramBotService;

    public HookController(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @PostMapping(value = "/${bot.token}")
    public void hook(@RequestBody Update update) {
        telegramBotService.reply(update);
    }
}
