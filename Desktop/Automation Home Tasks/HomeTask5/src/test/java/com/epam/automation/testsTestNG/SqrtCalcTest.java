package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class SqrtCalcTest extends BaseTest {

 @Test(dataProvider = "ValuesForSqrt", description = "Testing Sqrt method")
    public void testSqrtFunction(double value, double result) {
        double actual = calculator.sqrt(value);
        Assert.assertEquals(actual, result, "Invalid result of Sqrt operation");
    }


    @DataProvider(name = "ValuesForSqrt")
    public Object[][] ValuesForSqrt() {
        return new Object[][] {
                { 1, 1 },
                { 100, 10 },
                { 4, 2 },
                {-1, -1}
        };
    }
}

