package apim.ui.core.browsermanagers;

import java.util.Collections;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import apim.ui.core.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;

/** @author */
public class ChromeDriverManager extends DriverManager {
	/** Method to create instance of chrome driver */
	@Override
	protected void createDriver() {
		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		options.addArguments("ignore-certificate-errors");

		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

		this.driver = new ChromeDriver(options);
	}
}
