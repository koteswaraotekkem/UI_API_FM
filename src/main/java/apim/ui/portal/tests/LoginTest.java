package apim.ui.portal.tests;

import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import apim.ui.core.PortalTestBase;
import apim.ui.portal.pages.DashboardPage;
import apim.ui.portal.pages.LoginPage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginTest extends PortalTestBase {

	@Test(priority = 0, description = "Login with Valid Credentials", enabled=true)
	public void loginToPortal() throws Exception {
		log.info("Login Test");
		LoginPage loginPage = new LoginPage();
		DashboardPage dashPage = loginPage.loginToPortal();
		driver.findElement(RelativeLocator.withTagName("input").)
		

	}
@Test(priority = 1)
public void loginApp() {
	Assert.assertTrue(true, "This test expected to failed");

}

@Test(priority = 2)
public void loginWithFail() {
	Assert.assertFalse(true, "This test expected to failed");

}
	
	@AfterClass
	public void tearDown() {

	}
}
