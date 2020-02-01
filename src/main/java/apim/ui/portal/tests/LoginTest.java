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

	@Test(description = "Login with Valid Credentials")
	public void loginToPortal() throws Exception {
		log.info("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
		LoginPage loginPage = new LoginPage();
		DashboardPage dashPage = loginPage.loginToPortal();
		Assert.assertTrue(dashPage.isDashBboardPageDisplayed(),
				"Element could not be found on the page. Dashboard Page failed to load");

	}

	@AfterClass
	public void tearDown() {

	}
}
