package com.findlaw.wordpress.test;

import com.findlaw.common.config.AppSystemProperty;
import com.findlaw.common.ui.WaitExtensions;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.trgr.quality.qedarsenal.qualitylibrary.result.QualityCheck;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityAssert;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityVerify;
import com.wordpress.pages.abstractions.UserAuthorizedPage;
import org.testng.TestGroup;
import org.testng.annotations.Test;
import com.findlaw.common.ui.Browser;

/**
 * Created by Matt Goodmanson on 10/19/2018.
 */
public class WordpressMiscTests extends BaseTest {

    /**
     * Login and Logout Functionality
     * <br>
     * Validate that logging in and logging out work as expected
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login via safe</li>
     * <li>Verify that logging out navigates to logout page</li>
     * <li>Log back in with safe again and log out</li>
     * <li>Similarly, login with TRID, logout, and log back in</li>
     * <li>Login as customer</li>
     * <li>Begin editing a page and click preview button (opens new tab)</li>
     * <li>On the preview page (second tab), logout and close the tab</li>
     * <li>Verify that clicking Pages on left navigation navigates to a login page (user logged out)</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void loginLogoutFunctionality() {
        final QualityCheck safeLogin = qualityTestCase.addCheck("Logging in as Admin navigated to Dashboard");
        final QualityCheck safeLogout = qualityTestCase.addCheck("Logging out of Admin returned to logged out page");
        final QualityCheck safeLoginAfterLogout = qualityTestCase.addCheck("Logged back in as Admin");
        final QualityCheck tridLogin = qualityTestCase.addCheck("Logging in as Customer navigated to Dashboard");
        final QualityCheck tridLogout = qualityTestCase.addCheck("Logging out of Customer returned to logged out page");
        final QualityCheck tridLoginAfterLogout = qualityTestCase.addCheck("Logged back in as Customer");
        final QualityCheck onPreviewPage = qualityTestCase.addCheck("User must be on preview page (second tab) before logging out");
        final QualityCheck loggedOutFromSecondTab = qualityTestCase.addCheck("Logging out on one tab navigates user to login page on others");

        final String pageName = "About";

        QualityVerify.verifyEquals(safeLogin,
                wordpressTestLogin(getAdmin()).getPageTitle(),
                app.findaDashboardPage()
                        .getExpectedPageTitle(UserAuthorizedPage.PagesEnum.DASHBOARD_PAGE),
                "Logging in as Admin unsuccessful");

        QualityVerify.verifyTrue(safeLogout,
                app.findaTopNavigation()
                        .logout()
                        .loggedOutSafe(),
                "Logging out of SAFE did not navigate to SAFE logout page");

        // Login with SAFE - do not log out if user is already logged in when navigating to page
        app.findaLoginManager()
                .goToWordpressLoginNoLogout()
                .loginPlus(getAdmin().getUsername(), getAdmin().getPassword()).answerQuestion("answer");
        QualityVerify.verifyEquals(safeLoginAfterLogout,
                app.findaDashboardPage()
                        .getPageTitle(),
                app.findaDashboardPage()
                        .getExpectedPageTitle(UserAuthorizedPage.PagesEnum.DASHBOARD_PAGE),
                "Logging in as Admin unsuccessful");

        // Logout after second SAFE login
        app.findaTopNavigation().logout();

        QualityVerify.verifyEquals(tridLogin,
                wordpressTestLogin(getCustomer()).getPageTitle(),
                app.findaDashboardPage()
                        .getExpectedPageTitle(UserAuthorizedPage.PagesEnum.DASHBOARD_PAGE),
                "Logging in as Customer unsuccessful");

        QualityVerify.verifyTrue(tridLogout,
                app.findaTopNavigation()
                        .logout()
                        .loggedOutTrid(),
                "Logging out of TRID did not navigate to TRID logout page");

        QualityVerify.verifyEquals(tridLoginAfterLogout,
                app.findaLogoutPage()
                        .logBackInTrid(getCustomer().getUsername(), getCustomer().getPassword())
                        .getPageTitle(),
                app.findaDashboardPage()
                        .getExpectedPageTitle(UserAuthorizedPage.PagesEnum.DASHBOARD_PAGE),
                "Logging in as Customer unsuccessful");

        // Open a preview and verify that the preview is opened successfully
        QualityAssert.assertEquals(onPreviewPage,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageName)
                        .editPage(pageName)
                        .hitPreviewChangesEditPage()
                        .getTitle(),
                pageName,
                "Not on preview page in second tab");

        // Once we know we are on the preview page, logout and close
        app.findaPreviewPage().logoutAndClose();

        // Attempt to navigate to Pages tab - results in navigation to logout page
        app.findaLeftNavigation().pages();
        QualityVerify.verifyTrue(loggedOutFromSecondTab,
                app.findaLogoutPage()
                        .loggedOutTrid(),
                "Logging out of preview tab did not cause first tab to also log out on the next action");
    }

        /**
         * Automation: Log in After Idle
         * <br>
         * CMS-2364-Automation: Log in After Idle
         * <br>
         * Test Steps:
         * <ol>
         * <li>Navigate to the trid login page</li>
         * <li>Wait for 62 seconds</li>
         * <li>Login as customer and verify customer is able to login</li>     
         * </ol>
         */
        @Test(groups = { TestGroup.TestType.Regression })
        public void customerLoginAfterIdle() {

            final QualityCheck tridLogin = qualityTestCase.addCheck("Customer was successfully logged in after 62 sec idle");

        //Navigate to the trid login page    
            app.findaLoginManager()
			.goToTridLogin();
            
        //Wait for 60 seconds
			WaitExtensions.waitTill(62000);
        
        //Login as customer and verify customer is able to login
			QualityVerify.verifyEquals(tridLogin,
                    wordpressTestLogin(getCustomer()).getPageTitle(),
                    app.findaDashboardPage()
                            .getExpectedPageTitle(UserAuthorizedPage.PagesEnum.DASHBOARD_PAGE),
                    "Logging in as Customer unsuccessful");

        }

