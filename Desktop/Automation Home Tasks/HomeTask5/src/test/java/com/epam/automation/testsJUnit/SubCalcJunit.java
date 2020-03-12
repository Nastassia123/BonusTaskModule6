package com.epam.automation.testsJUnit;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.junit.Assert;
import org.junit.Test;


public class SubCalcJunit extends BaseTest {

    @Test
    public void testSubstructionOperationWithJUnit() {
        long result = calculator.sub(2, 23);
        Assert.assertTrue(result == -21);
    }
}
