package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class PowCalcTest extends BaseTest {

 @Test(dataProvider = "ValuesForPow", description = "Testing Pow method")
    public void testPowFunction(double a, double b,  double result) {
        double actual = calculator.pow(a, b);
        Assert.assertEquals(actual, result, "Invalid result of Pow operation");
    }


    @DataProvider(name = "ValuesForPow")
    public Object[][] ValuesForPow() {
        return new Object[][] {
                { 1.5, 1, 1.5 },
                { 2, 2.5, 	5.65685424949238 },
                { 4.12, 0, 1 },
                {-1.8, 2, 3.24 }
        };
    }
}

