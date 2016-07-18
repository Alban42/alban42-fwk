package com.alban42.classmanager.strategy;

import java.util.Set;

@FunctionalInterface
public interface ClassImplementationStrategy {

    <T> Class<? extends T> getImplementation(Set<Class<? extends T>> implementations);
}
