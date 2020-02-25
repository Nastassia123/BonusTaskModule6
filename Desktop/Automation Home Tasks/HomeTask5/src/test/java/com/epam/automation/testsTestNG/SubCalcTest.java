package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SubCalcTest extends BaseTest {

    @Test(groups = "divisionTest", dataProvider = "longParamsForSub")
    public void testSubWithLongValues(long a, long b, long expected) {
        long result = calculator.sub(a, b);
        Assert.assertEquals(result, expected, "Invalid result value of sub operation with long values");
    }

    @DataProvider(name = "longParamsForSub")
    Object[][] longValuesForSub() {
        return new Object[][]{
                {1, 1, 0},
                {45, 1, 44},
                {90, 0, 90},
                {135, -1, 136},
                {0, 120, -120}
        };
    }

    @Test(groups = "divisionTest", dataProvider = "doubleParamsForSub")
    public void testSubWithLongValues(double a, double b, double expected) {
        double result = calculator.sub(a, b);
        Assert.assertEquals(result, expected, "Invalid result value of sub operation with double values");
    }

    @DataProvider(name = "doubleParamsForSub")
    Object[][] doubleValuesForSub() {
        return new Object[][]{
                {1.5, 1, 0.5},
                {45.7, 1.0, 44.7},
                {-90.5, -1.5, -89.0},
                {135.5, -1, 136.5},
                {0.0, 120.0, -120.0}
        };
    }
}
