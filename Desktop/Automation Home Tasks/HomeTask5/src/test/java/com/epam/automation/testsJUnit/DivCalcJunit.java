package com.epam.automation.testsJUnit;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.junit.Assert;
import org.junit.Test;


public class DivCalcJunit extends BaseTest {

    @Test
    public void FourDivideFour() {
        long result = calculator.div(4, 4);
        Assert.assertTrue(result == 1);
    }
}
