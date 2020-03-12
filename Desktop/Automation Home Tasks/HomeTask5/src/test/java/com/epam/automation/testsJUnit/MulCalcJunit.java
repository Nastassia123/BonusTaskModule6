package com.epam.automation.testsJUnit;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.junit.Assert;
import org.junit.Test;


public class MulCalcJunit extends BaseTest {

    @Test
    public void testMultiplicationOperationWithJUnit() {
        long result = calculator.mult(1, 100);
        Assert.assertTrue(result == 100);
    }
}
