package com.webshoppe.ecommerce.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

// reads the properties file (messages.properties)
public class AppConfiguration {
    private static final String BUNDLE_NAME = "database";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private AppConfiguration() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
