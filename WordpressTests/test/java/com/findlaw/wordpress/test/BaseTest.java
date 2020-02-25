package com.findlaw.wordpress.test;

import com.findlaw.common.config.AppSystemProperty;
import com.findlaw.common.pages.SafeLoginPage;
import com.findlaw.common.ui.Browser;
import com.findlaw.common.ui.WaitExtensions;
import com.findlaw.common.utilities.FileUtils;
import com.trgr.quality.qedarsenal.qualitylibrary.result.QualityCheck;
import com.trgr.quality.qedarsenal.qualitylibrary.result.testng.QualityVerify;
import com.wordpress.pages.abstractions.AbstractEditPage;
import com.wordpress.pages.application.WordpressSafeLoginPage;
import com.wordpress.pages.dashboard.DashboardPage;
import com.wordpress.pages.application.WordpressApplication;
import org.testng.FindlawBaseTest;
import com.findlaw.common.utilities.JSONUtils;
import lombok.extern.log4j.Log4j;
import org.testng.Reporter;
import com.wordpress.data.entities.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Base test class for Wordpress.
 * 
 * Copied from SamPortalTests/BaseTest and modified for Wordpress
 */
@Log4j
public class BaseTest extends FindlawBaseTest {

	/** WordPress application instance - get access to all the pages */
	public static final WordpressApplication app = new WordpressApplication();

	/** Findlaw Customer Object */
	protected static InheritableThreadLocal<WPUser> wpCustomerLocal;
	
	/** Findlaw Copywriter Object */
	protected static InheritableThreadLocal<WPUser> wpCopywriterLocal;
	
	/** Findlaw DesignVendor Object */
	protected static InheritableThreadLocal<WPUser> wpDesignVendorLocal;
	
	/** Findlaw SuperAdmin Object */
	protected static InheritableThreadLocal<WPUser> wpSuperAdminLocal;

	/** Findlaw Advanced Customer Object */
	private static InheritableThreadLocal<WPUser> wpAdvancedCustomerLocal;

	/** Findlaw Admin Object */
	private static InheritableThreadLocal<WPUser> wpAdminLocal;

	public BaseTest() {
		super();
		onTestRun();
	}

	/**
	 * Gets FindlawCustomer object data
	 *
	 * @return FindlawCustomer object
	 */
	public WPUser getCustomer() {
		return wpCustomerLocal.get();
	}
	
	/**
	 * Gets FindlawCopywriter object data
	 *
	 * @return FindlawCopywriter object
	 */
	public WPUser getCopywriter() {
		return wpCopywriterLocal.get();
	}
	
	/**
	 * Gets FindlawDesignVendor object data
	 *
	 * @return FindlawDesignVendor object
	 */
	public WPUser getDesignVendor() {
		return wpDesignVendorLocal.get();
	}
	
	/**
	 * Gets FindlawSuperAdmin object data
	 *
	 * @return FindlawSuperAdmin object
	 */
	public WPUser getSuperAdmin() {
		return wpSuperAdminLocal.get();
	}

	/**
	 * Gets FindlawAdmin object data
	 *
	 * @return FindlawAdmin object
	 */
	public WPUser getAdmin() {
		return wpAdminLocal.get();
	}

	/**
	 * Gets FindlawAdvancedCustomer object data
	 *
	 * @return FindlawAdvancedCustomer object
	 */
	WPUser getAdvancedCustomer() {
		return wpAdvancedCustomerLocal.get();
	}

