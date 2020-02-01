package apim.ui.portal.pages.publish.apps;

import org.openqa.selenium.By;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppsOrganizationPage extends PublishPageBase {

	public boolean isOrganizationAvailable(String orgName) {
		By orgElement = By.xpath("//div[text()='" + orgName + "' and @id='orgsList']");
		if (isTextExistsInTable(orgElement, portalPageBaseObjects.orgsPageNumberDiv,
				portalPageBaseObjects.orgsArrowRightLink)) {
			driver.findElement(orgElement).findElement(By.xpath("following::button[1]")).click();
			return true;
		}
		else {
			log.error("Orgnaization with name - " + orgName + " is not found. Cancelling Application creation");
			click(publishPageObjects.cancelBtn);
			confirmDialog();
			return false;
		}
	}

	public AppsOrganizationPage selectOrganization(String orgName) {
		if (isOrganizationAvailable(orgName)) {
			return this;
		}
		return this;
	}

	public AppsInformationPage clickNext() {
		click(publishPageObjects.nextBtn);
		return new AppsInformationPage();
	}

}
