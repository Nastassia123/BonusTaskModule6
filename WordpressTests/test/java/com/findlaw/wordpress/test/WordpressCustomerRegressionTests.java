package com.findlaw.wordpress.test;

import com.trgr.quality.qedarsenal.qualitylibrary.result.QualityCheck;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityAssert;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityVerify;
import com.wordpress.pages.abstractions.UserAuthorizedPage;
import org.openqa.selenium.WebElement;
import org.testng.TestGroup;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Matt Goodmanson on 9/11/2018.
 */
public class WordpressCustomerRegressionTests extends BaseTest {

    /**
     * CMS-2067: Customer Denied Menu Capabilities and Firm Name Display
     * Validate Customer does not have access to certain menu capabilities and can see the firm name on front and back end
     *
     * 1. Login as customer
     * 2. Observe the left menu : Verify the following menu options do NOT exist: People, Comments, Ninja Forms, Stream,
     *      Users, Plugins, Divi, DiviMap, Appearance, Tools, Settings, WP Engine
     * 3. Hover over "My Sites" > Hover over one of the sites : Verify there is no "Manage Comments" menu option
     * 4. Hover over the username : Verify there is no "Edit your profile" option and that the username is not clickable
     * 5. Hover over "My Sites" : Verify there is no "Network Admin" option
     * 6. Observe the menu at the top of the page : Verify that the firm name is present on the top bar of the page
     * 7. Got to Pages > All Pages > hover over the home page > click View : Verify there is no Sync Content option and
     *      that the firm name is present on the top bar of the page
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void customerDeniedMenuCapabilitiesAndFirmNameDisplay() {
        final QualityCheck noPeopleMenuOption = qualityTestCase.addCheck("People option should not be present");
        final QualityCheck noCommentsMenuOption = qualityTestCase.addCheck("Comments option should not be present");
        final QualityCheck noNinjaForms = qualityTestCase.addCheck("Ninja Forms option should not be present");
        final QualityCheck noStream = qualityTestCase.addCheck("Stream option should not be present");
        final QualityCheck noUsers = qualityTestCase.addCheck("Users option should not be present");
        final QualityCheck noPlugins = qualityTestCase.addCheck("Plugins option should not be present");
        final QualityCheck noDivi = qualityTestCase.addCheck("Divi option should not be present");
        final QualityCheck noDiviMap = qualityTestCase.addCheck("Divi Map option should not be present");
        final QualityCheck noAppearance = qualityTestCase.addCheck("Appearance option should not be present");
        final QualityCheck noTools = qualityTestCase.addCheck("Tools option should not be present");
        final QualityCheck noSettings = qualityTestCase.addCheck("Settings option should not be present");
        final QualityCheck noWpEngine = qualityTestCase.addCheck("WP Engine option should not be present");
        final QualityCheck noManageComments = qualityTestCase.addCheck("There should be no Manage Comments option when hovering over a site");
        final QualityCheck noNetworkAdmin = qualityTestCase.addCheck("There should be no Network Admin option when hovering over My Sites");
        final QualityCheck noEditYourProfile = qualityTestCase.addCheck("There should be no Edit Your Profile option when hovering over username");
        final QualityCheck firmNamePresentDashboard = qualityTestCase.addCheck("The firm name is present at the top of the dashboard");
        final QualityCheck firmNamePresentPreview = qualityTestCase.addCheck("The firm name is present at the top of a preview page");
        final QualityCheck noSyncContent = qualityTestCase.addCheck("Sync content is not present at the top of a preview page");

        // login
        wordpressTestLogin(getCustomer());

        // check if people is present
        QualityVerify.verifyFalse(noPeopleMenuOption,
                app.findaLeftNavigation().isTabPresent("people"),
                "People tab is present");

        // check if comments is present
        QualityVerify.verifyFalse(noCommentsMenuOption,
                app.findaLeftNavigation().isTabPresent("comments"),
                "comments tab is present");

        // check if ninja forms is present
        QualityVerify.verifyFalse(noNinjaForms,
                app.findaLeftNavigation().isTabPresent("ninja forms"),
                "ninja forms tab is present");

        // check if stream is present
        QualityVerify.verifyFalse(noStream,
                app.findaLeftNavigation().isTabPresent("stream"),
                "Stream tab is present");

        // check if users is present
        QualityVerify.verifyFalse(noUsers,
                app.findaLeftNavigation().isTabPresent("users"),
                "Users tab is present");

        // check if plugins is present
        QualityVerify.verifyFalse(noPlugins,
                app.findaLeftNavigation().isTabPresent("plugins"),
                "Plugins tab is present");

        // check if divi is present
        QualityVerify.verifyFalse(noDivi,
                app.findaLeftNavigation().isTabPresent("divi"),
                "Divi tab is present");

        // check if divi map is present
        QualityVerify.verifyFalse(noDiviMap,
                app.findaLeftNavigation().isTabPresent("divi map"),
                "Divi Map tab is present");

        // check if Appearance is present
        QualityVerify.verifyFalse(noAppearance,
                app.findaLeftNavigation().isTabPresent("appearance"),
                "Appearance tab is present");

        // check if tools is present
        QualityVerify.verifyFalse(noTools,
                app.findaLeftNavigation().isTabPresent("tools"),
                "Tools tab is present");

        // check if settings is present
        QualityVerify.verifyFalse(noSettings,
                app.findaLeftNavigation().isTabPresent("settings"),
                "Settings tab is present");

        // check if WP Engine is present
        QualityVerify.verifyFalse(noWpEngine,
                app.findaLeftNavigation().isTabPresent("wp engine"),
                "WP Engine tab is present");

        // check for manage comments menu option when hovering over site
        QualityVerify.verifyFalse(noManageComments,
                app.findaTopNavigation().siteSubOptionExists(0, "Manage Comments"),
                "Manage comments subtab under Tools is present when it shouldn't be");

        // check for network admin option when hovering over My Sites
        QualityVerify.verifyFalse(noNetworkAdmin,
                app.findaTopNavigation().isNetworkAdminPresent(),
                "Network admin is present when hovering over My Sites");

        // check for edit your profile option when hovering over username
        QualityVerify.verifyFalse(noEditYourProfile,
                app.findaTopNavigation().isEditMyProfilePresent(),
                "Edit your profile option should not be present");

        // check for firm name at the top of the dashboard
        QualityVerify.verifyTrue(firmNamePresentDashboard,
                app.findaTopNavigation().isFirmNamePresent(),
                "Firm name is missing from the top of the dashboard");

        // navigate to preview page
        app.findaLeftNavigation().pages().previewPage("Home");

        // check for firm name at the top of the preview page
        QualityVerify.verifyTrue(firmNamePresentPreview,
                app.findaTopNavigation().isFirmNamePresent(),
                "Firm name is missing from the top of the preview page");

        // check for sync content at the top of the preview page
        QualityVerify.verifyFalse(noSyncContent,
                app.findaPreviewPage().isSyncContentPresent(),
                "Sync content should not be present on preview page");

        // logout
        app.findaTopNavigation().logout();
    }

    /**
     * Customer Page Restrictions
     * Validate Customer cannot create, delete, or fork pages and that there are no comments sections
     *
     * 1. Login as customer : should be at dashboard
     * 2. Click on Pages > All Pages : Verify there is no Add New Button
     * 3. Hover over an existing page : Verify the following links are NOT present: Fork for Copy Edit, Fork for Layout, Trash
     * 4. Click on an existing page and observe the sections on the right: Verify the following links are NOT present:
     * 		Fork for Copy Edit, Fork for Layout, and Move to Trash. Verify there is no Copy Information section
     * 5. Click on Screen Options : Verify there is NO Comments checkbox under Columns
     * 6. Expand the Page Type dropdown : Verify there is NO Comments Response Page option
     * 7. Click on Pages > All Pages and expand the bulk Actions dropdown : Verify there is NO Move to Trash option
     *
     * @author Matt Goodmanson
     */
    @Test(groups = { TestGroup.TestType.Regression })
    public void customerPageRestrictions() {
        final QualityCheck noAddNewPageButton = qualityTestCase.addCheck("There is no Add New page button");
        final QualityCheck noAddNewPageLink = qualityTestCase.addCheck("There is no Add New page link");
        final QualityCheck pageLinksNotPresent = qualityTestCase.addCheck("Restricted links not present when hovering over page");
        final QualityCheck noMoveToTrashBulkAction = qualityTestCase.addCheck("There is no Move to Trash option for page bulk actions");
        final QualityCheck editPageLinksNotPresent = qualityTestCase.addCheck("Restricted links not present on edit page");
        final QualityCheck noCopyInformationSection = qualityTestCase.addCheck("Copy Information section is not present on edit page page");
        final QualityCheck noCommentsInScreenOptions = qualityTestCase.addCheck("There is no Comments checkbox in screen options");
        final QualityCheck noCommentsResponsePagePageType = qualityTestCase.addCheck("There is no Comment Response page option in Page Type dropdown on edit page");

        // login as customer
        wordpressTestLogin(getCustomer());

        // check for add new page options
        QualityVerify.verifyFalse(noAddNewPageLink,
                app.findaLeftNavigation().isSubTabPresent("Pages", "Add New"),
                "Add New link is present for pages");

        QualityVerify.verifyFalse(noAddNewPageButton,
                app.findaLeftNavigation()
                        .pages()
                        .isAddNewButtonPresent(),
                "Add New button is present for pages");

        // check for page options
        WebElement page = app.findaPagesPage().searchForTitle("Home").getItemByIndex(0);

        QualityVerify.verifyFalse(pageLinksNotPresent,
                app.findaPagesPage().isItemOptionPresent(page, "Trash")
                        || app.findaPagesPage().isItemOptionPresent(page, "Fork for Copy Edit")
                        || app.findaPagesPage().isItemOptionPresent(page, "Fork for Layout")
                        || app.findaPagesPage().isItemOptionPresent(page, "Move to Trash"),
                "Customer has access to page option that should be restricted");

        // check for move to trash option in bulk actions
        QualityVerify.verifyFalse(noMoveToTrashBulkAction,
                app.findaPagesPage()
                        .isbulkActionPresent("Move to Trash"),
                "Move to Trash option is present under bulk actions");

        // check for links on edit page
        app.findaPagesPage().hitItem(page);

        QualityVerify.verifyFalse(editPageLinksNotPresent,
                app.findanEditPagePage().isForkForCopyEditButtonPresent()
                        || app.findanEditPagePage().isForkForLayoutLinkPresent()
                        || app.findanEditPagePage().isTrashLinkPresent(),
                "Customer has access to page option from edit page that should be restricted");

        // check for copy information section
        QualityVerify.verifyFalse(noCopyInformationSection,
                app.findanEditPagePage().isCopyInformationSectionPresent(),
                "Copy Information section present on edit page");

        // check for comments column in screen options
        QualityVerify.verifyFalse(noCommentsInScreenOptions,
                app.findaDashboardPage()
                        .expandScreenOptions()
                        .isScreenOptionPresent("Comments"),
                "There should be no Comments option under screen options column for edit page");

        // check for comments response page option in page type dropdown on edit page
        QualityVerify.verifyFalse(noCommentsResponsePagePageType,
                app.findanEditPagePage()
                        .expandPageTypeSection()
                        .isPageTypeOptionPresent("Comment Response Page"),
                "There should be no Comment Response Page option under page type dropdown on edit page");

        // logout
        app.findaTopNavigation().logout();
    }