	/** On test run - maps json file data to actual object */
	private void onTestRun() {
		if (wpCustomerLocal == null) {
			wpCustomerLocal = new InheritableThreadLocal<WPUser>() {

				@Override
				protected WPUser initialValue() {
					return new WPUser();
				}
			};
		}
		if (wpCopywriterLocal == null) {
			wpCopywriterLocal = new InheritableThreadLocal<WPUser>() {

				@Override
				protected WPUser initialValue() {
					return new WPUser();
				}
			};
		}
		if (wpDesignVendorLocal == null) {
			wpDesignVendorLocal = new InheritableThreadLocal<WPUser>() {

				@Override
				protected WPUser initialValue() {
					return new WPUser();
				}
			};
		}
		if (wpSuperAdminLocal == null) {
			wpSuperAdminLocal = new InheritableThreadLocal<WPUser>() {

				@Override
				protected WPUser initialValue() {
					return new WPUser();
				}
			};
		}
		if (wpAdminLocal == null) {
			wpAdminLocal = new InheritableThreadLocal<WPUser>() {

				@Override protected WPUser initialValue() {
					return new WPUser();
				}
			};
		}
		if (wpAdvancedCustomerLocal == null) {
			wpAdvancedCustomerLocal = new InheritableThreadLocal<WPUser>() {

				@Override
				protected WPUser initialValue() {
					return new WPUser();
				}
			};
		}
		
		wpCustomerLocal.set(JSONUtils.doMapping().jsonFileToObject(FileUtils.getFilePath("Customer.json"), WPUser.class));
		wpCopywriterLocal.set(JSONUtils.doMapping().jsonFileToObject(FileUtils.getFilePath("Copywriter.json"), WPUser.class));
		wpDesignVendorLocal.set(JSONUtils.doMapping().jsonFileToObject(FileUtils.getFilePath("DesignVendor.json"), WPUser.class));
		wpSuperAdminLocal.set(JSONUtils.doMapping().jsonFileToObject(FileUtils.getFilePath("SuperAdmin.json"), WPUser.class));
		wpAdminLocal.set(JSONUtils.doMapping().jsonFileToObject(FileUtils.getFilePath("Admin.json"), WPUser.class));
		wpAdvancedCustomerLocal.set(JSONUtils.doMapping().jsonFileToObject(FileUtils.getFilePath("AdvancedCustomer.json"), WPUser.class));
		
		Reporter.log("Account type credential information loaded");
	}

	protected DashboardPage wordpressTestLogin(WPUser userIn) {
		Reporter.log("User type: " + userIn.getType());
		switch(userIn.getType()) {
			case "TRID":
				app.findaLoginManager()
						.goToTridLogin()
						.loginTrid(userIn.getUsername(), userIn.getPassword());
				app.findaLeftNavigation().dashboard();
				break;
			case "SAFE":
				String answer = (userIn.getUsername().equals(getSuperAdmin().getUsername())) ? "nothing" : "answer";
				app.findaLoginManager()
						.goToWordpressSafeLogin()
						.loginPlus(userIn.getUsername(), userIn.getPassword())
						.answerQuestion(answer);
				break;
		}
		return new DashboardPage();
	}

	/**
	 * Used at the start of M-TC-2. Instead of logging in, navigate to the contact page's URL as a
	 * regular visitor to the site.
	 *
	 * USES http://, not https://
	 *
	 * Must use / before extension, url will not have one at the end
	 */
	protected void goToLiveSiteWithExtension(String extension) {
		String url = "";
		String environment = AppSystemProperty.getInstance().getEnvironment();
		if ("qa".equalsIgnoreCase(environment)) {
			url = "https://automationalias.fltestsite-qa2.com/";
		} else if ("stage".equalsIgnoreCase(environment)) {
			url="https://automationalias.fltestsite-stage2.com/";
		}
		url = url.concat(extension);
		Browser.goToUrl(url);
		WaitExtensions.waitForPageLoad(240);
	}

	/**
	 * Go to the network site instead of the test site. Used in smoke tests.
	 */
	protected void goToNetworkSite() {
		String siteUrl = Browser.getAppUrlFromConfigProperties(
				"wordpressnetwork",
				AppSystemProperty.getInstance().getEnvironment());
		Browser.goToUrl(siteUrl);
		WaitExtensions.waitForPageLoad(30);

		// Always login with super admin credentials
		String env = AppSystemProperty.getInstance().getEnvironment();
		if (env.contains("prod")) {
			// Use generic prod login for prod tests
			new SafeLoginPage().safeGenericProdLogin();
		} else {
			new WordpressSafeLoginPage().loginPlus(getSuperAdmin().getUsername(), getSuperAdmin().getPassword()).answerQuestion("nothing");
		}
		WaitExtensions.waitForPageLoad(120);
	}

