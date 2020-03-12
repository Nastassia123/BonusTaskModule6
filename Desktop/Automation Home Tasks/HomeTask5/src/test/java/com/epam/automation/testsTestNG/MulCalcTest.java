package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MulCalcTest extends BaseTest {


    @Test(groups = "TestDivision", dataProvider = "DoubleValuesForMultiplication")
    public void testMulOperationWithDoubleValuesTestNG(double a, double b, double expected) {
        double result = calculator.mult(a, b);
        Assert.assertEquals(result, expected, "Incorrect result of multiplication operation with double params");
    }


    @DataProvider(name = "DoubleValuesForMultiplication")
    Object[][] DoubleValuesForDivision() {
        return new Object[][]{
                {1.0, 1.0, 1.0},
                {135.0, 0, 0},
                {0, -120.0, 0},
                {-10.0, -12.0, 120},
                {-10, 3, -30}
        };
    }

    @Test(groups = "TestDivision", dataProvider = "LongValuesForMultiplication")
    public void testMulOperationWithLongValuesTestNG(long a, long b, long expected) {
        long result = calculator.mult(a, b);
        Assert.assertEquals(result, expected, "Incorrect result of multiplication operation with long params");
    }


    @DataProvider(name = "LongValuesForMultiplication")
    Object[][] LongValuesForDivision() {
        return new Object[][]{
                {10000, -10000, -100000000},
                {-45454545, 1, -45454545},
                {0, 0, 0},
                {0, 1200000, 0},
                {121212, 12000, 1454544000}
        };
    }
}
