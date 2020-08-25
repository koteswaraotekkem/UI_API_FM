package apim.ui.portal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.annotations.Optional;

import apim.ui.core.utils.HtmlOps;
import apim.ui.portal.utils.EnvVars;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginPage extends HtmlOps {

	private static PageObjects loginPage;
	private static String userName = "";
	private static String passWord = "";

	public LoginPage() {
		loginPage = new PageObjects();
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 60);
		PageFactory.initElements(factory, loginPage);
	}

	public DashboardPage loginToPortal() {

		driver.get(EnvVars.T1_PORTAL_URL);
		userName = EnvVars.T1_USERNAME;
		passWord = EnvVars.T1_PASSWORD;

		loginWithCredentials(userName, passWord);
		return new DashboardPage();
	}

	public DashboardPage loginToPortal(String url, @Optional String userName, @Optional String passWord) {

		driver.get(url);

		userName = userName == null ? EnvVars.T1_USERNAME : userName;
		passWord = passWord == null ? EnvVars.T1_PASSWORD : passWord;

		return loginWithCredentials(userName, passWord);
	}

	public DashboardPage loginWithCredentials(String userName, String passWord) {

		log.info("Entering User Name");
		click(loginPage.myAccLink);
		setInputField(userName, loginPage.usernameField);

		log.info("Entering Password");
		setInputField(passWord, loginPage.passwordField);

		log.info("Clicking on 'Sign in' button");
		click(loginPage.loginBtn);
		return new DashboardPage();

	}

	private static class PageObjects {

		@FindBy(id = "email")
		public WebElement usernameField;
		
		@FindBy(linkText = "My Account")
		public WebElement myAccLink;
		

		@FindBy(id = "password")
		public WebElement passwordField;

		@FindBy(xpath = "/html/body/div[1]/div/div/div/div[1]/div/form/div[3]/button")
		public WebElement loginBtn;

	}
}
