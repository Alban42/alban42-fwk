package com.alban42.classmanager;

import com.alban42.classmanager.strategy.MostValuableImplementation;

@MostValuableImplementation(value = 2)
public class Test3 implements ITest {

    @Override
    public String testMethod(final String stg) {
        return "Test3 " + stg;
    }

}
