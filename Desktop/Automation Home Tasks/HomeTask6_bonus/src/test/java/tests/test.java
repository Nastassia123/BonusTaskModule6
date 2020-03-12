package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.SearchGoogle;
import page.YouTubeWindow;


public class test extends SearchGoogle {

    @Test
    public void verifyOpenedVideoIsFromYouTube() {
        YouTubeWindow googleWindow = new SearchGoogle()
                .searchGoogleService()
                .openGoogleService()
                .openVideoInTheYouTube();
            Assert.assertTrue(getUrl().toLowerCase().contains("youtube".toLowerCase()),
                    "Opened video is not from YouTube");
    }
}
