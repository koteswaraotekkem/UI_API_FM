package apim.ui.portal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import apim.ui.core.utils.HtmlOps;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortalPageBase extends HtmlOps {
	protected PortalPageBase.PortalPageBaseObjects portalPageBaseObjects;

	public PortalPageBase() {
		portalPageBaseObjects = new PortalPageBase.PortalPageBaseObjects();
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, DEFAULT_UIELEMENT_WAIT_TIME);
		PageFactory.initElements(factory, portalPageBaseObjects);
	}

	public void logout() {
		log.info("Clicking on User Menu");
		click(portalPageBaseObjects.userMenu);
		log.info("Clicking on logout");
		click(portalPageBaseObjects.logoutLnk);
		log.info("Logging out");
	}

	/**
	 * This method is to get total number of pages count
	 *
	 * @param pageElement
	 * @return Total Pages Count. Returns 0 if No Pages.
	 */
	public int getPageCount(WebElement pageElement) {
		if (isElementPresent(pageElement)) {
			String text = pageElement.getText().trim();
			String values[] = text.split(" ");
			return Integer.valueOf(values[3]);
		}

		return 0;
	}

	public void clickOrgsArrowRightLink() {
		click(portalPageBaseObjects.orgsArrowRightLink);
	}

	public void clickOrgsArrowLeftLink() {
		click(portalPageBaseObjects.orgsArrowLeftLink);
	}

	public void confirmDialog() {
		sleep(5);
		click(portalPageBaseObjects.confirmBtn);
	}

	public void cancelDialog() {
		sleep(5);
		click(portalPageBaseObjects.cancelBtn);
	}

	/**
	 * This method is used to find text presence in table. Works for pagination too
	 *
	 * @param textLocator
	 * @param pageNumber
	 * @param arrowRightLink
	 * @return True if text is present in Table. Else false
	 */
	public boolean isTextExistsInTable(By textLocator, WebElement pageNumber, WebElement arrowRightLink) {
		int pageCount = getPageCount(pageNumber);

		if (pageCount == 0) {
			log.error("There are no rows in table");
			return false;
		}

		for (int i = 1; i <= pageCount; i++) {
			if (isElementExists(textLocator)) {
				return true;
			} else if (i < pageCount)
				click(arrowRightLink);
		}

		return false;
	}

	protected class PortalPageBaseObjects {
		@FindBy(xpath = "//button[@data-phoenix-test='header-requests-dropdown-target']")
		public WebElement requests;

		@FindBy(xpath = "//button[@data-phoenix-test='header-services-dropdown-target']")
		public WebElement services;

		@FindBy(xpath = "//button[@data-phoenix-test='header-settings-dropdown-target']")
		public WebElement settings;

		@FindBy(xpath = "//button[@data-phoenix-test='user-menu-target']")
		public WebElement userMenu;

		@FindBy(xpath = "//button[@id='confirm-dirty-form-confirm']")
		public WebElement confirmBtn;

		@FindBy(xpath = "//button[@id='confirm-dirty-form-cancel']")
		public WebElement cancelBtn;

		@FindBy(xpath = "//a[@data-phoenix-test='user-menu-logout']")
		public WebElement logoutLnk;

		// Table related objects here
		// Organization table related objects

		@FindBy(xpath = "//div[contains(@class,'pagination-orgs')]//i[@class='icon-portal-arrow-left']")
		public WebElement orgsArrowLeftLink;

		@FindBy(xpath = "//div[contains(@class,'pagination-orgs')]//i[@class='icon-portal-arrow-left2']")
		public WebElement orgsArrowLeft2Link;

		@FindBy(xpath = "//div[contains(@class,'pagination-orgs')]//i[@class='icon-portal-arrow-right']")
		public WebElement orgsArrowRightLink;

		@FindBy(xpath = "//div[contains(@class,'pagination-orgs')]//i[@class='icon-portal-arrow-right2']")
		public WebElement orgsArrowRight2Link;

		@FindBy(xpath = "//div[contains(@class,'page-number-orgs')]")
		public WebElement orgsPageNumberDiv;

		// APIs table related objects

		@FindBy(xpath = "//div[contains(@class,'page-number-apis')]")
		public WebElement apisPageNumberDiv;

		@FindBy(xpath = "//div[contains(@class,'pagination-apis')]//i[@class='icon-portal-arrow-right']")
		public WebElement apisArrowRightLink;

		// Groups table related objects

		@FindBy(xpath = "//div[contains(@class,'page-number-groups')]")
		public WebElement groupsPageNumberDiv;


		// Common datagrid related objects

		@FindBy(xpath = "//div[contains(@class,'datagrid-controls-footer')]//i[@class='icon-portal-arrow-left']")
		public WebElement arrowLeftLink;

		@FindBy(xpath = "//div[contains(@class,'datagrid-controls-footer')]//i[@class='icon-portal-arrow-left2']")
		public WebElement arrowLeft2Link;

		@FindBy(xpath = "//div[contains(@class,'datagrid-controls-footer')]//i[@class='icon-portal-arrow-right']")
		public WebElement arrowRightLink;

		@FindBy(xpath = "//div[contains(@class,'datagrid-controls-footer')]//i[@class='icon-portal-arrow-right2']")
		public WebElement arrowRight2Link;

		@FindBy(xpath = "//div[contains(@class,'datagrid-page-number')]")
		public WebElement pageNumberDiv;
	}
}
