package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public abstract class APIClass {



protected static WebDriver driver;

    public abstract WebDriver getDriver();

    public  void open (String url){
        getDriver().get(url);
    }


    public WebElement find(By locator){
        return  getDriver().findElement(locator);
    }




    public void delay(long millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}