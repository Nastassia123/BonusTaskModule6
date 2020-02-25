package com.findlaw.wordpress.test;

import com.trgr.quality.qedarsenal.qualitylibrary.result.QualityCheck;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityAssert;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityVerify;
import com.wordpress.pages.abstractions.AbstractEditPage;
import com.wordpress.pages.abstractions.UserAuthorizedPage.PagesEnum;

import org.openqa.selenium.WebElement;
import org.testng.TestGroup;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Matt Goodmanson on 9/17/2018.
 */
public class WordpressAdvancedCustomerRegressionTests extends BaseTest {

    /**
     * CMS-1966: Advanced Customer Create Page Capabilities
     *
     * 1. Login as Advanced Customer
     * 2. Click on Add New menu option under pages option : Verify a new page can be added using "Add New" button from the menu
     * 3. Click on All Pages : Verify a new page can be added using the "Add New" button from pages
     * 4. Click on All Pages : Verify a new page can be added using the "Quick Add" form at the bottom of the page
     *
     * @author Matt Goodmanson
     */
    @Test(groups = {TestGroup.TestType.Regression })
    public void advancedCustomerCreatePageCapabilities() {
        final QualityCheck canAddPageFromMenu = qualityTestCase
                .addCheck("Advanced Customer can add a page from the menu");
        final QualityCheck canAddPageFromPages = qualityTestCase
                .addCheck("Advanced Customer can add a page from the pages page");
        final QualityCheck canAddPageFromQuickAdd = qualityTestCase
                .addCheck("Advanced Customer can add a page from quick add section");

        final String pageFromMenuTitle = "Adv Customer Create Page Test: New Page From Menu";
        final String pageFromPagesTitle = "Adv Customer Create Page Test: New Page From Pages";
        final String pageFromQuickAddTitle = "Adv Customer Create Page Test: New Page From Quick Add";

        // login as advanced customer
        wordpressTestLogin(getAdvancedCustomer());

        // create a new page from the menu
        app.findaLeftNavigation().pages("Add New");

        QualityVerify.verifyNotNull(canAddPageFromMenu,
                app.findanEditPagePage()
                        .editPageTitleField(pageFromMenuTitle)
                        .saveChanges()
                        .goBackToPage(AbstractEditPage.DashboardMenu.PAGES)
                        .getItemByTitle(pageFromMenuTitle),
                "Advanced Customer cannot add a page from the menu");

        // create a new page from the add new button on All Pages
        QualityVerify.verifyNotNull(canAddPageFromPages,
                app.findaPagesPage()
                        .hitAddNewOnPage()
                        .editPageTitleField(pageFromPagesTitle)
                        .saveChanges()
                        .goBackToPage(AbstractEditPage.DashboardMenu.PAGES)
                        .getItemByTitle(pageFromPagesTitle),
                "Advanced Customer cannot add a page from the pages page");

        // create a new page in the quick add section
        QualityVerify.verifyNotNull(canAddPageFromQuickAdd,
                app.findaPagesPage()
                        .setQuickAddTitleField(pageFromQuickAddTitle)
                        .hitQuickAddPublishButton()
                        .getItemByTitle(pageFromQuickAddTitle),
                "Advanced Customer cannot add a page from quick add section");

        // logout
        app.findaTopNavigation().logout();
    }

