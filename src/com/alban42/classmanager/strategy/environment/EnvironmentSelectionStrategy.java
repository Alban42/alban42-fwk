package com.alban42.classmanager.strategy.environment;

import com.alban42.classmanager.strategy.ClassImplementationStrategy;
import com.alban42.classmanager.strategy.mvi.MostValuableImplementation;
import com.alban42.properties.Properties42;
import com.alban42.properties.Properties42FWK;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by alban on 20/07/2016.
 *
 * @author alban
 */
@MostValuableImplementation(value = 3)
public class EnvironmentSelectionStrategy implements ClassImplementationStrategy {

    @Override
    public <T> Class<? extends T> getImplementation(final Set<Class<? extends T>> implementations) {

        final EnvironmentEnum env = EnvironmentEnum.valueOf(Properties42.getProperty(Properties42FWK.ENVIRONEMENT));

        Comparator<Class<? extends T>> comparator = (x, y) -> {
            final MostValuableImplementation anno1 = x.getAnnotation(MostValuableImplementation.class);
            final MostValuableImplementation anno2 = y.getAnnotation(MostValuableImplementation.class);
            return (anno1 == null ? 0 : anno1.value()) - (anno2 == null ? 0 : anno2.value());
        };

        Predicate<Class<? extends T>> filter = x -> {
            boolean result = false;
            final EnvironmentSelection anno = x.getAnnotation(EnvironmentSelection.class);
            if (anno != null && anno.environment().equals(env)) {
                result = true;
            }
            return result;
        };

        return implementations.stream().filter(filter).max(comparator).orElse(null);
    }
}