	/**
	 * Customer Blog Post Tag Capabilities
	 * <br>
	 * Objective: Validate Customer can create, edit, and delete tags
	 * <br>
	 * Test steps: See blogPostTagCapabilities.
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void customerBlogPostTagCapabilities() {


		final String newTagOnTagsScreen = "CustomerTagTest_fromTagsScreen";
		final String newTagOnPostEditScreen = "CustomerTagTest_fromEditScreen";
		final String blogPostTitle="Schools are frequent sites of bad driving habits";

		// Customer can add a new Tags
		wordpressTestLogin(getCustomer());

		blogPostTagCapabilities(newTagOnTagsScreen, newTagOnPostEditScreen, blogPostTitle);

		// logout
		app.findaTopNavigation().logout();
	}


	/**
	 * Customer Edit Page Capabilities
	 * <br>
	 * Objective: Validate the Customer can edit pages
	 * <br>
	 * Test Steps:
	 * <br><ol>
	 * <li>Login as customer</li>
	 * <li>Click on Pages -> All Pages and click on an existing page's title</li>
	 * <li>Verify navigation to edit page page</li>
	 * <li>Return to All Pages and hover an an existing Published page and click on Edit</li>
	 * <li>Make edits to page body (using divi - page already using divi)</li>
	 * <li>Click preview changes</li>
	 * <li>Verify changes are in preview</li>
	 * <li>Click publish</li>
	 * <li>Return to all pages and click View option for edited page</li>
	 * <li>Verify correct content and return from View</li>
	 * <li>Hit Quick Edit option for a page and edit status</li>
	 * <li>Verify the status is changed</li>
	 * <li>Do the same steps for a draft page (edit existing draft page, preview changes and publish/save draft, but preview after save)</li>
	 * <li>Go to All Pages, click checkbox that selects all pages and perform bulk edit on author field and apply</li>
	 * <li>Ensure author has been updated</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void customerEditPageCapabilities() {

		QualityCheck canEditByHittingTitle = qualityTestCase.addCheck("Customer can navigate to editing page by hitting title");
		QualityCheck editedPagePreviewPublishedPage =
				qualityTestCase.addCheck("Edits are implemented in preview before publish/save - Published page");
		QualityCheck changesPublishedInViewPublishedPage =
				qualityTestCase.addCheck("Changes have been published when viewing page - Published page");
		QualityCheck canQuickEditStatusPublishedPage =
				qualityTestCase.addCheck("Can edit the status of publish page with quick edit - Published page");
		QualityCheck editedPagePreviewDraftPage =
				qualityTestCase.addCheck("Edits are implemented in preview before publish/save - Draft page");
		QualityCheck changesPublishedInViewDraftPage =
				qualityTestCase.addCheck("Changes have been saved when previewing page - Draft page");
		QualityCheck canQuickEditStatusDraftPage =
				qualityTestCase.addCheck("Can edit the status of publish page with quick edit - Draft page");
		QualityCheck bulkEditedStatus = qualityTestCase.addCheck("Use bulk action Edit to change the status of every page successfully");

		final String publishedPageTitle = "Accidents at Work";
		final String editedPublishedPageText = "Edited Published Page Text with new text because the edited text was in the backup";

		final String draftPageTitle = "Defective Equipment Accidents";
		final String editedDraftPageText = "Edited Draft Page Text with new text because the edited text was in the backup";

		final String newStatusPublishedPage = "Draft";
		final String newStatusDraftPage = "Published";

		final String bulkEditNewStatus = "Draft";

		wordpressTestLogin(getCustomer());

		QualityAssert.assertEquals(canEditByHittingTitle,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(publishedPageTitle)
						.clickByTitle(publishedPageTitle)
						.getPageTitle(),
				app.findaHomePage().getExpectedPageTitle(UserAuthorizedPage.PagesEnum.EDIT_PAGE_PAGE),
				"Clicking on page title did not navigate to editing the page");

		QualityVerify.verifyEquals(editedPagePreviewPublishedPage,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(publishedPageTitle)
						.editPage(publishedPageTitle)
						.editPageText(editedPublishedPageText)
						.hitPreviewChangesEditPage()
						.getTextAndClose(),
				editedPublishedPageText,
				"Preview of published page did not have correct (edited but not published) title or text");

		QualityVerify.verifyEquals(changesPublishedInViewPublishedPage,
				app.findanEditPagePage()
						.hitUpdateOrPublishEditPage()
						.returnToAllPages()
						.searchForTitle(publishedPageTitle)
						.previewPage(publishedPageTitle)
						.getTextAndBack(),
				editedPublishedPageText,
				"Title and text of page were incorrect after publishing and viewing");

		QualityVerify.verifyTrue(canQuickEditStatusPublishedPage,
				app.findaPagesPage()
						.searchForTitle(publishedPageTitle)
						.clickQuickEditLinkForPage(publishedPageTitle)
						.selectQuickEditStatus(newStatusPublishedPage)
						.pageHasStatus(publishedPageTitle, newStatusPublishedPage),
				"Author was not changed from quick edit published page");

		QualityVerify.verifyEquals(editedPagePreviewDraftPage,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(draftPageTitle)
						.editPage(draftPageTitle)
						.editPageText(editedDraftPageText)
						.hitPreviewChangesEditPage()
						.getTextAndClose(),
				editedDraftPageText,
				"Preview of draft page did not have correct (edited but not published) title or text");

		QualityVerify.verifyEquals(changesPublishedInViewDraftPage,
				app.findanEditPagePage()
						.saveDraftPage()
						.returnToAllPages()
						.searchForTitle(draftPageTitle)
						.previewPage(draftPageTitle)
						.getTextAndBack(),
				editedDraftPageText,
				"Title and text of draft page were incorrect after publishing and previewing");

		QualityVerify.verifyTrue(canQuickEditStatusDraftPage,
				app.findaPagesPage()
						.searchForTitle(draftPageTitle)
						.clickQuickEditLinkForPage(draftPageTitle)
						.selectQuickEditStatus(newStatusDraftPage)
						.pageHasStatus(draftPageTitle, newStatusDraftPage),
				"Author was not changed from quick edit on draft page");

		QualityVerify.verifyTrue(bulkEditedStatus,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(draftPageTitle)
						.checkPageCheckbox(publishedPageTitle)
						.checkPageCheckbox(draftPageTitle)
						.hitBulkEditAndClickApply()
						.selectStatusInBulkEdit(bulkEditNewStatus)
						.searchForTitle(draftPageTitle)
						.pagesHaveStatus(Arrays.asList(publishedPageTitle, draftPageTitle), bulkEditNewStatus),
				"Bulk edit status did not work, at least one page has a status that does not equal " + bulkEditNewStatus);

		app.findaTopNavigation().logout();
	}

	/**
	 * C-TC-4
	 * Customer Media Capabilities
	 * <br>
	 * Test Description: Validate Customer can view and upload file in Media library
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as Customer</li>
	 * <li>Navigate to Media -> Library and verify that Customer can view all items</li>
	 * <li>Verify that Customer can add a file with Media -> Add New</li>
	 * <li>Add a file with the Add New button on the Media -> Library page</li>
	 * <li>Verify that the file is in the Library page</li>
	 * <li>Search for the file and verify that searching for the added file places the first uploaded photo in the first item location</li>
	 * <li>Logout</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void customerMediaCapabilities() {
		wordpressTestLogin(getCustomer());
		mediaCapabilities("RegularCustomer");
		app.findaTopNavigation().logout();
	}

	/**
	 * C-TC-6
	 * Customer Blog Post Categories Capabilities
	 * <br>
	 * Description:  Validate the Customer can create, edit, and delete blog post categories
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as Customer</li>
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
	public void customerBlogPostCategoriesCapabilities() {
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

		final String newCategoryFromCategoryPage = "CustomerCategoryTest_fromCategoryPage";
		final String newCategoryFromEditPost = "CustomerCategoryTest_fromEditPost";
		final String editedCategory = "CustomerCategoryTest_fromCategoryPageEdited";
		final String postName = "Motorcycles may soon get their own AI safety technology";

		wordpressTestLogin(getCustomer());

		QualityVerify.verifyTrue(newCategoryAddedOnCategoryPage,
				app.findaLeftNavigation()
						.blogCategories()
						.enterNewCategoryName(newCategoryFromCategoryPage)
						.hitAddNewCategory()
						.titleExists(newCategoryFromCategoryPage),
				"Customer could not add category to Categories from Categories page");

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
				"Customer could not delete all categories from Categories page");

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
 * CMS-2077: Customer Blog Post Capabilities<br>
 * Test Steps:
 * <ol>
 * <li>Login as Customer</li>
 * <li>Verify Customer can add a new post from the menu</li>
 * <li>page goal is set to "Legal Information", the language is set to
 * "English", and the page type is set to "Blog Post"</li>
 * <li>Verify Customer can schedule a post to be published at a later date-Schedule
 *  post to be published tomorrow and click schedule : Verify that post appears in all
 *  posts and is scheduled for tomorrow</li>
 * <li>Verify Customer can add a new post from the All Blog Posts page (publish
 * the post)</li>
 * <li>Verify Customer can edit an existing post and can select a practice
 * area</li>
 * <li>Verify Customer can quick edit an existing post</li>
 * <li>Verify Customer can publish a post forked by an admin</li>
 * <li>Verify Customer can delete a blog post from All Blog Posts</li>
 * <li>Verify Customer can delete a post from the edit post page</li>
 * <li>Verify Customer can bulk delete posts</li>
 * </ol>
 */
@Test(groups = { TestGroup.TestType.Regression })
public void customerBlogPostCapabilities() {

	final String blogPostAddNewMenuOption = "Customer Blog Post Capabilities: Post with edited publish date";
	final String blogPostAddNewButton = "Customer Blog Post Capabilities: add new button";
	final String bulkDeletePost1 = "Post to Delete for Automation 5";
	final String bulkDeletePost2 = "Post to Delete for Automation 6";

	// login as Customer
	wordpressTestLogin(getCustomer());

	blogPostCapabilities("Customer", blogPostAddNewMenuOption, blogPostAddNewButton, bulkDeletePost1, bulkDeletePost2);

	// logout
	app.findaTopNavigation().logout();

}

