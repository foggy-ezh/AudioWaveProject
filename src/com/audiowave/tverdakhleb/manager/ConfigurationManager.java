package com.audiowave.tverdakhleb.manager;

import java.util.ResourceBundle;

public class ConfigurationManager {
    private static final String PATH_CONFIG = "properties.config";

    private ResourceBundle resourceBundle;

    public ConfigurationManager() {
        resourceBundle = ResourceBundle.getBundle(PATH_CONFIG);
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}