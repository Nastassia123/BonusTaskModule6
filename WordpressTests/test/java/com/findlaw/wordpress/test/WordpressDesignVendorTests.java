package com.findlaw.wordpress.test;

import com.trgr.quality.qedarsenal.qualitylibrary.result.QualityCheck;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityAssert;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityVerify;
import com.wordpress.pages.abstractions.UserAuthorizedPage;
import com.wordpress.pages.abstractions.UserAuthorizedPage.PagesEnum;

import java.util.Arrays;
import java.util.List;

import org.testng.TestGroup;
import org.testng.annotations.Test;

/**
 * Created by Matt Goodmanson on 10/19/2018.
 */
public class WordpressDesignVendorTests extends BaseTest {

    /**
     * DV-TC-7
     * Design Vendor Network Site Capabilities
     * <br>
     * Description: Validate DV can view all sites
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as DV</li>
     * <li>Go to My Sites -> Network Admin -> Sites</li>
     * <li>Hover over the site and click Dashboard, then verify navigation to dashboard</li>
     * <li>Return to Sites and verify navigation to dashboard again by clicking the site title</li>
     * <li>Return to Sites again and verify that clicking Visit option under site navigates to the site</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void designVendorNetworkSiteCapabilities() {
        final QualityCheck canViewDashboardOnHover = qualityTestCase.addCheck("Can view a site's dashboard by clicking Dashboard under site");
        final QualityCheck canViewDashboardOnTitleClick = qualityTestCase.addCheck("Can view sites dashboard by clicking site title");
        final QualityCheck canVisitSite = qualityTestCase.addCheck("Design vendor can visit a site");

        final String siteTitle = getSiteUrlNoHttp();
        final String visitPageTitle = "QA Test Automation | IM-Template";

        wordpressTestLogin(getDesignVendor());

        QualityVerify.verifyEquals(canViewDashboardOnHover,
                app.findaTopNavigation()
                        .goToNetworkSitesPage()
                        .goToDashboard(siteTitle)
                        .getPageTitle(),
                app.findaHomePage()
                        .getExpectedPageTitle(UserAuthorizedPage.PagesEnum.DASHBOARD_PAGE),
                "Could not view dashboard for site by clicking on Dashboard option when hovering over site");

        QualityVerify.verifyEquals(canViewDashboardOnTitleClick,
                app.findaTopNavigation()
                        .goToNetworkSitesPage()
                        .searchForText(siteTitle)
                        .clickByTitle(siteTitle)
                        .getPageTitle(),
                app.findaHomePage()
                        .getExpectedPageTitle(UserAuthorizedPage.PagesEnum.DASHBOARD_PAGE),
                "Could not view dashboard for site by clicking on the site title");

        QualityVerify.verifyEquals(canVisitSite,
                app.findaTopNavigation().goToNetworkSitesPage().visitSite(siteTitle).getTitleAndReturn(),
                visitPageTitle, "Could not open site by clicking visit");

        // logout
        app.findaTopNavigation().logout();
    }

    /**
     * DV-TC-6
     * Design Vendor Widget Capabilities <br>
     * Validate Design Vendor can move, add, and remove widgets
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as Design Vendor (DV)</li>
     * <li>Go to Appearance -> Widgets and assert that there are no Navigation Menu widgets already in the sidebar</li>
     * <li>Add Navigation Menu widget to Sidebar</li>
     * <li>Verify that widget is in Sidebar on Widgets page</li>
     * <li>Verify that widget is on the Disclaimer page</li>
     * <li>Delete widget from Sidebar</li>
     * <li>Verify that widget is not in Sidebar on Widgets page</li>
     * <li>Verify that widget is not on the Disclaimer page</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void designVendorWidgetCapabilities() {
        final QualityCheck dvWidgetInSidebar =
                qualityTestCase.addCheck("The DV can add a widget (navigation menu) to the Sidebar");
        final QualityCheck widgetVisibleOnView =
                qualityTestCase.addCheck("Widget appears on sidebar on Disclaimer page");
        final QualityCheck canEditWidgetTitle =
                qualityTestCase.addCheck("DV can edit the title of a widget");
        final QualityCheck widgetRenamedWhenViewingPage =
                qualityTestCase.addCheck("Widget title has been changed on preview/view page");
        final QualityCheck canRemoveWidgetFromSidebar =
                qualityTestCase.addCheck("The DV can delete a widget (navigation menu) from the Sidebar ");
        final QualityCheck widgetNotVisibleOnViewUponRemoval =
                qualityTestCase.addCheck("Widget does not appear on sidebar on Disclaimer page");

        final String navMenuWidget = "Navigation Menu";
        final String targetSidebar = "Sidebar";
        final String pageTitle = "Design Vendor Widgets";
        final String pageTitleWithFork = "--(Layout Fork) " + pageTitle;
        final String menuToUse = "Main Nav";
        final String newTitle = "Design Vendor Navigation Menu";

        wordpressTestLogin(getDesignVendor());

        QualityVerify.verifyTrue(dvWidgetInSidebar,
                app.findaLeftNavigation()
                        .widgets()
                        .addWidget(navMenuWidget, targetSidebar)
                        .selectNavMenu(menuToUse, targetSidebar)
                        .isWidgetInSidebar(navMenuWidget, targetSidebar),
                "Widget does not appear in Sidebar after attempting to add");

        QualityVerify.verifyTrue(widgetVisibleOnView,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageTitle)
                        .previewPage(pageTitleWithFork)
                        .hasNavMenuAndBrowserNavigateBack(),
                "Widget is not visible on the page");

        QualityVerify.verifyTrue(canEditWidgetTitle,
                app.findaLeftNavigation()
                        .widgets()
                        .editWidgetTitle(navMenuWidget, targetSidebar, newTitle)
                        .isWidgetInSidebarWithName(navMenuWidget, targetSidebar, newTitle),
                "Advanced Customer could not edit the title of the widget");

        QualityVerify.verifyTrue(widgetRenamedWhenViewingPage,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageTitle)
                        .previewPage(pageTitleWithFork)
                        .hasNavMenuWithTitleAndBrowserNavigateBack(newTitle),
                "Widget is not visible on page with expected title");

        QualityVerify.verifyFalse(canRemoveWidgetFromSidebar,
                app.findaLeftNavigation()
                        .widgets()
                        .removeWidget(navMenuWidget, targetSidebar)
                        .isWidgetInSidebar(navMenuWidget, targetSidebar),
                "Widget appears in Sidebar after attempted removal when it shouldn't");

        QualityVerify.verifyFalse(widgetNotVisibleOnViewUponRemoval,
                app.findaLeftNavigation()
                        .pages()
                        .searchForTitle(pageTitle)
                        .previewPage(pageTitleWithFork)
                        .hasNavMenuAndBrowserNavigateBack(),
                "Widget is visible on the Disclaimer page when it shouldn't be");
        app.findaTopNavigation().logout();
    }

    /**
     * DV-TC-4
     * Design Vendor Media Capabilities
     * <br>
     * Test Description: Validate Design Vendor can view and upload file in Media library
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as Design Vendor</li>
     * <li>Navigate to Media -> Library and verify that Design Vendor can view all items</li>
     * <li>Verify that Design Vendor can add a file with Media -> Add New</li>
     * <li>Add a file with the Add New button on the Media -> Library page</li>
     * <li>Verify that the file is in the Library page</li>
     * <li>Search for the file and verify that searching for the added file places the first uploaded photo in the first item location</li>
     * <li>Logout</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void designVendorMediaCapabilities() {
        wordpressTestLogin(getDesignVendor());
        mediaCapabilities("DesignVendor");
        app.findaTopNavigation().logout();
    }
    
    /**
	 * Design Vendor Denied Menu Options <br>
	 * Test Steps CMS-2234:
	 * <ol>
	 * <li>Login as Design Vendor</li>
	 * <li>Verify the absence of People, Comments, Stream, Blog Posts, Tools,
	 * Settings, Users, or Plugins</li>
	 * <li>Verify the absence of Themes and Menus, Customize, Background</li>
	 * <li>Verify there is no other sub menu option than "Sites" and "Dashboard"
	 * when hovering over Network Admin</li>
	 * <li>Verify there is no "Edit you profile" option and that the username is not
	 * clickable</li>
	 * <li>Verify there is no "Your Profile" submenu option under users</li>
	 * <li>on network sites, Verify that there is NO other option on the left than
	 * "Sites", there is NO "Add New" or "Duplicate" options, and there is no Bulk
	 * Action drop down</li>
	 * <li>Verify there are NO other links than "Dashboard" and "Visit" when
	 * hovering over sites</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void designVendorDeniedMenuOptions() {
		final QualityCheck noPeopleMenuOption = qualityTestCase.addCheck("People option should not be present");
		final QualityCheck noCommentsMenuOption = qualityTestCase.addCheck("Comments option should not be present");
		final QualityCheck noStream = qualityTestCase.addCheck("Stream option should not be present");
		final QualityCheck noBlogPosts = qualityTestCase.addCheck("Blog Posts option should not be present");
		final QualityCheck noTools = qualityTestCase.addCheck("Tools option should not be present");
		final QualityCheck noSettings = qualityTestCase.addCheck("Settings option should not be present");
		final QualityCheck noUsers = qualityTestCase.addCheck("Users option should not be present");
		final QualityCheck noPlugins = qualityTestCase.addCheck("Plugins option should not be present");
		final QualityCheck noAppearanceSubTab = qualityTestCase
				.addCheck("NoThemes,Menus,Customize,Backgroundsub tabs under appearance");
		final QualityCheck onlyDashboardAndVisitLinksOnNetworkAdminHover = qualityTestCase
				.addCheck("Network Admin menu only contains Dashboard and Sites");
		final QualityCheck noEditYourProfile = qualityTestCase
				.addCheck("There should be no Edit Your Profile option when hovering over username");
		final QualityCheck noUsernameRedirection = qualityTestCase.addCheck("No redirection upon clicking on username");
		final QualityCheck noYourProfile = qualityTestCase
				.addCheck("Your Profile option should not be present under Users");
		final QualityCheck onlyDashboardAndVisitLinksOnSiteHover = qualityTestCase
				.addCheck("Dashboard and Visit options are present when hovering over sites");
		final QualityCheck noSiteMenu = qualityTestCase
				.addCheck("No menu options other than Sites under Main Sites tab");
		final QualityCheck noBulkActionDropDown = qualityTestCase.addCheck("No Bulk action dropdown in Sites page");

		wordpressTestLogin(getDesignVendor());

		// check if people is present
		QualityVerify.verifyFalse(noPeopleMenuOption, app.findaLeftNavigation().isTabPresent("people"),
				"People tab is present");

		// check if comments is present
		QualityVerify.verifyFalse(noCommentsMenuOption, app.findaLeftNavigation().isTabPresent("comments"),
				"comments tab is present");

		// check if stream is present
		QualityVerify.verifyFalse(noStream, app.findaLeftNavigation().isTabPresent("stream"), "Stream tab is present");

		// check if Blog Posts is present
		QualityVerify.verifyFalse(noBlogPosts, app.findaLeftNavigation().isTabPresent("Blog Posts"),
				"Blog Posts tab is present");

		// check if tools is present
		QualityVerify.verifyFalse(noTools, app.findaLeftNavigation().isTabPresent("tools"), "Tools tab is present");

		// check if settings is present
		QualityVerify.verifyFalse(noSettings, app.findaLeftNavigation().isTabPresent("settings"),
				"Settings tab is present");

		// check if users is present
		QualityVerify.verifyFalse(noUsers, app.findaLeftNavigation().isTabPresent("users"), "Users tab is present");

		// check if plugins is present
		QualityVerify.verifyFalse(noPlugins, app.findaLeftNavigation().isTabPresent("plugins"),
				"Plugins tab is present");

		QualityVerify.verifyFalse(noAppearanceSubTab,
				app.findaLeftNavigation().isSubTabPresent("Appearance", "Themes")
						|| app.findaLeftNavigation().isSubTabPresent("Appearance", "Menus")
						|| app.findaLeftNavigation().isSubTabPresent("Appearance", "Customize")
						|| app.findaLeftNavigation().isSubTabPresent("Appearance", "Background"),
				" Appearance subtab  is present when it shouldn't be");

		// Check for sub menu option "Sites" and "Dashboard" when hovering over Network
		// Admin
		QualityVerify.verifyTrue(onlyDashboardAndVisitLinksOnNetworkAdminHover,
				app.findaTopNavigation().networkAdminSubOptionExists("Sites")
						&& app.findaTopNavigation().networkAdminSubOptionExists("Dashboard"),
				"Manage comments subtab under Tools is present when it shouldn't be");

		// Verify there is no "Edit your profile" option and that the username is not
		// clickable
		QualityVerify.verifyFalse(noEditYourProfile, app.findaTopNavigation().isEditMyProfilePresent(),
				"Edit Profile link is present when hovering over username");

		// Clicking on account email should not navigate away from current page
		app.findaLeftNavigation().dashboard();
		app.findaTopNavigation().hitUsername();
		QualityAssert.assertEquals(noUsernameRedirection, app.findaHomePage().getPageTitle(),
				app.findaHomePage().getExpectedPageTitle(PagesEnum.HOME_PAGE),
				"Hitting account email navigated away from Home Page");

		// Check for no "Your Profile" submenu option under users
		QualityVerify.verifyFalse(noYourProfile, app.findaLeftNavigation().isSubTabPresent("Users", "Your Profile"),
				"Your Profile subtab under Users is present when it shouldn't be");

		// Verify there is no other menu option other than "Sites" under Main Site Sites

		app.findaTopNavigation().goToNetworkSitesPage();
		QualityVerify.verifyFalse(noSiteMenu,
				app.findaLeftNavigation().isSubTabPresent("Sites", "All Sites")
						|| app.findaLeftNavigation().isSubTabPresent("Sites", "Add New")
						|| app.findaLeftNavigation().isSubTabPresent("Sites", "Duplicate"),
				"Sites menu is present when it shouldn't be");
		QualityVerify.verifyFalse(noBulkActionDropDown, app.findaViewSitesPage().isBulkActionButtonPresent(),
				"Bulk action dropdown is present in Sites page");

		List<String> siteHoverLinks = Arrays.asList("Dashboard", "Visit");
		QualityAssert.assertTrue(onlyDashboardAndVisitLinksOnSiteHover,
				app.findaViewSitesPage().optionsAvailableForAny(siteHoverLinks),
				"unexpected link is present when hovering over a site");
		
		app.findaTopNavigation().logout();

	}

    /**
     * DV-TC-2
     * Design Vendor Page Restrictions
     * <br>
     * Test Description: Validate Design Vendor (DV) does not have access to certain page features.
     * <br>
     * Test Steps:
     * <ol>
     * <li>Login as DV</li>
     * <li>Click on pages and verify:</li>
     *     <ul>
     *         <li>No Add New Button</li>
     *         <li>No Bulk Actions dropdown</li>
     *         <li>Page forked for copy edit are not clickable</li>
     *     </ul>
     * <li>Hover over a non forked page and verify that the following links are not present</li>
     *     <ul>
     *         <li>"Edit", "Quick Edit", "Trash", "Fork for Copy Edit"</li>
     *     </ul>
     * <li>Hover over a layout forked page and verify that "Trash" and "Fork for Copy Edit" are not present</li>
     * <li>Verify that there is no Quick Add form on the All Pages page</li>
     * <li>Click "Fork for Layout" on an existing page and verify that the following links are not present</li>
     *     <ul>
     *         <li>"Fork For Copy Edit", "Move to Trash"</li>
     *     </ul>
     * <li>Verify there is no Comments checkbox under Columns</li>
     * </ol>
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void designVendorPageRestrictions() {
        final QualityCheck noAddNewButtonAllPages = qualityTestCase.addCheck("No Add New button on Pages -> All Pages");
        final QualityCheck noBulkActionsAllPages = qualityTestCase.addCheck("No Bulk Actions dropdown on Pages -> All Pages");
        final QualityCheck copyEditForkedPageNotClickable = qualityTestCase.addCheck("Cannot click on a page that is forked for Copy Edit");
        final QualityCheck nonForkPageNotClickable = qualityTestCase.addCheck("Cannot click on a page that is not forked for Copy Edit");
        final QualityCheck nonForkedPageLinks =
                qualityTestCase.addCheck("Edit, Quick Edit, Trash, and Fork for Copy Edit are not present when hovering over non-forked page");
        final QualityCheck onLayoutForkEditPage = qualityTestCase.addCheck("Clicking Fork for Layout option on all pages navigated to edit page");
        final QualityCheck layoutForkedPageLinks = qualityTestCase.addCheck("Trash and Fork for Copy Edit are not available for layout forked pages");
        final QualityCheck noQuickAddForm = qualityTestCase.addCheck("No Quick Add form on Pages -> All Pages");
        final QualityCheck layoutForkedEditPageLinks =
                qualityTestCase.addCheck("When editing a layout fork, links Fork for Copy Edit and Move to Trash are not present");
        final QualityCheck noCommentsScreenOption = qualityTestCase.addCheck("No Comments checkbox in screen options");

        // Title of page that has been forked for copy edit
        final String copyForkPageTitle = "DV Page Restrictions Already Copy Forked";
        final String copyForkedPageTitle = copyForkPageTitle + " (Locked for Editing)";

        // Title of page that has been forked for layout
        final String layoutForkPageTitle = "DV Page Restrictions Layout Fork";
        final String layoutForkedPageTitle = layoutForkPageTitle + " (Locked for Editing)";

        // A page that has been forked for neither copy edit nor layout
        final String nonForkedPageTitle = "DV Page Restrictions To Copy Fork";
        final String nonForkedPageTitleAfterFork = "Edit Page ‹ QA Test Automation — WordPress";

        wordpressTestLogin(getDesignVendor());

        QualityVerify.verifyFalse(noAddNewButtonAllPages,
                app.findaLeftNavigation()
                        .pages()
                        .isAddNewButtonPresent(),
                "Add New button was on Pages -> All Pages as a DV");

        QualityVerify.verifyFalse(noBulkActionsAllPages,
                app.findaPagesPage()
                        .isBulkActionButtonPresent(),
                "Bulk Action button was on Pages -> All Pages");

        // Ensure that the page is not clickable and NOT that the page doesn't exist, and is for that reason not clickable
        QualityVerify.verifyTrue(copyEditForkedPageNotClickable,
                app.findaPagesPage()
                        .searchForTitle(copyForkPageTitle)
                        .titleExists(copyForkedPageTitle)
                &&
                !app.findaPagesPage()
                        .isPageClickable(copyForkedPageTitle),
                "Page that is forked for copy edit is clickable when it shouldn't be");

        // Ensure that the page is not clickable and NOT that the page doesn't exist, and is for that reason not clickable
        QualityVerify.verifyTrue(nonForkPageNotClickable,
                app.findaPagesPage()
                        .searchForTitle(nonForkedPageTitle)
                        .titleExists(nonForkedPageTitle)
                &&
                !app.findaPagesPage()
                        .isPageClickable(nonForkedPageTitle),
                "Page that is not a copy fork is clickable when it shouldn't be");

        QualityVerify.verifyTrue(nonForkedPageLinks,
                app.findaPagesPage()
                        .searchForTitle(nonForkedPageTitle)
                        .titleExists(nonForkedPageTitle)
                        &&
                !app.findaPagesPage()
                        .anyOptionsAvailableForTitle(nonForkedPageTitle,
                                Arrays.asList("Edit", "Quick Edit", "Trash", "Fork for Copy Edit")),
                "Edit, Quick Edit, Trash, or Fork for Copy Edit option was available when hovering over a non forked page");

        QualityVerify.verifyTrue(layoutForkedPageLinks,
                app.findaPagesPage()
                        .searchForTitle(layoutForkPageTitle)
                        .titleExists(layoutForkedPageTitle)
                        &&
                        !app.findaPagesPage()
                                .anyOptionsAvailableForTitle(layoutForkedPageTitle,
                                        Arrays.asList("Trash", "Fork for Copy Edit")),
                "Trash or Fork for Copy Edit was available as an option when hovering over a page forked for layout");

        QualityVerify.verifyFalse(noQuickAddForm,
                app.findaLeftNavigation()
                        .pages()
                        .isQuickAddFormPresent(),
                "Quick Add form was present when it shouldn't be");

        QualityAssert.assertEquals(onLayoutForkEditPage,
				app.findaPagesPage()
						.searchForTitle(nonForkedPageTitle)
						.clickForkForLayoutByPageName(nonForkedPageTitle)
						.getPageTitle(),
				nonForkedPageTitleAfterFork,
				"Clicking Fork for Layout option did not navigate to  edit page");

        QualityVerify.verifyFalse(layoutForkedEditPageLinks,
                app.findaEditPagePage()
                        .isForkForCopyEditButtonPresent()
                ||
                app.findaEditPagePage()
                        .isMoveToTrashButtonPresent(),
                "Fork for Copy Edit or Move to Trash link is present when it shouldn't be");

        QualityVerify.verifyFalse(noCommentsScreenOption,
                app.findaEditPagePage()
                        .expandScreenOptions()
                        .isScreenOptionPresent("Comments"),
                "Comments screen option is present when it shouldn't be");

        app.findaLeftNavigation().pages();
        app.findaTopNavigation().logout();
    }

	/**
	 * DV-TC-5
	 * Design Vendor Forms Capabilities
	 * <br>
	 * Description: Validate Design Vendor can add forms to pages
	 * <br>
	 * Test Steps: See formsCapabilities
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void designVendorFormsCapabilities() {
		final String shortFormSearchString = "Design Vendor Contact";
		final String shortFormPageName = "--(Layout Fork) " + shortFormSearchString;
		final String longFormSearchString = "Design Vendor Complaints Contact Form";
		final String longFormPageName = "--(Layout Fork) " + longFormSearchString;

		wordpressTestLogin(getDesignVendor());
		formsCapabilities(shortFormPageName, shortFormSearchString, longFormPageName, longFormSearchString);
		app.findaTopNavigation().logout();
	}

	/**
	 * DV-TC-3
	 * Design Vendor Fork Page Capabilities
	 * <br>
	 * Description: Validate Design Vendor can fork pages and edit forked pages
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as Design Vendor</li>
	 * <li>Hover over an unforked page and click Fork for Layout</li>
	 * <li>Verify that Merge Edits into Parents is not available to DV when editing the layout fork</li>
	 * <li>Edit the text, click Save Changes, and return to Pages - All Pages</li>
	 * <li>Preview the layout fork and verify that changes to the text were saved</li>
	 * <li>Also verify that the pages status is now "Pending"</li>
	 * <li>Now edit an existing layout fork (forked by another user - admin)</li>
	 * <li>Make edits to the text and save. Return to All Pages</li>
	 * <li>Preview the page and ensure the edits have been made and saved</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
    public void designVendorForkPageCapabilities() {
		final QualityCheck noMergeEditsIntoParent =
				qualityTestCase.addCheck("DV does not have a \"Merge Edits into Parents\" when editing a page");
		final QualityCheck changesOnPreviewAfterSave =
				qualityTestCase.addCheck("After saving, text edits appear correctly in preview");
		final QualityCheck pageStatusChangedToPending =
				qualityTestCase.addCheck("After saving edits to text, status is \"Pending\" on All Pages");
		final QualityCheck canEditPageForkedByAdmin =
				qualityTestCase.addCheck("DV can edit a page forked by admin and save changes and changes appear on preview");

		final String unforkedPageTitle = "DV Fork Page Capabilities Unforked";
		final String forkedPageTitle = "--(Layout Fork) " + unforkedPageTitle;
		final String adminForkedPageTitle = "DV Fork Page Capabilities Forked by Admin";
		final String pageForkTitle = "--(Layout Fork) " + adminForkedPageTitle;

		final String unforkedPageText = "Edited text for DV Fork Page Capabilities Unforked";
		final String forkedByAdminPageText = "Edited text for DV Fork Page Capabilities Forked by Admin";

		wordpressTestLogin(getDesignVendor());

		QualityVerify.verifyFalse(noMergeEditsIntoParent,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(unforkedPageTitle)
						.clickForkForLayoutByPageName(unforkedPageTitle)
						.isMergeEditsIntoParentLinkPresent(),
				"Merge Edits into Parent button is present on the edit page when it shouldn't be for a DV");

		QualityVerify.verifyEquals(changesOnPreviewAfterSave,
				app.findanEditPagePage()
						.editPageText(unforkedPageText)
						.saveChanges()
						.hitPreviewButton()
						.getTextAndClose(),
				unforkedPageText,
				"Text on preview is not the same as the new text saved on the page before previewing");

		QualityVerify.verifyEquals(pageStatusChangedToPending,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(unforkedPageTitle)
						.getStatusForTitle(forkedPageTitle),
				"Pending",
				"Page status was not changed to pending after making edits and saving page as DV");

		QualityVerify.verifyEquals(canEditPageForkedByAdmin,
				app.findaPagesPage()
						.searchForTitle(adminForkedPageTitle)
						.editPage(pageForkTitle)
						.editPageText(forkedByAdminPageText)
						.saveChanges()
						.hitPreviewButton()
						.getTextAndClose(),
				forkedByAdminPageText,
				"Text on page fork that is forked by admin was not saved after edit.");
	}
}
