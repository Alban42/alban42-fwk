package com.alban42.properties;

import com.alban42.classmanager.strategy.environment.EnvironmentEnum;

/**
 * Created by alban on 17/07/2016.
 *
 * @author alban
 */
public enum Properties42FWK implements IProperties {

    ENVIRONEMENT(EnvironmentEnum.DEVELOPEMENT.name(), "Set here the environment variable to be used in this application. Refer to the EnvironmentEnum for more details.");

    private String defaultValue;
    private String comment;

    Properties42FWK(final String defaultValue, final String comment) {
        this.defaultValue = defaultValue;
        this.comment = comment;
    }

    @Override
    public String defaultValue() {
        return defaultValue;
    }

    @Override
    public String getComment() {
        return comment;
    }
}
