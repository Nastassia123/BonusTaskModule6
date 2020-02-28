package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class IsPositiveTest extends BaseTest {

    @Test(dataProvider = "ValuesForIsPositiveTest")
    public void testIsNegativeValueWithPositiveParamTestNG(long value, boolean expected){
        boolean result = calculator.isPositive(value);
        Assert.assertEquals(result, expected, "Is positive operation has been failed");
    }

    @DataProvider(name = "ValuesForIsPositiveTest")
    Object[][] IsNegative() {
        return new Object[][]{
                {-1, true},
                {45, true},
                {90, false},
                {-135, true},
                {0, false}
        };
    }
}