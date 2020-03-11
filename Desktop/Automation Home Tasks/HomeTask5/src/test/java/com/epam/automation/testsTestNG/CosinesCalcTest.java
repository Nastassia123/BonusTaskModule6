package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class CosinesCalcTest extends BaseTest {

 @Test(dataProvider = "ValuesForCos", description = "Testing Cos method")
    public void testCosinesFunctionWithTestNG(double value, double result) {
        double actual = calculator.cos(value);
        Assert.assertEquals(actual, result, "Invalid result of Cos operation");
    }


    @DataProvider(name = "ValuesForCos")
    public Object[][] ValuesForCos() {
        return new Object[][] {
                { 0, 1 },
                { Math.PI / 2, 0 },
                { Math.PI / 4, Math.sqrt(2) / 2 }
        };
    }
}

