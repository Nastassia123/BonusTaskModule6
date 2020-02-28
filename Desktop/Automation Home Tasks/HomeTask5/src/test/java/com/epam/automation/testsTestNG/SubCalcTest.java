package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SubCalcTest extends BaseTest {

    @Test(groups = "divisionTest", dataProvider = "longParamsForSub")
    public void testSubstractionWithLongValuesTestNG(long a, long b, long expected) {
        long result = calculator.sub(a, b);
        Assert.assertEquals(result, expected, "Invalid result value of sub operation with long values");
    }

    @DataProvider(name = "longParamsForSub")
    Object[][] longValuesForSub() {
        return new Object[][]{
                {-1, -1, 2},
                {-3, 1, 2},
                {90, -45, 90},
                {135, 0, 136},
                {0, 120, -120}
        };
    }

    @Test(groups = "divisionTest", dataProvider = "doubleParamsForSub")
    public void testSubstractionWithDoubleValuesTestNG(double a, double b, double expected) {
        double result = calculator.sub(a, b);
        Assert.assertEquals(result, expected, "Invalid result value of sub operation with double values");
    }

    @DataProvider(name = "doubleParamsForSub")
    Object[][] doubleValuesForSub() {
        return new Object[][]{
                {-1.0, -1.0, 2.0},
                {-3.9, 1.5, 2.0},
                {90.0, -45.0, 90.0},
                {135.0, 0.0, 136.0},
                {0.0, 120.0, -120.0}
        };
    }
}
