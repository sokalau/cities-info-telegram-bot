package com.sokolov.touristtelegrambot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.sokolov.touristtelegrambot.dto.telegram.Message;
import com.sokolov.touristtelegrambot.dto.telegram.Update;
import com.sokolov.touristtelegrambot.entity.CityInfo;
import com.sokolov.touristtelegrambot.repository.CityInfoRepository;
import com.sokolov.touristtelegrambot.service.GeoDBCitiesService;
import com.sokolov.touristtelegrambot.service.TelegramBotService;
import com.sokolov.touristtelegrambot.service.TranslateService;
import com.sokolov.touristtelegrambot.util.ResourceBundleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class DefaultTelegramBotService implements TelegramBotService {
    private static final String DEFAULT_MESSAGE_BUNDLE_KEY = "defaultMessage";
    private static final String MESSAGES_BUNDLE = "Messages";

    private final TelegramBot bot;
    private final CityInfoRepository repository;
    private final TranslateService translateService;
    private final GeoDBCitiesService geoDBCitiesService;

    public DefaultTelegramBotService(TelegramBot bot, CityInfoRepository repository,
                                     TranslateService translateService,
                                     GeoDBCitiesService geoDBCitiesService) {
        this.bot = bot;
        this.repository = repository;
        this.translateService = translateService;
        this.geoDBCitiesService = geoDBCitiesService;
    }

    @Override
    public void reply(Update update) {
        Message message = update.getMessage();

        if (message != null) {
            Long chatId = message.getChat().getId();
            String text = message.getText();

            if (chatId != null && StringUtils.isNoneBlank(text)) {
                String normalizedText = text.trim().toLowerCase();
                String language = translateService.detectLanguage(normalizedText);

                boolean isNeedToTranslate = translateService.isNeedToTranslate(normalizedText);

                if (isNeedToTranslate) {
                    String translated = translateService.translate(
                            text, DefaultTranslateService.DEFAULT_LANGUAGE);
                    if (StringUtils.isNotBlank(translated)) {
                        normalizedText = translated;
                    }
                }

                CityInfo cityInfo = repository.findByName(normalizedText);
                String englishName = translateService.translate(
                        normalizedText, DefaultTranslateService.ENGLISH_LANGUAGE);
                String answer = getAnswer(englishName, cityInfo, language, isNeedToTranslate);

                bot.execute(new SendMessage(chatId, answer));
            }
        }
    }

    private String getAnswer(String name, CityInfo cityInfo, String languageCode,
                             boolean isNeedToTranslateDescription) {
        String answer = getDefaultMessage(languageCode);

        if (cityInfo != null) {
            String description = cityInfo.getDescription();
            if (StringUtils.isNotBlank(description)) {
                answer = isNeedToTranslateDescription
                        ? translateService.translate(description, languageCode)
                        : description;
            }
        } else {
            String geoDBCitiesDescription = geoDBCitiesService.getDescription(name, languageCode);
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
