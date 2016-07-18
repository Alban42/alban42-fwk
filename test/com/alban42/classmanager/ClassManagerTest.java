package com.alban42.classmanager;

import org.junit.Assert;
import org.junit.Test;

public class ClassManagerTest {

    @Test
    public void classManager() throws Exception {
        final ITest test = ClassManager.getInstance(ITest.class);
        final String alban = "Alban";
        Assert.assertTrue(test.testMethod(alban).equals("Test3 " + alban));
    }
}
