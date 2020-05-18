package com.sokolov.touristtelegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.sokolov.touristtelegrambot.dto.telegram.Update;
import com.sokolov.touristtelegrambot.entity.CityInfo;
import com.sokolov.touristtelegrambot.repository.CityInfoRepository;
import com.sokolov.touristtelegrambot.service.integration.geodb.GeoDBCitiesIntegration;
import com.sokolov.touristtelegrambot.service.integration.yandex.YandexTranslateIntegration;
import com.sokolov.touristtelegrambot.util.ResourceBundleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class DefaultTelegramBotService implements TelegramBotService {
    private static final String DEFAULT_MESSAGE_BUNDLE_KEY = "defaultMessage";
    private static final String MESSAGES_BUNDLE = "Messages";
    private static final String DEFAULT_LANGUAGE_CODE = "ru";
    private static final String ENGLISH_LANGUAGE_CODE = "en";

    private final TelegramBot bot;
    private final CityInfoRepository repository;
    private final GeoDBCitiesIntegration geoDBCitiesIntegration;
    private final YandexTranslateIntegration yandexTranslateIntegration;

    @Autowired
    public DefaultTelegramBotService(TelegramBot bot, CityInfoRepository repository,
                                     GeoDBCitiesIntegration geoDBCitiesIntegration,
                                     YandexTranslateIntegration yandexTranslateIntegration) {
        this.bot = bot;
        this.repository = repository;
        this.geoDBCitiesIntegration = geoDBCitiesIntegration;
        this.yandexTranslateIntegration = yandexTranslateIntegration;
    }

    @Override
    public void reply(Update update) {
        var message = update.getMessage();

        if (message != null) {
            var chatId = message.getChat().getId();
            var rawText = message.getText();

            if (chatId != null && StringUtils.isNoneBlank(rawText)) {
                var text = rawText.trim().toLowerCase();
                var language = yandexTranslateIntegration.detectLanguage(text);

                var isNeedToTranslate = isNeedToTranslate(text);

                if (isNeedToTranslate) {
                    var translated = yandexTranslateIntegration.translate(
                            rawText, DEFAULT_LANGUAGE_CODE);
                    if (StringUtils.isNotBlank(translated)) {
                        text = translated;
                    }
                }

                var cityInfo = repository.findByName(text);
                var englishName = yandexTranslateIntegration.translate(
                        text, ENGLISH_LANGUAGE_CODE);
                var answer = getAnswer(englishName, cityInfo, language, isNeedToTranslate);

                bot.execute(new SendMessage(chatId, answer));
            }
        }
    }

    private boolean isNeedToTranslate(String text) {
        var language = yandexTranslateIntegration.detectLanguage(text);
        return !StringUtils.isEmpty(language) && (!ENGLISH_LANGUAGE_CODE.equals(language));
    }

    private String getAnswer(String name, CityInfo cityInfo, String languageCode,
                             boolean isNeedToTranslateDescription) {
        var answer = getDefaultMessage(languageCode);

        if (cityInfo != null) {
            var description = cityInfo.getDescription();
            if (StringUtils.isNotBlank(description)) {
                answer = isNeedToTranslateDescription
                        ? yandexTranslateIntegration.translate(description, languageCode)
                        : description;
            }
        } else {
            var geoDBCitiesDescription = geoDBCitiesIntegration.getDescription(name, languageCode);
            if (StringUtils.isNotBlank(geoDBCitiesDescription)) {
                answer = geoDBCitiesDescription;
            }
        }

        return answer;
    }

    private String getDefaultMessage(String languageCode) {
        return ResourceBundleUtils.getMessage(
                MESSAGES_BUNDLE, DEFAULT_MESSAGE_BUNDLE_KEY, new Locale(languageCode));
    }
}
