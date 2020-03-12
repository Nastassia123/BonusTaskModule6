package page;

import base.BaseConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleWindow extends BaseConfiguration {


    By songLink = By.xpath("//a[contains(@href, 'youtube.com') and h3[contains(text(),'Despicable Me')]]");

    public YouTubeWindow openVideoInTheYouTube() {
        WebElement googleService = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(songLink));
        googleService.click();
        return new YouTubeWindow();
    }

}
