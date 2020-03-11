package com.epam.automation.testsTestNG;


import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SumCalcTest extends BaseTest {

    @Test(dataProvider = "LongParamsForSumTest")
    public void testSummationWithLongValuesTestNG(long a, long b,long expectedResult) {
        long result = calculator.sum(a, b);
        Assert.assertEquals(result, expectedResult,  "Invalid result value of sum operation with long params");
    }

    @DataProvider(name = "LongParamsForSumTest")
    public Object[][] valuesForSum() {
        return new Object[][]{
                {-1000000000, -222222222, -1222222222},
                {399999999, -5000000, 394999999},
                {0, 1999999999, 1999999999},
                {-310000000, 1000000000, 690000000},
                {0, 0, 0},
        };
    }

    @Test(dataProvider = "DoubleParamsForSumTest")
    public void testSummationWithDoubleValuesTestNG(double a, double b, double expectedResult) {
        double result = calculator.sum(a, b);
        Assert.assertEquals(result, expectedResult,  "Invalid result value of sum operation with double params");
    }

    @DataProvider(name = "DoubleParamsForSumTest")
    public Object[][] DoublevaluesForSum() {
        return new Object[][]{
                {-1.0, -2.0, -3.0},
                {3.0, -5.0, -2.0},
                {0.0, 1.0, 1.0},
                {-310.0, 10.0, -300.0},
                {0.0, 0.0, 0.0},
        };
    }
}
