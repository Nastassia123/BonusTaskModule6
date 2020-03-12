package base;

import base.APIClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import page.SearchGoogle;

public class BaseConfiguration extends APIClass {

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    public WebDriver initializeDriver() {
//        System.setProperty("webdriver.gecko.driver", "D:\\Selenium Drivers\\geckodriver-v0.26.0-win32\\geckodriver.exe");
       // driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "D:\\Selenium Drivers\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        return driver;
    }

    public String getUrl(){
        return getDriver().getCurrentUrl();
    }

    @BeforeClass
    public void setup(){
        initializeDriver();
    }




    @AfterClass
    public void closeDown() {
        delay(2000);
        driver.quit();
    }
}