	/**
	 * Some tests need the url of the site being accessed for various reasons. Used to retrive the URL from
	 * config.properties.
	 *
	 * @return String - the URL of the site being tested
	 */
	protected String getSiteUrlNoHttp() {
		String siteUrl = getSiteUrl();
		if (siteUrl.startsWith("http://")) {
			return siteUrl.substring("http://".length());
		} else if (siteUrl.startsWith("https://")) {
			return siteUrl.substring("https://".length());
		}
		return siteUrl;
	}

	protected String getSiteUrl() {
		return Browser.getAppUrlFromConfigProperties(
				AppSystemProperty.getInstance().getApplication(),
				AppSystemProperty.getInstance().getEnvironment());
	}

	/**
	 * Used by all Media Capabilities tests
	 * Test Steps:
	 * <ol>
	 * <li>Navigate to Media -> Library and verify that user can view all items</li>
	 * <li>Verify that user can add a file with Media -> Add New</li>
	 * <li>Add a file with the Add New button on the Media -> Library page</li>
	 * <li>Verify that the file is in the Library page</li>
	 * <li>Search for the file and verify that searching for the added file places the first uploaded photo in the first item location</li>
	 * </ol>
	 * @param role The role that will be used when performing the verifications. Also used for filenames.
	 */
	protected void mediaCapabilities(String role) {
		final QualityCheck canViewAllItems = qualityTestCase.addCheck(role + " can view all media items");
		final QualityCheck canAddNewPhotoFromLeftNavigation = qualityTestCase.addCheck(role + " can add a new media item from Media -> Add New");
		final QualityCheck canAddNewPhotoFromMediaLibrary = qualityTestCase.addCheck(role + " can add a new media item from library Add New");
		final QualityCheck canSearchLibrary = qualityTestCase.addCheck(role + " can search for an item by item name");

		final String photoNameToUploadFromLeftNavigationNoExtension = String.format("%sAutomated_Test_Photo", role);
		final String photoNameToUploadFromLeftNavigation = String.format("%s.jpg", photoNameToUploadFromLeftNavigationNoExtension);
		final String expectedPhotoFileName = String.format("File name: %s", photoNameToUploadFromLeftNavigation);

		// Use another photo - mp4 not allowed
		final String photoNameToUploadFromMediaLibraryNoExtension = String.format("%sWP_AutomationMediaFile", role);
		final String photoNameToUploadFromMediaLibrary = String.format("%s.jpg", photoNameToUploadFromMediaLibraryNoExtension);

		QualityVerify.verifyTrue(canViewAllItems,
				app.findaLeftNavigation()
						.mediaLibrary()
						.canViewFirstItem(),
				role + " can not view the first item on the Media -> Library page, or no items exist");

		QualityVerify.verifyTrue(canAddNewPhotoFromLeftNavigation,
				app.findaLeftNavigation()
						.addNewMedia()
						.uploadFile(photoNameToUploadFromLeftNavigation)
						.returnToMediaLibrary()
						.itemExistsWithName(photoNameToUploadFromLeftNavigationNoExtension),
				"Photo uploaded by " + role + " from LeftNavigationPanel is not in the list of media items");

		QualityVerify.verifyTrue(canAddNewPhotoFromMediaLibrary,
				app.findaLeftNavigation()
						.mediaLibrary()
						.uploadFile(photoNameToUploadFromMediaLibrary)
						.itemExistsWithName(photoNameToUploadFromMediaLibraryNoExtension),
				"Photo uploaded by " + role + " from MediaLibrary is not in the list of media items");

		// Search for photo uploaded first, will be second in entire library after second photo added
		QualityVerify.verifyEquals(canSearchLibrary,
				app.findaLeftNavigation()
						.mediaLibrary()
						.searchForMediaItem(photoNameToUploadFromLeftNavigation)
						.waitForMediaItemToDisappear(photoNameToUploadFromMediaLibraryNoExtension)
						.getFirstItemFileName(),
				expectedPhotoFileName,
				"Searching by file name for photo shows no file or a different file in the first index as " + role);
	}

