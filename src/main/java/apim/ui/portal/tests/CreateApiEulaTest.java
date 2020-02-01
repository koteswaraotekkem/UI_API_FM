package apim.ui.portal.tests;

import org.testng.annotations.Test;

import apim.ui.core.PortalTestBase;
import apim.ui.portal.pages.LoginPage;

public class CreateApiEulaTest extends PortalTestBase {

	@Test(priority = 1, description = "Login with Valid Credentials")
	public void createApiEula() throws Exception {

		LoginPage loginPage = new LoginPage();
		 loginPage.loginToPortal();
				}
}
