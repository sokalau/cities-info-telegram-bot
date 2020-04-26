package com.sokolov.touristtelegrambot.integration;

import com.sokolov.touristtelegrambot.dto.yandextranslate.DetectLanguageResponse;
import com.sokolov.touristtelegrambot.dto.yandextranslate.TranslateTextResponse;

public interface YandexTranslateAPIIntegration {
    DetectLanguageResponse detectLanguage(String text);

    TranslateTextResponse translateText(String text, String language);
}
