package apim.ui.portal.tests;

import java.util.Random;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apim.ui.core.PortalTestBase;
import apim.ui.portal.pages.LoginPage;
import apim.ui.portal.pages.publish.apps.AppsApiManagementPage;
import apim.ui.portal.pages.publish.apps.AppsPage;

public class PublishApplicationsTest extends PortalTestBase {

	public Integer stepCounter;
	public String APIName = "Test API Test 3";
	public String redirectURL = "https://tssg_portal.dev.ca.com/admin/api-groups";
	public String scope = "Test API Test 3";
	public String orgName = "Sample Org";
	String randomNum = String.valueOf(new Random().nextInt(1000));
	String AppName = "AppTest" + randomNum;

	@BeforeMethod
	public void beforeMethod() {
		stepCounter = 1;
	}

	@Test(priority = 1, description = "Add a Application")
	public void addApplication() throws Exception {
		

		STEP_INFO((stepCounter++).toString(), "Login as Admin User", "User should login Ssuccesfully");
		LoginPage loginPage = new LoginPage();
		
		STEP_INFO((stepCounter++).toString(), "Open Publish page", "");
		AppsPage appsPage = loginPage.loginToPortal().openPublishPage().openAppsPage();
		
		STEP_INFO((stepCounter++).toString(), "Add application", "");
		AppsApiManagementPage appsApiManagementPage = appsPage.clickAddApplication()
				.selectOrganization(orgName).clickNext().setAppName(AppName).setAppDescrioption("test" + randomNum)
				.clickNext();
		
		STEP_INFO((stepCounter++).toString(), "Filter " +APIName+ "apis from the available list", " ");
		Assert.assertTrue(appsApiManagementPage.filterAndSelectApiByName(APIName), String.format("Given API '%s' is not found in the Available APis list ", APIName));
		
		STEP_INFO((stepCounter++).toString(), "Verify "+APIName +"in Selected APIs List", APIName+ " Should be displyed in List");
		Assert.assertEquals(appsApiManagementPage.getSelectedApiName(APIName), APIName,
				"Api is not displayed at Selected apis Section");
		
		STEP_INFO((stepCounter++).toString(), "Set Auth Details and click create" , "Application should be created");
		appsApiManagementPage.clickNext().setRedirectURL(redirectURL).setScope(scope).create();
		
		STEP_INFO((stepCounter++).toString(), "Application should be added " , "Application should be created added to the Apps main page");
		//Assert.assertTrue(appsPage.isApplicationExists(AppName), "Application is Not added");
		
		Assert.fail();

	}

}
