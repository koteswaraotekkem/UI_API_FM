package apim.ui.portal.pages.publish.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppsApiManagementPage extends PublishPageBase {
	PageObjects appsApiManagementPage;
	AjaxElementLocatorFactory factory;

	public AppsApiManagementPage() {
		appsApiManagementPage = new PageObjects();
		factory = new AjaxElementLocatorFactory(driver, 60);
		PageFactory.initElements(factory, appsApiManagementPage);
	}

	public boolean filterAndSelectApiByName(String apiName) {
		setInputField(apiName, appsApiManagementPage.apisFilterInp);
		click(appsApiManagementPage.searchAPIsBtn);

		By availableElements = By.xpath("//a[contains(@href, 'available-apis-')]/div/div[text()='" + apiName + "']");

		if (isTextExistsInTable(availableElements, portalPageBaseObjects.apisPageNumberDiv,
				portalPageBaseObjects.apisArrowRightLink)) {
			driver.findElement(availableElements).findElement(By.xpath("following::button[1]")).click();
			click(appsApiManagementPage.acceptTermsConditionsBtn);

			return true;
		}

		else {
			log.error("API with name - " + apiName + " is not found. Cancelling Application creation");
			click(publishPageObjects.cancelBtn);
			confirmDialog();
			return false;
		}

	}

	public String getSelectedApiName(String apiName) {
		By selectedAPIs = By.xpath("//a[contains(@href, 'selected-apis-')]/div/div[text()='" + apiName + "']");
		if (isElementDisplayed(selectedAPIs))
			return getElementText(selectedAPIs);
		return "Invalid Element Text";

	}

	public AppsAuthenticationPage clickNext() {
		click(publishPageObjects.nextBtn);
		return new AppsAuthenticationPage();
	}

	private class PageObjects {

		@CacheLookup
		@FindBy(xpath = "//input[@id='apis-filter-by']")
		public WebElement apisFilterInp;

		@CacheLookup
		@FindBy(xpath = "//input[@id='groups-filter-by']")
		public WebElement apiGroupsFilterInp;

		@CacheLookup
		@FindBy(xpath = "//li[@ref-id='apis-tab']/a[1]")
		public WebElement tabAPIs;

		@CacheLookup
		@FindBy(xpath = "//li[@ref-id='apigroups-tab']/a[1]")
		public WebElement tabAPIgroups;

		@CacheLookup
		@FindBy(xpath = "//input[@id='apis-search-button']")
		public WebElement searchAPIsBtn;

		@CacheLookup
		@FindBy(xpath = "//input[@id='groups-search-button']")
		public WebElement searchAPIGroupsBtn;

		@CacheLookup
		@FindBy(xpath = "//button[@id='select-api']")
		public WebElement acceptTermsConditionsBtn;

		// @CacheLookup
		@FindBy(xpath = "//input[@id='groups-search-button']")
		public WebElement cancelBtn;
	}

}
