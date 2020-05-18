package com.sokolov.touristtelegrambot.controller;

import com.sokolov.touristtelegrambot.dto.telegram.Update;
import com.sokolov.touristtelegrambot.service.TelegramBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HookController {
    private final TelegramBotService service;

    @Autowired
    public HookController(TelegramBotService service) {
        this.service = service;
    }

    @PostMapping(value = "/${bot.token}")
    public void hook(@RequestBody Update update) {
        service.reply(update);
    }
}
