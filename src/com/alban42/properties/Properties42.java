package com.alban42.properties;

import com.alban42.classmanager.ClassManager;
import com.esotericsoftware.minlog.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by alban on 17/07/2016.
 *
 * @author alban
 */
public class Properties42 {

    protected static Properties properties;

    public static String getProperty(IProperties property) {
        return getProperties().getProperty(property.name());
    }

    public static void setProperty(IProperties property, String value) {
        // set the property
        getProperties().setProperty(property.name(), value);

        // save the property into the file
        FileOutputStream out = null;
        String propertyFileName = null;
        try {
            propertyFileName = getPropertyFileName(property.getClass());
            out = new FileOutputStream(propertyFileName);
            getProperties().store(out, property.getComment());
        } catch (java.io.IOException e) {
            Log.error(new StringBuilder("Cannot initialize the property file : ").append(propertyFileName).toString(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (final IOException e) {
                    Log.error(new StringBuilder("Cannot close the property file : ").append(propertyFileName).toString(), e);
                }
            }
        }
    }

    protected static void init(Class<IProperties> properties) {
        FileOutputStream out = null;
        String propertyFileName = null;
        try {
            propertyFileName = getPropertyFileName(properties);

            final File file = new File(propertyFileName);
            // If the file doesn't exist, then we create it and initialise it.
            if (!file.isFile()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            for (IProperties element : getPropertiesConstants(properties)) {
                if (!getProperties().contains(element.name())) {
                    getProperties().setProperty(element.name(), element.defaultValue());
                }
            }
            getProperties().store(out, null);
        } catch (java.io.IOException e) {
            Log.error(new StringBuilder("Cannot initialize the property file : ").append(propertyFileName).toString(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (final IOException e) {
                    Log.error(new StringBuilder("Cannot close the property file : ").append(propertyFileName).toString(), e);
                }
            }
        }
    }

    private static String getPropertyFileName(final Class<? extends IProperties> properties) {
        String propertyFileName = null;
        try {
            final String methodName = "getPropertyFile";
            // if the properties class override the method "getPropertyFile" then we use it, otherwise we call the method from IProperties
            if (Arrays.stream(properties.getMethods()).anyMatch(x -> x.getName().equals(methodName))) {
                propertyFileName = (String) properties.getMethod(methodName).invoke(null, null);
            } else {
                propertyFileName = (String) IProperties.class.getMethod(methodName).invoke(null, null);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            Log.error(new StringBuilder("Cannot invoke the method 'getPropertyFile' on ").append(properties.getSimpleName()).toString(), e);
        }
        return propertyFileName;
    }

    protected static synchronized Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            init(IProperties.class);
        }
        return properties;
    }

    private static List<IProperties> getPropertiesConstants(Class<IProperties> properties) {
        final List<IProperties> results = new ArrayList<>(0);
        final Set<Class<? extends IProperties>> enums = ClassManager.getSubTypesOf(properties);
        enums.stream().filter(x -> x.isEnum()).forEach(x -> results.addAll(Arrays.asList(x.getEnumConstants())));
        return results;
    }

}
