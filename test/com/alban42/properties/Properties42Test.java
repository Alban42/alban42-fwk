package com.alban42.properties;

import com.alban42.classmanager.strategy.environment.EnvironmentEnum;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by alban on 23/07/2016.
 *
 * @author alban
 */
public class Properties42Test {

    @Test
    public void initProperties() {
        final String property = Properties42.getProperty(Properties42FWK.ENVIRONEMENT);
        Assert.assertTrue(EnvironmentEnum.DEVELOPEMENT.equals(EnvironmentEnum.valueOf(property)));
    }

    @Test
    public void getAllProperties() {
        final String property = Properties42.getProperty(Properties42FWK.ENVIRONEMENT);
        Assert.assertTrue(EnvironmentEnum.DEVELOPEMENT.equals(EnvironmentEnum.valueOf(property)));

        Assert.assertTrue(PropertiesEnumTest.TEST2.defaultValue().equals(Properties42.getProperty(PropertiesEnumTest.TEST2)));
    }

    @Test
    public void setNewProperties() {
        Assert.assertTrue(PropertiesEnumTest.TEST2.defaultValue().equals(Properties42.getProperty(PropertiesEnumTest.TEST2)));
        final String newValue = "new Test2";
        Properties42.setProperty(PropertiesEnumTest.TEST2, newValue);
        Assert.assertTrue(newValue.equals(Properties42.getProperty(PropertiesEnumTest.TEST2)));
    }
}