	/**
	 * Admin and Advanced Customer can find the title of the pages to be edited by searching for the title
	 * itself. Design Vendor must edit pages forked for layout, and must search for the layout fork page
	 * differently (without Locked for Editing).
	 *
	 * @param shortFormPageName The title of the page to add a short form to (also the search string)
	 * @param longFormPageName The title of the page to add a long form to (also the search string)
	 */
	protected void formsCapabilities(String shortFormPageName, String longFormPageName){
		formsCapabilities(shortFormPageName, shortFormPageName, longFormPageName, longFormPageName);
	}

	/**
	 * Used by adminFormsCapabilities, advancedCustomerFormsCapabilities
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Go to shortFormPageName and add a form module to the page (use short form)</li>
	 * <li>Verify that the module has been added to Divi builder</li>
	 * <li>Preview the page and verify that the short form appears with correct fields (fields not in long form)</li>
	 * <li>Update the page and return to All Pages</li>
	 * <li>Go to longFormPageName and add a form module to the page (use long form)</li>
	 * <li>Verify that the module has been added to Divi builder</li>
	 * <li>Preview the page and verify that the long form appears on the page (fields not in short form appear)</li>
	 * <li>Logout</li>
	 * </ol>
	 */
	protected void formsCapabilities(String shortFormPageName, String shortFormSearchString, String longFormPageName,
									 String longFormSearchString) {
		final QualityCheck canAddShortForm = qualityTestCase.addCheck("Short form has been added to Divi builder");
		final QualityCheck shortFormOnPreviewPage = qualityTestCase.addCheck("Short form appears on preview page");
		final QualityCheck canAddLongForm = qualityTestCase.addCheck("Long form has been added to Divi builder");
		final QualityCheck longFormOnPreviewPage = qualityTestCase.addCheck("Long form appears on preview page");

		final String shortFormName = "Short Form";
		final String longFormName = "Long Form";

		QualityVerify.verifyTrue(canAddShortForm,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(shortFormSearchString)
						.editPage(shortFormPageName)
						.addNinjaFormToDiviBuilder(shortFormName)
						.hasNinjaFormWithFormType(shortFormName),
				"User could not add a short form to the page");

		// Hit update after adding the form to the page
		app.findaEditPagePage().saveChanges();

		QualityVerify.verifyTrue(shortFormOnPreviewPage,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(shortFormSearchString)
						.previewPage(shortFormPageName)
						.hasOnlyShortFormAndNavigateBack(),
				"Page preview does not have a short form, or also has a long form");

		QualityVerify.verifyTrue(canAddLongForm,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(longFormSearchString)
						.editPage(longFormPageName)
						.addNinjaFormToDiviBuilder(longFormName)
						.hasNinjaFormWithFormType(longFormName),
				"User could not add a long form to the page");

		// Hit update after adding the form to the page
		app.findaEditPagePage().saveChanges();

		QualityVerify.verifyTrue(longFormOnPreviewPage,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle(longFormSearchString)
						.previewPage(longFormPageName)
						.hasOnlyLongFormAndNavigateBack(),
				"Page preview does not have a long form, or also has a short form");
	}

