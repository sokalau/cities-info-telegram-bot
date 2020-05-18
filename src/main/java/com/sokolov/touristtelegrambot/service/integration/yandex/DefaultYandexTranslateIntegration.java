package com.sokolov.touristtelegrambot.service.integration.yandex;

import com.sokolov.touristtelegrambot.dto.yandextranslate.DetectLanguageResponse;
import com.sokolov.touristtelegrambot.dto.yandextranslate.TranslateTextResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class DefaultYandexTranslateIntegration implements YandexTranslateIntegration {
    public static final String DETECT_LANGUAGE_URL
            = "https://translate.yandex.net/api/v1.5/tr.json/detect?key=%s&text=%s";
    private static final String TRANSLATE_TEXT_URL
            = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=%s&text=%s&lang=%s";

    @Value("${yandex.translate.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public DefaultYandexTranslateIntegration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String detectLanguage(String text) {
        var language = EMPTY;

        if (StringUtils.isNoneBlank(text) && StringUtils.isNoneBlank(apiKey)) {
            var url = String.format(DETECT_LANGUAGE_URL, apiKey, text);
            var response = restTemplate.getForObject(url, DetectLanguageResponse.class);
            if (response != null) {
                language = response.getLanguage();
            }
        }

        return language;
    }

    @Override
    public String translate(String text, String language) {
        var translated = EMPTY;

        if (StringUtils.isNoneBlank(text) && StringUtils.isNoneBlank(language)
                && StringUtils.isNoneBlank(apiKey)) {
            var url = String.format(TRANSLATE_TEXT_URL, apiKey, text, language);
            var response = restTemplate.getForObject(url, TranslateTextResponse.class);

            if (response != null) {
                translated = String.join(EMPTY, response.getText()).toLowerCase();
            }
        }

        return translated;
    }
}
