package com.alban42.classmanager.strategy.mvi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by alban on 18/07/2016.
 *
 * @author alban
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MostValuableImplementation {

    /**
     * @return return the value of the implementation.
     */
    int value() default 0;
}
