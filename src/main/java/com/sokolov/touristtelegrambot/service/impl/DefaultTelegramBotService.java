package com.sokolov.touristtelegrambot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.sokolov.touristtelegrambot.entity.CityInfo;
import com.sokolov.touristtelegrambot.integration.GeoDBCitiesIntegration;
import com.sokolov.touristtelegrambot.model.CityDetails;
import com.sokolov.touristtelegrambot.model.Message;
import com.sokolov.touristtelegrambot.model.Update;
import com.sokolov.touristtelegrambot.repository.CityInfoRepository;
import com.sokolov.touristtelegrambot.service.TelegramBotService;
import com.sokolov.touristtelegrambot.utils.ResourceBundleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class DefaultTelegramBotService implements TelegramBotService {
    private static final String DEFAULT_MESSAGE_BUNDLE_KEY = "defaultMessage";

    @Value("${defaultMessage}")
    private String defaultMessage;

    private final TelegramBot bot;
    private final CityInfoRepository repository;
    private final GeoDBCitiesIntegration geoDBCitiesIntegration;

    public DefaultTelegramBotService(TelegramBot bot, CityInfoRepository repository,
                                     GeoDBCitiesIntegration geoDBCitiesIntegration) {
        this.bot = bot;
        this.repository = repository;
        this.geoDBCitiesIntegration = geoDBCitiesIntegration;
    }

    @Override
    public void reply(Update update) {
        Message message = update.getMessage();

        if (message != null) {
            Long chatId = message.getChat().getId();
            String text = message.getText();

            if (chatId != null && StringUtils.isNoneBlank(text)) {
                String preparedText = text.trim().toLowerCase();

                CityInfo cityInfo = repository.findByName(preparedText);

                //TODO: integration with GeoDB Cities REST API
                if (cityInfo == null) {
                    CityDetails cityDetails = geoDBCitiesIntegration.getCityDetails(preparedText);
                }

                String answer = getAnswer(cityInfo, message.getFrom().getLanguageCode());

                bot.execute(new SendMessage(chatId, answer));
            }
        }
    }

    private String getAnswer(CityInfo cityInfo, String languageCode) {
        String answer = getDefaultMessage(languageCode);

        if (cityInfo != null) {
            String description = cityInfo.getDescription();
            if (StringUtils.isNotBlank(description)) {
                answer = description;
            }
        }

        return answer;
    }

    private String getDefaultMessage(String languageCode) {
        return StringUtils.isNotBlank(languageCode)
                ? ResourceBundleUtils.getMessage(DEFAULT_MESSAGE_BUNDLE_KEY, new Locale(languageCode))
                : defaultMessage;
    }
}
