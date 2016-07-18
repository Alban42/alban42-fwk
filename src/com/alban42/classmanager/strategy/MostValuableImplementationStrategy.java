package com.alban42.classmanager.strategy;

import java.util.Comparator;
import java.util.Set;

/**
 * Created by alban on 18/07/2016.
 *
 * @author alban
 */
@MostValuableImplementation(value = 2)
public class MostValuableImplementationStrategy implements ClassImplementationStrategy {

    @Override
    public <T> Class<? extends T> getImplementation(final Set<Class<? extends T>> implementations) {

        Comparator<Class<? extends T>> comparator = (x, y) -> {
            final MostValuableImplementation imp1 = x.getAnnotation(MostValuableImplementation.class);
            final MostValuableImplementation imp2 = y.getAnnotation(MostValuableImplementation.class);
            return (imp1 == null ? 0 : imp1.value()) - (imp2 == null ? 0 : imp2.value());
        };

        return implementations.stream().max(comparator).orElse(null);
    }
}