	/**
	 * Blog Post Capabilities
	 * Validate User can create, edit, delete, publish, and schedule posts
	 *
	 * 1. Login as User
	 * 2. Click on Add New menu option under Blog Posts : Verify User can add a blog post from the menu
	 * 3. Schedule post to be published tomorrow and click schedule : Verify that post appears in all posts and is scheduled for tomorrow
	 * 4. Go to blog posts page and click Add New : Verify User can add a post from the blog posts page
	 * 5. Click on an existing blog post and update the practice area : Verify User can edit the practice area
	 * 6. Go to blog posts page and click Trash link under existing post : Verify User can delete a post from blog posts page
	 * 7. Click on an existing blog post and click the move to trash link : Verify User can delete a post from blog post edit page
	 * 8. Go to blog posts page, select multiple posts, select move to trash bulk action and click apply : Verify User can bulk delete pages
	 *
	 * @author Matt Goodmanson
	 */
	protected void blogPostCapabilities(String role, String blogPostAddNewMenuOption, String blogPostAddNewButton,
										String bulkDeletePost1, String bulkDeletePost2) {
		final QualityCheck pageGoalCorrect = qualityTestCase.addCheck("Page goal is set to \"Legal Information\"");
		final QualityCheck pageLanguageCorrect = qualityTestCase.addCheck("Language is set to \"English\"");
		final QualityCheck pageTypeCorrect = qualityTestCase.addCheck("Page type is set to \"Blog Post\"");
		final QualityCheck canCreateBlogPostFromAddNewMenuOption = qualityTestCase
				.addCheck(role + " can add a new blog post from the Add New button in the left navigation menu");
		final QualityCheck scheduledPostHasCorrectPublishedDate = qualityTestCase
				.addCheck("Scheduled blog post has correct publish date");
		final QualityCheck canCreateBlogPostFromAddNewButton = qualityTestCase
				.addCheck(role + " can add a new blog post from the Add New Button on all blog posts");
		final QualityCheck canEditPracticeArea = qualityTestCase
				.addCheck(role + " can edit the practice area of an existing post");
		final QualityCheck canQuickEditStatus = qualityTestCase
				.addCheck(role + " can quick edit an existing post");
		final QualityCheck canDeleteBlogPostFromBlogPostsPage = qualityTestCase
				.addCheck(role + " can delete a blog post from the All Blog Posts page");
		final QualityCheck canDeleteBlogPostFromEditPage = qualityTestCase
				.addCheck(role + " can delete a blog post from the edit blog post page");
		final QualityCheck canBulkDeletePosts = qualityTestCase
				.addCheck(role + " can bulk delete blog posts");

		final String pageGoal = "Legal Information";
		final String pageLanguage = "English";
		final String pageType = "Blog Post";

		final String practiceArea = "Aviation";
		final String newStatus = "Draft";
		final Calendar publishDate = Calendar.getInstance();
		final Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
		publishDate.setTime(tomorrow);

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

		// add new blog post from menu
		QualityVerify.verifyTrue(canCreateBlogPostFromAddNewMenuOption,
				app.findaLeftNavigation()
						.blogPosts()
						.addNewBlogPost()
						.editBlogPostPublishDate(publishDate)
						.fillInTitle(blogPostAddNewMenuOption)
						.saveChanges()
						.goBackToPage(AbstractEditPage.DashboardMenu.BLOG_POSTS)
						.searchForText(blogPostAddNewMenuOption)
						.titleExists(blogPostAddNewMenuOption),
				role + " failed to add new blog post from left navigation menu");

		// verify scheduled post has correct publish date
		QualityVerify.verifyTrue(scheduledPostHasCorrectPublishedDate,
				app.findaBlogPage()
						.verifyPublishDateForPost(blogPostAddNewMenuOption, publishDate),
				"Scheduled post does not have the correct publish date");

		// add new blog post from add new button on blog posts page
		QualityVerify.verifyTrue(canCreateBlogPostFromAddNewButton,
				app.findaBlogPage()
						.addNewPage()
						.fillInTitle(blogPostAddNewButton)
						.saveChanges()
						.goBackToPage(AbstractEditPage.DashboardMenu.BLOG_POSTS)
						.searchForText(blogPostAddNewButton)
						.titleExists(blogPostAddNewButton),
				role + " failed to add new blog post from blog posts page");

		// edit practice area of exiting post
		QualityVerify.verifyEquals(canEditPracticeArea,
				app.findaBlogPage()
						.openEntryForEditByPageTitle(blogPostAddNewButton)
						.selectPracticeArea(practiceArea)
						.saveChanges()
						.getPracticeArea(),
				practiceArea,
				"Could not edit the practice area of a post");

		// quick edit existing post
		app.findaLeftNavigation()
				.blogPosts()
				.searchForText(blogPostAddNewButton)
				.hitQuickEditForTitle(blogPostAddNewButton)
				.hitQuickEditAndSelectStatus(newStatus);

		QualityVerify.verifyEquals(canQuickEditStatus,
				app.findaLeftNavigation()
						.blogPosts()
						.searchForText(blogPostAddNewButton)
						.getPostStatusForPost(blogPostAddNewButton),
				newStatus,
				"Failed to quick edit status to draft page");

		// delete blog post from all blog posts page
		QualityVerify.verifyFalse(canDeleteBlogPostFromBlogPostsPage,
				app.findaLeftNavigation()
						.blogPosts()
						.hitTrashLinkForTitle(blogPostAddNewMenuOption)
						.searchForText(blogPostAddNewMenuOption)
						.titleExists(blogPostAddNewMenuOption),
				"Could not delete blog post from all posts page");

		// delete blog post from blog post edit page
		QualityVerify.verifyFalse(canDeleteBlogPostFromEditPage,
				app.findaLeftNavigation()
						.blogPosts()
						.searchForText(blogPostAddNewButton)
						.openEntryForEditByPageTitle(blogPostAddNewButton)
						.hitTrashLink()
						.searchForText(blogPostAddNewButton)
						.titleExists(blogPostAddNewButton),
				"Could not delete blog post from edit page");

		// bulk delete blog posts
		QualityVerify.verifyFalse(canBulkDeletePosts,
				app.findaLeftNavigation()
						.blogPosts()
						.hitCheckBoxForTitle(bulkDeletePost1)
						.hitCheckBoxForTitle(bulkDeletePost2)
						.selectBulkAction("Move to Trash")
						.hitApplyBulkAction()
						.searchForText(bulkDeletePost1)
						.titleExists(bulkDeletePost1)
						|| app.findaBlogPage()
						.searchForText(bulkDeletePost2)
						.titleExists(bulkDeletePost2),
				"Could not bulk delete posts");
	}