	/**
	 * C-TC-8
	 * Customer Blog Post Author Functionality
	 * <br>
	 * Description: Validate the author displays correctly on blog posts
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Login as Customer</li>
	 * <li>Go to Blog Post -> Add New and verify that the firm name is not listed in the author drop down</li>
	 * <li>Run tests described in blogPostAuthorFunctionality()</li>
	 * <li>Logout</li>
	 * </ol>
	 */
	@Test(groups = { TestGroup.TestType.Regression })
	public void customerBlogPostAuthorFunctionality() {

		final QualityCheck noFirmNameInAuthorDropdown =
				qualityTestCase.addCheck("Firm name does not appear in Author dropdown as customer");

		final String firmName = "Abraham, Watkins, Nichols, Sorrels, Agosto & Friend";
		final String blogPostTitle = "Blog Post Author Functionality Customer";
		final String authorName = "qaautomationcustomer";
		final String authorFieldValue = "by " + authorName;

		wordpressTestLogin(getCustomer());

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
	 * C-TC-9
	 * Customer Video Capabilities
	 * <br>
	 * Description: Validate that [Add Video from Brightcove Video Center] button works as expected (CMS-2940)
	 * <br>
	 * Test Steps: see videoCapabilities
	 *
	 * @author Pavel Bychkou
	 */
	@Test(groups = { TestGroup.TestType.Regression, "Metadata||tester-name:PavelBychkou||Automated:Yes"  })
	public void customerVideoCapabilities() {

		// login as Copywriter Vendor
		wordpressTestLogin(getCustomer());

		videoCapabilities("Customer");

		//logout
		app.findaTopNavigation().logout();
	}
}
