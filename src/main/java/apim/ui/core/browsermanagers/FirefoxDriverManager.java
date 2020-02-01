package apim.ui.core.browsermanagers;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import apim.ui.core.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;

/** @author koteswarao tekkem */
public class FirefoxDriverManager extends DriverManager {

	/** Method to create driver instance of firefox */
	@Override
	protected void createDriver() {
		WebDriverManager.firefoxdriver().setup();

		FirefoxOptions options = new FirefoxOptions();
		options.setAcceptInsecureCerts(true);

		this.driver = new FirefoxDriver(options);
	}
}
