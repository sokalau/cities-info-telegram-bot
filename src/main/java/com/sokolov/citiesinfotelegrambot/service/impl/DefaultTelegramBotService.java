package com.sokolov.citiesinfotelegrambot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.sokolov.citiesinfotelegrambot.model.Message;
import com.sokolov.citiesinfotelegrambot.model.Update;
import com.sokolov.citiesinfotelegrambot.model.entity.CityInfo;
import com.sokolov.citiesinfotelegrambot.repository.CityInfoRepository;
import com.sokolov.citiesinfotelegrambot.service.TelegramBotService;
import com.sokolov.citiesinfotelegrambot.utils.ResourceBundleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class DefaultTelegramBotService implements TelegramBotService {
    private static final String NO_INFO_ABOUT_THIS_CITY_DEFAULT
            = "There is no information about this city in my database. Please try another one.";
    private static final String NO_INFO_RESOURCE_BUNDLE_KEY = "noInformation";

    private final TelegramBot bot;
    private final CityInfoRepository repository;

    public DefaultTelegramBotService(TelegramBot bot, CityInfoRepository repository) {
        this.bot = bot;
        this.repository = repository;
    }

    @Override
    public void reply(Update update) {
        Message message = update.getMessage();

        if (message != null) {
            Long chatId = message.getChat().getId();
            String text = message.getText();
            if (chatId != null && StringUtils.isNoneBlank(text)) {
                List<CityInfo> citiesInfo = repository.findAll();
                String answer = getAnswer(citiesInfo, text, message.getFrom().getLanguageCode());
                bot.execute(new SendMessage(chatId, answer));
            }
        }
    }

    //TODO: Resolve issue with cyrillic values stored in db
    private String getAnswer(List<CityInfo> citiesInfo, String text, String languageCode) {
        String answer = getNoSuchInfoMessage(NO_INFO_ABOUT_THIS_CITY_DEFAULT, languageCode);

        if (!CollectionUtils.isEmpty(citiesInfo)) {
            Optional<CityInfo> cityInfo = citiesInfo.stream()
                    .filter(ci -> ci.getName().equals(text.trim().toLowerCase()))
                    .findFirst();
            if (cityInfo.isPresent()) {
                String description = cityInfo.get().getDescription();
                if (StringUtils.isNotBlank(description)) {
                    answer = description;
                }
            }
        }

        return answer;
    }

    private String getNoSuchInfoMessage(String key, String languageCode) {
        return StringUtils.isNotBlank(languageCode)
                ? ResourceBundleUtils.getMessage(NO_INFO_RESOURCE_BUNDLE_KEY, new Locale(languageCode))
                : key;
    }
}
