package apim.ui.portal.pages.publish.apps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import apim.ui.portal.pages.PortalPageBase;
import apim.ui.portal.pages.publish.apps.AppsPage;

public class PublishPageBase extends PortalPageBase {

	public PublishPageObjects publishPageObjects;

	public PublishPageBase() {
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 60);
		publishPageObjects = new PublishPageObjects();
		PageFactory.initElements(factory, publishPageObjects);
	}

	public class PublishPageObjects {
		@FindBy(xpath = "//ul[starts-with(@class,'HeaderNav__Links')]//a[@href='/publish/apis']")
		private WebElement apisLink;

		@FindBy(xpath = "//ul[starts-with(@class,'HeaderNav__Links')]//a[@href='/admin/apis/api-eulas']")
		private WebElement eulasLink;

		@CacheLookup
		@FindBy(xpath = "//a[@href='/admin/applications']")
		public WebElement appsLink;
		
		//TODO Need to manage these elements
		@CacheLookup
		@FindBy(xpath = "//div[@class='tab-pane active']//li[@class='next']//a[text()='Next']")
		public WebElement nextBtn;
		
		@CacheLookup
		@FindBy(xpath = "//div[@class='tab-pane active']//li[@class='previous']//a[text()='Previous']")
		public WebElement previousBtn;
		
		@CacheLookup
		@FindBy(xpath = "//a[text()='Cancel']")
		public WebElement cancelBtn;
		
		@CacheLookup
		@FindBy(xpath = "//button[@type='submit']")
		public WebElement createBtn;
	}

	
	public EulaPage openEulaPage() {
		click(publishPageObjects.eulasLink);
		return new EulaPage();
	}

	public AppsPage openAppsPage() {
		click(publishPageObjects.appsLink);
		return new AppsPage();
	}

}
