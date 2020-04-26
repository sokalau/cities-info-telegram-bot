package com.sokolov.touristtelegrambot.service.impl;

import com.sokolov.touristtelegrambot.dto.yandextranslate.DetectLanguageResponse;
import com.sokolov.touristtelegrambot.dto.yandextranslate.TranslateTextResponse;
import com.sokolov.touristtelegrambot.integration.YandexTranslateAPIIntegration;
import com.sokolov.touristtelegrambot.service.TranslateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class DefaultTranslateService implements TranslateService {
    public static final String DEFAULT_LANGUAGE = "ru";
    public static final String ENGLISH_LANGUAGE = "en";

    private final YandexTranslateAPIIntegration yandexTranslateAPIIntegration;

    public DefaultTranslateService(YandexTranslateAPIIntegration yandexTranslateAPIIntegration) {
        this.yandexTranslateAPIIntegration = yandexTranslateAPIIntegration;
    }

    public boolean isNeedToTranslate(String text) {
        DetectLanguageResponse response = yandexTranslateAPIIntegration.detectLanguage(text);
        return response != null && (!DEFAULT_LANGUAGE.equals(response.getLanguage()));
    }

    @Override
    public String translate(String text, String language) {
        String translated = EMPTY;

        if (StringUtils.isNoneBlank(text) && StringUtils.isNotBlank(language)) {
            TranslateTextResponse response = yandexTranslateAPIIntegration.translateText(text, language);
            if (response != null) {
                translated = String.join(EMPTY, response.getText());
            }
        }

        return translated.toLowerCase();
    }

    @Override
    public String detectLanguage(String text) {
        String result = EMPTY;

        DetectLanguageResponse response = yandexTranslateAPIIntegration.detectLanguage(text);
        if (response != null) {
            result = response.getLanguage();
        }

        return result;
    }
}
