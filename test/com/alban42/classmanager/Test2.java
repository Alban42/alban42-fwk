package com.alban42.classmanager;

import com.alban42.classmanager.strategy.MostValuableImplementation;

@MostValuableImplementation(value = 1)
public class Test2 implements ITest {

    @Override
    public String testMethod(final String stg) {
        return "Test2 " + stg;
    }

}
