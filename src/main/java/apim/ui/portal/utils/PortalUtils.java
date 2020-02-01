package apim.ui.portal.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import apim.ui.core.PortalTestBase;
import lombok.extern.slf4j.Slf4j;

/** @author koteswarao tekkem */
@Slf4j
public class PortalUtils {
	private static String configPropertyPath = "/config.properties";
	private static String analyticsPropertyFile = "/data/AnalyticsChartData.properties";
	private static Properties props = null;

	/**
	 * Read values from properties file
	 *
	 * @param propertyName Property key to read value for
	 * @return Return read value of property key
	 */
	public static String getPropertyValue(String propertyName) {
		try {
			props = getAllProperty(configPropertyPath, analyticsPropertyFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return props.getProperty(propertyName);
	}

	/**
	 * Get current date in simple format. Eg : Tuesday, September 16, 2019
	 *
	 * @return Today's date in simple format
	 */
	public static String getCurrentSimpleDate() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH);
		Date date = new Date();
		return df.format(date);
	}

	/**
	 * Return Custom Date by adding number days passed as argument
	 *
	 * @param days Number of days to add to current date
	 * @return Return new date after adding number of days passed in argument
	 */
	public static String getCustomDate(int days) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		Date cusDate = cal.getTime();
		return df.format(cusDate);
	}

	public static void setT1PortalUrl(String tenantName) {
		String t1PortalhostEntry = tenantName + "ca.com";
		PortalTestBase.T1_PORTAL_URL = "https://" + t1PortalhostEntry + "/admin/login?to-default-config=true";
	}

	public static void executeShellScript(String pathToFile) {
		Process p;
		try {
			p = Runtime.getRuntime().exec("sudo bash " + pathToFile);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void scrollToTopOfPage(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollTop)");

	}

	public static void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void typeKeys(String data) {
		// TODO Handle other Operating Systems
		Robot robot;
		try {
			robot = new Robot();
			StringSelection stringSelection = new StringSelection(data);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, stringSelection);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			pressEnter();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static void pressEnter() {
		if (DeviceAccessAndOperations.isWindows()) {
			Robot robot;
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Load The Properties files into project
	 *
	 * @param propertyFileName Property File name
	 * @return Returns the all the loaded files
	 * @throws Exception
	 */
	public static Properties getAllProperty(String... fileNames) throws Exception {
		PortalUtils portalUtils = new PortalUtils();
		if (props == null) {
			props = new Properties();
			for (String fileName : fileNames) {
				InputStream file = portalUtils.getFileFromResources(fileName);
				if (file != null) {
					props.load(file);
					log.info("File with name " + fileName + " Loaded");
				} else {
					log.info("No File with name " + fileName + " Loaded");
				}
			}
		}
		return props;
	}

	/**
	 * @param propertyFileName gets file from classpath, resources folder
	 * @return returns the File object
	 */
	public InputStream getFileFromResources(String fileName) {
		return getClass().getResourceAsStream(fileName);

	}
}