	/**
	 * Blog Post Tags Capabilities
	 * <br>
	 * Test Description: Validate user can create, edit, and delete tags.
	 * <br>
	 * Test Steps:
	 * <ol>
	 * <li>Add a Blog Post tag from the tags screen and verify that it appears</li>
	 * <li>Add the tag to a blog post and verify that the tag appears in the tag sidebar widget</li>
	 * <li>Add a tag from the Blog Post Edit page and verify that it appears in the list of tags there</li>
	 * <li>Preview the page and verify that the new tag appears in the tag sidebar widget</li>
	 * <li>Save the blog post and return to the Blog Post Tags page</li>
	 * <li>Edit the name of the first tag (added from the Tags page) and update</li>
	 * <li>Verify that the edited tag name appears when previewing a blog post that the original tag was tagged to</li>
	 * <li>Return to the All Blog Post Tags page and delete one of the tags by clicking the delete sub option</li>
	 * <li>Delete the other tag by clicking edit then delete on the edit tag page</li>
	 * <li>Verify that both tags are no longer in the Tags page</li>
	 * <li>Then preview the page that had both tags and verify that the preview no longer shows the tabs</li>
	 * </ol>
	 * @param newTagOnTagsScreen The name of the tag to add from the Tags page
	 * @param newTagOnPostEditScreen The name of the tag to add from the Blog Post Edit page
	 * @param blogPostTitle The name of the blog post to add the tags to
	 */
	protected void blogPostTagCapabilities(String newTagOnTagsScreen, String newTagOnPostEditScreen, String blogPostTitle) {

		final QualityCheck newTagAddedFromTagsScreen = qualityTestCase.addCheck("Newly added tag from tag screen");
		final QualityCheck newAddedTagDisplaysOnPreview = qualityTestCase.addCheck("Newly created Tag from edit page found in preview page");
		final QualityCheck newTagAddedFromPostEditScreen= qualityTestCase.addCheck("Newly added tag from edit screen");
		final QualityCheck tagAddedFromPostEditScreenOnPreview = qualityTestCase.addCheck("Tag created on post edit displays on preview");
		final QualityCheck editedNewlyCreatedTag = qualityTestCase.addCheck("Newly created tag was edited ");
		final QualityCheck tagsAreDeletedFromList = qualityTestCase.addCheck("Newly created tags deleted from tag list");
		final QualityCheck deletedTagsNotInPreview = qualityTestCase.addCheck("Deleted tags do not display in preview ");

		final String editedTag = newTagOnTagsScreen + "_Edited";

		QualityVerify.verifyTrue(newTagAddedFromTagsScreen,
				app.findaLeftNavigation()
						.blogPostTags()
						.addNewTagWithName(newTagOnTagsScreen)
						.hasTagWithName(newTagOnTagsScreen),
				"Tag with title :" + newTagOnTagsScreen + "  not found in tag list");

		QualityVerify.verifyTrue(newAddedTagDisplaysOnPreview,
				app.findaLeftNavigation()
						.blogPosts()
						.editBlogPost(blogPostTitle)
						.selectTagInBlogPost(newTagOnTagsScreen)
						.saveChanges()
						.hitPreviewButton()
						.hasTagInSidebarAndClose(newTagOnTagsScreen),
				"Tag with title :" + newTagOnTagsScreen + "  not found in preview page");

		QualityVerify.verifyTrue(newTagAddedFromPostEditScreen,
				app.findaBlogPostEditPage()
						.addTagFromEditPage(newTagOnPostEditScreen)
						.isTagPresentInEditBlogPostTagList(newTagOnPostEditScreen),
				"Tag with title :" + newTagOnPostEditScreen + "not found in edit blog post tag list");

		QualityVerify.verifyTrue(tagAddedFromPostEditScreenOnPreview,
				app.findaBlogPostEditPage()
						.saveChanges()
						.hitPreviewButton()
						.hasTagInSidebarAndClose(newTagOnPostEditScreen),
				"Tag with title :" + newTagOnTagsScreen + "  not found in preview page");

		app.findaLeftNavigation()
				.blogPostTags()
				.clickOnTagName(newTagOnTagsScreen)
				.editTagNameField(editedTag)
				.hitUpdateButton();
		QualityVerify.verifyTrue(editedNewlyCreatedTag,
				app.findaLeftNavigation()
						.blogPosts()
						.editBlogPost(blogPostTitle)
						.hitPreviewButton()
						.hasTagInSidebarAndClose(editedTag),
				"Tag with title :" + editedTag + "not found in preview page");

		QualityVerify.verifyFalse(tagsAreDeletedFromList,
				app.findaLeftNavigation()
						.blogPostTags()
						.hitDeleteOptionForTag(newTagOnPostEditScreen)
						.clickOnTagName(editedTag)
						.hitDeleteButton()
						.hasTagWithName(editedTag)
						|| app.findaBlogTagsPage().hasTagWithName(newTagOnPostEditScreen),
				"Tags are not deleted");

		QualityVerify.verifyFalse(deletedTagsNotInPreview,
				app.findaLeftNavigation()
						.blogPosts()
						.editBlogPost(blogPostTitle)
						.hitPreviewButton()
						.hasTagInSidebarAndClose(newTagOnPostEditScreen) ||
						app.findaBlogPostEditPage()
								.hitPreviewButton()
								.hasTagInSidebarAndClose(editedTag),
				"Deleted tags are still in preview page");

		app.findaLeftNavigation().dashboard();
	}

