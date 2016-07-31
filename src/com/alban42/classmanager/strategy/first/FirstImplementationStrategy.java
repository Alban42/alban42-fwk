package com.alban42.classmanager.strategy.first;

import com.alban42.classmanager.strategy.ClassImplementationStrategy;
import com.alban42.classmanager.strategy.mvi.MostValuableImplementation;

import java.util.Set;

@MostValuableImplementation
public class FirstImplementationStrategy implements ClassImplementationStrategy {

    @Override
    public <T> Class<? extends T> getImplementation(Set<Class<? extends T>> implementations) {
        return implementations.stream().findFirst().orElse(null);
    }
}
