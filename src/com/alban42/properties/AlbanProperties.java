package com.alban42.properties;

import java.util.Properties;

/**
 * Created by alban on 17/07/2016.
 *
 * @author alban
 */
public class AlbanProperties {

    private static String PROPERTY_FILE = ".properties";
    private static Properties properties;
    private static IProperties iproperties;

    public void init(IProperties properties) {
        iproperties = properties;

        for (IProperties element : properties.values()) {
            getProperties().setProperty(element.key(), element.defaultValue());

        }

//        getProperties().store(getClass().getResourceAsStream(PROPERTY_FILE), null);
    }

    private synchronized Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
        }
        return properties;
    }

}
