package com.sokolov.touristtelegrambot.controller;

import com.sokolov.touristtelegrambot.model.Update;
import com.sokolov.touristtelegrambot.service.TelegramBotService;
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
