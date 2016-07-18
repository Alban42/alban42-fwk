package com.alban42.classmanager;

import com.alban42.classmanager.strategy.MostValuableImplementation;

@MostValuableImplementation
public class Test1 implements ITest {

    @Override
    public String testMethod(final String stg) {
        return "Test1 " + stg;
    }

}
