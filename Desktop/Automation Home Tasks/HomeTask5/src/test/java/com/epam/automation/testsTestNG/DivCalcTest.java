package com.epam.automation.testsTestNG;

import com.epam.automation.BaseConfigurations.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DivCalcTest extends BaseTest {

    @Test(groups = "TestDivision", dataProvider = "DoubleValuesForDivision")
    public void testDivOperationWithDoubleValuesTestNG(double a, double b, double expected){
       double result =  calculator.div(a, b);
       Assert.assertEquals(result,expected, "Incorrect result of division operation with double params");
    }


    @DataProvider(name = "DoubleValuesForDivision")
    Object [][] DoubleValuesForDivision(){
        return new Object[][]{
                {1.0, 0, 1.0},
                {0, 1.0, 0},
                {90.0, -10.0, -9.0 },
                {135.0, -1.0, -135.0},
                {-10, -10, -10}
        };
    }

    @Test(groups = "TestDivision", dataProvider = "LongValuesForDivision")
    public void testDivOperationWithLongValuesTestNG(long a, long b, long expected){
        long result =  calculator.div(a,b);
        Assert.assertEquals(result, expected, "Incorrect result of division operation with long params");
    }


    @DataProvider(name = "LongValuesForDivision")
    Object [][] LongValuesForDivision(){
        return new Object[][]{
                {10000, -10000, -1},
                {-45454545, 1, -45454545},
                {0, 0, 0},
                {0, 1200000, 0},
                {1200000000, 12000, 100000}
        };
    }

}
