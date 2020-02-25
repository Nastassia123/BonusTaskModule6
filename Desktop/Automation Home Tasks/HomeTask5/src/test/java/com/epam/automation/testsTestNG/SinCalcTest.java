package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class SinCalcTest extends BaseTest {

 @Test(dataProvider = "ValuesForSin", description = "Testing Sin method")
    public void testSinFunction(double value, double result) {
        double actual = calculator.sin(value);
        Assert.assertEquals(actual, result, "Invalid result of Sin operation");
    }

    
    @DataProvider(name = "ValuesForSin")
    public Object[][] ValuesForSin() {
        return new Object[][] {
                { 0, 0 },
                { Math.PI / 2, 1 },
                { Math.PI / 4, 0.7071067811865475 }
        };
    }

}