    /**
     * CMS-1969 Advanced Customer Delete Page Capabilities
     * Validate the Advanced Customer can delete a page
     *
     * 1. Login as Advanced Customer
     * 2. Click on Pages
     * 3. Hover over a page and click on the Trash link : Verify Advanced Customer can delete a page from All Pages
     * 4. Click on a page and click on the "Move to Trash" link : Verify Advanced Customer can delete a page from the edit screen
     *
     * @author Matt Goodmanson
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerDeletePageCapabilities() {
        final QualityCheck canDeletePageFromAllPages = qualityTestCase
                .addCheck("Advanced Customer can delete a page from All Pages");
        final QualityCheck canDeletePageFromEditPage = qualityTestCase
                .addCheck("Advanced Customer can delete a page from the edit page");

        final String pageToDeleteAllPages = "Page to Delete For Automation 1";
        final String pageToDeleteEditPage = "Page to Delete For Automation 4";

        // login as advanced customer
        wordpressTestLogin(getAdvancedCustomer());

        // delete a page from the all pages page
        QualityVerify.verifyNull(canDeletePageFromAllPages,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageToDeleteAllPages)
                        .hitTrashForTitle(pageToDeleteAllPages)
                        .searchForText(pageToDeleteAllPages)
                        .getItemByTitle(pageToDeleteAllPages),
                "Failed to delete page from all pages"
        );

        // delete a page from the edit page
        QualityVerify.verifyNull(canDeletePageFromEditPage,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageToDeleteEditPage)
                        .editPage(pageToDeleteEditPage)
                        .hitTrashLink()
                        .searchForText(pageToDeleteEditPage)
                        .getItemByTitle(pageToDeleteEditPage),
                "Failed to delete a page from edit page"
        );

        // logout
        app.findaTopNavigation().logout();
    }

    /**
     * Advanced Customer Bulk Actions Page Capabilities
     * Validate the Advanced Customer can perform bulk actions on pages
     *
     * 1. Login as advanced customer
     * 2. Navigate to All Pages page
     * 3. Select multiple pages checkboxes, select bulk edit, edit the status, and click apply : Verify advanced customer can edit status
     * 4. Select multiple pages checkboxes, select move to trash in bulk dropdown, and click apply : Verify advanced customer can bulk delete pages
     *
     * @author Matt Goodmanson
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerBulkActionsPageCapabilities() {
        final QualityCheck canBulkEditPages = qualityTestCase
                .addCheck("Advanced Customer can bulk edit pages");
        final QualityCheck canBulkDeletePages = qualityTestCase
                .addCheck("Advanced Customer can bulk delete pages");

        final String searchText = "Page to Delete For Automation";
        final String bulkEditPage1 = "Page to Delete For Automation 5";
        final String bulkEditPage2 = "Page to Delete For Automation 6";

        // login as advanced customer
        wordpressTestLogin(getAdvancedCustomer());

        // bulk edit pages
        QualityVerify.verifyTrue(canBulkEditPages,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(searchText)
                        .selectItem(app.findaPagesPage().getItemByTitle(bulkEditPage1))
                        .selectItem(app.findaPagesPage().getItemByTitle(bulkEditPage2))
                        .selectBulkAction("Edit")
                        .hitApplyBulkAction()
                        .selectStatusForBulkEdit("Draft")
                        .hitUpdateBulkEdit()
                        .hitDraftFilter()
                        .getItemByTitle(bulkEditPage1) != null
                        && app.findaPagesPage()
                        .getItemByTitle(bulkEditPage2) != null,
                "Failed to bulk edit page status");

        // bulk delete pages
        app.findaPagesPage()
                .searchForTitle(searchText)
                .selectItem(app.findaPagesPage().getItemByTitle(bulkEditPage1))
                .selectItem(app.findaPagesPage().getItemByTitle(bulkEditPage2))
                .selectBulkAction("Move to Trash")
                .hitApplyBulkAction();

        QualityVerify.verifyTrue(canBulkDeletePages,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(bulkEditPage1)
                        .getItemByTitle(bulkEditPage1) == null
                        && app.findaPagesPage()
                        .searchForTitle(bulkEditPage2)
                        .getItemByTitle(bulkEditPage2) == null,
                "Failed to bulk delete pages");

        // logout
        app.findaTopNavigation().logout();
    }

    /**
     * Advanced Customer Integration Capabilities
     * Validate Advanced Customer can resync Coportal and Directory
     *
     * 1. Login as Advanced Customer
     * 2. Click on My Sites > name of site being tested > Visit Site
     * 3. Click on Sync Content > Directory Content : Verify that the resync is successful
     * 4. Click on My Sites > name of site being tested > Visit Site
     * 5. Click on Sync Content > CoPortal Site Meta : Verify that the resync is successful
     *
     * @author Matt Goodmanson
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerIntegrationCapabilities() {
        final QualityCheck directorySyncSuccessfulFromPreviewHeader = qualityTestCase
                .addCheck("Directory was successfully synced from preview header");
        final QualityCheck coportalSiteMetaDataSyncSuccessfulFromPreviewHeader = qualityTestCase
                .addCheck("Coportal Site Meta was successfully synced from preview header");

        // login as advanced customer
        wordpressTestLogin(getAdvancedCustomer());

        // advanced customer can resync directory from Preview header
        app.findaTopNavigation().visitTestSite();

        QualityVerify.verifyTrue(directorySyncSuccessfulFromPreviewHeader,
                app.findaTopNavigation()
                        .clickSyncDirectoryContent()
                        .isSyncDirectorySuccessful(),
                "Directory sync failed from preview header");

        // advanced customer can resync coportal from preview header
        app.findaTopNavigation().visitTestSite();

        QualityVerify.verifyTrue(coportalSiteMetaDataSyncSuccessfulFromPreviewHeader,
                app.findaTopNavigation()
                        .clickSyncCoPortalSiteMeta()
                        .isSyncCoportalSiteMetaSuccessful(),
                "Coportal sync failed from preview header");

        // logout
        app.findaTopNavigation().logout();
    }

    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerBlogPostCapabilities() {

        final String blogPostAddNewMenuOption = "Advanced Customer Blog Post Capabilities: Post with edited publish date";
        final String blogPostAddNewButton = "Advanced Customer Blog Post Capabilities: add new button";
        final String bulkDeletePost1 = "Post to Delete for Automation 3";
        final String bulkDeletePost2 = "Post to Delete For Automation 4";

        // Login as Advanced Customer
        wordpressTestLogin(getAdvancedCustomer());

       blogPostCapabilities("Advanced Customer", blogPostAddNewMenuOption, blogPostAddNewButton, bulkDeletePost1, bulkDeletePost2);

        // Logout
        app.findaTopNavigation().logout();
    }

    /**
     * CMS-1967: Advanced Customer Edit Published Page Capabilities
     * Validate the advanced customer can edit a published page
     *
     * 1. Login as advanced customer
     * 2. Click on pages
     * 3. Click on an existing published page : Verify you can edit the page and save
     * 4. Hover over an existing published page and click on Edit, make edits, and click on Preview : Verify user can edit
     *      and preview an existing published page
     * 5. Click on publish : Verify that changes are published
     * 6. Hover over an existing published page and click on Quick Edit, add a note to the page
     *      : Verify user can quick edit an existing published page
     * 7. View edit page : Verify that quick add note is present and not duplicated on the page
     *
     * @author Matt Goodmanson
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerEditPublishedPageCapabilities() {
        final QualityCheck canEditPublishedPage = qualityTestCase
                .addCheck("Advanced Customer can edit a published page");
        final QualityCheck canPreviewChanges = qualityTestCase
                .addCheck("Advanced Customer can preview changes to a published page");
        final QualityCheck canPublishChanges = qualityTestCase
                .addCheck("Advanced Customer can publish previewed changes");
        final QualityCheck canQuickEditPageAddNote = qualityTestCase
                .addCheck("Advanced Customer can quick edit a published page (add a note)");
        final QualityCheck isQuickEditNoteDuplicated = qualityTestCase
                .addCheck("Discovery note added through quick edit is not duplicated on edit page");

        final String publishedPageTitle = "Thank You";
        final String editedBody1 = "Thank you very much";
        final String editedBody2 = "Thank you very very much";
        final String quickEditNotes = "Quick note";

        // login as advanced customer
        wordpressTestLogin(getAdvancedCustomer());

        // edit existing published page by clicking on page
        QualityVerify.verifyEquals(canEditPublishedPage,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(publishedPageTitle)
                        .openPage(publishedPageTitle)
                        .editTextContentInDiviBuilder(editedBody1)
                        .saveChanges()
                        .hitPreviewButton()
                        .getTextAndClose(),
                editedBody1,
                "page was not updated with edits");

        // edit existing published page by clicking on edit and preview changes
        QualityVerify.verifyEquals(canPreviewChanges,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(publishedPageTitle)
                        .editPage(publishedPageTitle)
                        .editTextContentInDiviBuilder(editedBody2)
                        .hitPreviewButton()
                        .getTextAndClose(),
                editedBody2,
                "page was not updated with edits on preview");

        // publish previewed changes
        QualityVerify.verifyEquals(canPublishChanges,
                app.findanEditPagePage()
                        .saveChanges()
                        .hitPreviewButton()
                        .getTextAndClose(),
                editedBody2,
                "page was not updated with edits after being published");

        // advanced customer can quick edit page (add a note)
        QualityVerify.verifyTrue(canQuickEditPageAddNote,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(publishedPageTitle)
                        .quickEditNotesForTitle(publishedPageTitle, quickEditNotes)
                        .openEntryForEditByPageTitle(publishedPageTitle)
                        .isNotePresent(quickEditNotes),
                "Quick edit page did not update notes");

        // check for duplicate discovery notes (needed because of bug CMS-2195)
        QualityVerify.verifyFalse(isQuickEditNoteDuplicated,
                app.findanEditPagePage().isNoteDuplicated(quickEditNotes),
                "Duplicate notes were created on edit page");

        // logout
        app.findaTopNavigation().logout();
    }

    /**
     * AC-TC-15
     * Advanced Customer Widget Capabilities
     * <br>
     * Description: Validate Advanced Customer can edit, add and remove widgets
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as advanced customer</li>
     * <li>Go to Appearance -> Widgets page</li>
     * <li>Add a Calendar widget to the sidebar and verify it is there (on Widgets page and preview page)</li>
     * <li>Edit the title of the Calendar widget and verify the change is (on Widgets page and preview page)</li>
     * <li>Remove the widet from the sidebar and verify it is no longer there (on Widgets page and preview page)</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerWidgetCapabilities() {
        final QualityCheck canAddWidgetToSidebar =
                qualityTestCase.addCheck("Advanced customer can add a Calendar widget to Sidebar");
        final QualityCheck widgetVisibleOnView = qualityTestCase
                .addCheck("Widget appears on sidebar on Accidents at Work page");
        final QualityCheck canEditWidgetTitle =
                qualityTestCase.addCheck("Advanced Customer can edit the title of a widget");
        final QualityCheck widgetRenamedWhenViewingPage =
                qualityTestCase.addCheck("Widget title has been changed on preview/view page");
        final QualityCheck canRemoveWidgetFromSidebar =
                qualityTestCase.addCheck("Advanced customer can remove the Calendar widget from Sidebar");
        final QualityCheck widgetNotVisibleOnView = qualityTestCase
                .addCheck("Widget does not appear on sidebar on Accidents at Work page");

        final String widgetUsed = "Calendar";
        final String targetSidebar = "Sidebar";
        final String newTitle = "Advanced Customer Calendar";
        final String testPage = "Accidents at Work";

        wordpressTestLogin(getAdvancedCustomer());

        QualityVerify.verifyTrue(canAddWidgetToSidebar,
                app.findaLeftNavigation()
                        .widgets()
                        .addWidget(widgetUsed, targetSidebar)
                        .isWidgetInSidebar(widgetUsed, targetSidebar),
                "Advanced Customer could not add Calendar widget to Sidebar");
        QualityVerify.verifyTrue(widgetVisibleOnView,
                app.findaLeftNavigation()
                        .pages()
						.searchForTitle(testPage)
                        .previewPage(testPage)
                        .hasCalendarAndBrowserNavigateBack(),
                "Widget is not visible on page \"" + testPage + "\" after it should have been added");

        QualityVerify.verifyTrue(canEditWidgetTitle,
                app.findaLeftNavigation()
                        .widgets()
                        .editWidgetTitle(widgetUsed, targetSidebar, newTitle)
                        .isWidgetInSidebarWithName(widgetUsed, targetSidebar, newTitle),
                "Advanced Customer could not edit the title of the widget");

        QualityVerify.verifyTrue(widgetRenamedWhenViewingPage,
                app.findaLeftNavigation()
                        .pages()
						.searchForTitle(testPage)
                        .previewPage(testPage)
                        .hasCalendarWithTitleAndBrowserNavigateBack(newTitle),
                "Widget is not visible on page with expected title");

        QualityVerify.verifyFalse(canRemoveWidgetFromSidebar,
                app.findaLeftNavigation()
                        .widgets()
                        .removeWidget(widgetUsed, targetSidebar)
                        .isWidgetInSidebar(widgetUsed, targetSidebar),
                "Advanced Customer can not remove Calendar from Sidebar");

        QualityVerify.verifyFalse(widgetNotVisibleOnView,
                app.findaLeftNavigation()
                        .pages()
						.searchForTitle(testPage)
                        .previewPage(testPage)
                        .hasCalendarAndBrowserNavigateBack(),
                "Widget is visible on page \"" + testPage + "\" after it should have been removed");

        // logout
        app.findaTopNavigation().logout();
    }

	/**
	 * Advanced Customer Denied Menu Options <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as Advanced Customer</li>
	 * <li>Verify the absence of "people"menu option in left menu</li>
	 * <li>Verify the absence of comments in left menu</li>
	 * <li>Verify there is no "stream" menu option in left menu</li>
	 * <li>Verify there is no "users" menu option in left menu</li>
	 * <li>Verify there is no "plugin" menu option in left menu</li>
	 * <li>Verify there is no "tools" menu option in left menu</li>
     * // 07/23/19 UPD by Pavel Bychkou, CMS-3017
	 * <li>Verify there is no "settings" menu option in left menu</li>
	 * <li>Verify there is no "role editor" submenu option under Divi menu</li>
	 * <li>Verify there is no "themes" submenu under appearance</li>
	 * <li>Verify there is no "Your Profile" submenu option under users</li>
	 * <li>Verify there is no "network admin" submenu option under my sites</li>
	 * <li>Verify there is no "Edit your profile" option when hovering over user
	 * email</li>
	 * <li>Verify there is no username redirection</li>
	 * </ol>
	 */
    @Test(groups = { TestGroup.TestType.Regression })
	public void advancedCustomerDeniedMenuOptions() {
		final QualityCheck noSettingsMenu = qualityTestCase.addCheck("Settings Menu not present in left menu");
		final QualityCheck noPeopleMenu = qualityTestCase.addCheck("People Menu not present in left menu");
		final QualityCheck noCommentsMenu = qualityTestCase.addCheck("Comments Menu not present in left menu");
		final QualityCheck noStreamMenu = qualityTestCase.addCheck("Stream Menu not present in left menu");
		final QualityCheck noUsersMenu = qualityTestCase.addCheck("Users Menu not present in left menu");
		final QualityCheck noPluginsMenu = qualityTestCase.addCheck("Plugins Menu not present in left menu");
		final QualityCheck noToolsMenu = qualityTestCase.addCheck("Tools Menu not present in left menu");
		final QualityCheck noThemesMenu = qualityTestCase.addCheck("Themes Menu not present in Appearance menu");
		final QualityCheck noNetworkAdminMenu = qualityTestCase.addCheck("Network Admin menu not present when hovering over My Sites");
		final QualityCheck noEditProfile = qualityTestCase.addCheck("No 'Edit My Profile' link under username");
		final QualityCheck noUsernameRedirection = qualityTestCase.addCheck("No redirection upon clicking on username");
		final QualityCheck noRoleEditor = qualityTestCase.addCheck("Role Editor not present in Divi menu");

		wordpressTestLogin(getAdvancedCustomer());

		//menu options do NOT exist: Settings, People, Comments, Stream, Users, Plugins, Tools
		QualityVerify.verifyFalse(noSettingsMenu,app.findaLeftNavigation().isTabPresent("Settings"),"Settings Menu is present in left menu");
		QualityVerify.verifyFalse(noPeopleMenu,app.findaLeftNavigation().isTabPresent("People"),"People Menu is present in left menu");
		QualityVerify.verifyFalse(noCommentsMenu,app.findaLeftNavigation().isTabPresent("Comments"),"Comments Menu is present in left menu");
		QualityVerify.verifyFalse(noStreamMenu,app.findaLeftNavigation().isTabPresent("Stream"),"Stream Menu is present in left menu");
		QualityVerify.verifyFalse(noUsersMenu,app.findaLeftNavigation().isTabPresent("Users"),"Users Menu is present in left menu");
		QualityVerify.verifyFalse(noPluginsMenu,app.findaLeftNavigation().isTabPresent("Plugins"),"Plugins Menu is present in left menu");
		QualityVerify.verifyFalse(noToolsMenu,app.findaLeftNavigation().isTabPresent("Tools"),"Tools Menu is present in left menu");

		//Verify there is no "Role Editor" sub menu option
		QualityVerify.verifyFalse(noRoleEditor,app.findaLeftNavigation().isSubTabPresent("Divi","Role Editor"),"Role Editor is present in left menu under Divi");

		//Verify there is no "Themes" sub menu option
		QualityVerify.verifyFalse(noThemesMenu,app.findaLeftNavigation().isSubTabPresent("Appearance","Themes"),"Themes is present in left menu under Appearance");

		//Verify there is not "Network Admin" option
		QualityVerify.verifyFalse(noNetworkAdminMenu,app.findaTopNavigation().isNetworkAdminPresent(),"Network Admin menu present when hovering over My Sites");

		//Verify there is no "Edit your profile" option and that the username is not clickable
		QualityVerify.verifyFalse(noEditProfile,app.findaTopNavigation().isEditMyProfilePresent(),"Edit Profile link is present when hovering over username");

		// Clicking on account email should not navigate away from current page
		app.findaLeftNavigation().dashboard();
		app.findaTopNavigation().hitUsername();
		QualityAssert.assertEquals(noUsernameRedirection, app.findaHomePage().getPageTitle(),app.findaHomePage().getExpectedPageTitle(PagesEnum.HOME_PAGE),"Hitting account email navigated away from Home Page");

		   // logout
        app.findaTopNavigation().logout();
	}

