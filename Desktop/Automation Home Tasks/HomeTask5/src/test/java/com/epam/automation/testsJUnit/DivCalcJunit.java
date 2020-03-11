package com.epam.automation.testsJUnit;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.junit.Assert;
import org.junit.Test;


public class DivCalcJunit extends BaseTest {

    @Test
    public void testDivisionOperationWithJUnit() {
        long result = calculator.div(4, 1);
        Assert.assertTrue(result == 4);
    }
}
