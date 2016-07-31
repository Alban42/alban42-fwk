package com.alban42.classmanager.strategy.environment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Determine the environment of the class.
 * To be used with the {@link com.alban42.classmanager.strategy.ClassImplementationStrategy}
 *
 * @author alban
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnvironmentSelection {

    /**
     * @return the environment to be used for the class.
     */
    EnvironmentEnum environment() default EnvironmentEnum.DEVELOPEMENT;
}
