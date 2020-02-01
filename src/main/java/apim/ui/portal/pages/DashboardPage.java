package apim.ui.portal.pages;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import apim.ui.portal.pages.publish.apps.PublishPageBase;



public class DashboardPage extends PortalPageBase {

	// Tile in Dashboard
	public static final String TILE_PUBLISH = "Publish";
	public static final String TILE_DEVELOP = "Develop";
	public static final String TILE_ADMINISTRATION = "Administration";
	public static final String TILE_APPEARANCE = "Appearance";
	public static final String TILE_MONITOR = "Monitor";
	public static final String TILE_CONTENT_MANAGEMENT = "Content Management";
	public static final String TILE_HOME = "Home";
	DashBoardPageObjects dashBoardPageObjects;
	JavascriptExecutor js;

	public DashboardPage() {
		dashBoardPageObjects = new DashBoardPageObjects();
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, DEFAULT_UIELEMENT_WAIT_TIME);
		PageFactory.initElements(factory, dashBoardPageObjects);
		js = (JavascriptExecutor) driver;
	}


	public void logout() {
		try {
			click(dashBoardPageObjects.userMenu);
		} catch (ElementClickInterceptedException e) {
			js.executeScript("arguments[0].click();", dashBoardPageObjects.userMenu);
		}
		click(dashBoardPageObjects.logoutLink);
	}

	/**
	 * @return True if Settings menu displayed on Dashboard page
	 */
	public boolean isDashBboardPageDisplayed() {
		return isElementPresent(dashBoardPageObjects.settingsMenu);
	}

	private class DashBoardPageObjects {

		@FindBy(xpath = "//button[@data-phoenix-test='header-settings-dropdown-target']")
		public WebElement settingsMenu;

		@FindBy(xpath = "//div[@id='phoenix-header']//button[@data-phoenix-test='user-menu-target']//span[1]")
		public WebElement userMenu;

		@FindBy(xpath = "//div[@id='phoenix-header']//button[@data-phoenix-test='header-services-dropdown-target']//*[local-name()='svg']")
		public WebElement headerServicesMenu;

		@FindBy(xpath = "//div[starts-with(@class,'HeaderServicesPanel')]//img[@alt='Publish Icon']")
		public WebElement publishServiceOption;

		@FindBy(xpath = "//div[starts-with(@class,'HeaderServicesPanel')]//img[@alt='Monitor Icon']")
		public WebElement monitorServiceOption;

		@CacheLookup
		@FindBy(xpath = "//ul[starts-with(@class,'HeaderNav__Links')]//a[@href='/publish/apis']")
		public WebElement apisLink;

		@CacheLookup
		@FindBy(xpath = "//a[@href='/admin/applications']")
		public WebElement appsLink;

		@CacheLookup
		@FindBy(xpath = "//ul[starts-with(@class,'HeaderNav__Links')]//a[@href='/admin/apis/api-eulas']")
		public WebElement eulasLink;

		@CacheLookup
		@FindBy(xpath = "//a[@data-phoenix-test='dashboard-tile-Publish']//h3")
		private WebElement publishTile;

		@CacheLookup
		@FindBy(xpath = "//a[@data-phoenix-test='dashboard-tile-Develop']//h3")
		private WebElement developTile;

		@CacheLookup
		@FindBy(xpath = "//a[@data-phoenix-test='dashboard-tile-Administration']//h3")
		private WebElement administrationTile;

		@CacheLookup
		@FindBy(xpath = "//a[@data-phoenix-test='dashboard-tile-Appearance']//h3")
		private WebElement appearanceTile;

		@CacheLookup
		@FindBy(xpath = "//a[@data-phoenix-test='dashboard-tile-Monitor']//h3")
		private WebElement monitorTile;

		@CacheLookup
		@FindBy(xpath = "//a[@data-phoenix-test='dashboard-tile-Content Management']//h3")
		private WebElement contentManagementTile;

		@CacheLookup
		@FindBy(xpath = "//a[@data-phoenix-test='dashboard-tile-Home']//h3")
		private WebElement homeTile;

		@FindBy(xpath = "//a[@data-phoenix-test='user-menu-logout']//span[text()='Logout']")
		private WebElement logoutLink;
	}

	public PublishPageBase openPublishPage() {
		// TODO Auto-generated method stub
		return null;
	}
}
