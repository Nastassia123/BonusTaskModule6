package com.epam.automation.testsTestNG;


import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SumCalcTest extends BaseTest {

    @Test(dataProvider = "LongParamsForSumTest")
    public void twoPlusOneTest(long a, long b,long expectedResult) {
        long result = calculator.sum(a, b);
        Assert.assertEquals(result, expectedResult,  "Invalid result value of sum operation with long params");
    }

    @DataProvider(name = "LongParamsForSumTest")
    public Object[][] valuesForSum() {
        return new Object[][]{
                {1, 2, 3},
                {3, -5, -2},
                {0, 1, 1},
                {310, -10, 300},
                {-10, -11, -21},
        };
    }

    @Test(dataProvider = "DoubleParamsForSumTest")
    public void twoPlusOneTest(double a, double b, double expectedResult) {
        double result = calculator.sum(a, b);
        Assert.assertEquals(result, expectedResult,  "Invalid result value of sum operation with double params");
    }

    @DataProvider(name = "DoubleParamsForSumTest")
    public Object[][] DoublevaluesForSum() {
        return new Object[][]{
                {1.0, 2.0, 3.0},
                {3.5, -5, -1.5},
                {0.0, 1.0, 1.0},
                {310.0, -10.5, 299.5},
                {-10.0, -11.0, -21.0},
        };
    }
}
