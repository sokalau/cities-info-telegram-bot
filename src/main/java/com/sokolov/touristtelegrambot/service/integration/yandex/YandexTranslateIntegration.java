package com.sokolov.touristtelegrambot.service.integration.yandex;

public interface YandexTranslateIntegration {
    String detectLanguage(String text);

    String translate(String text, String language);
}
