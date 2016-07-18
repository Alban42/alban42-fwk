package com.alban42.properties;

/**
 * Created by alban on 17/07/2016.
 *
 * @author alban
 */
public interface IProperties {

    IProperties[] values();

    String key();

    String defaultValue();
}