	/**
	 * Test selecting an author for a blog post and verifying it displays correctly.
	 *
	 * Note: Must start on a new BlogPostEditPage before this method. Method begins assuming
	 * user is on a new blog post edit page (also must have set author, title already).
	 *
	 * Test Steps:
	 * <ol>
	 * <li>Select an author, Add a title, publish the post, and click view at it</li>
	 * <li>Verify that given authorFieldValue displays on post</li>
	 * <li>Repeat the same verification by clicking the permalink on the edit blog page</li>
	 * <li>Navigate to page "Blog"</li>
	 * <li>Look for new blog post on page and verify that the post has the given authorFieldValue</li>
	 * </ol>
	 *
	 * @param blogPostTitle The title of the blog post created in the test (created before these verifications)
	 * @param authorFieldValue What appears in the author field when previewing a post or on the Blog page
	 */
	protected void blogPostAuthorFunctionality(String blogPostTitle, String authorFieldValue) {
		final QualityCheck authorCorrectOnPreview =
				qualityTestCase.addCheck("New Post displays correct authorFieldValue \"" + authorFieldValue + "\" when opening from Preview");
		final QualityCheck authorCorrectFromPermalink =
				qualityTestCase.addCheck("New post displays correct authorFieldValue \"" + authorFieldValue + "\" when opening preview from permalink");
		final QualityCheck authorCorrectOnBlogPage =
				qualityTestCase.addCheck("Correct authorFieldValue \"" + authorFieldValue + "\" displays on Blog Page");

		// Must already be editing a blog post

		QualityVerify.verifyEquals(authorCorrectOnPreview,
				app.findaBlogPostEditPage()
						.hitPreviewButton()
						.getAuthorFieldAndClosePreview(),
				authorFieldValue,
				"Author field does not appear correctly when previewing blog post");

		QualityVerify.verifyEquals(authorCorrectFromPermalink,
				app.findaBlogPostEditPage()
						.saveChanges()
						.openPreviewWithPermalink()
						.getAuthorFieldAndNavigateBack(),
				authorFieldValue,
				"Author was not correct when viewing the page from permalink.");

		app.findaBlogPostEditPage().saveChanges();

		QualityVerify.verifyEquals(authorCorrectOnBlogPage,
				app.findaLeftNavigation()
						.pages()
						.searchForTitle("Blog")
						.previewPage("Blog")
						.getAuthorOfPostOnBlogPageAndNavigateBack(blogPostTitle),
				authorFieldValue,
				"Author of new blog post was not set properly");
	}

