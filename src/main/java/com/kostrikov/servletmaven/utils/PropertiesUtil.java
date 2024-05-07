package com.kostrikov.servletmaven.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@UtilityClass
public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (var input = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return Objects.requireNonNullElseGet(PROPERTIES.getProperty(key), () -> "");
    }
}
