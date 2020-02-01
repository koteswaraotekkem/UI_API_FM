package apim.ui.portal.pages.publish.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import apim.ui.portal.pages.PortalPageBase;

public class AppsPage extends PortalPageBase {
	private AppsPageObjects appsPageObjects;

	public AppsPage() {
		appsPageObjects = new AppsPageObjects();
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 60);
		PageFactory.initElements(factory, appsPageObjects);

	}

	public AppsOrganizationPage clickAddApplication() {
		click(appsPageObjects.addApplicationBtn);
		return new AppsOrganizationPage();

	}

	public void isHowDoIAddAppPopUpDisplayed() {
		isElementPresent(appsPageObjects.howDoIAddAppPopUp);
		if (isElementEnabled(appsPageObjects.PlayVideoLink))
		appsPageObjects.PlayVideoLink.click();
	}
	
	public boolean isApplicationExists(String appName) {

		By availableElements = By.xpath("//td[text()='"+ appName +"']");

		if (isTextExistsInTable(availableElements, appsPageObjects.appsPageCount,
				appsPageObjects.appsArrowRightLink)) {

			return true;
		}
		return false;
	}

	private class AppsPageObjects {

		@FindBy(xpath = "//button[text()='Add Application']")
		public WebElement addApplicationBtn;

		@FindBy(xpath = "//div[@class='trial-video-sidebar']")
		public WebElement howDoIAddAppPopUp;

		@FindBy(xpath = "//a[contains(text(), 'Play Video')]")
		public WebElement PlayVideoLink;

		@FindBy(xpath = "//a[contains(text(), '//div[text()='Sample Org']')]")
		public WebElement sampleOrg;

		@FindBy(xpath = "//div[text()='Sample Org']/following::button[1]")
		public WebElement selectSampleOrg;
		
		@FindBy(xpath = "//div[contains(@class, 'apieula-page-')]")
		public WebElement appsPageCount;
		
		@FindBy(xpath = "(//div[contains(@id,'applications-component')]//i[@class='icon-portal-arrow-right'])[2]")
		public WebElement appsArrowRightLink;

	}

}
