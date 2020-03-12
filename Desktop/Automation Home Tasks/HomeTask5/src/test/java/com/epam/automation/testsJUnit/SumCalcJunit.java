package com.epam.automation.testsJUnit;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.junit.Assert;
import org.junit.Test;


public class SumCalcJunit extends BaseTest {

    @Test
    public void testSummationOperationWithJUnit() {
        long result = calculator.sum(4, -4);
        Assert.assertTrue(result == 0);
    }
}
