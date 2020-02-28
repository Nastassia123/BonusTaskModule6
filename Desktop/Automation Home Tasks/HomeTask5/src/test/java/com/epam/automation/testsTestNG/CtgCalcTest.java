package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class CtgCalcTest extends BaseTest {

    @Test(dataProvider = "ValuesForCtg", description = "Testing Ctg method")
    public void testCotangensFunctionWithTestNG(double value, double result) {
        double actual = calculator.ctg(Math.toRadians(value));
        Assert.assertEquals(actual, result, "Invalid result of Ctg operation");
    }


    @DataProvider(name = "ValuesForCtg")
    public Object[][] ValuesForTg() {
        return new Object[][]{
                {30, Math.sqrt(3)},
                {45, 1},
                {60, 1},
                {270, 0}
        };
    }
}

