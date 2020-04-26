package com.sokolov.touristtelegrambot.util;

import java.util.Locale;
import java.util.ResourceBundle;

public final class ResourceBundleUtils {
    private ResourceBundleUtils() {
    }

    public static String getMessage(String bundleName, String key, Locale locale) {
        return ResourceBundle.getBundle(bundleName, locale).getString(key);
    }
}
