package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class SinCalcTest extends BaseTest {

 @Test(dataProvider = "ValuesForSin", description = "Testing Sin method")
    public void testSinesFunctionWithTestNG(double value, double result) {
        double actual = calculator.sin(value);
        Assert.assertEquals(actual, result, "Invalid result of Sin operation");
    }

    
    @DataProvider(name = "ValuesForSin")
    public Object[][] ValuesForSin() {
        return new Object[][] {
                { 0, 0 },
                { -3, 1 },
                { 1, 1 }
        };
    }

}


