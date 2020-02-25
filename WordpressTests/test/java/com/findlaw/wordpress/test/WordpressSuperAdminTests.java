package com.findlaw.wordpress.test;

import com.findlaw.common.config.AppSystemProperty;
import com.trgr.quality.qedarsenal.qualitylibrary.result.QualityCheck;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityAssert;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityVerify;
import org.testng.TestGroup;
import org.testng.annotations.Test;

/**
 * Created by Matt Goodmanson on 10/19/2018.
 */
public class WordpressSuperAdminTests extends BaseTest {

	/**
	 * SA-TC-1
	 * Super Admin Duplicate IM Template
	 * <br>
	 * Description: Validate that the Super Admin (SA) can successfully duplicate the IM template
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as SA</li>
	 * <li>Go to Sites -> Network Admin -> Duplication</li>
	 * <li>Fill in The address, title, and admin email of the new site and click duplicate</li>
	 * <li>Verify that message confirms duplication</li>
	 * <li>Go to the duplicated sites dashboard, then Settings -> Site Settings</li>
	 * <li>Verify that the setting, Lock Site, is not locked</li>
	 * <li>Also on the Site Settings page, enter the Folder Name "beckydmiller" and click update</li>
	 * <li>Go to Tools -> Sync Content and sync the CoPortal site meta</li>
	 * <li>Verify that the firm name appears on the top banner of the page</li>
	 * <li>View a page on the site and verify that the name appears on the top banner</li>
	 * <li>Go to Sites -> Network Admin -> Sites and delete the page</li>
	 * <li>Verify that the page is deleted</li>
	 * <li>Logout</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void superAdminDuplicateImTemplate() {
		final QualityCheck duplicationConfirmationDisplayed =
				qualityTestCase.addCheck("After duplicating template, message confirms successful duplication");
		final QualityCheck duplicateNotLocked =
				qualityTestCase.addCheck("Duplicate site is not locked after being created");
		final QualityCheck firmNameOnDashboardBanner =
				qualityTestCase.addCheck("Firm name is on top banner of dashboard");
		final QualityCheck firmNameOnLivePageBanner =
				qualityTestCase.addCheck("Firm name is on top banner on page on actual site");
		final QualityCheck duplicateIsDeleted =
				qualityTestCase.addCheck("Site no longer exists after being deleted");

		final String newSiteAddress = "test-duplication";
		final String newSiteTitle = "Test Duplication";
		final String newSiteAdminEmail = "brian.kasprzyk@thomsonreuters.com";
		final String folderName = "beckydmiller";
		final String expectedFirmName = "Becky D. Miller";

		final String newSiteFullAddress = newSiteAddress + ".findlaw2.flsitebuilder-" +
				AppSystemProperty.getInstance().getEnvironment() + ".com";

		wordpressTestLogin(getSuperAdmin());

		// Go to duplication page
		app.findaTopNavigation().clickOptionUnderNetworkAdminSubmenu("Duplication");

		QualityAssert.assertTrue(duplicationConfirmationDisplayed,
				app.findaDuplicatePage()
						.enterNewSiteAddress(newSiteAddress)
						.enterNewSiteTitle(newSiteTitle)
						.enterNewSiteEmail(newSiteAdminEmail)
						.hitDuplicateButton()
						.duplicationMessageReceived(),
				"Message was not received after user attempted to duplicate the site");

		QualityVerify.verifyTrue(duplicateNotLocked,
				app.findaDuplicatePage()
						.hitDashboardLink()
						.goToPage()
						.siteSettings()
						.isSiteUnlocked(),
				"Site is locked when it should be unlocked");

		// Change folder name
		app.findaSiteSettingsPage().enterFolderName(folderName).hitUpdateButton();

		// Sync CoPortal meta
		app.findaLeftNavigation().tools("Sync Content");
		app.findaSyncContentPage().hitSyncCoportalSiteMetaButton();

		QualityVerify.verifyEquals(firmNameOnDashboardBanner,
				app.findaLeftNavigation()
						.dashboard()
						.getFirmName(),
				expectedFirmName,
				"The firm name on the banner was not updated after syncing CoPortal meta");

		app.findaTopNavigation().clickOptionUnderNetworkAdminSubmenu("Sites");
		app.findaViewSitesPage().visitSite(newSiteFullAddress);

		QualityVerify.verifyEquals(firmNameOnLivePageBanner,
				app.findaDashboardPage()
						.getFirmName(),
				expectedFirmName,
				"The firm name on the banner of the site was not updated after syncing CoPortal meta");

		// Go to Network Admin -> Sites
		app.findaTopNavigation().clickOptionUnderNetworkAdminSubmenu("Sites");

		QualityVerify.verifyFalse(duplicateIsDeleted,
				app.findaViewSitesPage()
						.deleteSite(newSiteFullAddress)
						.titleExists(newSiteFullAddress),
				"Site is still in All Sites when it should have been deleted");

		app.findaTopNavigation().logout();
	}

	@Test(groups = { TestGroup.TestType.Regression })
	public void superAdminLogin() {
		final QualityCheck successfullyLoggedIn =
				qualityTestCase.addCheck("Successfully logged in as SuperAdmin");
		wordpressTestLogin(getSuperAdmin());

		QualityVerify.verifyTrue(successfullyLoggedIn,
				app.findaDashboardPage().getFirmName() != null,
				"Failed to login SuperAdmin");
	}
}
