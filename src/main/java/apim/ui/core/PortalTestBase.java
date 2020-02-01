package apim.ui.core;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;

import apim.ui.core.utils.ExtentTestManager;
import apim.ui.core.utils.HtmlOps;
import apim.ui.portal.enums.DriverType;
import apim.ui.portal.utils.EnvVars;
import lombok.extern.slf4j.Slf4j;

/** @author koteswarao tekkem */
@Slf4j
public class PortalTestBase extends HtmlOps {
	/*
	 * private ATUTestRecorder recorder; private String videoRecordsPath =
	 * "testRecords"; private boolean firstStopBeforeClassRecordingCall = true;
	 * private String beforeClassFilename;
	 */private ITestResult result;
	public static String T1_PORTAL_URL = EnvVars.T1_PORTAL_URL;
	public static String BROWSER = EnvVars.BROWSER;

	@BeforeClass
	public void initDriver() {
		/*
		 * beforeClassFilename = getTestClassName() + ".beforeClass";
		 * recordTestAsVideo(beforeClassFilename);
		 */	log.info("Initialising WebDriver");
		try {
			DriverType driverType = BROWSER.toUpperCase().equals("CHROME") ? DriverType.CHROME : DriverType.FIREFOX;

			DriverManager driverManager = DriverManagerFactory.getDriverManager(driverType);
			driver = driverManager.getDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(DEFAULT_UIELEMENT_WAIT_TIME, TimeUnit.SECONDS);

		} catch (NullPointerException ex) {
			throw new RuntimeException("Web driver could not be initialised for device ");
		}
	}

	@BeforeMethod
	public void setupTestBaseMethod(Method method, ITestResult result) throws Exception {
		String filename = method.getDeclaringClass().getName() + "." + method.getName();
		/*
		 * stopTestRecording(beforeClassFilename, result); recordTestAsVideo(filename);
		 */log.info("===========================================================================================\n");
		log.info("STARTING TEST CASE '" + method.getDeclaringClass().getName() + "." + method.getName() + "'");
		log.info("===========================================================================================\n\n");
	}

	/**
	 * Get instance of driver
	 *
	 * @return instance of driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDownTestBaseMethod(Method method, ITestResult result) {
		this.result = result;
		String filename = method.getDeclaringClass().getName() + "." + method.getName();
		log.info("Post Condition TEAR DOWN for method: " + filename);
		//stopTestRecording(filename, result);
		log.info("===========================================================================================\n\n");
	}

	@AfterClass(alwaysRun = true)
	public void portalTestBaseAfterClass() {
		/*
		 * if (ITestResult.FAILURE == result.getStatus()) {
		 * stopBeforeClassRecording(beforeClassFilename, false); } else {
		 * stopBeforeClassRecording(beforeClassFilename, true); }
		 */
		log.debug("**** In PortalTestBase AfterClass method ****");
		driver.quit();
	}

	/*
	 * private void stopBeforeClassRecording(String filename, boolean
	 * deleteRecording) { if (firstStopBeforeClassRecordingCall) { // This code will
	 * be executed only once for each test class try { if (filename != null) {
	 * log.info("Stop recording for class: " + filename); }
	 * 
	 * if (null != recorder) { recorder.stop(); }
	 * 
	 * if (deleteRecording) { log.info("Deleting recording for class: " + filename);
	 * DeviceAccessAndOperations.deleteFileIfExists(videoRecordsPath, filename); } }
	 * catch (Exception e) { log.error("Exception occurred in stopTestRecording." +
	 * e.getMessage()); e.printStackTrace(); }
	 * 
	 * recorder = null; } firstStopBeforeClassRecordingCall = false; }
	 */

	/*
	 * private void stopTestRecording(String filename, ITestResult result) { try {
	 * if (filename != null) { log.info("Stop recording for method: " + filename); }
	 * 
	 * if (null != recorder) { recorder.stop(); }
	 * 
	 * if (result != null) { if (result.getStatus() == ITestResult.SUCCESS) {
	 * log.info("Deleting recording for method: " + filename);
	 * DeviceAccessAndOperations.deleteFileIfExists(videoRecordsPath, filename); }
	 * else { if (null != filename) { log.info("Test '" + filename +
	 * "' has failed."); } } } } catch (Exception e) {
	 * log.error("Exception occurred in stopTestRecording." + e.getMessage());
	 * e.printStackTrace(); }
	 * 
	 * recorder = null; }
	 */

	private String getTestClassName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * *
	 *
	 * @param fileName To Record UITests
	 */
	/*
	 * private void recordTestAsVideo(String fileName) { // -------- enable video
	 * recording -------------- try { File file = new File(videoRecordsPath); if
	 * (!file.exists()) { if (file.mkdir()) {
	 * log.debug("Create dir for video reports."); } else {
	 * log.error("Can't create dir for video reports."); } } } catch (Exception e) {
	 * log.error(e.toString()); } stopTestRecording(null, null); try {
	 * DeviceAccessAndOperations.deleteFileIfExists(videoRecordsPath, fileName);
	 * recorder = new ATUTestRecorder(videoRecordsPath, fileName, false);
	 * log.info("Start recording for class: " + beforeClassFilename);
	 * recorder.start(); } catch (Exception e) { e.printStackTrace(); } }
	 */

	public void STEP_INFO(String stepNumber, String description, String expected) {
		stepInfo("\n   Step_" + stepNumber + ": \n   Description: " + description + "\n   Expected: " + expected);
	}

	public void STEP_INFO(int stepNumber, String description, String expected) {
		stepInfo("\n   Step " + stepNumber + ": \n   Description: " + description + "\n   Expected: " + expected);
	}

	private void stepInfo(String infoMessage) {
		log.info(infoMessage);

		if (ExtentTestManager.getTest() != null) {
			ExtentTestManager.getTest().log(Status.INFO, infoMessage.replace("\n", "<br/>"));
		}
	}
}
