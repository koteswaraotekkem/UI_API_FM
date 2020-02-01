package apim.ui.portal.pages.publish.apps;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AppsAuthenticationPage extends PublishPageBase {
	PageObjects  appsAuthPage;
	AjaxElementLocatorFactory factory;

	public AppsAuthenticationPage() {
		appsAuthPage = new PageObjects();
		factory = new AjaxElementLocatorFactory(driver, 60);
		PageFactory.initElements(factory, appsAuthPage);
	}
	
	
	public AppsAuthenticationPage setRedirectURL(String description) {
		setInputField(description, appsAuthPage.redirectUrlInp);
		return this;
	}
	
	public AppsAuthenticationPage setScope(String description) {
		setInputField(description, appsAuthPage.acopeInp);
		return this;
	}
	
	public AppsPage create() {
		click(publishPageObjects.createBtn);
		return new AppsPage();
	}

	
	public enum visibilityType{
		NONE, PUBLIC, PRIVATE;
	}
	
	private class PageObjects {
		@FindBy(xpath = "//textarea[@id='oauth-callback-url']")
		WebElement redirectUrlInp;

		@FindBy(xpath = "//textarea[@id='oauth-scope']")
		WebElement acopeInp;
	}
}
