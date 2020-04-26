package com.sokolov.touristtelegrambot.integration.impl;

import com.sokolov.touristtelegrambot.dto.yandextranslate.DetectLanguageResponse;
import com.sokolov.touristtelegrambot.dto.yandextranslate.TranslateTextResponse;
import com.sokolov.touristtelegrambot.integration.YandexTranslateAPIIntegration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DefaultYandexTranslateAPI implements YandexTranslateAPIIntegration {
    private static final String DETECT_LANGUAGE_URL = "https://translate.yandex.net/api/v1.5/tr.json/detect?key=%s&text=%s";
    private static final String TRANSLATE_TEXT_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=%s&text=%s&lang=%s";

    @Value("${yandextranslate.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public DefaultYandexTranslateAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public DetectLanguageResponse detectLanguage(String text) {
        DetectLanguageResponse response = new DetectLanguageResponse();

        if (StringUtils.isNoneBlank(text) && StringUtils.isNoneBlank(apiKey)) {
            String url = String.format(DETECT_LANGUAGE_URL, apiKey, text);
            ResponseEntity<DetectLanguageResponse> responseEntity = restTemplate.getForEntity(
                    url, DetectLanguageResponse.class);
            response = responseEntity.getBody();
        }

        return response;
    }

    @Override
    public TranslateTextResponse translateText(String text, String language) {
        TranslateTextResponse response = new TranslateTextResponse();

        if (StringUtils.isNoneBlank(text) && StringUtils.isNoneBlank(language)
                && StringUtils.isNoneBlank(apiKey)) {
            String url = String.format(TRANSLATE_TEXT_URL, apiKey, text, language);
            ResponseEntity<TranslateTextResponse> responseEntity = restTemplate.getForEntity(
                    url, TranslateTextResponse.class);
            response = responseEntity.getBody();
        }

        return response;
    }
}
