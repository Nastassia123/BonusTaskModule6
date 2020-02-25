package com.epam.automation.testsJUnit;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.junit.Assert;
import org.junit.Test;


public class SubCalcJunit extends BaseTest {

    @Test
    public void FourDivideFour() {
        long result = calculator.sub(2, 1);
        Assert.assertTrue(result == 1);
    }
}
