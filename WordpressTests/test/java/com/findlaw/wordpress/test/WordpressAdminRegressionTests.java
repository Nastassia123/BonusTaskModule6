package com.findlaw.wordpress.test;

import com.findlaw.common.utilities.RandomUtils;
import com.trgr.quality.qedarsenal.qualitylibrary.result.QualityCheck;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityAssert;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityVerify;
import org.testng.TestGroup;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.wordpress.pages.abstractions.AbstractEditPage.DashboardMenu;
import static com.wordpress.pages.abstractions.UserAuthorizedPage.PagesEnum;

public class WordpressAdminRegressionTests extends BaseTest {

    @Test(groups = {TestGroup.TestType.Regression})
    public void adminLogin() {
        final QualityCheck successfullyLoggedIn =
                qualityTestCase.addCheck("Successfully logged in as Admin");
        wordpressTestLogin(getAdmin());

        QualityVerify.verifyTrue(successfullyLoggedIn,
                app.findaDashboardPage().getFirmName() != null,
                "Failed to login as Admin");
    }

    /**
     * A-TC-2
     * Admin Denied Post and Page Capabilities
     * <br>
     * Description: Validate Admin doesn't have access to Comments, User Profile, Themes, Network Settings
     * in any menu options
     * <br>
     * Note: See deniedPostAndPageCapabilities comment for test steps
     */
    @Test(groups = {TestGroup.TestType.Regression, "Metadata||tester-name:Ina||Automated:Yes"})
    public void adminDeniedPostCapabilities() {

        wordpressTestLogin(getAdmin());

        // Open a Blog Post Edit Page
        app.findaLeftNavigation().blogPosts().clickByTitle("Motorcycles may soon get their own AI safety technology");

        // Make Verifications
        deniedPostAndPageCapabilities("Blog Posts");

        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-2
     * Admin Denied Post and Page Capabilities
     * <br>
     * Description: Validate Admin doesn't have access to Comments, User Profile, Themes, Network Settings
     * in any menu options
     * <br>
     * Note: See deniedPostAndPageCapabilities comment for test steps
     */
    @Test(enabled = false, groups = {TestGroup.TestType.Regression, "Metadata||tester-name:Ina||Automated:Yes"})
    public void adminDeniedLandingPageCapabilities() {

        wordpressTestLogin(getAdmin());

        // Open a Landing Pages Edit Page
        app.findaLeftNavigation().landingPages().clickByTitle("Need Landing Page In Backup");

        // Make Verifications
        deniedPostAndPageCapabilities("Landing Page");

        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-2
     * Admin Denied Post and Page Capabilities
     * <br>
     * Description: Validate Admin doesn't have access to Comments, User Profile, Themes,
     * Network Settings in any menu options
     * <br>
     * Note: See deniedPostAndPageCapabilities comment for test steps
     */
    @Test(groups = {TestGroup.TestType.Regression, "Metadata||tester-name:Ina||Automated:Yes"})
    public void adminDeniedPageCapabilities() {
        wordpressTestLogin(getAdmin());

        // Open an Edit Page page
        app.findaLeftNavigation().pages().searchForTitle("About").openPage("About");

        // Make Verifications
        deniedPostAndPageCapabilities("Page");

        app.findaTopNavigation().logout();
    }

    /**
     * Used by all three Denied Post and Page Capabilities tests.
     * <br>
     * Checks whether:
     * <ol>
     * <li>The current page is an edit page</li>
     * <li>There is a comment screen option</li>
     * <li>There is a comment secion on the edit page</li>
     * <li>The type of post (page, landing page, blog post) can be set to Comment Response Page.</li>
     * </ol>
     * Should already be at an edit page for the corresponding edit page type, otherwise it would be very easy to pass these
     * verify's
     *
     * @param testType The type of item being edited (Landing Page, Page, Blog Posts)
     */
    private void deniedPostAndPageCapabilities(String testType) {
        final QualityCheck onAnEditPage = qualityTestCase.addCheck("Test is on a " + testType + " edit page");
        final QualityCheck noCommentsScreenOption =
                qualityTestCase.addCheck(testType + " edit page does not have a Comments checkbox");
        final QualityCheck noCommentsSection =
                qualityTestCase.addCheck(testType + " edit page does not have a Comments section");
        final QualityCheck onPageTypeOptions = qualityTestCase
                .addCheck("Page type options do not contain Comment Response Page: " + testType);

        QualityAssert.assertTrue(onAnEditPage,
                app.findaBlogPostEditPage().isEditingItemWithType(testType),
                "Admin is not editing a " + testType);

        QualityVerify.verifyFalse(noCommentsScreenOption,
                app.findaBlogPostEditPage().isScreenOptionPresent("Comments"),
                "Comments column is displayed in screen options");

        QualityVerify.verifyFalse(noCommentsSection,
                app.findaBlogPostEditPage().isCommentSectionPresent(),
                "Comments section appears on edit page");

        QualityAssert.assertFalse(onPageTypeOptions,
                app.findaBlogPostEditPage()
                        .isPageTypeOptionPresent("Comment Response Page"),
                "Comment Response Page option present");

        // Navigate away from the blog post so that the user does not remain editing (interfering with other tests)
        app.findaLeftNavigation().blogPosts();
    }

    /**
     * A-TC-7
     * Admin Fork for Layout Page Capabilities
     * <br>
     * Description: Validate admin can fork pages for layout/copy edit and merge them
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as admin</li>
     * <li>Go to all pages and click Fork for Layout option under PAGENAME</li>
     * <li>Once on the Edit Page page, edit the body of the page and click Save Changes</li>
     * <li>Navigate back to All Pages and verify that PAGENAME is Locked for Editing</li>
     * <li>Return to layout fork draft page and click Merge Edits into Parents button</li>
     * <li>Navigate back to all pages and verify that the forked page has been merged into parent by previewing the page</li>
     * <li>Edit PAGENAME and click Fork for Layout</li>
     * <li>Make more changes to the text and save, then merge back into parent</li>
     * <li>Preview PAGENAME and verify that the changes to the text were merged back</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression, "Metadata||tester-name:Ina||Automated:Yes"})
    public void adminForkForLayoutPageCapabilities() {

        final QualityCheck pageIsLockedAfterForkForLayout =
                qualityTestCase.addCheck("Forked page is Locked for Editing");
        final QualityCheck editsMergedIntoParentForkFromAllPages =
                qualityTestCase.addCheck("Edits made after Forking for Layout from All Pages merged into live");
        final QualityCheck editsMergedIntoParentForkFromEditPage =
                qualityTestCase.addCheck("Edits made after Forking for Layout on Edit Page Page merged into live");

        wordpressTestLogin(getAdmin());

        String title = "Admin Fork for Layout";
        String forkTitle = String.format("--(Layout Fork) %s", title);
        String editedTextForkFromAllPages = "Forked for Layout Text from All Pages";
        String editedTextForkFromEditPage = "Forked for Layout Text from Edit Page page";

        QualityVerify.verifyTrue(pageIsLockedAfterForkForLayout,
                app.findaLeftNavigation()
                        .pages()
                        .searchForText(title)
                        .clickForkForLayoutByPageName(title)
                        .editTextContentInDiviBuilder(editedTextForkFromAllPages)
                        .saveChanges()
                        .goBackToPage(DashboardMenu.PAGES)
                        .searchForText(title)
                        .checkEntryIsLockedForEditing(title),
                "Forked Entry is not locked after forking for layout");

        QualityVerify.verifyEquals(editsMergedIntoParentForkFromAllPages,
                app.findaPagesPage()
                        .openPage(forkTitle)
                        .mergeEditsIntoParentPage()
                        .returnToAllPages()
                        .previewPage(title)
                        .getTextAndBack(),
                editedTextForkFromAllPages,
                "Edits are not merged into parent from Fork for Layout from All Pages");

        QualityVerify.verifyEquals(editsMergedIntoParentForkFromEditPage,
                app.findaPagesPage()
                        .openPage(title)
                        .hitForkForLayoutEditPagePage()
                        .editPageText(editedTextForkFromEditPage)
                        .hitUpdateOrPublishEditPage()
                        .mergeEditsIntoParentPage()
                        .returnToAllPages()
                        .previewPage(title)
                        .getTextAndBack(),
                editedTextForkFromEditPage,
                "Edits are not merged into parent from Fork for Layout from edit page");

        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-7
     * Admin Fork for Copy Edit Page Capabilities
     * <br>
     * Description: Validate admin can fork pages for copy edit and merge them
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as Admin</li>
     * <li>Click Fork for Copy Edit option under a page, edit the text, and click Save Changes</li>
     * <li>Return to all pages and verify that the forked page is Locked for Editing</li>
     * <li>Edit the Copy Fork page and click Enter Redlining Mode button</li>
     * <li>Click Edits Resolved, add some text, and click on Save Progress</li>
     * <li>Then click Merge Edits into Live and return to All Pages</li>
     * <li>Verify that the forked page has been merged into parent by checking the text in the preview</li>
     * <li>Repeat last two verifications after clicking Fork for Copy Edit while editing the original page</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression, "Metadata||tester-name:Ina||Automated:Yes"})
    public void adminForkForCopyEditPageCapabilities() {

        final QualityCheck forkedPageIsLocked = qualityTestCase.addCheck("Page is locked after forking for copy edit");
        final QualityCheck editsMergedForkFromAllPages =
                qualityTestCase.addCheck("Text is edited when viewing on Edit Page (All Page Fork");
        final QualityCheck pageMergedForkFromAllPages =
                qualityTestCase.addCheck("Edited text appears on merged page in preview (All Page Fork)");
        final QualityCheck editsMergedForkFromEditPage =
                qualityTestCase.addCheck("Text is edited when viewing on Edit Page (Edit Page Fork)");
        final QualityCheck pageMergedForkFromEditPage =
                qualityTestCase.addCheck("Edited text appears on merged page in preview (Edit Page Fork)");

        wordpressTestLogin(getAdmin());

        String title = "Admin Fork for Copy";
        String copyEditForkTitle = String.format("--(Copy Fork) %s", title);
        String editedContentAllPages = "Forked for Edit Text (Fork from All Pages)";
        String editedContentEditPage = "Forked for Edit Text (Fork from Edit Page)";

        QualityVerify.verifyTrue(forkedPageIsLocked,
                app.findaLeftNavigation()
                        .pages()
                        .searchForText(title)
                        .clickForkForCopyEdit(title)
                        .saveChanges()
                        .goBackToPage(DashboardMenu.PAGES)
                        .searchForText(title)
                        .checkEntryIsLockedForEditing(title),
                "Forked page is not locked for editing");

        QualityVerify.verifyEquals(editsMergedForkFromAllPages,
                app.findaPagesPage()
                        .editPage(copyEditForkTitle)
                        .clickOnEnterRedlineMode()
                        .clickOnEditsResolved()
                        .editTextInRedlineMode(editedContentAllPages)
                        .acceptAllEdits()
                        .clickOnEditsResolved()
                        .saveProgress()
                        .mergeEditsIntoLive()
                        .getContentFromDiviBuilder(),
                editedContentAllPages,
                "Text edited in redline mode has not been edited in original page's Divi builder after merge (from All Pages)");

        // Needed since Divi builder was opened
        app.findanEditPagePage().hitUpdateOrPublishEditPage().returnToAllPages();

        QualityVerify.verifyEquals(pageMergedForkFromAllPages,
                app.findaPagesPage()
                        .searchForTitle(title)
                        .previewPage(title)
                        .getTextAndBack(),
                editedContentAllPages,
                "Edits are not merged into parent after copy edit from All Pages");

        QualityVerify.verifyEquals(editsMergedForkFromEditPage,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(title)
                        .editPage(title)
                        .clickForkForCopyEditOnEditPage()
                        .clickOnEnterRedlineMode()
                        .clickOnEditsResolved()
                        .editTextInRedlineMode(editedContentEditPage)
                        .acceptAllEdits()
                        .clickOnEditsResolved()
                        .saveProgress()
                        .mergeEditsIntoLive()
                        .getContentFromDiviBuilder(),
                editedContentEditPage,
                "Text edited in redline mode has not been edited in original page's Divi builder after merge (from Edit Page)");

        // Needed since Divi builder was opened
        app.findanEditPagePage().hitUpdateOrPublishEditPage().returnToAllPages();

        QualityVerify.verifyEquals(pageMergedForkFromEditPage,
                app.findaPagesPage()
                        .previewPage(title)
                        .getTextAndBack(),
                editedContentEditPage,
                "Edits are merged into parent after copy edit from Edit Page");

        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-16 Admin Menu Capabilities Validate Admin can create, edit and delete a
     * menu
     * <p>
     * 1 login as admin should be at dashboard page 2 Click on Appearance > Menus
     * should be at Menus Page 3 Click on "create a new menu" link. Enter a name and
     * click on "Create Menu" verify admin can create a new menu 4 Edit an existing
     * menu by adding a menu item and Save Menu Verify admin can edit a menu 5
     * Delete the menu created in step 3 Verify admin can delete a menu
     *
     * @author Ina Taikina
     */
    @Test(groups = {TestGroup.TestType.Regression, "Metadata||tester-name:Ina||Automated:Yes"})
    public void adminMenuCapabilities() {

        final QualityCheck isMenuCreated = qualityTestCase.addCheck("The menu is created");
        final QualityCheck isMenuEdited = qualityTestCase.addCheck("The menu is edited");
        final QualityCheck isMenuDeleted = qualityTestCase.addCheck("The menu is deleted");

        wordpressTestLogin(getAdmin());
        app.findaLeftNavigation().menu();

        String menuName = RandomUtils.getRandomAlphabetic(10);
        String newMenuName = RandomUtils.getRandomAlphabetic(10);

        QualityAssert.assertTrue(isMenuCreated, app.findaMenuPage().hitCreateNewMenuLink().enterMenuName(menuName)
                .hitCreateNewMenu().hitSaveMenu().checkIfTheMenuExists(menuName), "The menu is not created");

        QualityAssert.assertTrue(isMenuEdited, app.findaMenuPage().clickOnSelectButton().enterMenuName(newMenuName)
                .hitSaveMenu().checkIfTheMenuExists(newMenuName), "The menu is not edited");

        QualityAssert.assertFalse(isMenuDeleted,
                app.findaMenuPage().deleteMenu(newMenuName).checkIfTheMenuExists(newMenuName),
                "The menu is not deleted");

        app.findaTopNavigation().logout();
    }

    /**
     * Admin Landing Page Capabilities <br>
     * Objective: Validate Admin can create, edit, and delete a Landing page <br>
     * Test Steps:
     * <ol>
     * <li>Login as admin</li>
     * <li>Click on Add New submenu under Landing page</li>
     * <li>Click on Landing page</li>
     * <li>Click Add New button on menu</li>
     * <li>Edit name and click save</li>
     * <li>Check for the name you entered</li>
     * <li>Pavel Bychkou, CMS-2976: verify if Practice Area attribute is available </li>
     * <li>Click Trash option under Landing page that was created</li>
     * </ol>
     *
     * @throws InterruptedException
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminPublishedLandingPageCapabilities() throws InterruptedException {

        final QualityCheck canAddNewLandingPageFromSubMenu = qualityTestCase
                .addCheck("New Landing page added from sub menu");
        final QualityCheck canAddNewLandingPageFromAllPages = qualityTestCase
                .addCheck("New Landing page added from all pages");
        final QualityCheck editedLandingPage = qualityTestCase.addCheck("Edit Landing page from sub option");
        final QualityCheck editedLandingPageLanguage = qualityTestCase.addCheck("Landing page language edited");
        final QualityCheck newlyAddedLandingPageFromSubMenuInList = qualityTestCase
                .addCheck("Newly created Landing page is present in list");
        final QualityCheck landingPageRemovedFromList = qualityTestCase.addCheck("Landing page was removed from list");
        final QualityCheck landingPageRemovedFromEditPage = qualityTestCase
                .addCheck("Landing page was removed from page edit");
        final QualityCheck landingPagePreviewPage = qualityTestCase.addCheck("Preview edited Landing page");
        final QualityCheck canEditPracticeArea = qualityTestCase.addCheck("Practice Area attribute is available on Landing page and can be edited");

        final String LandingPageTest = "Published Landing page Test";
        final String addButtonLandingPageTest = "add Button Landing page Test";
        final String editLandingPageTest = "Edit Landing page Test";
        final String previewLandingPageTest = "Preview Landing page Test";
        final String editPreviewLandingPageTest = "Edit preview Landing page Test";
        final String language = "Spanish";
        final String practiceArea = "Sexual Harassment";


        // Login
        wordpressTestLogin(getAdmin());

        // Add a new landing page with add new button in menu
        app.findaLeftNavigation()
                .landingPages("Add New");
        app.findaEditLandingPage().editTitleField(LandingPageTest);
        QualityVerify.verifyTrue(canAddNewLandingPageFromSubMenu,
                app.findaEditLandingPage().isUpdateOrPublishButtonPresent(), "Can't find Update/Publish button");
        // Click the Publish button
        app.findaEditLandingPage().saveChanges();
        app.findaLeftNavigation().landingPages();
        QualityVerify.verifyTrue(newlyAddedLandingPageFromSubMenuInList,
                app.findaLandingPage().hasLandingPageWithTitle(LandingPageTest),
                "Landing page with title:" + LandingPageTest + " was not found");

        // Add a new Landing Page with add new button on Landing Pages page
        app.findaLeftNavigation().landingPages().hitAddNew();
        app.findaEditLandingPage().editTitleField(addButtonLandingPageTest);
        QualityVerify.verifyTrue(canAddNewLandingPageFromAllPages,
                app.findaEditLandingPage().isUpdateOrPublishButtonPresent(), "Can't find Update/Publish button");
        // Click the Publish button
        app.findaEditLandingPage().saveChanges();
        app.findaLeftNavigation().landingPages();
        QualityVerify.verifyTrue(newlyAddedLandingPageFromSubMenuInList,
                app.findaLandingPage().hasLandingPageWithTitle(addButtonLandingPageTest),
                "Landing page with title: " + addButtonLandingPageTest + " was not found");

        // Edit Landing page
        app.findaLandingPage().hitOptionLandingPage(LandingPageTest, "Edit");
        app.findaEditLandingPage().editTitleField(editLandingPageTest);
        app.findaEditLandingPage().selectLanguage(language);
        // Click the Update button
        app.findaEditLandingPage().saveChanges();
        app.findaLeftNavigation().landingPages();
        QualityVerify.verifyTrue(editedLandingPage,
                app.findaLandingPage()
                        .hasLandingPageWithTitle(editLandingPageTest),
                "Editing landing page with title: " + editLandingPageTest + " Failed");
        QualityVerify.verifyTrue(editedLandingPageLanguage,
                app.findaLandingPage()
                        .searchForText(editLandingPageTest)
                        .openEntryForEditByPageTitle(editLandingPageTest)
                        .isLanguageOptionSelected(language),
                "Language " + language + " not selected.");

        //Edit Practice Area and check
        QualityVerify.verifyEquals(canEditPracticeArea,
                app.findaEditLandingPage()
                        .selectPracticeArea(practiceArea)
                        .saveChanges()
                        .getPracticeArea(),
                practiceArea,
                "Could not edit Practice area. ");

        // Delete a page from All Landing Pages
        app.findaLeftNavigation().landingPages();
        app.findaLandingPage().hitOptionLandingPage(editLandingPageTest, "Trash");
        QualityVerify.verifyFalse(landingPageRemovedFromList,
                app.findaLandingPage().hasLandingPageWithTitle(editLandingPageTest),
                "Landing page with title: " + editLandingPageTest + " not deleted from trash link");

        // Delete a page from edit Landing Pages
        app.findaLandingPage().hitOptionLandingPage(addButtonLandingPageTest, "Edit");
        app.findaEditLandingPage().hitTrashLink();
        QualityVerify.verifyFalse(landingPageRemovedFromEditPage,
                app.findaLandingPage().hasLandingPageWithTitle(addButtonLandingPageTest),
                "Landing page with title: " + addButtonLandingPageTest + " not deleted from move to trash link");

        // Edit and preview an existing published Landing page
        app.findaLeftNavigation().landingPages("Add New");
        app.findaEditLandingPage().editTitleField(previewLandingPageTest);
        // click the publish button
        app.findaEditLandingPage().
                saveChanges();
        app.findaLeftNavigation().landingPages();
        app.findaLandingPage().hitOptionLandingPage(previewLandingPageTest, "Edit");
        app.findaEditLandingPage().editTitleField(editPreviewLandingPageTest);
        // click the update button
        app.findaEditLandingPage().saveChanges();

        QualityVerify.verifyEquals(landingPagePreviewPage,
                app.findaEditLandingPage()
                        .previewLandingPage()
                        .getLandingPageTitle(),
                editPreviewLandingPageTest);

        app.findaPreviewLandingPage().closePreview();

        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-13 Admin Landing Page Fork for Layout abilities Validate admin can fork
     * Landing Pages for layout and merge forks
     * <p>
     * 1 login as admin should be at dashboard page 2 Click on Landing Pages should
     * be at All Landing Pages 3 Click on "Fork for Layout" option under a page,
     * make some edits and click on the Save Changes button. Go back to All Pages
     * Verify the forked page is "Locked for Editing" 4 Click on the Layout Fork
     * draft page and click the Merge Edits into Parents button. Go back to All
     * Pages Verify the forked page has been merged into the parent 5 Repeat steps
     * 3-4 this time using the "Fork for Layout link that is under the Publish
     * section on the right side of the page
     */
    @Test(groups = {TestGroup.TestType.Regression, "Metadata||tester-name:Ina||Automated:Yes"})
    public void adminLandingPageForkForLayoutAbilities() {

        final QualityCheck pageIsLockedForLayoutFromEditPageLink = qualityTestCase
                .addCheck("Verify the forked page is Locked for Editing");
        final QualityCheck onMergeEditsIntoLive = qualityTestCase.addCheck("Edits merged into live");
        final QualityCheck onForForLayoutEditPageLink = qualityTestCase
                .addCheck("Verify the forked page is Locked for Editing by edit page link");

        wordpressTestLogin(getAdmin());
        app.findaLeftNavigation().landingPages();

        String title = "AdminLPForkForLayoutTestPage";
        String forkForLayoutTitle = "Forked for Layout " + title;
        String changedContent = "Your Justice was our priority";

        QualityAssert.assertTrue(pageIsLockedForLayoutFromEditPageLink,
                app.findaLandingPage()
                        .clickForkForLayoutByPageName(title)
                        .fillInTitle(forkForLayoutTitle)
                        .editTextContentInDiviBuilder(changedContent)
                        .saveChanges()
                        .goBackToPage(DashboardMenu.LANDING_PAGES)
                        .searchForText(title)
                        .checkEntryIsLockedForEditing(title),
                "Forked Entry is not locked");

        QualityVerify.verifyEquals(
                onMergeEditsIntoLive, app.findaLandingPage()
                        .openEntryForEditByPageTitle(forkForLayoutTitle)
                        .mergeEditsIntoParent()
                        .checkEditsMergedIntoParent(),
                changedContent, "Edits are not merged into parent");

        app.findaEditLandingPage().saveChanges();

        QualityAssert.assertTrue(onForForLayoutEditPageLink,
                app.findaLeftNavigation()
                        .landingPages()
                        .searchForText(title)
                        .openEntryForEditByPageTitle(title)
                        .clickForkForLayoutOnEditPage()
                        .isForkCurrentlyViewed(),
                "Entry is not locked");

        app.findaTopNavigation().logout();
    }

    /**
     * Admin Denied Menu Options <br>
     * Test Steps:
     * <ol>
     * <li>Login as admin</li>
     * <li>Verify the absence of "Delete comments" submenu option under tools</li>
     * <li>Verify the absence of disable comments under settings</li>
     * <li>Verify there is no "Manage Comments" submenu option under settings</li>
     * <li>Verify there is no "Themes" submenu under appearance</li>
     * <li>Verify there is no "Your Profile" submenu option under users</li>
     * <li>Verify there is no "Edit your profile" option when hovering over user
     * email</li>
     * <li>Click on username (top right)</li>
     * <li>Verify there is no "Role Editor" submenu option under Divi</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminDeniedMenuOptions() {
        final QualityCheck noDeleteComments = qualityTestCase.addCheck("No delete comments under tools");
        final QualityCheck noDisableComments = qualityTestCase.addCheck("No Disable Comments under settings");
        final QualityCheck noThemes = qualityTestCase.addCheck("No themes under appearance");
        final QualityCheck noYourProfile = qualityTestCase.addCheck("No Your Profile under users");
        final QualityCheck noEditProfile = qualityTestCase.addCheck("No Edit Your Profile under email");
        final QualityCheck noUsernameRedirection = qualityTestCase.addCheck("No redirection upon clicking on username");
        final QualityCheck noRoleEditor = qualityTestCase.addCheck("No Role Editor under Divi");

        wordpressTestLogin(getAdmin());

        QualityVerify.verifyFalse(noDeleteComments,
                app.findaLeftNavigation()
                        .isSubTabPresent("Tools", "Delete Comments"),
                "Delete comments subtab under Tools is present when it shouldn't be");

        QualityVerify.verifyFalse(noDisableComments,
                app.findaLeftNavigation()
                        .isSubTabPresent("Settings", "Disable Comments"),
                "Disable Comments subtab under Settings is present when it shouldn't be");

        QualityVerify.verifyFalse(noThemes,
                app.findaLeftNavigation()
                        .isSubTabPresent("Appearance", "Themes"),
                "Themes subtab under Appearance is present when it shouldn't be");

        QualityVerify.verifyFalse(noYourProfile,
                app.findaLeftNavigation()
                        .isSubTabPresent("Users", "Your Profile"),
                "Your Profile present under Users when it shouldn't be");

        QualityVerify.verifyFalse(noEditProfile,
                app.findaTopNavigation()
                        .isEditMyProfilePresent(),
                "Edit My Profile is present when it shouldn't be");

        // Clicking on account email should not navigate away from current page
        app.findaTopNavigation().hitUsername();
        QualityAssert.assertEquals(noUsernameRedirection,
                app.findaHomePage().getPageTitle(),
                app.findaHomePage().getExpectedPageTitle(PagesEnum.HOME_PAGE),
                "Hitting account email navigated away from Home Page");

        QualityVerify.verifyFalse(noRoleEditor,
                app.findaLeftNavigation()
                        .isSubTabPresent("Divi", "Role Editor"),
                "Role Editor present under Divi when it shouldn't be");

        app.findaTopNavigation().logout();
    }

    /**
     * Admin Denied Network Capabilities <br>
     * Test Steps:
     * <ol>
     * <li>Click on All Users and hover over each user checking if they have an
     * "Edit" or "Remove" option</li>
     * <li>Click My Sites -> Network Admin -> Sites</li>
     * <li>Check for "Add New" option</li>
     * <li>Check for Delete, Deactivate, Archive, Span, Duplicate options for any of
     * the sites</li>
     * <li>Check for Bulk Action Dropdown</li>
     * <li>Click My Sites -> Network Admin -> Users</li>
     * <li>Check for "Edit" or "Delete" options for each user</li>
     * <li>Click on Settings -> General Settings</li>
     * <li>Check if admin can view all info</li>
     * <li>Check if admin can edit any info</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminDeniedNetworkCapabilities() {
        QualityCheck noEditRemoveSiteUsers = qualityTestCase
                .addCheck("No Edit or Remove option when hovering over user");
        QualityCheck noAddNewSite = qualityTestCase.addCheck("No Add New button on Network Admin -> Sites");
        QualityCheck noSiteOptions = qualityTestCase
                .addCheck("No Delete, Deactivate, Archive, Span, Duplicate Options for any sites");
        QualityCheck noBulkAction = qualityTestCase.addCheck("Should be no Bulk Action dropdown");
        QualityCheck noEditDeleteNetworkUsers = qualityTestCase.addCheck("No Edit or Delete options for any user");
        QualityCheck noSiteSettingsTab = qualityTestCase.addCheck("No Settings -> General Settings tab for admin");

        final String siteName = getSiteUrlNoHttp();

        wordpressTestLogin(getAdmin());

        QualityVerify.verifyFalse(noEditRemoveSiteUsers,
                app.findaLeftNavigation().users().optionsAvailableForAny(Arrays.asList("Edit", "Remove")),
                "Had Edit or Remove option when should be unable");

        app.findaTopNavigation().clickOptionUnderNetworkAdminSubmenu("Sites");

        QualityVerify.verifyFalse(noAddNewSite, app.findaViewSitesPage().isAddNewButtonPresent(),
                "Admin should not see Add New on Network Admin -> Sites");

        List<String> viewSitesNotOptions = Arrays.asList("Delete", "Deactivate", "Archive", "Span", "Duplicate");
        QualityAssert.assertFalse(noSiteOptions, app.findaViewSitesPage().optionsAvailableForAny(viewSitesNotOptions),
                "One of the following options was available for one of the below sites: "
                        + viewSitesNotOptions.toString());

        QualityVerify.verifyFalse(noBulkAction, app.findaViewSitesPage().isBulkActionButtonPresent(),
                "Bulk action was present when it should not be for Admin");

        app.findaTopNavigation().clickOptionUnderNetworkAdminSubmenu("Users");

        QualityVerify.verifyFalse(noEditDeleteNetworkUsers,
                app.findaViewUsersNetworkPage().optionsAvailableForAny(Arrays.asList("Edit", "Delete")),
                "Edit or Delete was available for at least one user for admin - Shouldn't be possible");

        // Navigate back from Network Admin to Findlaw Site Builder (ASSUMING CORRECT
        // SITE IS IN FIRST SPOT)
        app.findaTopNavigation().clickOptionUnderNetworkAdminSubmenu("Sites");
        app.findaViewSitesPage().goToDashboard(siteName);

        QualityVerify.verifyFalse(noSiteSettingsTab,
                app.findaLeftNavigation().isSubTabPresent("Settings", "Site Settings"),
                "A Site Settings tab under Settings was visible when it should not be for an admin");

        app.findaTopNavigation().logout();
    }

    /**
     * Admin People Capabilities <br>
     * Objective: Validate Admin can create, edit, and delete a person <br>
     * Test Steps:
     * <ol>
     * <li>Login as admin</li>
     * <li>Click on People</li>
     * <li>Click Add New button on menu</li>
     * <li>Edit name and click save</li>
     * <li>Check for the name you entered</li>
     * <li>Click Trash option under person that was created</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminPeopleCapabilities() {
        final QualityCheck newlyAddedPersonInList = qualityTestCase.addCheck("Found newly created person");
        final QualityCheck canEditExistingPerson = qualityTestCase.addCheck("Can edit an existing person");
        final QualityCheck personRemoved = qualityTestCase.addCheck("Person was removed");


        final String testMiddleName = "MiddleTest";
        final String testFirstName = "FirstTest";
        final String testLastName = "LastTest";

        final String testMiddleNameEdit = "MiddleTestEdit";
        final String testFirstNameEdit = "FirstTestEdit";
        final String testLastNameEdit = "LastTestEdit";

        wordpressTestLogin(getAdmin());

        QualityAssert.assertTrue(newlyAddedPersonInList,
                app.findaLeftNavigation()
                        .people()
                        .hitAddNewPerson()
                        .editPersonTitle(testFirstName, testMiddleName, testLastName)
                        .editName(testFirstName, testMiddleName, testLastName)
                        .hitPublishButton()
                        .returnToAllPeople()
                        .personInList(testFirstName, testMiddleName, testLastName),
                "Could not find person that should have been previously added");

        QualityVerify.verifyTrue(canEditExistingPerson,
                app.findaPeoplePage()
                        .editPerson(testFirstName, testMiddleName, testLastName)
                        .editPersonTitle(testFirstNameEdit, testMiddleNameEdit, testLastNameEdit)
                        .editName(testFirstNameEdit, testMiddleNameEdit, testLastNameEdit)
                        .hitPublishButton()
                        .returnToAllPeople()
                        .personInList(testFirstNameEdit, testMiddleNameEdit, testLastNameEdit),
                "No edit option under person");

        QualityAssert.assertFalse(personRemoved,
                app.findaPeoplePage()
                        .removePerson(testFirstNameEdit, testMiddleNameEdit, testLastNameEdit)
                        .personInList(testFirstNameEdit, testMiddleNameEdit, testLastNameEdit), "Person was not removed properly");

        app.findaTopNavigation().logout();
    }

    /**
     * Admin Published Page Capabilities <br>
     * Objective: Validate admin can create, edit and delete published pages <br>
     * Test Steps:
     * <ol>
     * <li>Login as admin</li>
     * <li>Click "Add New" submenu option under "Pages" option in the left menu</li>
     * <li>Click "Pages" menu option in left menu</li>
     * <li>Click "Add New" button on page</li>
     * <li>Click "Pages" menu option in left menu</li>
     * <li>Fill in title field of "Quick Add" form at the bottom of the page and hit
     * publish</li>
     * <li>Click on page created in step 6</li>
     * <li>Edit the title to something else and click the Update button</li>
     * <li>Click "Pages" menu option in left menu</li>
     * <li>Click Edit option under page created in step 6</li>
     * <li>Edit the title to something else and click the Preview Changes
     * button</li>
     * <li>Close the preview tab and click the Update button</li>
     * <li>Click "Pages" menu option in left menu</li>
     * <li>Click the "View" option under the page created in step 6</li>
     * <li>Navigate back to Pages</li>
     * <li>Click "Quick Edit" option under the page created in step 6, change the
     * title, and click Update</li>
     * <li>Hover over an existing published page and click on Quick Edit, add a note to the page
     * : Verify user can quick edit an existing published page</li>
     * <li> View edit page : Verify that quick add note is present and not duplicated on the page</li>
     * <li>Click "Trash" option under the page created in step 6</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminPublishedPageCapabilities() {
        final QualityCheck canAddNewPageFromLeftNav = qualityTestCase.addCheck("Can use Pages -> Add New");
        final QualityCheck canAddNewPageFromAllPages = qualityTestCase.addCheck("Can use Add New on Pages page");
        final QualityCheck newlyQuickAddedPageInList = qualityTestCase
                .addCheck("Page appears in list with given title");
        final QualityCheck editedTitle = qualityTestCase.addCheck("After page reloads, title field updated correctly");
        final QualityCheck newTabPreviewTitle = qualityTestCase
                .addCheck("New tab has been opened with preview of page");
        final QualityCheck atPreviewAgain = qualityTestCase.addCheck("When clicking view, preview page opens again");
        final QualityCheck quickEditedTitle = qualityTestCase.addCheck("Title changed after Quick Edit");
        final QualityCheck removedFromList = qualityTestCase.addCheck("Page was removed from list");
        final QualityCheck canAddDiscoveryNote = qualityTestCase
                .addCheck("Admin can add a discovery note on the quick edit page");
        final QualityCheck isQuickEditNoteDuplicated = qualityTestCase
                .addCheck("Discovery note added through quick edit is not duplicated on edit page");

        final String testPageTitle = "Test Pages Title";
        final String testPageTitleAddNew = "Test Pages Title (From Add New)";
        final String testPageTitleEdited = "Test Pages Title (Edited)";
        final String testPageTitlePreviewed = "Test Pages Title (Previewed)";
        final String testPageTitleQuick = "Test Pages Title (Quick Rename)";
        final String quickEditNote = "Quick edit note";

        wordpressTestLogin(getAdmin());

        QualityVerify.verifyEquals(canAddNewPageFromLeftNav, app.findaLeftNavigation().pages("Add New").getPageTitle(),
                app.findanEditPagePage().getExpectedPageTitle(PagesEnum.ADD_NEW_PAGE_PAGE),
                "Couldn't add a new page by using Left navigation");

        QualityVerify.verifyTrue(canAddNewPageFromAllPages,
                app.findaLeftNavigation()
                        .pages()
                        .hitAddNewOnPage()
                        .editPageTitleField(testPageTitleAddNew)
                        .hitUpdateOrPublishEditPage()
                        .returnToAllPages()
                        .searchForTitle(testPageTitleAddNew)
                        .hasPageWithTitle(testPageTitleAddNew),
                "Couldn't add a new page by using Add New on page");

        QualityVerify.verifyTrue(newlyQuickAddedPageInList,
                app.findaLeftNavigation().pages().setQuickAddTitleField(testPageTitle).hitQuickAddPublishButton()
                        .searchForTitle(testPageTitle).hasPageWithTitle(testPageTitle),
                "No page on Pages page with title: " + testPageTitle);

        QualityVerify.verifyTrue(editedTitle,
                app.findaPagesPage().openPage(testPageTitle).editPageTitleField(testPageTitleEdited)
                        .hitUpdateOrPublishEditPage().titleEquals(testPageTitleEdited),
                "Title of post is not equal to " + testPageTitle);

        QualityVerify.verifyEquals(newTabPreviewTitle,
                app.findaLeftNavigation().pages().searchForTitle(testPageTitleEdited).editPage(testPageTitleEdited)
                        .editPageTitleField(testPageTitlePreviewed).hitPreviewChangesEditPage().getTitleAndClose(),
                testPageTitlePreviewed, "Post title was incorrect in preview after editing");

        QualityVerify.verifyEquals(atPreviewAgain,
                app.findanEditPagePage().hitUpdateOrPublishEditPage().returnToAllPages()
                        .searchForTitle(testPageTitlePreviewed).previewPage(testPageTitlePreviewed).getTitleAndBack(),
                testPageTitlePreviewed, "Cannot preview using View option on existing page");

        QualityVerify.verifyTrue(quickEditedTitle,
                app.findaLeftNavigation().pages().searchForTitle(testPageTitlePreviewed)
                        .quickRenamePage(testPageTitlePreviewed, testPageTitleQuick).searchForTitle(testPageTitleQuick)
                        .hasPageWithTitle(testPageTitleQuick),
                "Could not rename page with Quick Edit");

        // admin can quick edit page (add a note)
        QualityVerify.verifyTrue(canAddDiscoveryNote,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(testPageTitleQuick)
                        .quickEditNotesForTitle(testPageTitleQuick, quickEditNote)
                        .openEntryForEditByPageTitle(testPageTitleQuick)
                        .isNotePresent(quickEditNote),
                "Discovery note is missing");

        // check for duplicate discovery notes (needed because of bug CMS-2195)
        QualityVerify.verifyFalse(isQuickEditNoteDuplicated,
                app.findanEditPagePage().isNoteDuplicated(quickEditNote),
                "Duplicate notes were created on edit page");

        QualityVerify.verifyFalse(removedFromList,
                app.findaLeftNavigation().pages().searchForTitle("").checkAllWithTitle(testPageTitleAddNew)
                        .checkAllWithTitle(testPageTitle).checkAllWithTitle(testPageTitleEdited)
                        .checkAllWithTitle(testPageTitlePreviewed).checkAllWithTitle(testPageTitleQuick)
                        .bulkDeletePages().hasPageWithTitle(testPageTitle),
                "Could not delete page, still in pages list");

        app.findaTopNavigation().logout();
    }

    /**
     * CMS-1881 Admin Page Bulk Actions Validate admin can perform bulk actions on
     * pages
     * <p>
     * 1. Login as admin 2. Navigate to All Pages 3. Search for page in search bar :
     * Verify page is found 4. Click on the Published pages and Draft pages tabs
     * above the list of pages : Verify that these tabs filter the pages 5. Select a
     * month from the filter dropdown and click filter : Verify that the visible
     * pages are all published in the filtered month 6. Select all pages, select
     * "Edit" from the bulk actions dropdown, click the apply button, edit the
     * author, and click update : Verify Admin can bulk edit fields 7. Select a
     * page, select "Move to Trash" in the bulk actions dropdown and click apply :
     * Verify Admin can bulk delete pages
     *
     * @author Matt Goodmanson
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminPageBulkActions() {
        final QualityCheck canSearchForPages = qualityTestCase.addCheck("Verify admin can search for a page");
        final QualityCheck canFilterPagesByCategoryPublished = qualityTestCase
                .addCheck("Verify admin can filter to published pages");
        final QualityCheck canFilterPagesByCategoryDraft = qualityTestCase
                .addCheck("Verify admin can filter to draft pages");
        final QualityCheck canFilterPagesByDatePublishedSeptember = qualityTestCase
                .addCheck("Verify admin can filter pages by date published for September");
        final QualityCheck canFilterPagesByDatePublishedAugust = qualityTestCase
                .addCheck("Verify admin can filter pages by date published for August");
        final QualityCheck canBulkEditPages = qualityTestCase.addCheck("Verify admin can bulk edit pages");
        final QualityCheck canBulkDeletePages = qualityTestCase.addCheck("Verify admin can bulk delete pages");

        // login as admin
        wordpressTestLogin(getAdmin());

        // navigate to pages
        app.findaLeftNavigation().pages();

        // search for disclaimer page
        QualityVerify.verifyTrue(canSearchForPages,
                app.findaPagesPage().searchForText("Disclaimer").getItemsList().size() == 1 && app.findaPagesPage()
                        .getItemTitle(app.findaPagesPage().getItemsList().get(0)).equalsIgnoreCase("Disclaimer"),
                "Search did not filter pages");

        // filter pages by category
        app.findaLeftNavigation().pages();
        QualityVerify.verifyTrue(canFilterPagesByCategoryPublished,
                app.findaPagesPage().hitPublishFilter().allItemsHaveStatus("publish"),
                "Not all pages were published after applying filter");

        QualityVerify.verifyTrue(canFilterPagesByCategoryDraft,
                app.findaPagesPage().hitDraftFilter().allItemsHaveStatus("draft"),
                "Not all pages were drafts after applying filter");

        // filter by date published
        app.findaLeftNavigation().pages();

        QualityVerify.verifyTrue(
                canFilterPagesByDatePublishedAugust, app.findaPagesPage()
                        .selectPublishedDateAndFilterResults("August 2018").allItemsInFilteredDate("08", "2018"),
                "Not all pages were published in August 2018");

        QualityVerify.verifyTrue(
                canFilterPagesByDatePublishedSeptember, app.findaPagesPage()
                        .selectPublishedDateAndFilterResults("September 2018").allItemsInFilteredDate("09", "2018"),
                "Not all pages were published in September 2018");

        // bulk edit pages
        app.findaLeftNavigation().pages()
                .searchForTitle("Page to Delete For Automation")
                .selectItem(app.findaPagesPage().getItemByTitle("Page to Delete For Automation 2"))
                .selectItem(app.findaPagesPage().getItemByTitle("Page to Delete For Automation 3"))
                .selectBulkAction("Edit").hitApplyBulkAction().selectStatusForBulkEdit("Draft").hitUpdateBulkEdit()
                .hitDraftFilter();

        QualityVerify.verifyTrue(canBulkEditPages,
                app.findaPagesPage()
                        .getItemByTitle("Page to Delete For Automation 2") != null
                        && app.findaPagesPage()
                        .getItemByTitle("Page to Delete For Automation 3") != null,
                "Failed to bulk update status");

        // bulk delete pages
        app.findaLeftNavigation().pages()
                .searchForTitle("Page to Delete For Automation")
                .selectItem(app.findaPagesPage()
                        .getItemByTitle("Page to Delete For Automation 2"))
                .selectItem(app.findaPagesPage()
                        .getItemByTitle("Page to Delete For Automation 3"))
                .selectBulkAction("Move to Trash")
                .hitApplyBulkAction();

        QualityVerify.verifyTrue(canBulkDeletePages,
                app.findaPagesPage()
                        .getItemByTitle("Page to Delete For Automation 2") == null
                        && app.findaPagesPage()
                        .getItemByTitle("Page to Delete For Automation 3") == null,
                "Page was not deleted in bulk delete");

        // logout
        app.findaTopNavigation().logout();
    }

    /**
     * Admin Network Site Capabilities <br>
     * Description: Validate admin can view and edit all sites <br>
     * Steps: <br>
     * <ol>
     * <li>Login as admin</li>
     * <li>Click on My Sites -> Network Admin -> Sites</li>
     * <li>Hover over a site and click Dashboard</li>
     * <li>Hover over a site and click Visit</li>
     * <li>Hover over a site and click Edit</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminNetworkSiteCapabilities() {
        final QualityCheck canViewDashboard = qualityTestCase.addCheck("Can view a site's dashboard");
        final QualityCheck firmNameOnTopNavigation = qualityTestCase.addCheck("Firm name appears on site's top navigation");
        final QualityCheck canVisitSite = qualityTestCase.addCheck("Admin can visit a site");
        final QualityCheck canEditSite = qualityTestCase
                .addCheck("Admin can edit a site (info, users, themes, settings, aliases");

        final String siteTitle = getSiteUrlNoHttp();
        final String visitPageTitle = "QA Test Automation | IM-Template";
        final String expectedFirmName = "Abraham, Watkins, Nichols, Sorrels, Agos...";

        wordpressTestLogin(getAdmin());

        QualityVerify.verifyEquals(canViewDashboard,
                app.findaTopNavigation()
                        .goToNetworkSitesPage()
                        .goToDashboard(siteTitle)
                        .getPageTitle(),
                app.findaHomePage().getExpectedPageTitle(PagesEnum.DASHBOARD_PAGE),
                "Could not view dashboard for site");

        QualityVerify.verifyEquals(firmNameOnTopNavigation,
                app.findaTopNavigation()
                        .getFirmName(),
                expectedFirmName,
                "Firm name does not appear correctly on top navigation");

        QualityVerify.verifyEquals(canVisitSite,
                app.findaTopNavigation()
                        .goToNetworkSitesPage()
                        .visitSite(siteTitle)
                        .getTitleAndReturn(),
                visitPageTitle, "Could not open site by clicking visit");

        QualityVerify.verifyTrue(canEditSite,
                app.findaViewSitesPage()
                        .editSite(siteTitle)
                        .hasAllTabs(Arrays.asList("Info", "Users", "Themes", "Settings", "Aliases")),
                "Could not find \"Info\", \"Users\", \"Themes\", \"Settings\", \"Aliases\" tabs for site");

        // logout
        app.findaTopNavigation().logout();
    }


    /**
     * Admin Blog Post Capabilities
     * Validate Admin can create, edit, delete, publish, and schedule posts
     * <p>
     * See blogPostCapabilities()
     *
     * @author Matt Goodmanson
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminBlogPostCapabilities() {
        final String blogPostAddNewMenuOption = "Admin Blog Post Capabilities: Post with edited publish date";
        final String blogPostAddNewButton = "Admin Blog Post Capabilities: add new button";
        final String bulkDeletePost1 = "Post to Delete for Automation 1";
        final String bulkDeletePost2 = "Post to delete for Automation 2";

        // login as admin
        wordpressTestLogin(getAdmin());

        blogPostCapabilities("Admin", blogPostAddNewMenuOption, blogPostAddNewButton, bulkDeletePost1, bulkDeletePost2);

        // logout
        app.findaTopNavigation().logout();
    }

    /**
     * Admin Widget Capabilities <br>
     * Validate Admin can move, add, and remove widgets <br>
     * Test Steps:
     * <ol>
     * <li>Login as Admin</li>
     * <li>Go to Appearance -> Widgets and add Calendar widget to Sidebar</li>
     * <li>Verify that widget is in Sidebar on Widgets page</li>
     * <li>Verify that widget is on the Disclaimer page</li>
     * <li>Delete widget from Sidebar</li>
     * <li>Verify that widget is not in Sidebar on Widgets page</li>
     * <li>Verify that widget is not on the Disclaimer page</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression, "Metadata||tester-name:Ina||Automated:Yes"})
    public void adminWidgetCapabilities() {

        final QualityCheck adminWidgetInSidebar =
                qualityTestCase.addCheck("The admin can add a widget (calendar) to the Sidebar");
        final QualityCheck widgetVisibleOnView =
                qualityTestCase.addCheck("Widget appears on sidebar on Disclaimer page");
        final QualityCheck canEditWidgetTitle =
                qualityTestCase.addCheck("Admin can edit the title of a widget");
        final QualityCheck widgetRenamedWhenViewingPage =
                qualityTestCase.addCheck("Widget title has been changed on preview/view page");
        final QualityCheck adminCanRemoveWidgetFromSidebar =
                qualityTestCase.addCheck("The admin can delete a widget (calendar) from the Sidebar ");
        final QualityCheck widgetNotVisibleOnView =
                qualityTestCase.addCheck("Widget does not appear on sidebar on Disclaimer page");

        final String calendarWidget = "Calendar";
        final String targetSidebar = "Sidebar";
        final String pageTitle = "Accidents at Work";
        final String newTitle = "Admin Calendar Widget Title";

        wordpressTestLogin(getAdmin());
        app.findaLeftNavigation().widgets();

        QualityVerify.verifyTrue(adminWidgetInSidebar,
                app.findaWidgetsPage()
                        .addWidget(calendarWidget, targetSidebar)
                        .isWidgetInSidebar(calendarWidget, targetSidebar),
                "Widget does not appear in Sidebar after attempting to add");

        QualityVerify.verifyTrue(widgetVisibleOnView,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageTitle)
                        .previewPage(pageTitle)
                        .hasCalendarAndBrowserNavigateBack(),
                "Widget is not visible on the Disclaimer page");

        QualityVerify.verifyTrue(canEditWidgetTitle,
                app.findaLeftNavigation()
                        .widgets()
                        .editWidgetTitle(calendarWidget, targetSidebar, newTitle)
                        .isWidgetInSidebarWithName(calendarWidget, targetSidebar, newTitle),
                "Admin could edit the title of the widget");

        QualityVerify.verifyTrue(widgetRenamedWhenViewingPage,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageTitle)
                        .previewPage(pageTitle)
                        .hasCalendarWithTitleAndBrowserNavigateBack(newTitle),
                "Widget is not visible on page with expected title");


        QualityVerify.verifyFalse(adminCanRemoveWidgetFromSidebar,
                app.findaLeftNavigation()
                        .widgets()
                        .removeWidget(calendarWidget, targetSidebar)
                        .isWidgetInSidebar(calendarWidget, targetSidebar),
                "Widget appears in Sidebar after attempted removal when it shouldn't");

        QualityVerify.verifyFalse(widgetNotVisibleOnView,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageTitle)
                        .previewPage(pageTitle)
                        .hasCalendarAndBrowserNavigateBack(),
                "Widget is visible on the Disclaimer page when it shouldn't be");

        app.findaTopNavigation().logout();
    }

    /**
     * Admin Users Capabilities <br>
     * Description: Validate Admin can view all users, add new and existing users at
     * a site and network level <br>
     * Test Steps
     * <ol>
     * <li>Click on Users -> Add New and verify admin can add an existing user to
     * the site</li>
     * <li>Click on Users -> Add New and verify Admin can add a new user to the
     * site</li>
     * <li>Go to My Sites -> Network Admin -> Users and verify Admin can view all
     * users</li>
     * <li>Go to My Sites -> Network Admin -> Users and verify Admin can add a user
     * at the network level</li>
     * </ol>
     * <br>
     * Cleanup required: <br>
     * Remove existing network user from automationtestsite Remove two users added to
     * network
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminUsersCapabilities() {

        final QualityCheck addExistingUserNotification = qualityTestCase
                .addCheck("Admin received notification that existing user added");
        final QualityCheck addedExistingUserInSiteAllUsers = qualityTestCase
                .addCheck("Existing user appears in Site -> All Users after being added by Admin");
        final QualityCheck addNewUserToSiteNotification = qualityTestCase
                .addCheck("Admin received notification that new user added");
        final QualityCheck addedNewUserToSiteInSiteAllUsers = qualityTestCase
                .addCheck("New user appears in Site -> All Users after being added by Admin");
        final QualityCheck canViewAllUsers = qualityTestCase.addCheck("Admin has view option on all users");
        final QualityCheck addUserToNetworkNotification = qualityTestCase
                .addCheck("Notification received when Admin adds a user at the network level");
        final QualityCheck addedNetworkUserInNetworkAllUsers = qualityTestCase
                .addCheck("User added to the network level by Admin appears in All Users");

        final String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        final String existingUsername = "qaautomationadmintestexistingcustomer";
        final String existingEmail = String.format("eh.trtesting+%s@gmail.com", existingUsername);
        final String newUsername = String.format("qaautomationadmintestaddtositecustomer%s", dateTime);
        final String newEmail = String.format("eh.trtesting+%s@gmail.com", newUsername);

        final String addNetworkUserUsername = String.format("qaautomationadmintestaddtonetworkcopyvendor%s", dateTime);
        final String addNetworkUserEmail = String.format("eh.trtesting+%s@gmail.com", addNetworkUserUsername);

        wordpressTestLogin(getAdmin());

        app.findaLeftNavigation()
                .addNewUser()
                .enterExistingEmailOrUsername(existingUsername)
                .hitAddExistingUser();

        QualityVerify.verifyTrue(addExistingUserNotification,
                app.findaLeftNavigation()
                        .addNewUser()
                        .enterExistingEmailOrUsername(existingUsername)
                        .hitAddExistingUser()
                        .existingUserNotificationReceived(),
                "Notification not received after adding existing user to site");

        QualityVerify.verifyTrue(addedExistingUserInSiteAllUsers,
                app.findaLeftNavigation()
                        .users()
                        .userExists(existingEmail),
                "Existing user does not appear in users list");

        QualityVerify.verifyTrue(addNewUserToSiteNotification,
                app.findaLeftNavigation()
                        .addNewUser()
                        .enterNewUserUsername(newUsername)
                        .enterNewUserEmail(newEmail)
                        .hitAddNewUser()
                        .notificationReceived(),
                "Notification not received after adding new user to site");

        QualityVerify.verifyTrue(addedNewUserToSiteInSiteAllUsers,
                app.findaLeftNavigation()
                        .users()
                        .userExists(newEmail), "New user does not appear in users list");

        app.findaTopNavigation()
                .clickOptionUnderNetworkAdminSubmenu("Users");

        QualityVerify.verifyTrue(canViewAllUsers,
                app.findaViewUsersNetworkPage()
                        .canViewAllUsers(),
                "Cannot see View option for all sites for all users as admin");

        QualityVerify.verifyTrue(addUserToNetworkNotification,
                app.findaViewUsersNetworkPage()
                        .hitAddUser()
                        .enterUsername(addNetworkUserUsername)
                        .enterEmail(addNetworkUserEmail)
                        .hitAddUserButton()
                        .notificationReceived(),
                "Admin could not add new user to network");

        QualityVerify.verifyTrue(addedNetworkUserInNetworkAllUsers,
                app.findaAddNewUsersNetworkPage()
                        .returnToAllUsersNetwork()
                        .userExists(addNetworkUserEmail),
                "New network user does not appear in network users list");

        // Logout
        app.findaTopNavigation().logout();
    }

    /*
     * Admin Draft Page Capabilities <br> Objective: Validate admin can create, edit
     * and delete Draft page <br> Test Steps: <ol> <li>Login as admin</li> <li>Click
     * "Add New" page->save page->verify admin can create draft page <li>Click on
     * page->edit draft page->verify admin can edit draft page <li>Click on screen
     * option->verify no comments checkbox is present <li>Click on page->click on
     * Page type drop down->verify no "Comment Response Page" option present <li>Go
     * to page ->click on "move to trash"->verify admin can delete draft page </ol>
     */

    @Test(groups = {TestGroup.TestType.Regression})
    public void adminDraftPageCapabilities() {

        final QualityCheck newlyCreatedDraftPageNameInList = qualityTestCase
                .addCheck("Newly created Draft page found in Page list");
        final QualityCheck noCommentOptionInPageTypeDropdown = qualityTestCase
                .addCheck("Comment option not in page type dropdown");
        final QualityCheck editedNewlyCreatedDraftPageBody = qualityTestCase
                .addCheck("Newly created Draft page body was edited");
        final QualityCheck newlyCreatedDraftPageRemovedFromList = qualityTestCase
                .addCheck("Newly created Draft page removed from Page list");
        final QualityCheck noCommentInScreenOptions = qualityTestCase
                .addCheck("Comments checkbox not found under Screen Options");

        final String draftPageTitle = "Admin Draft Page Test";
        final String draftPageBodyText = "Work-related truck and auto accidents may not often be thought of as being covered by workers� comp. "
                + "When these accidents happen in the scope of employment, however, injured workers may still be eligible"
                + " for benefits and should still speak with an experienced attorney about their rights.";
        final String draftPageEditedBodyText = "Work-related truck and auto accidents may not often be thought of as being covered by workers� "
                + "compensation benefits. When these accidents happen in the scope of employment, however, injured workers"
                + "may still be eligible for benefits and should still speak with an experienced attorney about their rights. "
                + "Learn more information about your rights by contacting our firm.";
        wordpressTestLogin(getAdmin());
        // admin can add a new draft page

        app.findaLeftNavigation().pages();
        app.findaPagesPage().hitAddNew();
        app.findanEditPagePage().editTitleField(draftPageTitle);
        app.findanEditPagePage().editDraftPageTextArea(draftPageBodyText);
        app.findanEditPagePage().hitSaveDraftButton();
        app.findaLeftNavigation().pages();
        QualityAssert.assertTrue(newlyCreatedDraftPageNameInList, app.findaPagesPage().hasPageWithTitle(draftPageTitle),
                "Page with title:" + draftPageTitle + " not found ");

        // admin can edit an existing draft page

        app.findaPagesPage().openPage(draftPageTitle);
        app.findanEditPagePage().editDraftPageTextArea(draftPageEditedBodyText);
        app.findanEditPagePage().hitSaveDraftButton();
        app.findaLeftNavigation().pages();
        app.findaPagesPage().openPage(draftPageTitle);
        QualityVerify.verifyTrue(editedNewlyCreatedDraftPageBody,
                app.findanEditPagePage().isPageBodyEdited(draftPageEditedBodyText),
                "Page body is not updated with " + draftPageEditedBodyText);

        // no "Comments" checkbox under Screen Options tab

        QualityVerify.verifyFalse(noCommentInScreenOptions,
                app.findaDashboardPage().expandScreenOptions().isScreenOptionPresent("Comments"),
                "Comments checkbox present under Screen Options");

        // no "Comment Response Page" option in the Page type drop down box on the edit
        // page
        app.findaLeftNavigation().pages();
        app.findaPagesPage().openPage(draftPageTitle);
        QualityVerify.verifyFalse(noCommentOptionInPageTypeDropdown,
                app.findaBlogPostEditPage().isPageTypeOptionPresent("Comment Response Page"),
                "Comment Response Page option is present in page type drop down");

        // delete the page by clicking the "Move to Trash" link under the publish
        // section

        app.findanEditPagePage().hitTrashLink();
        QualityAssert.assertFalse(newlyCreatedDraftPageRemovedFromList,
                app.findaPagesPage().hasPageWithTitle(draftPageTitle),
                "Page with title :" + draftPageTitle + "is not deleted");

        app.findaTopNavigation().logout();
    }

    /*
     * Admin Integration Capabilities <br> Objective: Validate admin can sync
     * coportal and directory sync <br> Test Steps: <ol> <li>Login as admin</li>
     * <li>Click "TOOLS"->Click on SYNC CONTENT->Click on sync directory->verify
     * success message <li>Click "TOOLS"->Click on SYNC CONTENT->Click on sync
     * coportal site metadata->verify success message <li>Click on MYSITE ->Go to
     * site->Click on MYSITE ,go to site <li>Click on SYNC CONTENT->Click on sync
     * directory->verify success message <li>Click "TOOLS"->Click on SYNC
     * CONTENT->Click on sync coportal site metadata->verify success message </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminIntegrationCapabilities() throws InterruptedException {

        final QualityCheck directorySyncSuccessfulFromTools = qualityTestCase
                .addCheck("Directory was successfully synced from Tools menu");
        final QualityCheck directorySyncSuccessfulFromPreviewHeader = qualityTestCase
                .addCheck("Directory was successfully synced from preview header");
        final QualityCheck coportalSiteMetaDataSyncSuccessfulFromTools = qualityTestCase
                .addCheck("Coportal Site Meta was successfully synced from Tools menu");
        final QualityCheck coportalSiteMetaDataSyncSuccessfulFromPreviewHeader = qualityTestCase
                .addCheck("Coportal Site Meta was successfully synced from preview header");
        final QualityCheck syncContentMenuInPreviewHeader = qualityTestCase
                .addCheck("Checking Sync content menu in preview page");
        final QualityCheck navigateToSitePreviewForSyncContentMenu = qualityTestCase
                .addCheck("Navigationg to site");

        wordpressTestLogin(getAdmin());

        // admin can resync directory from Tools

        app.findaLeftNavigation().tools("Sync Content");
        app.findaSyncContentPage().hitSyncDirectoryContentButton();
        QualityVerify.verifyTrue(directorySyncSuccessfulFromTools,
                app.findaSyncContentPage().isSyncDirectorySuccessful(),
                "Directory sync failed from Tools");

        // admin can resync coportal from Tools

        app.findaLeftNavigation().tools("Sync Content");
        app.findaSyncContentPage().hitSyncCoportalSiteMetaButton();
        QualityVerify.verifyTrue(coportalSiteMetaDataSyncSuccessfulFromTools,
                app.findaSyncContentPage().isSyncCoportalSiteMetaSuccessful(),
                "Coportal sync failed from Tools");

        // admin can resync directory from Preview header

        app.findaTopNavigation().clickOptionUnderNetworkAdminSubmenu("Sites");
        QualityVerify.verifyTrue(navigateToSitePreviewForSyncContentMenu,
                app.findaViewSitesPage().visitSite(), "Unable to navigate to site view");

        QualityVerify.verifyTrue(syncContentMenuInPreviewHeader, app.findaTopNavigation().isSyncContentMenuPresent(),
                "Sync content menu not found in preview page");
        app.findaTopNavigation().clickOptionUnderSyncContent("Directory Content");
        QualityVerify.verifyTrue(directorySyncSuccessfulFromPreviewHeader,
                app.findaSyncContentPage().isSyncDirectorySuccessful(),
                "Directory sync failed from preview header");

        // admin can resync coportal from Preview header

        app.findaTopNavigation().clickOptionUnderNetworkAdminSubmenu("Sites");
        QualityVerify.verifyTrue(navigateToSitePreviewForSyncContentMenu, app.findaViewSitesPage().visitSite(),
                "Unable to navigate to site view");
        app.findaTopNavigation().clickOptionUnderSyncContent("CoPortal Site Meta");
        QualityVerify.verifyTrue(coportalSiteMetaDataSyncSuccessfulFromPreviewHeader,
                app.findaSyncContentPage().isSyncCoportalSiteMetaSuccessful(),
                "Coportal sync failed from preview header");

        app.findaTopNavigation().logout();
    }

    /**
     * Admin Blog Post Tag Capabilities
     * <br>
     * Objective: Validate admin can create, edit, and delete tags
     * <br>
     * Test steps: See blogPostTagCapabilities.
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminBlogPostTagCapabilities() {

        final String newTagOnTagsScreen = "AdminTagTest_fromTagsScreen";
        final String newTagOnPostEditScreen = "AdminTagTest_fromEditScreen";
        final String blogPostTitle = "Schools are frequent sites of bad driving habits";

        wordpressTestLogin(getAdmin());

        blogPostTagCapabilities(newTagOnTagsScreen, newTagOnPostEditScreen, blogPostTitle);

        app.findaTopNavigation().logout();
    }

    /**
     * Admin Default Tagging Settings
     * <br>
     * <ol>
     * <li>Click blog posts and verify
     * <li>page goal set to "Legal Information"
     * <li>Language set to English
     * <li>page type set to "Blog post"</li>
     *
     * <li>Click pages and verify
     * <li>page goal set to "Legal Information"
     * <li>Language set to "English"
     * <li>page type set to "Marketing Page"</li>
     *
     * <li>Click landing pages and verify
     * <li>page goal set to "Lead Generation"
     * <li>Language set to "English"
     * <li>page type set to "PPC Landing Page"</li>
     *
     * <li>Click people and verify
     * <li>page goal set to "Other"
     * <li>Language set to "English"
     * <li>page type set to "Attorney Profile Page"</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminDefaultTaggingSettings() {


        final QualityCheck defaultSettingsOnBlogPostPage = qualityTestCase.addCheck("Default settings are selected on Blog Post");
        final QualityCheck defaultSettingsOnPage = qualityTestCase.addCheck("Default settings are selected on page");
        final QualityCheck defaultSettingsOnLandingPage = qualityTestCase.addCheck("Default settings are selected on landing page");
        final QualityCheck defaultSettingsOnPeoplePage = qualityTestCase.addCheck("Default settings are selected on people page");

        wordpressTestLogin(getAdmin());

        app.findaLeftNavigation().blogPosts("Add New");
        QualityVerify.verifyTrue(defaultSettingsOnBlogPostPage, app.findaBlogPostEditPage().isPageGoalOptionSelected("Legal Information")
                && app.findaBlogPostEditPage().isLanguageOptionSelected("English")
                && app.findaBlogPostEditPage().isPageTypeOptionSelected("Blog Post"), "Default values for drop downs are not selected in blog post");

        app.findaLeftNavigation().pages("Add New");
        QualityVerify.verifyTrue(defaultSettingsOnPage, app.findaBlogPostEditPage().isPageGoalOptionSelected("Legal Information")
                && app.findaBlogPostEditPage().isLanguageOptionSelected("English")
                && app.findaBlogPostEditPage().isPageTypeOptionSelected("Marketing Page"), "Default values for drop downs are not selected in page");

        app.findaLeftNavigation().landingPages("Add New");
        QualityVerify.verifyTrue(defaultSettingsOnLandingPage, app.findaBlogPostEditPage().isPageGoalOptionSelected("Lead Generation")
                && app.findaBlogPostEditPage().isLanguageOptionSelected("English")
                && app.findaBlogPostEditPage().isPageTypeOptionSelected("PPC Landing Page"), "Default values for drop downs are not selected in landing page ");

        app.findaLeftNavigation().people("Add New");
        QualityVerify.verifyTrue(defaultSettingsOnPeoplePage, app.findaBlogPostEditPage().isPageGoalOptionSelected("Other")
                && app.findaBlogPostEditPage().isLanguageOptionSelected("English")
                && app.findaBlogPostEditPage().isPageTypeOptionSelected("Attorney Profile"), "Default values for drop downs are not selected in people page ");

        app.findaTopNavigation().logout();

    }

    /**
     * A-TC-19
     * Admin Blog Post Categories Capabilities
     * <br>
     * Description: Validate admin can create, edit, and delete categories
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as Administrator</li>
     * <li>Go to Blog Posts -> Categories and add a new category (newCategoryFromCategoryPage)</li>
     * <li>Verify newCategoryFromCategoryPage was added to Categories list</li>
     * <li>Edit an existing blog post by adding the category (newCategoryFromCategoryPage) and click update</li>
     * <li>Click Preview Changes and verify that the newCategoryFromCategoryPage is displaying on the preview</li>
     * <li>Return from preview to edit blog post page</li>
     * <li>Add a new category from the edit post page (newCategoryFromEditPost)</li>
     * <li>Verify that newCategoryFromEditPost is in the category list on the Blog Post Edit page</li>
     * <li>Preview the post and verify that newCategoryFromEditPost is on the preview</li>
     * <li>Go to Blog Posts -> Categories</li>
     * <li>Edit newCategoryFromCategoryPage name to editedCategory and click update</li>
     * <li>Return to blog post preview and verify that editedCategory is on the preview</li>
     * <li>Go to Blog Posts -> Categories and delete editedCategory with the delete option on hover</li>
     * <li>Edit the newCategoryFromEditPost and click delete, which returns to Categories list</li>
     * <li>Verify editedCategory and newCategoryFromEditPost were removed from the Categories list</li>
     * <li>Go to the Blog Post preview</li>
     * <li>Verify editedCategory and newCategoryFromEditPost were removed from preview</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminBlogPostCategoriesCapabilities() {
        final QualityCheck newCategoryAddedOnCategoryPage =
                qualityTestCase.addCheck("Category added from Blog Posts -> Category appears on All Categories list");
        final QualityCheck newlyAddedCategoryDisplaysOnPreview =
                qualityTestCase.addCheck("Category (categoryAddNew) added to a blog post displays on a preview of the post");
        final QualityCheck newCategoryAddedOnEditPostPage =
                qualityTestCase.addCheck("Category added from Blog Post Edit Page is in list of categories for the page");
        final QualityCheck newlyAddedCategoryOnEditPostDisplaysOnPreview =
                qualityTestCase.addCheck("Category (categoryEditPost) displays on preview page of the post to which it was added");
        final QualityCheck categoryWasEdited =
                qualityTestCase.addCheck("After editing name of category (categoryAddNew -> categoryAddNewEdited), new category displays"
                        + "on preview");
        final QualityCheck categoriesWereDeleted =
                qualityTestCase.addCheck("After deleting categories they no longer appear in Categories list");
        final QualityCheck deletedCategoriesNotInPreview =
                qualityTestCase.addCheck("After deleting categoryEditPost it no longer displays on preview of a blog post");

        final String newCategoryFromCategoryPage = "AdminCategoryTest_fromCategoryPage";
        final String newCategoryFromEditPost = "AdminCategoryTest_fromEditPost";
        final String editedCategory = "AdminCategoryTest_fromCategoryPageEdited";
        final String postName = "Motorcycles may soon get their own AI safety technology";

        wordpressTestLogin(getAdmin());

        QualityVerify.verifyTrue(newCategoryAddedOnCategoryPage,
                app.findaLeftNavigation()
                        .blogCategories()
                        .enterNewCategoryName(newCategoryFromCategoryPage)
                        .hitAddNewCategory()
                        .titleExists(newCategoryFromCategoryPage),
                "Admin could not add category to Categories from Categories page");

        QualityVerify.verifyTrue(newlyAddedCategoryDisplaysOnPreview,
                app.findaLeftNavigation()
                        .blogPosts()
                        .editBlogPost(postName)
                        .selectExistingCategory(newCategoryFromCategoryPage)
                        .saveChanges()
                        .hitPreviewButton()
                        .hasCategoryAndClose(newCategoryFromCategoryPage),
                "Category was not shown on preview page after adding in edit post page");

        QualityVerify.verifyTrue(newCategoryAddedOnEditPostPage,
                app.findaBlogPostEditPage()
                        .addNewCategory(newCategoryFromEditPost)
                        .categoryInSection(newCategoryFromEditPost),
                "Category added from Blog Post Edit Page is not appearing on Categories page list");

        QualityVerify.verifyTrue(newlyAddedCategoryOnEditPostDisplaysOnPreview,
                app.findaBlogPostEditPage()
                        .saveChanges()
                        .hitPreviewButton()
                        .hasCategoryAndClose(newCategoryFromEditPost),
                "Category added from Blog Post Edit Page does not display on preview page");

        // Edit the name of categoryAddNew
        app.findaLeftNavigation()
                .blogCategories()
                .editBlogPostCategory(newCategoryFromCategoryPage)
                .editName(editedCategory)
                .hitUpdateButton();

        QualityVerify.verifyTrue(categoryWasEdited,
                app.findaLeftNavigation()
                        .blogPosts()
                        .editBlogPost(postName)
                        .hitPreviewButton()
                        .hasCategoryAndClose(editedCategory),
                "Edited category (categoryAddNewEdited) does not display on preview page");

        QualityVerify.verifyFalse(categoriesWereDeleted,
                app.findaLeftNavigation()
                        .blogCategories()
                        .hitDeleteOptionForCategory(editedCategory)
                        .hitDeleteOptionForCategory(newCategoryFromEditPost)
                        .anyTitlesExist(Arrays.asList(editedCategory, newCategoryFromEditPost)),
                "Admin could not delete all categories from Categories page");

        QualityVerify.verifyFalse(deletedCategoriesNotInPreview,
                app.findaLeftNavigation()
                        .blogPosts()
                        .editBlogPost(postName)
                        .hitPreviewButton()
                        .hasAnyOneCategory(Arrays.asList(editedCategory, newCategoryFromEditPost)),
                "A category appears in preview when it should have been deleted");

        // Navigate away from blog post editing before logging out
        app.findaLeftNavigation().blogPosts();

        // Logout
        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-15
     * Admin Media Capabilities
     * <br>
     * Test Description: Validate Admin can view and upload file in Media library
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as Admin</li>
     * <li>Navigate to Media -> Library and verify that Admin can view all items</li>
     * <li>Verify that Admin can add a file with Media -> Add New</li>
     * <li>Add a file with the Add New button on the Media -> Library page</li>
     * <li>Verify that the file is in the Library page</li>
     * <li>Search for the file and verify that searching for the added file places the first uploaded photo in the first item location</li>
     * <li>Logout</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminMediaCapabilities() {
        wordpressTestLogin(getAdmin());
        mediaCapabilities("Admin");
        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-17
     * Admin Stream Capabilities
     * <br>
     * Description: Validate Admin can access stream, create notifications, and edit settings
     * <br>
     * Test Steps:
     * <br>
     * <ol>
     * <li>Login as Admin</li>
     * <li>Click on stream</li>
     * <li>Check that Admin can "View" all stream records</li>
     * <li>Click Stream -> Alerts and add a new alert</li>
     * <li>Verify that the alert was created</li>
     * <li>Delete the alert and veriy that it was deleted</li>
     * <li>Go to Stream -> Settings and verify admin can view the 3 tabs on the page</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminStreamCapabilities() {
        final QualityCheck canViewStreamRecords =
                qualityTestCase.addCheck("Admin has \"View\" suboption for all stream records");
        final QualityCheck canCreateNewAlert =
                qualityTestCase.addCheck("Admin can add a new Stream alert");
        final QualityCheck canDeleteAlert =
                qualityTestCase.addCheck("Admin can delete a stream record");
        final QualityCheck canEditSettings =
                qualityTestCase.addCheck("Admin has access to tabs General, Exclude, and Advanced in Stream -> Settings");

        final String alertAuthor = "qaautomationcustomer";
        final String alertContext = "Fields";
        final String alertAction = "Created";

        wordpressTestLogin(getAdmin());

        QualityVerify.verifyEquals(canViewStreamRecords,
                app.findaLeftNavigation()
                        .stream()
                        .getPageTitle(),
                app.findaHomePage()
                        .getExpectedPageTitle(PagesEnum.STREAM),
                "Admin did not navigate to Stream Records page when clicking Stream");

        QualityAssert.assertTrue(canCreateNewAlert,
                app.findaLeftNavigation()
                        .alerts()
                        .hitAddNewButton()
                        .addNewAlert(alertAuthor, alertContext, alertAction)
                        .alertExists(alertAuthor, alertContext, alertAction),
                "Alert is in the list of Alerts");

        QualityVerify.verifyFalse(canDeleteAlert,
                app.findanAlertsPage()
                        .deleteAlert(alertAuthor, alertContext, alertAction)
                        .alertExists(alertAuthor, alertContext, alertAction));

        QualityVerify.verifyTrue(canEditSettings,
                app.findaLeftNavigation()
                        .streamSettings()
                        .canSeeSettingsTabs(),
                "Admin can not view all three tabs");

        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-20
     * Admin Forms Capabilities
     * <br>
     * Description: Validate Admin can add forms to pages
     * <br>
     * Test Steps: See formsCapabilities
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminFormsCapabilities() {
        final String shortFormPageName = "Admin Contact";
        final String longFormPageName = "Admin Complaints Contact Form";

        wordpressTestLogin(getAdmin());
        formsCapabilities(shortFormPageName, longFormPageName);
        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-26
     * Admin Custom Widget Visual Builder Capabilities
     * <br>
     * Description: Validate that the admin can add a custom widget to a page through the visual builder.
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as Admin</li>
     * <li>Go to Appearance -> Widgets and add a custom widget area with a Calendar widget with a title</li>
     * <li>View a page and click on Enable Visual Builder</li>
     * <li>Add the new custom widget area to the page</li>
     * <li>Verify that the calendar in the custom widget area is visible on the page</li>
     * <li>Navigate to Tools - Sync Content</li>
     * <li>Hover over Sync Content and click on CoPortal Site Meta</li>
     * <li>Return to the page and Verify that the calendar is still visible on the page</li>
     * </ol>
     * <br>
     * Cleanup Required:
     * Remove custom widget area created by this test
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminCustomWidgetVisualBuilderCapabilities() {
        final QualityCheck customWidgetAreaOnPage =
                qualityTestCase.addCheck("Custom widget area appears on page after adding with Visual Builder");
        final QualityCheck widgetAreaOnPageAfterCoPortalSync =
                qualityTestCase.addCheck("Custom widget area is still on the page after syncing CoPortal site meta");

        final String pageName = "Accidents at Work";

        final String widgetAreaName = "Visual Builder Widget Area";
        final String navigationMenuWidgetName = "Custom WA Title";
        final String navigationMenuWidget = "Navigation Menu";
        final String navMenuName = "Main Nav";

        wordpressTestLogin(getAdmin());

        // Add custom widget area
        app.findaLeftNavigation()
                .widgets()
                .addWidgetArea(widgetAreaName)
                .addWidgetByDragging(navigationMenuWidget, widgetAreaName)
                .selectNavMenu(navMenuName, widgetAreaName)
                .editWidgetTitle(navigationMenuWidget, widgetAreaName, navigationMenuWidgetName);

        // Add custom WA to sidebar module in Visual Builder
        QualityVerify.verifyTrue(customWidgetAreaOnPage,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageName)
                        .previewPage(pageName)
                        .enableVisualBuilder()
                        .addWidgetModule(widgetAreaName)
                        .hasNavMenuWithTitle(navigationMenuWidgetName),
                "Custom Nav Menu widget area is not on page after attempting to add");

        // Sync CoPortal meta
        app.findaTopNavigation().returnToSiteDashboardFromSiteVisit();
        app.findaLeftNavigation().tools("Sync Content");
        app.findaSyncContentPage().hitSyncCoportalSiteMetaButton();

        // Return to page
        QualityVerify.verifyTrue(widgetAreaOnPageAfterCoPortalSync,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageName)
                        .previewPage(pageName)
                        .hasNavMenuWithTitleAndBrowserNavigateBack(navigationMenuWidgetName),
                "Custom Nav Menu widget area is not on page after attempting to add");

        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-28
     * Admin Blog Post Author Functionality
     * <br>
     * Description: Validate the author displays correctly on blog posts
     * <br>
     * Test Steps:
     * <ol>i
     * <li>Login as Admin</li>
     * <li>Go to Blog Post -> Add New and verify that the firm name is not listed in the author drop down</li>
     * <li>Run tests described in blogPostAuthorFunctionality()</li>
     * <li>Logout</li>
     * </ol>
     */
    @Test(groups = {TestGroup.TestType.Regression})
    public void adminBlogPostAuthorFunctionality() {

        final QualityCheck firmNameDefaultAuthor =
                qualityTestCase.addCheck("When adding a blog post as admin, default author is the firm name");

        final String firmName = "Abraham, Watkins, Nichols, Sorrels, Agosto & Friend";
        final String blogPostTitle = "Blog Post Author Functionality Admin";
        final String firmAuthorFieldValue = "On behalf of " + firmName;
        final String customerName = "qaautomationadvancedcustomer";
        final String customerAuthorFieldValue = "by " + customerName;

        wordpressTestLogin(getAdmin());

        QualityVerify.verifyEquals(firmNameDefaultAuthor,
                app.findaLeftNavigation()
                        .blogPosts()
                        .addNewBlogPost()
                        .getCurrentlySelectedAuthor(),
                firmName,
                "Default author when adding page as admin is not the firm name");

        // Before running the rest of the verifications, set the author of the new blog post and fill in the title
        app.findaBlogPostEditPage()
                .fillInTitle(blogPostTitle);
        blogPostAuthorFunctionality(blogPostTitle, firmAuthorFieldValue);

        // Now set author to customer and make same verifications
        app.findaLeftNavigation()
                .blogPosts()
                .searchForText(blogPostTitle)
                .editBlogPost(blogPostTitle)
                .setAuthor(customerName)
                .saveChanges();
        blogPostAuthorFunctionality(blogPostTitle, customerAuthorFieldValue);

        app.findaTopNavigation().logout();
    }

    /**
     * A-TC-29
     * Admin Video Capabilities
     * <br>
     * Description: Validate that [Add Video from Brightcove Video Center] button works as expected (CMS-2940)
     * <br>
     * Test Steps: see videoCapabilities
     *
     * @author Pavel Bychkou
     */
    @Test(groups = {TestGroup.TestType.Regression, "Metadata||tester-name:PavelBychkou||Automated:Yes"})
    public void adminVideoCapabilities() {

        // login as admin
        wordpressTestLogin(getAdmin());

        videoCapabilities("Admin");

        //logout
        app.findaTopNavigation().logout();
    }


    @Test(groups = {TestGroup.TestType.Regression, "name:Nastassia Chaliapina"})
    public void adminAddLandingPage() {
        wordpressTestLogin(getAdmin());
        final QualityCheck nameQualityCheck =
                qualityTestCase.addCheck("My check");

        app.findaLeftNavigation().landingPages().addNewPage();


    }


}

