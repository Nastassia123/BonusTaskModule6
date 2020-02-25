package com.findlaw.wordpress.test;

import com.trgr.quality.qedarsenal.qualitylibrary.result.QualityCheck;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityAssert;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityVerify;
import com.wordpress.pages.abstractions.AbstractEditPage;
import com.wordpress.pages.abstractions.UserAuthorizedPage;
import com.wordpress.pages.abstractions.UserAuthorizedPage.PagesEnum;

import org.testng.TestGroup;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class WordpressCopywriterVendorRegressionTests extends BaseTest{

	/**
	 * Copywriter Vendor Denied Menu Options <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as Copywriter Vendor</li>
	 * <li>Verify the absence of "people"menu option in left menu</li>
	 * <li>Verify the absence of "comments" in left menu</li>
	 * * <li>Verify the absence of "Media" in left menu</li>
	 * <li>Verify there is no "stream" menu option in left menu</li>
	 * <li>Verify there is no "Ninja Forms" menu option in left menu</li>
	 * <li>Verify there is no "users" menu option in left menu</li>
	 * <li>Verify there is no "plugin" menu option in left menu</li>
	 * * <li>Verify there is no "Appearance" menu option in left menu</li>
	 * <li>Verify there is no "tools" menu option in left menu</li>
	 * <li>Verify there is no "settings" menu option in left menu</li>
	 * <li>Verify the presence of Dashboard, Blog Posts, Landing Pages, Pages, Divi Map menu</li>
	 * <li>Verify there is no other sub menu option under Network Admin than "Sites" and "Dashboard"</li>
	 * <li>Verify there is no "Edit your profile" option when hovering over user email</li>
	 * <li>Verify there is no username redirection</li>
	 * 
	 * <li>Verify there is no other menu option other than "Sites" under Main Site Sites tab</li>
	 * <li>Verify there is no "Add New" or "Duplicate" options, and that there is no Bulk Action drop down under Main Site Sites tab </li>
	 * <li>Verify there is no other links than "Dashboard" and "Visit" when hovering over sites</li>
	 * <li>Verify clicking on site redirects to the dashboard</li>
	 * </ol>
	 */
	 @Test(groups = { TestGroup.TestType.Regression })
		public void copywriterVendorDeniedMenuOptions() {

			final QualityCheck noPeopleMenu = qualityTestCase.addCheck("People Menu not present in left menu");
			final QualityCheck noCommentsMenu = qualityTestCase.addCheck("Comments Menu not present in left menu");
			final QualityCheck noMediaMenu = qualityTestCase.addCheck("Media Menu not present in left menu");
			final QualityCheck noStreamMenu = qualityTestCase.addCheck("Stream Menu not present in left menu");
			final QualityCheck noNinjaFormsMenu = qualityTestCase.addCheck("Ninja Forms Menu not present in left menu");
			final QualityCheck noUsersMenu = qualityTestCase.addCheck("Users Menu not present in left menu");
			final QualityCheck noPluginsMenu = qualityTestCase.addCheck("Plugins Menu not present in left menu");
			final QualityCheck noAppearanceMenu = qualityTestCase.addCheck("Appearance Menu not present in left menu");
			final QualityCheck noToolsMenu = qualityTestCase.addCheck("Tools Menu not present in left menu");
			final QualityCheck noSettingsMenu = qualityTestCase.addCheck("Settings Menu not present in left menu");
			final QualityCheck dashboardMenuPresent = qualityTestCase.addCheck("Dashboard Menu present in left menu");
			final QualityCheck blogPostMenuPresent = qualityTestCase.addCheck("Blog Posts Menu present in left menu");
			final QualityCheck landingPageMenuPresent = qualityTestCase.addCheck("Landing Pages Menu present in left menu");
			final QualityCheck pagesMenuPresent = qualityTestCase.addCheck("Pages Menu present in left menu");
			final QualityCheck diviMapMenuPresent = qualityTestCase.addCheck("Divi Map Menu present in left menu");
			final QualityCheck sitesMenuUnderNetworkAdminPresent = qualityTestCase.addCheck("Sites Menu present under network admin");
			final QualityCheck dashboardMenuUnderNetworkAdminPresent = qualityTestCase.addCheck("Dashbpard Menu present under network admin");
			final QualityCheck noEditProfile = qualityTestCase.addCheck("No 'Edit My Profile' link under username");
			final QualityCheck noUsernameRedirection = qualityTestCase.addCheck("No redirection upon clicking on username");
			final QualityCheck noSiteMenu = qualityTestCase.addCheck("No menu options other than Sites under Main Sites tab");
			final QualityCheck noBulkActionDropDown = qualityTestCase.addCheck("No Bulk action dropdown in Sites page");
			final QualityCheck sitesLinksPresent = qualityTestCase.addCheck("site links are present");
			final QualityCheck sitesRedirection = qualityTestCase.addCheck("site is redirecting to dashboard");

		 final String siteTitle = getSiteUrlNoHttp();
			
			wordpressTestLogin(getCopywriter());

			//menu options do NOT exist: People, Comments,Media, Stream,Ninja Forms, Users, Plugins,Appearance, Tools & Settings
    	
			QualityVerify.verifyFalse(noPeopleMenu,app.findaLeftNavigation().isTabPresent("People"),"People Menu is present in left menu");
			QualityVerify.verifyFalse(noCommentsMenu,app.findaLeftNavigation().isTabPresent("Comments"),"Comments Menu is present in left menu");
			QualityVerify.verifyFalse(noMediaMenu,app.findaLeftNavigation().isTabPresent("Media"),"Media Menu is present in left menu");
			QualityVerify.verifyFalse(noStreamMenu,app.findaLeftNavigation().isTabPresent("Stream"),"Stream Menu is present in left menu");
			QualityVerify.verifyFalse(noNinjaFormsMenu,app.findaLeftNavigation().isTabPresent("Ninja Forms"),"Ninja Forms Menu is present in left menu");
			QualityVerify.verifyFalse(noUsersMenu,app.findaLeftNavigation().isTabPresent("Users"),"Users Menu is present in left menu");
			QualityVerify.verifyFalse(noPluginsMenu,app.findaLeftNavigation().isTabPresent("Plugins"),"Plugins Menu is present in left menu");
			QualityVerify.verifyFalse(noAppearanceMenu,app.findaLeftNavigation().isTabPresent("Appearance"),"Appearance Menu is present in left menu");
			QualityVerify.verifyFalse(noToolsMenu,app.findaLeftNavigation().isTabPresent("Tools"),"Tools Menu is present in left menu");
			QualityVerify.verifyFalse(noSettingsMenu,app.findaLeftNavigation().isTabPresent("Settings"),"Settings Menu is present in left menu");
			QualityVerify.verifyTrue(dashboardMenuPresent,app.findaLeftNavigation().isTabPresent("Dashboard"),"Dashboard Menu is not present in left menu");
			QualityVerify.verifyTrue(blogPostMenuPresent,app.findaLeftNavigation().isTabPresent("Blog Posts"),"Blog Posts Menu is not present in left menu");
			QualityVerify.verifyTrue(landingPageMenuPresent,app.findaLeftNavigation().isTabPresent("Landing Pages"),"Landing Pages Menu is not present in left menu");
			QualityVerify.verifyTrue(pagesMenuPresent,app.findaLeftNavigation().isTabPresent("Pages"),"Pages Menu is present not in left menu");
			QualityVerify.verifyTrue(diviMapMenuPresent,app.findaLeftNavigation().isTabPresent("Divi Map"),"Divi Map Menu is present not in left menu");
			

			//Verify  sub menu option under Network Admin - "Sites" and "Dashboard"
			QualityVerify.verifyTrue(sitesMenuUnderNetworkAdminPresent,app.findaTopNavigation().networkAdminSubOptionExists("Sites"),"Sites Menu not present under network admin");
			QualityVerify.verifyTrue(dashboardMenuUnderNetworkAdminPresent,app.findaTopNavigation().networkAdminSubOptionExists("Dashboard"),"dashboard Menu not present under network admin");

			
			//Verify there is no "Edit your profile" option and that the username is not clickable
			QualityVerify.verifyFalse(noEditProfile,app.findaTopNavigation().isEditMyProfilePresent(),"Edit Profile link is present when hovering over username");

			// Clicking on account email should not navigate away from current page
			app.findaLeftNavigation().dashboard();
			app.findaTopNavigation().hitUsername();
			QualityVerify.verifyEquals(noUsernameRedirection, app.findaHomePage().getPageTitle(),app.findaHomePage().getExpectedPageTitle(PagesEnum.HOME_PAGE),"Hitting account email navigated away from Home Page");
			
			//Verify there is no other menu option other than "Sites" under Main Site Sites tab
			app.findaTopNavigation().goToNetworkSitesPage();
			QualityVerify.verifyFalse(noSiteMenu,app.findaLeftNavigation().isSubTabPresent("Sites","All Sites"),"All Sites sub menu is present in left menu under Sites");
			QualityVerify.verifyFalse(noSiteMenu,app.findaLeftNavigation().isSubTabPresent("Sites","Add New"),"Add New submenu is present in left menu under Sites");
			QualityVerify.verifyFalse(noSiteMenu,app.findaLeftNavigation().isSubTabPresent("Sites","Duplicate"),"Duplicate submenu is present in left menu under Sites");
			QualityVerify.verifyFalse(noBulkActionDropDown,app.findaViewSitesPage().isBulkActionButtonPresent(),"Bulk action dropdown is present in Sites page");
			
			// check for links on hovering over sites
			QualityVerify.verifyTrue(sitesLinksPresent,
					app.findaViewSitesPage().searchForSite(siteTitle).optionAvailableForTitle(siteTitle, "Dashboard")
					&& app.findaViewSitesPage().optionAvailableForTitle(siteTitle, "Visit"),
					"Site links are not present");
			
			//Verify clicking on site redirects to the dashboard
			app.findaViewSitesPage().clickByTitle((siteTitle));
			QualityAssert.assertEquals(sitesRedirection,
					app.findaDashboardPage()
							.getPageTitle(),
					app.findaDashboardPage()
							.getExpectedPageTitle(PagesEnum.DASHBOARD_PAGE),
					"Hitting site not  navigating to dashboard");

			 // logout
	           app.findaTopNavigation().logout();           
    }

	/**
	 * CV-TC-7
	 * Copywriter Vendor Network Site Capabilities
	 * <br>
	 * Description: Validate CV can view all sites
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as CV</li>
	 * <li>Go to My Sites -> Network Admin -> Sites</li>
	 * <li>Hover over the site and click Dashboard, then verify navigation to dashboard</li>
	 * <li>Return to Sites and verify navigation to dashboard again by clicking the site title</li>
	 * <li>Return to Sites again and verify that clicking Visit option under site navigates to the site</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void copywriterVendorNetworkSiteCapabilities() {
		final QualityCheck canViewDashboardOnHover = qualityTestCase.addCheck("Can view a site's dashboard by clicking Dashboard under site");
		final QualityCheck canViewDashboardOnTitleClick = qualityTestCase.addCheck("Can view sites dashboard by clicking site title");
		final QualityCheck canVisitSite = qualityTestCase.addCheck("CV can visit a site");

		final String siteTitle = getSiteUrlNoHttp();
		final String visitPageTitle = "QA Test Automation | IM-Template";

		wordpressTestLogin(getCopywriter());

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
	 * CV-TC-2
	 * Copywriter Vendor Page Restrictions
	 * <br>
	 * Description: Validate Copywriter Vendor (CV) cannot create or delete a page
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as CV</li>
	 * <li>Go to Pages -> All Pages and verify there is no Add New</li>
	 * <li>Ensure that all pages on the first page do not have the options Trash and Fork for Layout</li>
	 * <li>Verify there is no Quick Add form on Pages -> All Pages</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void copywriterVendorPageRestrictions() {
		final QualityCheck noAddNewButton = qualityTestCase.addCheck("No Add New button on Pages -> All Pages");
		final QualityCheck noTrashOrForkForLayout = qualityTestCase.addCheck("No Trash or Fork for Layout options for any pages");
		final QualityCheck noQuickAddForm = qualityTestCase.addCheck("No Quick Add form on the Pages -> All Pages page");

		wordpressTestLogin(getCopywriter());

		QualityVerify.verifyFalse(noAddNewButton,
				app.findaLeftNavigation()
						.pages()
						.isAddNewButtonPresent(),
				"Add New button is on page when it should not be for Copywriter Vendor");

		QualityVerify.verifyFalse(noTrashOrForkForLayout,
				app.findaPagesPage()
						.optionsAvailableForAny(Arrays.asList("Trash", "Fork for Layout")),
				"Trash or Fork for Layout is available on a page when it should not be for Copywriter Vendor");

		QualityVerify.verifyFalse(noQuickAddForm,
				app.findaPagesPage()
						.isQuickAddFormPresent(),
				"Quick Add form was on the page when it should not be for Copywriter Vendor");

		app.findaTopNavigation().logout();
	}

	/**
	 * CV-TC-3
	 * Copywriter Vendor Edit Page Capabilities
	 * <br>
	 * Description: Validate Copywriter Vendor (CV) can edit a page
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as CV</li>
	 * <li>Navigate to edit an existing page and verify that the following are not available:
	 * 	<ol>
	 * 	    <li>Fork for Layout</li>
	 * 	    <li>Move to Trash</li>
	 * 	    <li>Copy Information Section</li>
	 * 	</ol>
	 * </li>
	 * <li>Click on Screen Options and verify there is no "Comments" column checkbox</li>
	 * <li>Edit the Divi text module of the page</li>
	 * <li>Preview the pages to ensure that the changes were made</li>
	 * <li>Update the page and preview again to make sure the changes were saved</li>
	 * <li>Go to an existing draft page and edit the page</li>
	 * <li>Save the edited draft and preview the page to make sure the changes were made</li>
	 * <li>Publish the draft page and preview again, making sure the changes have been published</li>
	 * <li>Return to All Pages, edit a page and make two edits, saving each time</li>
	 * <li>Verify that the version history appears in the publish section</li>
	 * <li>Return to All Pages and attempt to bulk edit the status of both pages used to Draft</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void copywriterVendorEditPageCapabilities() {
		final QualityCheck forkForLayoutNotVisible =
				qualityTestCase.addCheck("Option Fork for Layout is not available when editing a page");
		final QualityCheck moveToTrashNotVisible =
				qualityTestCase.addCheck("Option Move to Trash is not available when editing a page");
		final QualityCheck copyInformationSectionNotVisible =
				qualityTestCase.addCheck("Copy Information section is not available");
		final QualityCheck noCommentsColumn =
				qualityTestCase.addCheck("No Comments column in screen options when editing a page");
		final QualityCheck previewCorrectBeforeUpdate =
				qualityTestCase.addCheck("Previewed page has edited text before updating");
		final QualityCheck previewCorrectAfterUpdate =
				qualityTestCase.addCheck("Previewed page has edited text after updating");
		final QualityCheck draftPreviewCorrectBeforePublish =
				qualityTestCase.addCheck("Previewed draft page has edited text before publishing");
		final QualityCheck draftPreviewCorrectAfterPublish =
				qualityTestCase.addCheck("Previewed draft page has edited text after publishing");
		final QualityCheck versionHistoryDisplays =
				qualityTestCase.addCheck("After editing and updating the page twice, version history is displayed");
		final QualityCheck canBulkEditStatusPublished = qualityTestCase.addCheck("Can bulk edit status of published page to draft");
		final QualityCheck canBulkEditStatusDraft = qualityTestCase.addCheck("Can bulk edit status of draft page to draft");

		final String publishedPage = "Copywriter Edit Page";
		final String draftPage = "Copywriter Edit Page Draft";
		final String bulkSearchString = "Copywriter Edit Page";

		final String editedPublishedPageText = "Edited published page text";
		final String editedDraftPageText = "Edited draft page text";
		final String newStatus = "Draft";

		wordpressTestLogin(getCopywriter());

		QualityVerify.verifyFalse(forkForLayoutNotVisible,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(publishedPage)
						.editPage(publishedPage)
						.isForkForLayoutLinkPresent(),
				"Fork for layout is present on an edit page as CV when it shouldn't be");

		QualityVerify.verifyFalse(moveToTrashNotVisible,
				app.findanEditPagePage()
						.isMoveToTrashButtonPresent(),
				"Move to Trash is present on an edit page as a CV when it shouldn't be");

		QualityVerify.verifyFalse(copyInformationSectionNotVisible,
				app.findanEditPagePage()
						.isSectionPresent("Copy Information"),
				"Copy Information section is displayed on an edit page when it shouldn't be as a CV");

		QualityVerify.verifyFalse(noCommentsColumn,
				app.findanEditPagePage()
						.isScreenOptionPresent("Comments"),
				"Screen option column checkbox Comments is displayed when it should not be");

		QualityVerify.verifyEquals(previewCorrectBeforeUpdate,
				app.findanEditPagePage()
						.editTextContentInDiviBuilder(editedPublishedPageText)
						.hitPreviewButton()
						.getTextAndClose(),
				editedPublishedPageText,
				"Preview page before updating published page has incorrect text");

		QualityVerify.verifyEquals(previewCorrectAfterUpdate,
				app.findanEditPagePage()
						.hitUpdateOrPublishEditPage()
						.returnToAllPages()
						.searchForTitle(publishedPage)
						.previewPage(publishedPage)
						.getTextAndBack(),
				editedPublishedPageText,
				"Preview page after updating published page has incorrect text");

		QualityVerify.verifyEquals(draftPreviewCorrectBeforePublish,
				app.findaPagesPage()
						.searchForTitle(draftPage)
						.editPage(draftPage)
						.editTextContentInDiviBuilder(editedDraftPageText)
						.hitPreviewButton()
						.getTextAndClose(),
				editedDraftPageText,
				"Preview page before updating draft page has incorrect text");

		QualityVerify.verifyEquals(draftPreviewCorrectAfterPublish,
				app.findanEditPagePage()
						.hitUpdateOrPublishEditPage()
						.returnToAllPages()
						.searchForTitle(draftPage)
						.previewPage(draftPage)
						.getTextAndBack(),
				editedDraftPageText,
				"Preview page after updating draft page has incorrect text");

		QualityVerify.verifyTrue(versionHistoryDisplays,
				app.findaLeftNavigation()
						.pages()
						.editPage(publishedPage)
						.editTextContentInDiviBuilder("A second edit to the page")
						.saveChanges()
						.isVersionHistoryPresent(),
				"Version history did not display after making two edits to the published page");

		QualityVerify.verifyEquals(canBulkEditStatusPublished,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(bulkSearchString)
						.checkAllWithTitle(publishedPage)
						.checkAllWithTitle(draftPage)
						.selectBulkAction("Edit")
						.hitApplyBulkAction()
						.clickBulkEditAndSelectStatus(newStatus)
						.getStatusForTitle(publishedPage),
				newStatus,
				"Published page status did not change to Draft after bulk edit");

		QualityVerify.verifyEquals(canBulkEditStatusDraft,
				app.findaPagesPage()
						.getStatusForTitle(draftPage),
				newStatus,
				"Draft page status did not change to Draft after bulk edit");

		app.findaTopNavigation().logout();
	}

	/**
	 * CV-TC-6
	 * Copywriter Vendor Blog Posts Tags and Categories Capabilities
	 * <br>
	 * Test Description: Validate the Copywriter Vendor (cv) cannot manage tags and categories, quick editing works,
	 * and the edit page displays correctly.
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as CV</li>
	 * <li>Verify that there are no submenu options Tags or Categories under Blog Posts</li>
	 * <li>Go to Blog Posts, click Quick Edit under a post and verify that CV can change status with quick edit</li>
	 * <li>Edit an existing post and verify that the CV can select categories and tags in the blog post edit page</li>
	 * <li>Verify that there is no Comments checkbox under columns and no comments section anywhere on the page</li>
	 * <li>Verify that there is no Comment Response Page option under the page type dropdown</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void copywriterVendorBlogPostTagsAndCategoriesCapabilities() {
		final QualityCheck noTagsSubmenu = qualityTestCase.addCheck("No submenu Blog Posts -> Tags");
		final QualityCheck noCategoriesSubmenu = qualityTestCase.addCheck("No submenu Blog Posts -> Categories");
		final QualityCheck canQuickEditStatus = qualityTestCase.addCheck("CV can quick edit status");
		final QualityCheck canSelectCategories = qualityTestCase.addCheck("CV can select categories on blog post edit page");
		final QualityCheck canSelectTags = qualityTestCase.addCheck("CV can select tags on blog post edit page");
		final QualityCheck noCommentsColumnsCheckbox =
				qualityTestCase.addCheck("No Comments checkbox in Columns section of screen options");
		final QualityCheck noCommentsSectionOnPage = qualityTestCase.addCheck("No Comments section on the blog post edit page");
		final QualityCheck noCommentResponsePagePageTypeOption = qualityTestCase.addCheck("No page type option Comment Response page");

		final String blogPostTitle = "CV Blog Post Tags and Categories Post";
		final String newStatus = "Draft";
		final String categoryName = "Transportation";
		final String tagName = "Law";

		wordpressTestLogin(getCopywriter());

		QualityVerify.verifyFalse(noTagsSubmenu,
				app.findaLeftNavigation()
					.isSubTabPresent("Blog Posts", "Tags"),
			"Blog Posts -> Tags exists for a CV when it shouldn't.");

		QualityVerify.verifyFalse(noCategoriesSubmenu,
				app.findaLeftNavigation()
					.isSubTabPresent("Blog Posts", "Categories"),
			"Blog Posts -> Categories exists for a CV when it shouldn't.");

		QualityVerify.verifyEquals(canQuickEditStatus,
				app.findaLeftNavigation()
					.blogPosts()
					.quickEditStatusForBlogTitle(blogPostTitle, newStatus)
					.getStatusForBlogTitle(blogPostTitle),
			newStatus,
			"CV could not quick edit status of existing page from Published to Draft.");

		// Next two verifications failing (CMS-2331) - need to save before category/tag displays on preview

		QualityVerify.verifyTrue(canSelectCategories,
				app.findaBlogPage()
						.searchForText(blogPostTitle)
						.editBlogPost(blogPostTitle)
						.selectExistingCategory(categoryName)
						.saveChanges()
						.hitPreviewButton()
						.hasCategoryAndClose(categoryName),
				"Category selected by user did not appear in preview");

		QualityVerify.verifyTrue(canSelectTags,
				app.findaBlogPostEditPage()
						.selectTagInBlogPost(tagName)
						.saveChanges()
						.hitPreviewButton()
						.hasTagInSidebarAndClose(tagName),
				"Tag selected by user did not appear in preview");

		QualityVerify.verifyFalse(noCommentsColumnsCheckbox,
				app.findaBlogPostEditPage()
						.expandScreenOptions()
						.isScreenOptionPresent("Comments"),
				"Comments column is displayed in screen options");

		QualityVerify.verifyFalse(noCommentsSectionOnPage,
				app.findaBlogPostEditPage()
						.isCommentSectionPresent(),
				"Comments section appears on edit page");

		QualityVerify.verifyFalse(noCommentResponsePagePageTypeOption,
				app.findaBlogPostEditPage()
						.isPageTypeOptionPresent("Comment Response Page"),
				"Comment Response Page is an available option for blog post page type.");

		app.findaLeftNavigation().blogPosts();
	}

	/**
	 * CV-TC-5
	 * Copywriter Vendor Blog Posts Capabilities
	 * <br>
	 * Description: Validate Copywriter Vendor (CV) can create, schedule, and delete blog posts
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as CV</li>
	 * <li>Click on Blog Posts -> Add New and verify that:
	 *     <ol>
	 *         <li>Page goal is set to "Legal Information"</li>
	 *         <li>Language is set to "English"</li>
	 *         <li>Page type is set to "Blog Post"</li>
	 *     </ol></li>
	 * <li>Fill in page title and text, publish, and verify that the title exists in Blog Posts - All Pages</li>
	 * <li>Open bulkDelete1, edit the text in the body and preview the post. Verify that the text is correct in the preview</li>
	 * <li>Select a practice area and verify that the practice area is changed in the dropdown</li>
	 * <li>Click on Blog Posts and then hit the Add New button, verify that the CV lands on an Edit blog post page</li>
	 * <li>Hit Edit next to publish immediately and change to a later date</li>
	 * <li>Return to Blog Posts - All Page and verify that the post is scheduled for a later publish time</li>
	 * <li>Go to All Posts, hover over a post, and click on the Trash link under a post, verify the post is deleted</li>
	 * <li>Click on a post to edit it and click the Move to Trash link, verify that the post has been deleted</li>
	 * <li>Select a few more posts and select Move to Trash in the bulk actions dropdown, veriy that they are gone</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void copywriterVendorBlogPostCapabilities() {

		final QualityCheck pageGoalCorrect = qualityTestCase.addCheck("Page goal is set to \"Legal Information\"");
		final QualityCheck pageLanguageCorrect = qualityTestCase.addCheck("Language is set to \"English\"");
		final QualityCheck pageTypeCorrect = qualityTestCase.addCheck("Page type is set to \"Blog Post\"");
		final QualityCheck canAddNewPostFromMenu =
				qualityTestCase.addCheck("Page created with Blog Posts -> Add New was published correctly");
		final QualityCheck canEditText = qualityTestCase.addCheck("Text added in body displays on the preview");
		final QualityCheck canSetPracticeArea = qualityTestCase.addCheck("Practice area is set on the post");
		final QualityCheck canAddPostFromAllBlogPosts =
				qualityTestCase.addCheck("Clicking Add New button on Blog Posts page navigates to Edit Blog Post page");
		final QualityCheck canChangePublishTime = qualityTestCase.addCheck("Post is scheduled for a later publish time after editing the publish time");
		final QualityCheck canDeleteOnHover = qualityTestCase.addCheck("Post is deleted on hover");
		final QualityCheck canDeleteFromEditPage = qualityTestCase.addCheck("Post is deleted from Edit Post page");
		final QualityCheck canBulkDeletePosts = qualityTestCase.addCheck("Two more posts deleted with bulk delete no longer exist");

		final String newPostTitleFromLeftMenu = "CV Page Added Left Menu";
		final String newPostLeftMenuText = "New page left menu text";
		final String newPostNameAllPostsPage = "CV Page Added All Posts Page";
		final String bulkDelete1 = "Pharmacist Found Guilty of Health Care Fraud";
		final String bulkDelete2 = "How safe are nail guns?";

		final String bulkDeleteEditText = "Text used for verification canEditText";

		final String pageGoal = "Legal Information";
		final String pageLanguage = "English";
		final String pageType = "Blog Post";
		final String practiceArea = "Admiralty & Maritime Law";

		final Calendar publishDate = Calendar.getInstance();
		final Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
		publishDate.setTime(tomorrow);

		wordpressTestLogin(getCopywriter());

		app.findaLeftNavigation().blogPosts("Add New");
		QualityVerify.verifyEquals(pageGoalCorrect,
				app.findaBlogPostEditPage()
						.getPageGoal(),
				pageGoal,
				"Page goal is incorrect.");

		QualityVerify.verifyEquals(pageLanguageCorrect,
				app.findaBlogPostEditPage()
						.getPageLanguage(),
				pageLanguage,
				"Page language is incorrect.");

		QualityVerify.verifyEquals(pageTypeCorrect,
				app.findaBlogPostEditPage()
						.getPageType(),
				pageType,
				"Page type is incorrect.");

		// Publish page newPostTitleFromLeftMenu
		app.findaBlogPostEditPage().fillInPageContent(newPostLeftMenuText).fillInTitle(newPostTitleFromLeftMenu).saveChanges();
		QualityVerify.verifyTrue(canAddNewPostFromMenu,
				app.findaLeftNavigation()
						.blogPosts()
						.searchForText(newPostTitleFromLeftMenu)
						.titleExists(newPostTitleFromLeftMenu),
				"Post not found in All Posts after publish");

		QualityVerify.verifyEquals(canEditText,
				app.findaBlogPage()
						.searchForText(bulkDelete1)
						.editBlogPost(bulkDelete1)
						.fillInPageContent(bulkDeleteEditText)
						.hitPreviewButton()
						.getTextAndClose(),
				bulkDeleteEditText,
				"Text edited does not appear properly when previewing the post.");

		QualityVerify.verifyEquals(canSetPracticeArea,
				app.findaBlogPostEditPage()
						.selectPracticeArea(practiceArea)
						.saveChanges()
						.getPracticeArea(),
				practiceArea,
				"Could not edit the practice area of a post");

		app.findaBlogPage().hitAddNew();
		app.findaBlogPostEditPage()
				.editBlogPostPublishDate(publishDate)
				.fillInTitle(newPostNameAllPostsPage)
				.saveChanges();
		QualityVerify.verifyTrue(canAddPostFromAllBlogPosts,
				app.findaLeftNavigation()
						.blogPosts()
						.searchForText(newPostNameAllPostsPage)
						.titleExists(newPostNameAllPostsPage),
				"User could not add a post from the Add New button on All Blog Posts page");

		QualityVerify.verifyTrue(canChangePublishTime,
				app.findaBlogPage()
						.verifyPublishDateForPost(newPostNameAllPostsPage, publishDate),
				"Scheduled post does not have the correct publish date");

		QualityVerify.verifyFalse(canDeleteOnHover,
				app.findaLeftNavigation()
						.blogPosts()
						.searchForText(newPostTitleFromLeftMenu)
						.hitTrashForTitle(newPostTitleFromLeftMenu)
						.titleExists(newPostTitleFromLeftMenu),
				"Failed to delete post using Trash link on hover");

		QualityVerify.verifyFalse(canDeleteFromEditPage,
				app.findaLeftNavigation()
						.blogPosts()
						.searchForText(newPostNameAllPostsPage)
						.editBlogPost(newPostNameAllPostsPage)
						.hitTrashLink()
						.searchForText(newPostNameAllPostsPage)
						.titleExists(newPostNameAllPostsPage),
				"Failed to delete post from Move to Trash link on edit post page");

		QualityVerify.verifyFalse(canBulkDeletePosts,
				app.findaBlogPage()
						.searchForText("")
						.hitCheckBoxForTitle(bulkDelete1)
						.hitCheckBoxForTitle(bulkDelete2)
						.selectBulkAction("Move to Trash")
						.hitApplyBulkAction()
						.searchForText(bulkDelete1)
						.titleExists(bulkDelete1)
				||
				app.findaBlogPage()
						.searchForText(bulkDelete2)
						.titleExists(bulkDelete2),
				"Failed to bulk delete pages, at least one title still exists.");

		app.findaTopNavigation().logout();
	}
	
	/**
	 * CV-TC-4
	 * Copywriter Vendor Fork for Copy Edit Page Capabilities
	 * <br>
	 * Test Description: Validate CV (copywriter vendor) can fork a page for copy edit and merge changes)
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as CV</li>
	 * <li>Fork a page for copy edit by clicking the sub option on hover over a page</li>
	 * <li>Make some edits to the text and click Save Changes</li>
	 * <li>Return to All Pages and verify that the forked page is "Locked for Editing"</li>
	 * <li>Click on the fork page and click enter redline mode</li>
	 * <li>Click on edits resolved, edit the text again, and click save progress</li>
	 * <li>Verify that the Save Progress Button becomes Progress Saved</li>
	 * <li>Then click Merge Edits into Live and verify that the CV is redirected to the page that was originally forked</li>
	 * <li>Go back to all pages and verify that the text edits appear on the preview</li>
	 * <li>Repeat all steps using the Fork for Copy Edit link under the Publish section on the edit page page</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void copywriterVendorForkForCopyEditPageCapabilities() {
		final QualityCheck forkedPageIsLocked = qualityTestCase.addCheck("Page is locked after forking for copy edit");
		final QualityCheck saveProgressButtonChanges =
				qualityTestCase.addCheck("Save Progress button becomes Progress Saved after clicking it");
		final QualityCheck redirectedAfterMerge =
				qualityTestCase.addCheck("User is redirected to the forked page after merging edits");
		final QualityCheck editsMerged =
				qualityTestCase.addCheck("Text is edited when viewing on Edit Page (All Page Fork");
		final QualityCheck editsAppearOnPreview =
				qualityTestCase.addCheck("Edited text appears on merged page in preview (All Page Fork)");
		final QualityCheck copyForkFromEditPage =
				qualityTestCase.addCheck("After clicking Fork for Copy Edit link, Currently Viewing Fork message displays");

		wordpressTestLogin(getCopywriter());

		String title = "Copywriter Fork for Copy Edit";
		String copyEditForkTitle = String.format("--(Copy Fork) %s", title);
		String editedContentRedline = "Forked for Edit Text";
		String editPageTitle = "Copy Edit ‹ QA Test Automation — WordPress";

		QualityVerify.verifyTrue(forkedPageIsLocked,
				app.findaLeftNavigation()
						.pages()
						.searchForText(title)
						.clickForkForCopyEdit(title)
						.saveChanges()
						.goBackToPage(AbstractEditPage.DashboardMenu.PAGES)
						.searchForText(title)
						.checkEntryIsLockedForEditing(title),
				"Forked page is not locked for editing");

		QualityVerify.verifyTrue(saveProgressButtonChanges,
				app.findaPagesPage()
						.editPage(copyEditForkTitle)
						.clickOnEnterRedlineMode()
						.clickOnEditsResolved()
						.editTextInRedlineMode(editedContentRedline)
						.acceptAllEdits()
						.clickOnEditsResolved()
						.isSaveProgressButtonUpdatedAfterClicking(),
				"Save Progress button does not change to Progress Saved after clicking it");

		QualityVerify.verifyEquals(redirectedAfterMerge,
				app.findaEditPagePage()
						.mergeEditsIntoLive()
						.getPageTitle(),
				editPageTitle,
				"User is not redirected to forked page after clicking Merge Edits into Live in Redline Mode");

		QualityVerify.verifyEquals(editsMerged,
				app.findaEditPagePage()
						.getContentFromDiviBuilder(),
				editedContentRedline,
				"Text edited in redline mode has not been edited in original page's Divi builder after merge (from All Pages)");

		// Needed since Divi builder was opened
		app.findanEditPagePage().hitUpdateOrPublishEditPage().returnToAllPages();

		QualityVerify.verifyEquals(editsAppearOnPreview,
				app.findaPagesPage()
						.searchForTitle(title)
						.previewPage(title)
						.getTextAndBack(),
				editedContentRedline,
				"Edits are not merged into parent after copy edit from All Pages");

		QualityVerify.verifyTrue(copyForkFromEditPage,
				app.findaPagesPage()
						.searchForTitle(title)
						.editPage(title)
						.hitForkForCopyEdit()
						.isForkCurrentlyViewed(),
				"Currently viewing fork message does not appear after clicking Fork for Copy Edit when it should");

		// Delete the last fork so that the CV does not continue to edit the page
		app.findaEditPagePage().hitTrashLink();
		app.findaLeftNavigation().dashboard();

		// Logout
		app.findaTopNavigation().logout();
	}

	/**
	 * CV-TC-8
	 * Copywriter Vendor Blog Post Author Functionality
	 * <br>
	 * Description: Validate the author displays correctly on blog posts
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as Copywriter Vendor</li>
	 * <li>Go to Blog Post -> Add New and verify that the firm name is not listed in the author drop down</li>
	 * <li>Run tests described in blogPostAuthorFunctionality()</li>
	 * <li>Logout</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void copywriterVendorBlogPostAuthorFunctionality() {

		final QualityCheck noAuthorDropdown =
				qualityTestCase.addCheck("No screen option \"Author\" present and no author dropdown");

		final String firmName = "Abraham, Watkins, Nichols, Sorrels, Agosto & Friend";
		final String blogPostTitle = "Blog Post Author Functionality CV";
		final String authorFieldValue = "On behalf of " + firmName;

		wordpressTestLogin(getCopywriter());

		QualityVerify.verifyFalse(noAuthorDropdown,
				app.findaLeftNavigation()
						.blogPosts()
						.addNewBlogPost()
						.expandScreenOptions()
						.isBoxesScreenOptionPresent("Author")
				||
				app.findaBlogPostEditPage().isSectionPresent("Author"),
				"Copywriter Vendor has an author dropdown when they shouldn't");

		// Before running the rest of the verifications, fill in the title
		app.findaBlogPostEditPage()
				.fillInTitle(blogPostTitle);
		blogPostAuthorFunctionality(blogPostTitle, authorFieldValue);

		app.findaTopNavigation().logout();
	}

	/**
	 * CV-TC-9
	 * Copywriter Vendor Video Capabilities
	 * <br>
	 * Description: Validate that [Add Video from Brightcove Video Center] button works as expected (CMS-2940)
	 * <br>
	 * Test Steps: see videoCapabilities
	 *
	 * @author Pavel Bychkou
	 */
	@Test(groups = { TestGroup.TestType.Regression, "Metadata||tester-name:PavelBychkou||Automated:Yes"  })
	public void copywriterVendorVideoCapabilities() {

		// login as Copywriter Vendor
		wordpressTestLogin(getCopywriter());

		videoCapabilities("Copywriter Vendor");

		//logout
		app.findaTopNavigation().logout();
	}
}