    /**
   	 * Advanced Customer  Default Tagging Settings
   	 * <br>
   	 * <ol>
   	 *     <li>Click blog posts and verify
   	 *     <li>page goal set to "Legal Information"
   	 *     <li>Language set to English
   	 *     <li>page type set to "Blog post"</li>
   	 *
   	 *     <li>Click pages and verify
   	 *     <li>page goal set to "Legal Information"
   	 *     <li>Language set to "English"
   	 *     <li>page type set to "Marketing Page"</li>

   	 * </ol>
   	 */
   	@Test(groups = { TestGroup.TestType.Regression })
   	public void advancedCustomerDefaultTaggingSettings() {


   		final QualityCheck defaultSettingsOnBlogPostPage = qualityTestCase.addCheck("Default settings are selected on Blog Post");
   		final QualityCheck defaultSettingsOnPage = qualityTestCase.addCheck("Default settings are selected on Page");

   		wordpressTestLogin(getAdvancedCustomer());

   		app.findaLeftNavigation().blogPosts("Add New");
   		QualityVerify.verifyTrue(defaultSettingsOnBlogPostPage,app.findaBlogPostEditPage().isPageGoalOptionSelected("Legal Information")
   				&& app.findaBlogPostEditPage().isLanguageOptionSelected("English")
   				&& app.findaBlogPostEditPage().isPageTypeOptionSelected("Blog Post"),"Default values for drop downs are not selected in blog post");

   		app.findaLeftNavigation().pages("Add New");
   		QualityVerify.verifyTrue(defaultSettingsOnPage, app.findaBlogPostEditPage().isPageGoalOptionSelected("Legal Information")
   				&& app.findaBlogPostEditPage().isLanguageOptionSelected("English")
   				&& app.findaBlogPostEditPage().isPageTypeOptionSelected("Marketing Page"),"Default values for drop downs are not selected in page");


           // logout
           app.findaTopNavigation().logout();

   	}