    /**
     * M-TC-2
     * Submit a Contact Form
     * <br>
     * Description: Validate that a contact form can be submitted without error
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as admin</li>
     * <li>Navigate to the sites contact page</li>
     * <li>Fill out the form and click submit</li>
     * <li>Verify that the success notification displays and that the user has been redirected to the Thank You page</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void submitContactForm() {
        final QualityCheck redirectionToThankYou =
                qualityTestCase.addCheck("After submitting contact form, user is redirected to Thank You page");

        final String email = "eh.trtesting+qaautomationcustomer@gmail.com";
        final String thankYouPageTitle = "Thank You";

        goToLiveSiteWithExtension("/contact-2/");

        QualityVerify.verifyTrue(redirectionToThankYou,
                app.findaPreviewPage()
                        .submitFormWithEmail(email)
                        .isUserRedirectedUponContactFormSubmission(thankYouPageTitle),
                "User was not redirected to Thank You page after submitting the form");
    }

    /**
     * M-TC-3
     * Footer Links
     * <br>
     * Test Description: Validate that all 6 footer links work and direct to the correct pages
     * <br>
     * Test Steps:
     * <ol>
     * <li>Navigate to live site with extension /blog-2/</li>
     * <li>Click the following footer links and verify that the appropriate page opens
     * <ol>
     *     <li>Firm name - firm profile page in pview</li>
     *     <li>Disclaimer - disclaimer page</li>
     *     <li>Site Map - Site Map page</li>
     *     <li>Privacy Policy - Privacy page</li>
     *     <li>Business Development Solutions - Lawyer Marketing home page</li>
     *     <li>Findlaw - Findlaw.com home page</li>
     * </ol>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void footerLinks() {
        QualityCheck firmNameLink = qualityTestCase.addCheck("Firm profile page opens when clicking link");
        QualityCheck disclaimerLink = qualityTestCase.addCheck("Disclaimer page opens when clicking link");
        QualityCheck siteMapLink = qualityTestCase.addCheck("Site Map page opens when clicking link");
        QualityCheck privacyPolicyLink = qualityTestCase.addCheck("Privacy page opens when clicking link");
        QualityCheck businessDevelopmentSolutionsLink =
                qualityTestCase.addCheck("Lawyer Marketing home page opens when clicking link");
        QualityCheck findlawLink = qualityTestCase.addCheck("Findlaw home page opens when clicking link");

        final String firmName = "Abraham, Watkins, Nichols, Sorrels, Agosto & Friend";
        final String firmPageTitle = "Abraham, Watkins, Nichols, Sorrels, Agosto & Aziz - a Houston, Texas (TX) Personal Injury Law Firm";

        goToLiveSiteWithExtension("/blog-2/");

        QualityVerify.verifyEquals(firmNameLink,
                app.findaPreviewPage()
                        .clickFooterLinkAndGetPageTitle(firmName),
                firmPageTitle,
                "Clicking firm name did not navigate to page with correct title");

        QualityVerify.verifyEquals(disclaimerLink,
                app.findaPreviewPage()
                        .clickFooterLinkAndGetPageTitle("Disclaimer"),
                "Disclaimer",
                "Clicking Disclaimer did not navigate to page with correct title");

        QualityVerify.verifyEquals(siteMapLink,
                app.findaPreviewPage()
                        .clickFooterLinkAndGetPageTitle("Site Map"),
                "Site Map",
                "Clicking Site Map did not navigate to page with correct title");

        QualityVerify.verifyEquals(privacyPolicyLink,
                app.findaPreviewPage()
                        .clickFooterLinkAndGetPageTitle("Privacy Policy"),
                "Privacy Policy",
                "Clicking Privacy Policy did not navigate to page with correct title");

        QualityVerify.verifyEquals(businessDevelopmentSolutionsLink,
                app.findaPreviewPage()
                        .clickFooterLinkAndGetPageTitle("Business Development Solutions"),
                "Lawyer Marketing",
                "Clicking Business Development Solutions did not navigate to page with correct title");

        QualityVerify.verifyEquals(findlawLink,
                app.findaPreviewPage()
                        .clickFooterLinkAndGetPageTitle("FindLaw"),
                "Find Laws, Legal Information, and Attorneys - FindLaw",
                "Clicking FindLaw did not navigate to page with correct title");
    }
    /**
   	 *<br>
        * Test Description: Test is to Validate blog page set up milestone
        * <br>
        * <ol>
        *     <li>Navigate to site</li>
        *     <li>View the Blog page</li>
        *     <li>Verify that there is at least 1 blog post listed on the page</li>
        * </ol>
   	 */
   	@Test(groups = { TestGroup.TestType.MilestoneBLOG })
   	public void blogPageSetUpMilestoneCheck() {
   		final QualityCheck atLeastOnePostOnBlogPage = qualityTestCase.addCheck("At least 1 blog post is listed on the page");
   		
   		String siteUrl = "https://cdevalidpass1.findlaw2.flsitebuilder-qa.com/";
   		
   		Browser.goToUrl(siteUrl+"blog/");
   		QualityVerify.verifyTrue(atLeastOnePostOnBlogPage,
   				app.findaPreviewPage().isAnyBlogPostPresent(),
   				"No Blog post present on Blog page");		
   	}

    /**
     * M-TC-4
     * Cache Control Validation
     * <br>
     * Test Description: Test is to validate that cache-control value is: max-age=2592000, public
     * <br>
     * Test Steps:
     * <ol>
     * <li>Open test site</li>
     * <li>Check Response Header, cache-control item for text/html</li>
     * <li>Verify that max-age is 2592000</li>
     * <li>Ref: https://fljira.westlan.com/browse/CMS-2981</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression,"Metadata||tester-name:PavelBychkou||Automated:Yes" })
    public void cacheControlVerification() {

        final QualityCheck cacheControl = qualityTestCase.addCheck("cache-control value is: max-age=2592000, public");

        String rpCacheControlHeader = "max-age=2592000, public";

        RestAssured.baseURI = Browser.getAppUrlFromConfigProperties(
                AppSystemProperty.getInstance().getApplication(),
                AppSystemProperty.getInstance().getEnvironment());

        Response response = RestAssured.when()
                .get()
                .andReturn();

        QualityVerify.verifyEquals(cacheControl,
                response.getHeader("cache-control"),
                rpCacheControlHeader,
                "max-age value is not 2592000.");
    }

}
