package apim.ui.portal.pages.publish.apps;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppsInformationPage extends PublishPageBase {
	PageObjects appsInformationPage;
	AjaxElementLocatorFactory factory;

	public AppsInformationPage() {
		appsInformationPage = new PageObjects();
		factory = new AjaxElementLocatorFactory(driver, 60);
		PageFactory.initElements(factory, appsInformationPage);
	}

	public AppsInformationPage setAppName(String name) {
		setInputField(name, appsInformationPage.appName);
		return this;
	}

	public AppsInformationPage setAppDescrioption(String description) {
		setInputField(description, appsInformationPage.appDescription);
		return this;
	}

	public AppsApiManagementPage clickNext() {
		log.info("Clicking on Next Button on the Apps InformationPage Page ");
		click(publishPageObjects.nextBtn);
		return new AppsApiManagementPage();
	}

	public AppsInformationPage previous() {
		log.info("Clicking on Previous Button on the Apps InformationPage Page ");
		click(publishPageObjects.previousBtn);
		return new AppsInformationPage();
	}

	public AppsPage cancel() {
		log.info("Clicking on Cancel Buttonon the Apps InformationPage Page");
		click(publishPageObjects.cancelBtn);
		return new AppsPage();
	}

	private class PageObjects {
		@CacheLookup
		@FindBy(xpath = "//input[@id='app-name']")
		public WebElement appName;

		@CacheLookup
		@FindBy(xpath = "//textarea[@id='public-description']")
		public WebElement appDescription;

	}

}