    /**
     * Advanced Customer Menu Capabilities (AC-TC-12)
     * <br>
     * Description:
     * Validate Advanced Customer can create, edit, and delete a menu
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as advanced customer</li>
     * <li>Click on Appearance -> Menus, then click 'create a new menu' link</li>
     * <li>Enter name for menu and click 'Create Menu' and verify menu exists</li>
     * <li>Edit an existing menu item by adding a menu item and clicking Save Menu</li>
     * <li>Click on 'Delete Menu' link under existing menu and verify that menu is gone</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerMenuCapabilities() {
        final QualityCheck canCreateMenu = qualityTestCase.addCheck("Advanced customer can create a new menu");
        final QualityCheck canEditMenu = qualityTestCase.addCheck("Advanced customer can edit a menu");
        final QualityCheck canDeleteMenu = qualityTestCase.addCheck("Advanced customer can delete a menu");

        final String menuName = "Advanced Customer Add Menu (AC-TC-12)";
        final String pageToAdd = "Thank You";

        wordpressTestLogin(getAdvancedCustomer());

        QualityVerify.verifyTrue(canCreateMenu,
                app.findaLeftNavigation()
                        .menu()
                        .hitCreateNewMenuLink()
                        .enterMenuName(menuName)
                        .hitCreateNewMenu()
                        .navigateToMenu(menuName)
                        .checkIfTheMenuExists(menuName),
                "Menu was not created by Advanced Customer");

        QualityVerify.verifyTrue(canEditMenu,
                app.findaMenuPage()
                        .navigateToMenu(menuName)
                        .addPageToMenu(pageToAdd)
                        .hitSaveMenu()
                        .menuContains(pageToAdd),
                "Menu does not contain \"" + pageToAdd + "\" after attempting to add as Advanced Customer");

        QualityVerify.verifyFalse(canDeleteMenu,
                app.findaLeftNavigation()
                        .menu()
                        .deleteMenu(menuName)
                        .checkIfTheMenuExists(menuName),
                "Menu was not deleted");

        app.findaTopNavigation().logout();
    }

    /**
     * AC-TC-4
     * Advanced Customer Edit Draft Page Capabilities
     * <br>
     * Description: Validate the advanced customer can edit a draft page
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as Advanced Customer</li>
     * <li>Go to pages and click on the title of an existing draft page</li>
     * <li>Edit the body of the text and save</li>
     * <li>Return to Pages page and hover over another draft page and click the Edit option</li>
     * <li>Edit the body and verify the changes are in the Preview after clicking Preview Changes</li>
     * <li>Click on Page and verify the page is published</li>
     * <li>Use Quick Edit option for a draft page to change the (STATUS OR AUTHOR)</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerEditDraftPageCapabilities() {
        final QualityCheck canEditDraftPageAndSave =
                qualityTestCase.addCheck("Advanced Customer can edit an existing draft page and save");
        final QualityCheck canEditDraftPageAndPreview =
                qualityTestCase.addCheck("Changes made to an existing draft page by an Advanced Customer display in preview");
        final QualityCheck canPublishDraftPage = qualityTestCase.addCheck("Advanced Customer can publish a draft page");
        final QualityCheck canQuickEditExistingDraftPage = qualityTestCase.addCheck("Advanced Customer can quick edit a draft page");

        final String draftPageToEdit = "Experience";
        final String draftPageToQuickEdit = "Lost Wages";
        final String newStatusForQuickEdit = "Pending Review";

        final String pageTextEditAndSave = "Edit and Save text";
        final String pageTextEditAndPreview = "Edit and Preview text";

        wordpressTestLogin(getAdvancedCustomer());

        QualityVerify.verifyEquals(canEditDraftPageAndSave,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(draftPageToEdit)
                        .openPage(draftPageToEdit)
                        .editTextContentInDiviBuilder(pageTextEditAndSave)
                        .hitSaveDraftButton()
                        .getContentFromDiviBuilder(),
                pageTextEditAndSave,
                "Changes made by Advanced Customer were not saved");

        // Opening Divi just to get content (no edit) requires a save as WP thinks there are changes
        app.findanEditPagePage().saveDraftPage().returnToAllPages();

        QualityVerify.verifyEquals(canEditDraftPageAndPreview,
                app.findaPagesPage()
                        .searchForTitle(draftPageToEdit)
                        .editPage(draftPageToEdit)
                        .editTextContentInDiviBuilder(pageTextEditAndPreview)
                        .hitPreviewButton()
                        .getTextAndClose(),
                pageTextEditAndPreview,
                "Edited text not displaying on preview page");

        QualityVerify.verifyEquals(canPublishDraftPage,
                app.findanEditPagePage()
                        .saveChanges()
                        .getStatus(),
                "Published",
                "Advanced Customer could not publish the page by clicking the Publish button while editing");

        QualityVerify.verifyTrue(canQuickEditExistingDraftPage,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(draftPageToQuickEdit)
                        .clickQuickEditLinkForPage(draftPageToQuickEdit)
                        .selectQuickEditStatus(newStatusForQuickEdit)
                        .pageHasStatus(draftPageToQuickEdit, "Pending"));

        app.findaTopNavigation().logout();
    }

	/**
	 * CMS-1965: Advanced Customer Denied Capabilities
	 *
	 * 1. Login as Advanced Customer
	 * <ol>
	 * <li>Verify the absence of "Fork for Copy Edit" & "Fork for Layout" on edit
	 * page</li>
	 * <li>Verify the absence of "Copy Information" section on edit page or landing
	 * page</li>
	 * <li>Verify there is NO "Comments" checkbox under "Columns" and NO comments
	 * section on edit page, landing page, or blog post</li>
	 * <li>Verify the "Add New" button is NOT present on All Landing Pages</li>
	 * <li>Verify the following links are NOT present when hovering over landing
	 * page: "Trash", "Fork for Copy Edit" & "Fork for Layout"</li>
	 * <li>Verify there is NO "Move to Trash" option in Landing Pages bulk edit
	 * options</<li>Verify the following links are NOT present in the Publish
	 * section on the right of a Landing Page: "Move to Trash", "Fork for Copy Edit"
	 * & "Fork for Layout"</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void advancedCustomerDeniedCapabilities() {

		final QualityCheck editPageLinksNotPresent = qualityTestCase
				.addCheck("Restricted links not present on edit page");
		final QualityCheck noCopyInformationSection = qualityTestCase
				.addCheck("Copy Information section is not present on edit page");
		final QualityCheck noCommentsInScreenOptions = qualityTestCase
				.addCheck("There is no Comments checkbox in screen options");
		final QualityCheck noAddNewButton = qualityTestCase.addCheck("NO Add New button on All Landing Pages");
		final QualityCheck landingPageLinksNotPresent = qualityTestCase
				.addCheck("Restricted links not present when hovering over landing page");
		final QualityCheck editLandingPageLinksNotPresent = qualityTestCase
				.addCheck("Restricted links not present in the Publish section on the right of a Landing Page");
		final QualityCheck noMoveToTrashInBulkOptions = qualityTestCase
				.addCheck("NO Move to Trash option in Landing Pages bulk edit options");

		// login as advanced customer
		wordpressTestLogin(getAdvancedCustomer());

		// check for links on edit Page
		app.findaLeftNavigation().pages();
		WebElement page = app.findaPagesPage().searchForTitle("Home"). getItemByTitle("Home");
		QualityVerify.verifyFalse(editPageLinksNotPresent,
				app.findaPagesPage().isItemOptionPresent(page, "Fork for Copy Edit")
						|| app.findaPagesPage().isItemOptionPresent(page, "Fork for Layout"),
				"Customer has access to page option that should be restricted");

		// check for copy information section on edit page
		QualityVerify.verifyFalse(noCopyInformationSection, app.findanEditPagePage().isCopyInformationSectionPresent(),
				"Copy Information section present on edit page");

		// check for comments column in screen options
		QualityVerify.verifyFalse(noCommentsInScreenOptions,
				app.findaDashboardPage().expandScreenOptions().isScreenOptionPresent("Comments"),
				"There should be no Comments option under screen options column for edit page");

		// check for Add New button on All landing page
		QualityVerify.verifyFalse(noAddNewButton, app.findaLeftNavigation().landingPages().isAddNewButtonPresent(),
				"Add New button is present for All Landing Pages");

		// check for links on hovering over landing page
		WebElement landingPage = app.findaPagesPage().searchForTitle("Edit preview Landing Page Test")
				.getItemByTitle("Edit preview Landing Page Test");

		QualityVerify.verifyFalse(landingPageLinksNotPresent,
				app.findaPagesPage().isItemOptionPresent(landingPage, "Trash")
						|| app.findaPagesPage().isItemOptionPresent(landingPage, "Fork for Copy Edit")
						|| app.findaPagesPage().isItemOptionPresent(landingPage, "Fork for Layout"),
				"Customer has access to page option that should be restricted");

		// check for links on edit Landing Page
		app.findaPagesPage().hitItem(landingPage);

		QualityVerify.verifyFalse(editLandingPageLinksNotPresent,
				app.findanEditPagePage().isForkForCopyEditButtonPresent()
						|| app.findanEditPagePage().isForkForLayoutLinkPresent()
						|| app.findanEditPagePage().isTrashLinkPresent(),
				"Customer has access to page option from edit page that should be restricted");

		// check for move to trash option in bulk actions
		app.findaLeftNavigation().landingPages();
		QualityVerify.verifyFalse(noMoveToTrashInBulkOptions, app.findaPagesPage().isbulkActionPresent("Move to Trash"),
				"Move to Trash option is present under bulk actions");

		app.findaTopNavigation().logout();
	}

	/**
	 * Advanced Customer Blog Post Tag Capabilities
	 * <br>
	 * Objective: Validate Advanced Customer can create, edit, and delete tags
	 * <br>
	 * Test steps: See blogPostTagCapabilities.
	 */
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerBlogPostTagCapabilities() {

        final String newTagOnTagsScreen = "AdvCustomerTagTest_fromTagsScreen";
        final String newTagOnPostEditScreen = "AdvCustomerTagTest_fromEditScreen";
        final String blogPostTitle="Schools are frequent sites of bad driving habits";

        wordpressTestLogin(getAdvancedCustomer());

        blogPostTagCapabilities(newTagOnTagsScreen, newTagOnPostEditScreen, blogPostTitle);

         // logout
        app.findaTopNavigation().logout();
    }

