package com.sokolov.touristtelegrambot.service;

public interface TranslateService {
    boolean isNeedToTranslate(String text);

    String translate(String text, String language);

    String detectLanguage(String text);
}