	/**
	 * Video Capabilities
	 * <br>
	 * Description: Validate that [Add Video from Brightcove Video Center] button works as expected (CMS-2940)
	 * <br>
	 * Test Steps:
	 * <ol>i
	 * <li>Login as [User]</li>
	 * <li>Add a single Video on BlogPost - Publish blog - Check Preview </li>
	 * <li>Add a Playlist on BlogPost - Publish blog - Check Preview</li>
	 * <li>Add a Video and Playlist on BlogPost, check Only Video - Publish blog - Check Preview</li>
	 * </ol>
	 * @param role The role that will be used when performing the verifications. Also used for filenames.
	 * @author Pavel Bychkou
	 */

	protected void videoCapabilities(String role) {

		final QualityCheck addVideoOnBlogpost =
				qualityTestCase.addCheck(role + " can add video on BlogPost");
		final QualityCheck addPlaylistOnBlogpost =
				qualityTestCase.addCheck(role + " can add playlist on BlogPost");
		final QualityCheck addOnlyVideoOnBlogpost =
				qualityTestCase.addCheck(role + " can add the only video out of the playlist on BlogPost");

		final String videoID = "5798975880001";
		final String video2ID = "5799953585001";
		final String playlistID = "5799146118001";

		final String blogPostVideoTitle = "Blog Post Single Video";
		final String blogPostPlaylistTitle = "Blog Post Playlist Video";
		final String blogPostOnlyVideoTitle = "Blog Post Only Video";

		//Check Add video functionality on BlogPost
		QualityVerify.verifyTrue(addVideoOnBlogpost,
				app.findaLeftNavigation()
						.blogPosts()
						.addNewBlogPost()
						.fillInTitle(blogPostVideoTitle)
						.clickAddVideo()
						.fillInAddVideoForm(null, videoID, false)
						.saveChanges()
						.openPreviewWithPermalink()
						.checkSingleVideoAndBack(),
				"The expected video is not displayed on BlogPost for " + role);

		QualityVerify.verifyTrue(addPlaylistOnBlogpost,
				app.findaLeftNavigation()
						.blogPosts()
						.addNewBlogPost()
						.fillInTitle(blogPostPlaylistTitle)
						.clickAddVideo()
						.fillInAddVideoForm(playlistID, null, false)
						.saveChanges()
						.openPreviewWithPermalink()
						.checkPlaylistAndBack(),
				"The expected playlist is not displayed on BlogPost for " + role);

		QualityVerify.verifyTrue(addOnlyVideoOnBlogpost,
				app.findaLeftNavigation()
						.blogPosts()
						.addNewBlogPost()
						.fillInTitle(blogPostOnlyVideoTitle)
						.clickAddVideo()
						.fillInAddVideoForm(playlistID, video2ID, true)
						.saveChanges()
						.openPreviewWithPermalink()
						.checkOnlyVideoAndBack(),
				"The only video is not displayed on BlogPost for " + role);
	}
}