    /**
     * AC-TC-11
     * Advanced Customer Media Capabilities
     * <br>
     * Test Description: Validate Advanced Customer can view and upload file in Media library
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as Advanced Customer</li>
     * <li>Navigate to Media -> Library and verify that Advanced Customer can view all items</li>
     * <li>Verify that Advanced Customer can add a file with Media -> Add New</li>
     * <li>Add a file with the Add New button on the Media -> Library page</li>
     * <li>Verify that the file is in the Library page</li>
     * <li>Search for the file and verify that searching for the added file places the first uploaded photo in the first item location</li>
     * <li>Logout</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerMediaCapabilities() {
        wordpressTestLogin(getAdvancedCustomer());
        mediaCapabilities("AdvancedCustomer");
        app.findaTopNavigation().logout();
    }

    /**
     * AC-TC-14
     * Advanced Customer Blog Post Categories Capabilities
     * <br>
     * Description: Validate Advanced Customer can create, edit, and delete categories
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as Advanced Customer</li>
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
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerBlogPostCategoriesCapabilities() {
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

        final String newCategoryFromCategoryPage = "AdvancedCustomerCategoryTest_fromCategoryPage";
        final String newCategoryFromEditPost = "AdvancedCustomerCategoryTest_fromEditPost";
        final String editedCategory = "AdvancedCustomerCategoryTest_fromCategoryPageEdited";
        final String postName = "Motorcycles may soon get their own AI safety technology";

        wordpressTestLogin(getAdvancedCustomer());

        QualityVerify.verifyTrue(newCategoryAddedOnCategoryPage,
                app.findaLeftNavigation()
                        .blogCategories()
                        .enterNewCategoryName(newCategoryFromCategoryPage)
                        .hitAddNewCategory()
                        .titleExists(newCategoryFromCategoryPage),
                "Advanced Customer could not add category to Categories from Categories page");

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
                "Advanced Customer could not delete all categories from Categories page");

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
     * AC-TC-17
     * Advanced Customer Forms Capabilities
     * <br>
     * Description: Validate Advanced Customer can add forms to pages
     * <br>
     * Test Steps: See formsCapabilities
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void advancedCustomerFormsCapabilities() {
        final String shortFormPageName = "Adv Customer Contact";
        final String longFormPageName = "Adv Customer Complaints Contact Form";

        wordpressTestLogin(getAdvancedCustomer());
        formsCapabilities(shortFormPageName, longFormPageName);
        app.findaTopNavigation().logout();
    }

	/**
	 * AC-TC-5
	 * Advanced Customer Fork Page Capabilities
	 * <br>
	 * Description: Validate the Advanced Customer can perform fork actions on pages
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as Advanced Customer</li>
	 * <li>Search for a forked (for layout) page, edit the fork to click merge edits and publish the edits</li>
	 * <li>Verify in the preview that the edits have been merged into the original page</li>
	 * <li>Search for a forked (for copy edit) page, edit the fork to click merge edits and publish the edits</li>
	 * <li>Verify in the preview that the edits have been merged into the original page</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void advancedCustomerForkPageCapabilities() {
		final QualityCheck layoutForkMerged =
				qualityTestCase.addCheck("Advanced Customer can merge a Layout Fork into master");
		final QualityCheck copyEditForkMerged =
				qualityTestCase.addCheck("Advanced Customer can merge a Copy Edit Fork into master");

		final String layoutForkedTitle = "Adv Customer Layout Fork Page";
		final String layoutForkTitle = String.format("--(Layout Fork) %s", layoutForkedTitle);
		final String layoutForkText = "Layout Fork Edited Text";
		final String copyEditForkedTitle = "Adv Customer Copy Fork Page";
		final String copyEditForkTitle = String.format("--(Copy Fork) %s", copyEditForkedTitle);
		final String copyEditForkText = "Copy Edit Fork Edited Text";

		wordpressTestLogin(getAdvancedCustomer());

		QualityVerify.verifyEquals(layoutForkMerged,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(layoutForkedTitle)
						.editPage(layoutForkTitle)
						.editPageText(layoutForkText)
						.hitUpdateOrPublishEditPage()
						.mergeEditsIntoParentPage()
						.returnToAllPages()
						.searchForTitle(layoutForkedTitle)
						.previewPage(layoutForkedTitle)
						.getTextAndBack(),
				layoutForkText,
				"Advanced Customer could not merge edits from Layout Fork into parent");

		QualityVerify.verifyEquals(copyEditForkMerged,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(copyEditForkedTitle)
						.editPage(copyEditForkTitle)
						.enterRedlineModePage()
						.editPageTextRedlineMode(copyEditForkText)
						.mergeEditsIntoLivePage()
						.hitPreviewButton()
						.getTextAndBack(),
				copyEditForkText,
				"Advanced Customer could not merge edits from Copy Edit Fork into parent");

		app.findaTopNavigation().logout();
	}

	/**
	 * AC-TC-19
	 * Advanced Customer Blog Post Author Functionality
	 * <br>
	 * Description: Validate the author displays correctly on blog posts
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as Advanced Customer</li>
	 * <li>Go to Blog Post -> Add New and verify that the firm name is not listed in the author drop down</li>
	 * <li>Run tests described in blogPostAuthorFunctionality()</li>
	 * <li>Logout</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void advancedCustomerBlogPostAuthorFunctionality() {

		final QualityCheck noFirmNameInAuthorDropdown =
				qualityTestCase.addCheck("Firm name does not appear in Author dropdown as advanced customer");

		final String firmName = "Abraham, Watkins, Nichols, Sorrels, Agosto & Friend";
		final String blogPostTitle = "Blog Post Author Functionality Advanced Customer";
		final String authorName = "qaautomationadvancedcustomer";
		final String authorFieldValue = "by " + authorName;

		wordpressTestLogin(getAdvancedCustomer());

		QualityVerify.verifyFalse(noFirmNameInAuthorDropdown,
				app.findaLeftNavigation()
						.blogPosts()
						.addNewBlogPost()
						.canSelectAuthor(firmName),
				"Customer can select the firm as the author of a post when they shouldn't be able to");

		// Before running the rest of the verifications, set the author of the new blog post and fill in the title
		app.findaBlogPostEditPage()
				.setAuthor(authorName)
				.fillInTitle(blogPostTitle);
		blogPostAuthorFunctionality(blogPostTitle, authorFieldValue);

		app.findaTopNavigation().logout();
	}
	
	/**
	 * CMS-1973 Advanced Customer Bulk Action Landing Page Capabilities
	 * Login as Advanced Customer and 
	 * Verify Advanced Customer can bulk edit Landing pages
	 * @author Nikita Sharma
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void advancedCustomerBulkActionsLandingPageCapabilities() {

		final QualityCheck canBulkEditLandingPages = qualityTestCase.addCheck("Verify advanced customer can bulk edit Landing pages");

		// login as Advanced Customer
		wordpressTestLogin(getAdvancedCustomer());

		// bulk edit pages
		app.findaLeftNavigation().landingPages()
				.searchForText("Landing Page For Bulk Edit")
				.selectItem(app.findaPagesPage().getItemByTitle("Landing Page For Bulk Edit 1"))
				.selectItem(app.findaPagesPage().getItemByTitle("Landing Page For Bulk Edit 2"))
				.selectBulkAction("Edit").hitApplyBulkAction().selectStatusForBulkEdit("Draft").hitUpdateBulkEdit()
				.hitDraftFilter();

		QualityVerify.verifyTrue(canBulkEditLandingPages,
				app.findaPagesPage().getItemByTitle("Landing Page For Bulk Edit 1") != null
						&& app.findaPagesPage().getItemByTitle("Landing Page For Bulk Edit 2") != null,
				"Failed to bulk edit status");
	
		// logout
		app.findaTopNavigation().logout();
	}
	
	/**
     * Advanced Customer Landing Page Restrictions CMS-1971
     *Test steps
     *<ol>
     * <li>Login as Advanced customer <li>
     * <li>Verify the absence of "Add New" menu and at the top of all landing page<li>
     * <li>Verify the presence of  "Edit", "Quick Edit", "View" on hovering over a landing page<li>
     * <li>Verify there is no  "Move to Trash" option in Landing Page bulk actions options<li>
     * <li>Verify there is no  "Move to Trash" ,"Fork for Copy Edit", "Fork for Layout" in the Publish section of a Landing Page edit page<li>
     * </ol>
     */
	@Test(groups = { TestGroup.TestType.Regression })
	public void advancedCustomerLandingPageRestrictions() {

		final QualityCheck noAddNewButton = qualityTestCase.addCheck("No Add New button on All Landing pages");
		final QualityCheck noAddNewSubMenu = qualityTestCase.addCheck("No Add New sub menu on Landing page menu");
		final QualityCheck landingPageLinksPresent = qualityTestCase
				.addCheck("Edit, Quick Edit, View present when hovering over page");
		final QualityCheck editLandingPageLinksNotPresent = qualityTestCase
				.addCheck("Restricted links not present in the publish section on the right of a Landing Page");
		final QualityCheck noMoveToTrashInBulkOptions = qualityTestCase
				.addCheck("No Move to Trash option in Landing Page bulk actions options");

		// login as customer
		wordpressTestLogin(getAdvancedCustomer());

		// check for add new button on All landing page
		QualityVerify.verifyFalse(noAddNewButton, app.findaLeftNavigation().landingPages().isAddNewButtonPresent(),
				"Add New button is present for All Landing pages");

		// check for no Add new link on hovering on Landing page

		WebElement landingPage = app.findaLandingPage().searchForText("Edit preview Landing Page Test")
				.getItemByTitle("Edit preview Landing Page Test");

		QualityVerify.verifyFalse(noAddNewSubMenu,
				app.findaLeftNavigation().isSubTabPresent("Landing Pages", "Add New"),
				"Add new sub menu is present in left menu under Landing pages");

		// check for the presence of "Edit", "Quick Edit", "View" on hovering over a
		// landing page

		QualityVerify.verifyTrue(landingPageLinksPresent,
				app.findaLandingPage().isItemOptionPresent(landingPage, "Edit")
						|| app.findaPagesPage().isItemOptionPresent(landingPage, "View")
						|| app.findaPagesPage().isItemOptionPresent(landingPage, "Quick Edit"),
				"Customer does not has  access to page options");

		// check for links on edit Landing page
		app.findaLandingPage().hitItem(landingPage);
		QualityVerify.verifyFalse(editLandingPageLinksNotPresent,
				app.findaEditLandingPage().isForkForCopyEditButtonPresent()
						|| app.findaEditLandingPage().isForkForLayoutLinkPresent()
						|| app.findaEditLandingPage().isTrashLinkPresent(),
				"Customer has access to landing page option from edit page that should be restricted");

		// check for move to trash option in bulk actions
		app.findaLeftNavigation().landingPages();
		QualityVerify.verifyFalse(noMoveToTrashInBulkOptions,
				app.findaLandingPage().isbulkActionPresent("Move to Trash"),
				"Move to Trash option is present under bulk actions");

		// logout
		app.findaTopNavigation().logout();
	}

    /**
     * AC-TC-22
     * Advanced Customer Video Capabilities
     * <br>
     * Description: Validate that [Add Video from Brightcove Video Center] button works as expected (CMS-2940)
     * <br>
     * Test Steps: see videoCapabilities
     *
     * @author Pavel Bychkou
     */
    @Test(groups = { TestGroup.TestType.Regression, "Metadata||tester-name:PavelBychkou||Automated:Yes"  })
    public void advancedCustomerVideoCapabilities() {

        // login as Advanced Customer
        wordpressTestLogin(getAdvancedCustomer());

        videoCapabilities("Advanced Customer");

        //logout
        app.findaTopNavigation().logout();
    }

}
