package com.findlaw.wordpress.test;

import org.testng.Reporter;
import org.testng.annotations.Test;

import org.testng.TestGroup.TestType;
import com.trgr.quality.qedarsenal.qualitylibrary.result.QualityCheck;
import com.wordpress.pages.application.WordpressApplication;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityAssert;

@Test(groups = { TestType.Default })
public class WordpressSmoke extends BaseTest {

	private static final WordpressApplication app = new WordpressApplication();

	/**
	 * Logs in as a Super Admin and confirms successful navigation to the network site (All Sites)
	 *
	 * goToNetworkSite uses the environment specified by the command line, and uses wordpressnetwork as the application
	 * instead of wordpress, because the URLs are different from the current regression tests (only network site, not test site)
	 */
	@Test(groups = { TestType.Smoke, TestType.SmokeJenkins })
	public void successfulLogin() {

		QualityCheck atAllSites = qualityTestCase.addCheck("Login successful, at All Sites");

		Reporter.log("At wordpressTestLogin page, logging in with Super Admin credentials");
		goToNetworkSite();

		// Home Page
		QualityAssert.assertTrue(atAllSites,
				app.findaViewSitesPage()
						.atAllSitesPage(),
				"Navigation to All Sites failed");

		// Logout
		app.findaTopNavigation().logout();
		Reporter.log("Logout");
	}
	
}
