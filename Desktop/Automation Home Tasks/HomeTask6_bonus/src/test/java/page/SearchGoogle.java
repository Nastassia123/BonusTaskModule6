package page;

import base.BaseConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchGoogle extends BaseConfiguration {

    private   String url = "https://www.google.com/";
    By googleLink = By.xpath("//input[@name='q']");
    private String searchText = "Banana song";


    public SearchGoogle searchGoogleService(){
       open(url);
       return this;
    }

    public GoogleWindow openGoogleService(){
        WebElement googleService = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(googleLink));
        googleService.sendKeys(searchText, Keys.ENTER);
    return new GoogleWindow();
    }



}
