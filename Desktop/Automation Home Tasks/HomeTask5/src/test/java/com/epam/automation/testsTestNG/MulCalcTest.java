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
                {45.5, 1.0, 45.5},
                {90.0, 10.0, 900.0},
                {135.0, -1.0, -135.0},
                {0, 120.0, 0}
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
                {1, -1, 1},
                {-45, 1, 45},
                {0, 0, 10},
                {135, -1, -135},
                {0, 120, 0}
        };
    }
}
