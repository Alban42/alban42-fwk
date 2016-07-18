package com.alban42.properties;

/**
 * Created by alban on 17/07/2016.
 *
 * @author alban
 */
public enum PropertiesEnum {
    ;

    private String key;
    private String defaultValue;

    PropertiesEnum(final String key, final String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String key() {
        return key;
    }

    public String defaultValue() {
        return defaultValue;
    }
}
