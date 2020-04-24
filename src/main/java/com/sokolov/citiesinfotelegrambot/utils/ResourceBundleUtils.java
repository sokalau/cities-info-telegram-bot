package com.sokolov.citiesinfotelegrambot.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public final class ResourceBundleUtils {
    private static final String BUNDLE_NAME = "Messages";

    private ResourceBundleUtils() {
    }

    public static String getMessage(String key, Locale locale) {
        return ResourceBundle.getBundle(BUNDLE_NAME, locale).getString(key);
    }
}
