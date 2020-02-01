package apim.ui.core;

import apim.ui.core.browsermanagers.ChromeDriverManager;
import apim.ui.core.browsermanagers.FirefoxDriverManager;
import apim.ui.portal.enums.DriverType;

/** @author koteswarao tekkem */
public class DriverManagerFactory {

	/**
	 * Method to create driver based on browser type and return instance of Driver
	 * Manager
	 *
	 * @param driverType Enum - Type of browser like chrome or firefox
	 * @return Instance of Driver Manager based on browser type passed
	 */
	public static DriverManager getDriverManager(DriverType driverType) {
		DriverManager driverManager;
		switch (driverType) {
		case CHROME:
			driverManager = new ChromeDriverManager();
			break;
		case FIREFOX:
			driverManager = new FirefoxDriverManager();
			break;

		default:
			driverManager = getDriverManager(DriverType.CHROME);
			break;
		}
		return driverManager;
	}
}
