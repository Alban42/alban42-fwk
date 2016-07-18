package com.alban42.classmanager.strategy;

import java.util.Set;

@MostValuableImplementation
public class FirstImplementationStrategy implements ClassImplementationStrategy {

    @Override
    public <T> Class<? extends T> getImplementation(Set<Class<? extends T>> implementations) {
        return implementations.stream().findFirst().orElse(null);
    }
}
