package com.alban42.classmanager;

import com.alban42.classmanager.invocation.InvocationHandler42;
import com.alban42.classmanager.strategy.ClassImplementationStrategy;
import com.alban42.classmanager.strategy.mvi.MostValuableImplementationStrategy;
import com.esotericsoftware.minlog.Log;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class allow to instantiate the related class given an interface. Different strategies {@link ClassImplementationStrategy} can be applied in
 * order to select the class to instantiate.
 *
 * @author Alban
 */
public class ClassManager {

    public static Reflections reflections;

    /**
     * Get a new instance of the interface given in parameter. The {@link InvocationHandler42} will be the proxy used to execute the methods of the
     * implementation.
     *
     * @param clazz the interface to instantiate.
     * @return a new instance of the clazz.
     */
    public static <T> T getInstance(final Class<? extends T> clazz) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{clazz}, getInvocationHandler(clazz));
    }

    /**
     * Get a new instance of the interface given in parameter.
     *
     * @param clazz the interface to instantiate.
     * @return a new instance of the clazz.
     */
    public static <T> T getNewInstanceOf(final Class<T> clazz) {
        final Set<Class<? extends T>> implementations = getSubTypesOf(clazz);
        final Class<T> clz = (Class<T>) getClassImplementationStrategy().getImplementation(implementations);
        return newInstanceOrNull(clz);
    }

    /**
     * Return a {@link Set} of classes implementing the clazz in param.
     *
     * @param clazz the class type.
     * @param <T> the type of the clazz.
     * @return a {@link Set} of classes.
     */
    public static <T> Set<Class<? extends T>> getSubTypesOf(final Class<T> clazz) {
        return getNewReflectionsInstance().getSubTypesOf(clazz);
    }

    /**
     * This methods create a new {@link Reflections} instance (singleton).
     *
     * @return a new instance of {@link Reflections}.
     */
    private static Reflections getNewReflectionsInstance() {
        if (reflections == null) {
            final int availableProcessors = Runtime.getRuntime().availableProcessors();
            final ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);
            final ClassLoader classLoader = ClassManager.class.getClassLoader();
            final Set<URL> forClassLoader = new HashSet<>(ClasspathHelper.forClassLoader(classLoader));
            reflections = new Reflections(new ConfigurationBuilder().addUrls(forClassLoader).setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()).setExecutorService(executorService));
            executorService.shutdown();
        }
        return reflections;
    }

    /**
     * Return a new instance of given class in parameter.
     *
     * @param clazz the class to instantiate.
     * @return the instance.
     */
    public static <T extends Object> T newInstanceOrNull(final Class<T> clazz) {
        T newInstance = null;
        if (clazz != null) {
            try {
                newInstance = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                Log.error(new StringBuilder("Error during the creation of instance of ").append(clazz.getSimpleName()).toString(), e);
            }
        }
        return newInstance;
    }

    /**
     * Return an instance of {@link ClassImplementationStrategy} using the defined {@link ClassImplementationStrategy}.
     *
     * @return a new instance of {@link ClassImplementationStrategy} null if none is founded.
     */
    public static ClassImplementationStrategy getClassImplementationStrategy() {
        final Set<Class<? extends ClassImplementationStrategy>> implementations = getNewReflectionsInstance().getSubTypesOf(ClassImplementationStrategy.class);
        return newInstanceOrNull(new MostValuableImplementationStrategy().getImplementation(implementations));
    }

    /**
     * Return an instance of {@link InvocationHandler42} using the {@link MostValuableImplementationStrategy}.
     *
     * @return a new instance of {@link ClassImplementationStrategy} null if none is founded.
     */
    public static <T> InvocationHandler42 getInvocationHandler(final Class<? extends T> clazz) {
        final InvocationHandler42 invocationHandler = ClassManager.getNewInstanceOf(InvocationHandler42.class);
        invocationHandler.setInstance(getNewInstanceOf(clazz));
        return invocationHandler;
    }
}
