package apim.ui.portal.pages.publish.apps;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;


public class EulaPage extends PublishPageBase {

	EulaPageObjects eulaPageObjects;
	String eulaName;

	public EulaPage() {
		eulaPageObjects = new EulaPageObjects();
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 60);
		PageFactory.initElements(factory, eulaPageObjects);

	}

	public EulaPage setEULAName(String eulaName) {
		click(eulaPageObjects.addApiEulaBtn);
		setInputField(eulaName, eulaPageObjects.eulaNameInp);

		return this;
	}

	public EulaPage setEULADescription(String eulaDescription) {
		switchToFrame("iframe");
		setInputField(eulaDescription, eulaPageObjects.eulaDescInp);
		switchToDefaultFrame();

		return this;
	}

	public String populateApiEula(String eulaName, String eulaDescription) {
		setEULAName(eulaName);
		setEULADescription(eulaDescription);

		return eulaName;
	}

	public void addApiEulaAndVerify(String eulaName, String eulaDescription) {
		populateApiEula(eulaName, eulaDescription);
		click(eulaPageObjects.createEulaBtn);

		// TODO EULA verification is not completed in previous framework. This need to
		// be
		// done.
	}

	private class EulaPageObjects {

		@CacheLookup
		@FindBy(xpath = "//div[@id='apieulas-component']//button[starts-with(@class,'add-apieula')]")
		public WebElement addApiEulaBtn;

		@CacheLookup
		@FindBy(xpath = "//div[@id='apieulas-component']//input[@id='name']")
		public WebElement eulaNameInp;

		@CacheLookup
		@FindBy(xpath = "//body[starts-with(@class,'textarea-block')]")
		public WebElement eulaDescInp;

		@CacheLookup
		@FindBy(xpath = "//div[@id='apieulas-component']//button[@type='submit']")
		public WebElement createEulaBtn;

		@CacheLookup
		@FindBy(xpath = "//div[@id='apieulas-component']//button[starts-with(@class,'cancel-button')]")
		public WebElement cancelEulaBtn;

		@FindBy(xpath = "//div[@id='apieulas-component']//a[@class='api-eula-label']")
		public List<WebElement> eulaList;
	}
}
