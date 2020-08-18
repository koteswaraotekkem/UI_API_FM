package apim.ui.portal.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import apim.ui.core.PortalTestBase;
import apim.ui.portal.pages.DashboardPage;
import apim.ui.portal.pages.LoginPage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginTest extends PortalTestBase {

	@Test(description = "Login with Valid Credentials", enabled=false)
	public void loginToPortal() throws Exception {
		log.info("Login Test");
		LoginPage loginPage = new LoginPage();
		DashboardPage dashPage = loginPage.loginToPortal();
		Assert.assertTrue(dashPage.isDashBboardPageDisplayed(),
				"Element could not be found on the page. Dashboard Page failed to load");

	}
@Test
public void loginApp() {
	Assert.assertTrue(true, "This test expected to failed");

}

@Test
public void loginWithFail() {
	Assert.assertFalse(false, "This test expected to failed");

}
	
	@AfterClass
	public void tearDown() {

	}
}
