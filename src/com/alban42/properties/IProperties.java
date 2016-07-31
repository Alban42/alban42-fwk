package com.alban42.properties;

/**
 * Created by alban on 17/07/2016.
 *
 * @author alban
 */
public interface IProperties {

    /**
     * @return the file name of the property file.
     */
    static String getPropertyFile() {
        return "alban42.properties";
    }

    /**
     * @return the name of the property (aka the key)
     */
    String name();

    /**
     * @return the default value of the property (to be initialize)
     */
    String defaultValue();

    /**
     * @return the comment associated with the property.
     */
    String getComment();
}
