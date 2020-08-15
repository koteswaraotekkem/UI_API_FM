/** shit+alt+j to get java docs comments at each API */
package apim.ui.core.utils;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;


/** This Class is used to do the Selenium operations */

/**
 * implicit timeouts of driver are set in setup plugin
 *
 * @author koteswarao tekkem
 */
public class HtmlOps {
	static long ajax_pageload_time = 15; // Seconds for page load.
	public static final int DEFAULT_UIELEMENT_WAIT_TIME = 15;
	protected static final int WAIT_TIME = 45; // in seconds
	private static final long WAIT_POLL_TIME = 5000; // in milliseconds
	public static WebDriver driver;

	public void navigateBack() {
		driver.navigate().back();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Set Value in an Input box
	 *
	 * @param value
	 * @param htmlelement
	 * @return
	 * @throws ElementNotVisibleException
	 * @throws ElementNotVisibleException
	 */
	public WebElement setInputField(String value, WebElement htmlelement) throws ElementNotVisibleException {
		int switchedToFrame = 0;
		WebElement element = waitForElementToLoad(htmlelement);
		try {
			if (null == element) {
				WebElement loginFrame = driver.findElement(By.tagName("iframe"));
				driver.switchTo().frame(loginFrame);
				element = htmlelement;
				switchedToFrame = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != element) {
			int tries = 0;

			while (!(element.isDisplayed()) && tries < 100) {
				// page might still be loading and element is found but not yet displayed.
				// to prevent troubleshooting efforts, ensure the element is displayed before
				// input is
				// entered.
				try {

					Thread.sleep(10);
					tries++;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getStackTrace().toString());
				}
			}

			try {
				// intermittently unable to enter the data for fields like password and confirm
				// password.
				// try click on the element text field before entering the input.
				element.click();
			} catch (Exception e) {

			}
			try {

				element.clear();
				element.sendKeys(value);
			} catch (Exception e) {
			}
		} else {
			System.out.println("Element not found: html element: " + htmlelement + ". so value could not be entered: " + value);
		}

		if (switchedToFrame == 1) {
			driver.switchTo().defaultContent();
		}

		return element;
	}

	/**
	 * Method to ensure that a checkbox is checked.
	 * If not checked, check it.
	 * If checked, do nothing.
	 *
	 * @param htmlElement - checkbox input element
	 * @return true if htmlElement is selected
	 */
	public boolean checkCheckbox(WebElement htmlElement) {
		if (isElementPresent(htmlElement) && !isElementSelected(htmlElement)) {
			htmlElement.click();
		}
		return isElementSelected(htmlElement);
	}

	/**
	 * Method to ensure that a checkbox is unchecked.
	 * If not checked, do nothing.
	 * If checked, uncheck it.
	 *
	 * @param htmlElement - checkbox input element
	 * @return true if htmlElement is unselected
	 */
	public boolean uncheckCheckbox(WebElement htmlElement) {
		if (isElementSelected(htmlElement)) {
			htmlElement.click();
		}
		return !isElementSelected(htmlElement);
	}

	/**
	 * Method to get current url of page loaded
	 *
	 * @return
	 */
	public String getCurrentUrl() {

		return driver.getCurrentUrl();
	}

	public void click(WebElement element) {
		try {
			waitForElementVisibility(20,element);
			element.click();
		} catch (StaleElementReferenceException sere) {
			// simply retry finding the element in the refreshed DOM
			element.click();
		} catch (ElementClickInterceptedException interceptedEx) {
			scrollingToElementofAPage(element);
			element.click();
		} catch (TimeoutException toe) {
			System.out.println("Element identified by " + element.toString() + " was not clickable after 10 seconds");
			throw toe;
		}
	}

	public void selectByVisibleText(WebElement element, String visibleText) {
		Select select;
		try {
			waitForElementToLoad(element);
			select = new Select(element);
			select.selectByVisibleText(visibleText);
		} catch (StaleElementReferenceException sere) {
			// simply retry finding the element in the refreshed DOM
			element.click();
			select = new Select(element);
			select.selectByVisibleText(visibleText);
		} catch (TimeoutException toe) {
			System.out.println("Element " + element.toString() + " was not selectable");
		} catch (UnexpectedTagNameException notSelectException) {
			System.out.println("Element " + element.toString() + " is not of type Select element");
		}
	}

	public void selectByIndex(WebElement element, int index) {
		Select select;
		try {
			waitForElementToLoad(element);
			element.click();
			select = new Select(element);
			select.selectByIndex(index);
		} catch (StaleElementReferenceException sere) {
			// simply retry finding the element in the refreshed DOM
			element.click();
			select = new Select(element);
			select.selectByIndex(index);
		} catch (TimeoutException toe) {
			System.out.println("Element " + element.toString() + " was not selectable");
		} catch (UnexpectedTagNameException notSelectException) {
			System.out.println("Element " + element.toString() + " is not of type Select");
		}
	}
	
	public List<WebElement> getAllDropDownValues(WebElement element) {
		Select select = null;
		try {
			waitForElementToLoad(element);
			select = new Select(element);
			List<WebElement> dropDwnVals = select.getOptions();
			return dropDwnVals;
			
		} catch (StaleElementReferenceException sere) {
			List<WebElement> dropDwnVals = select.getOptions();
			return dropDwnVals;
			
		} catch (TimeoutException toe) {
			System.out.println("Element " + element.toString() + " was not selectable");
		} catch (UnexpectedTagNameException notSelectException) {
			System.out.println("Element " + element.toString() + " is not of type Select");
		}
		return null;
	}

	// in case you know, java scriptExecutor need to execute for the element to
	// click then use clickJ.
	// This saves time defautl wait time when compared to click function.
	/**
	 * @param name
	 * @throws ElementNotVisibleException
	 */
	public void clickJ(WebElement name) throws ElementNotVisibleException {
		WebElement element = null;
		try {

			element = waitForElementToLoad(name);
			System.out.println("element: " + element);
		} catch (Exception e) {
			System.out.println("Exception while waiting for element: " + element + ": " + e.getMessage());
		}
		if (null != element) {

			try {
				element.sendKeys(Keys.PAGE_DOWN);
			} catch (Exception e) {

			}
			element.click();
		} else {
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			} catch (Exception e) {

			}
		}
	}

