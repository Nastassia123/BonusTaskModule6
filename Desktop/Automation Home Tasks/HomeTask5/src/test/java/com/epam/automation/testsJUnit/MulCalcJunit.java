package com.epam.automation.testsJUnit;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.junit.Assert;
import org.junit.Test;


public class MulCalcJunit extends BaseTest {

    @Test
    public void FourDivideFour() {
        long result = calculator.mult(1, 2);
        Assert.assertTrue(result == 2);
    }
}
