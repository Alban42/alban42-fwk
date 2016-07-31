package com.alban42.classmanager.invocation;

import com.alban42.classmanager.strategy.environment.EnvironmentEnum;
import com.alban42.classmanager.strategy.environment.EnvironmentSelection;
import com.alban42.classmanager.strategy.mvi.MostValuableImplementation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@EnvironmentSelection(environment = EnvironmentEnum.DEVELOPEMENT)
@MostValuableImplementation
public abstract class InvocationHandler42<T> implements InvocationHandler {

//    static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//    static Validator validator = factory.getValidator();

    protected T instance;

    /**
     * Set the instance of the class (T) where the method need to be invoke.
     *
     * @param instance the instance of type T.
     */
    public void setInstance(T instance) {
        this.instance = instance;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        beforeInvoke(method, args);
        Object result = method.invoke(instance, args);
        afterInvoke(method, args);
        return result;
    }

    /**
     * Method called <u>before</u> the invocation of the method with the given arguments.
     *
     * @param method the method to invoke.
     * @param args   the arguments passed to the method to invoke.
     */
    protected abstract void beforeInvoke(final Method method, final Object[] args);

    /**
     * Method called <u>after</u> the invocation of the method with the given arguments.
     *
     * @param method the method to invoke.
     * @param args   the arguments passed to the method to invoke.
     */
    protected abstract void afterInvoke(final Method method, final Object[] args);


}