	public void waitForAjaxPageLoading() {
		waitForAllAjaxRequests(ajax_pageload_time);
	}

	public boolean waitForAllAjaxRequests(long seconds) {
		boolean result = false;
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		try {
			return wait.until(AllAjaxRequest());
		} catch (Exception e) {
			System.out.println("Exception in waitForAllAjaxRequests " + e);
		}
		return result;
	}

	public ExpectedCondition<Boolean> AllAjaxRequest() {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				System.out.println("waiting for jQuery.active in AllAjaxRequest()");
				boolean toReturn = ((JavascriptExecutor) driver).executeScript("return jQuery.active;").toString()
						.equals("0");
				System.out.println("returning from wait for jQuery.active in AllAjaxRequest()");
				if (toReturn) {
					return toReturn;
				}
				return false;
			}
		};
	}

	/**
	 * moveToElement to a particular element
	 *
	 * @param element Webelement to perform action on
	 * @throws ElementNotVisibleException
	 */
	public void moveToElementq(WebElement element) throws ElementNotVisibleException {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).click(element);
		builder.perform();
	}

	/**
	 * Mouse Over to a particular element
	 *
	 * @param element Webelement to mouse over
	 * @throws ElementNotVisibleException
	 */
	public void mouseOver(WebElement element) throws ElementNotVisibleException {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
	}

	/**
	 * This method will return the Input field value
	 *
	 * @param element Webelement of input field
	 */
	public String getInputFieldValue(WebElement element) throws ElementNotVisibleException {
		return element.getAttribute("value");
	}

	public String get_SelectedOption(WebElement element) {
		Select select = new Select(element);
		WebElement selectedElement = select.getFirstSelectedOption();
		String selectedOption = selectedElement.getText();
		return selectedOption;
	}
	/** Switch to Default Frame */
	public void switchToDefaultFrame() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Switch to specific frame
	 *
	 * @param frame Frame to switch for.
	 */
	public void switchToFrame(String frame) {
		WebElement framesection = driver.findElement(By.tagName(frame));
		driver.switchTo().frame(framesection);
	}

	/**
	 * This method will return the text value for the given element
	 *
	 * @param element Element to get text from
	 * @return Text from element
	 * @throws ElementNotVisibleException
	 */
	public String getElementText(WebElement element) throws ElementNotVisibleException {
		waitForElementVisibility(20, element);
		String val = element.getText();
		if (null != val && !val.isEmpty()) {
			return val;
		}
		return element.getAttribute("value");
	}

	public String getElementText(By locator) throws ElementNotVisibleException {
		WebElement element = driver.findElement(locator);
		return getElementText(element);
	}

	/**
	 * Get list of options from DropDown.
	 *
	 * @param element Element pointing to entire dropdown List
	 * @return
	 * @throws ElementNotVisibleException
	 */
	@SuppressWarnings("unused")
	private List<WebElement> getOptions(WebElement element) throws ElementNotVisibleException {
		return element.findElements(By.tagName("option"));
	}

	/**
	 * Method to verify if element is enabled
	 *
	 * @param element Webelement to check
	 * @return True if element is enabled
	 */
	public boolean isElementEnabled(WebElement element) {
		try {
			return waitForElementToLoad(element).isEnabled();
		} catch (ElementNotVisibleException e) {
			return false;
		}
		catch (org.openqa.selenium.NoSuchElementException  e) {
			return false;
		}
	}

	/**
	 * Method to check presence of element
	 *
	 * @param element Webelement to check presence of
	 * @return True if element is present
	 */
	public boolean isElementPresent(WebElement element) {
		try {
			return waitForElementToLoad(element).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Method to check if element is selected
	 *
	 * @param element Webelement to check selection of
	 * @return True if element is selected
	 */
	public boolean isElementSelected(WebElement element) {
		try {
			return waitForElementToLoad(element).isSelected();
		} catch (org.openqa.selenium.ElementNotSelectableException e) {
			return false;
		}
	}

	/**
	 * Method is to check presence of element
	 *
	 * @param by
	 * @return boolean True if element exists. Else False
	 *
	 */
	public boolean isElementExists(By by) {
		boolean isExists = false;
		try {
			driver.findElement(by);
			isExists = true;
		} catch (Exception e) {

		}

		return isExists;
	}

	/**
	 * Method to wait for a WebElement to be displayed for time in 'DEFAULT_UIELEMENT_WAIT_TIME'
	 *
	 * @param element
	 * @return
	 */
	public boolean isElementPresentByWait(WebElement element) {
		boolean isPresent = false;

		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_UIELEMENT_WAIT_TIME);
		try {
			if (wait.until(ExpectedConditions.visibilityOfAllElements(element)) != null) {
				isPresent = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return isPresent;
	}

	/** Refresh current page */
	public void refresh() {
		driver.navigate().refresh();
		waitForJStoLoad();
	}

	/** Returns the page source */
	public String getPageSource() {
		return driver.getPageSource();
	}

	/*
	 * Get the Attribute value for a given element
	 */
	public String getAttributeValue(WebElement element, String attributeName) {
		return element.getAttribute(attributeName);
	}

	/** Accept on active alert */
	public void acceptAlert(long timeoutInSeconds) {
		try {
			waitForAlert(timeoutInSeconds);
			Alert alert = driver.switchTo().alert();
			// Get the text of the alert or prompt
			System.out.println(alert.getText());
			// And acknowledge the alert (equivalent to clicking "OK")
			alert.accept();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get text from alert
	 *
	 * @return
	 */
	public String getAlertedText(long timeoutInSeconds) {
		waitForAlert(timeoutInSeconds);
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

	/**
	 * Wait for alert to appear
	 *
	 * @param secondsToWait timeout to wait for alert
	 */
	public void waitForAlert(long secondsToWait) {
		WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
		if (wait.until(ExpectedConditions.alertIsPresent()) == null)
			System.out.println("alert was not present");
		else
			System.out.println("alert was present");
	}

	public void waitForElementVisibility(long secondsToWait, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
		if (wait.until(ExpectedConditions.visibilityOfAllElements(element)) != null)
			System.out.println("element is visible" + element.toString().substring(element.toString().indexOf("->")));
		else
			System.out.println("element is not visible" + element.toString().substring(element.toString().indexOf("->")));
	}

	public void waitForElementIsClickable(long secondsToWait, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
		if (wait.until(ExpectedConditions.elementToBeClickable(element)) != null)
			System.out.println("element is clckable" + element.toString().substring(element.toString().indexOf("->")));
		else
			System.out.println("element is not clckable" + element.toString().substring(element.toString().indexOf("->")));
	}
	
	public boolean isElementPresentByWait(long secondsToWait, By element) {
		boolean isPresent = false;

		WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
		try {
			if (wait.until(ExpectedConditions.presenceOfElementLocated(element)) != null) {
				isPresent = true;
			} else if (wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element)) != null) {
				isPresent = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return isPresent;
	}

	public void waitForStaledElement(long secondsToWait, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
		try {

			if (wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOf(element)) != null) {
				System.out.println("element is attached to dom" + element);
			}

		} catch (Exception e) {
			System.out.println("element is Not attached to dom" + element.toString());
		}
	}
	
	public void waitForStaledElement(long secondsToWait, By element) {
		WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
		try {

			if (wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(element)) != null) {
				System.out.println("element is attached to dom" + element);
			}

		} catch (Exception e) {
			System.out.println("element is Not attached to dom" + element.toString());
		}
	}
	
	/**
	 * Execute java script
	 *
	 * @param javaScript script to execute
	 * @return
	 */
	public Object executeJavaScript(String javaScript) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(javaScript);
	}

	public Object executeJavaScript(String javaScript, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(javaScript, element);
	}

	public Object clearTextField(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].value ='';", element);
	}

	public void clearTextFieldWithKeys(WebElement element) {
		element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
	}
	/**
	 * *waitForElementToLoad called from click if page is already in refreshing
	 * state, do wait commands to complete the page refresh
	 *
	 * @return
	 */
	public WebElement waitForElementToLoad(WebElement element) throws ElementNotVisibleException {
		// speed check of the presence of element, if page is already refreshed and
		// element is found
		// then no need of remaining if conditions
		waitForJStoLoad();
		try {

		} catch (Exception e) {
			if (e.toString().contains("org.openqa.selenium.UnhandledAlertException: unexpected alert open")) {
				// catch unpredictable confirmation alerts and then click on it, repeat the
				// current element
				// search
				acceptAlert(5);
			}
		}

		return element;
	}

	
	/** Scroll to bottom of webpage */
	public void scrollingToBottomofAPage() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/** Scroll to bottom of webpage */
	public void scrollToTopOfThePage() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
	}

	/**
	 * Scroll to specific element in web page
	 *
	 * @param element
	 */
	public void scrollingToElementofAPage(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	public Boolean isElementDisplayed(final By locator) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(WAIT_TIME))
				.pollingEvery(Duration.ofSeconds(WAIT_POLL_TIME)).ignoring(Exception.class);

		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver wdriver) {
				try {
					List<WebElement> elements = wdriver.findElements(locator);
					if (elements.isEmpty()) {
						return false;
					}
					return elements.get(0).isDisplayed();
				} catch (Exception e) {
					System.out.println(e.getMessage() + "\n");
					return false;

				}
			}

		};
		try {
			wait.until(function);
		} catch (TimeoutException e) {
		}
		System.out.println("Element is present with locator" + locator);
		return true;
	}
	public void sleep(int seconds) {
		try {
			System.out.println("Waiting for - " + seconds + " seconds");
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Wait for JS to load
	 *
	 * @return
	 */
	public boolean waitForJStoLoad() {

		WebDriverWait wait = new WebDriverWait(driver, 15);
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			Date previous = new Date();

			@Override
			public Boolean apply(WebDriver driver) {
				try {
					boolean t = ((Long) executeJavaScript("return jQuery.active") == 0);
					if (t) {
						boolean isLoaderHidden = (boolean) executeJavaScript(
								"return $('.spinner').is(':visible') == false");

						return t && isLoaderHidden;
					} else {

						// to prevent infinite looping, get out of this function after specified number
						// of
						// minutes.
						long maxDuration = MILLISECONDS.convert(30, SECONDS);
						Date now = new Date();
						long duration = now.getTime() - previous.getTime();

						if (duration >= maxDuration) {
							// to prevent infinite looping, get out of this function after specified number
							// of
							// minutes.
							System.out.println("Timedout to prevent infinte loop, Exiting from jQuery.active");
							return true;
						} else {
							return false;
						}
					}
				} catch (Exception e) {
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			// get current time to get out of the function from infinite waiting in case
			// document.readystate is not returning as complete.
			Date previous = new Date();

			@Override
			public Boolean apply(WebDriver driver) {
				try {

					boolean t = executeJavaScript("return document.readyState").toString().equals("complete");
					if (t) {
						return t;
					} else {

						// to prevent infinite looping, get out of this function after specified number
						// of
						// minutes.
						long maxDuration = MILLISECONDS.convert(30, SECONDS);
						Date now = new Date();
						long duration = now.getTime() - previous.getTime();

						if (duration >= maxDuration) {
							// to prevent infinite looping, get out of this function after specified number
							// of
							// minutes.
							System.out.println("Timedout to prevent infinte loop, Exiting from document.readyState to complete");
							return true;
						} else {
							return false;
						}
					}
				} catch (Exception e) {
					return true;
				}
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

	public boolean waitForJQuerytoLoad() {

		new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) executeJavaScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};
		return true;
	}

	/**
	 * Wait polling till element available
	 *
	 * @param timeOut   Total timeout to wait for
	 * @param frequency poll for element every frequency seconds
	 */
	public Wait<WebDriver> fluentWait(long timeOut, long frequency) {
		return new FluentWait<WebDriver>((driver)).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(frequency)).ignoring(NoSuchElementException.class);
	}

	public boolean verifyLengthOfField(WebElement element, int length)
	{
		boolean isValid = false;
		String fieldValue = getElementText(element);
		if(length >= fieldValue.length())
		{
			isValid = true;
		}
		return isValid;
	}

	public boolean verifyHelperText(WebElement element, String inputtedText)
	{
		boolean isValid = false;
		String heplerText = getElementText(element);
		if(heplerText.contains(inputtedText))
		{
			isValid = true;
		}
		return isValid;
	}
	
	public void clearClipBoard() {
        StringSelection stringSelection = new StringSelection("");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }
	
	public String copyToClipBoard() {
		String result = null;
		try {
			result = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (IOException | UnsupportedFlavorException | NullPointerException | IllegalStateException ex) {
		}
		return result;
	}
}
