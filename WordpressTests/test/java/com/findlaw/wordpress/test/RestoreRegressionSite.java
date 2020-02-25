package com.findlaw.wordpress.test;

import com.findlaw.common.config.AppSystemProperty;
import com.findlaw.common.ui.Browser;
import lombok.extern.log4j.Log4j;
import org.testng.TestGroup;
import org.testng.Reporter;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

/**
 * Created by Matt Goodmanson on 9/6/2018.
 */
@Log4j
public class RestoreRegressionSite extends BaseTest {

    private static int retryCount = 0;


    @BeforeGroups(groups = { TestGroup.TestType.Regression })
    @Test
    public void restoreSite() throws Exception {
        String siteUrl = Browser.getAppUrlFromConfigProperties(
                AppSystemProperty.getInstance().getApplication(),
                AppSystemProperty.getInstance().getEnvironment()
        );

        if (siteUrl.startsWith("https://")) {
            siteUrl = siteUrl.substring("https://".length());
        }

        // login as super admin
        wordpressTestLogin(getSuperAdmin());

        // adminUsersCapabilities() cleanup - remove qaautomationadmintestexistingcustomer
			// from automationtestsite
		app.findaLeftNavigation().users().removeUser("qaautomationadmintestexistingcustomer");

        // restore site
        try {
            if (!app.findaTopNavigation()
                    .hoverOverUpdraftPlusAndClickExistingBackups()
                    .hitRestoreForExistingBackup("QA Automation Backup Updated")
                    .navigateRestorePopup(siteUrl)
                    .restoreSuccess()) {
                throw new Exception("Failed to restore site");
            }
        } catch (Exception e) {
            // retry once
            if (retryCount < 1) {
                Reporter.log("Restoration failed: Retrying");
                retryCount++;
                app.findaTopNavigation().logout();
                this.restoreSite();
            } else {
                Reporter.log(e.getMessage());
                throw(e);
            }
        }

        // adminUsersCapabilities() cleanup - delete three added network accounts added
            // (leave existing user for next test run)
        app.findaTopNavigation()
                .clickOptionUnderNetworkAdminSubmenu("Users");
        app.findaViewUsersNetworkPage()
                .deleteUsersContainingSubstring("qaautomationadmintestaddto");

        // superAdminDuplicateImTemplate() cleanup - delete site test-duplication-env if the previous
            // test run did not delete it
        final String duplicateSiteName = "test-duplication.findlaw2.flsitebuilder-" +
                AppSystemProperty.getInstance().getEnvironment() + ".com";
        app.findaTopNavigation().clickOptionUnderNetworkAdminSubmenu("Sites");
        if (app.findaViewSitesPage().searchForText(duplicateSiteName).titleExists(duplicateSiteName)) {
            Reporter.log("Site still exists - attempting to delete now");
            app.findaViewSitesPage().deleteSite(duplicateSiteName);
        }

        // logout
        app.findaTopNavigation().logout();
    }
}
