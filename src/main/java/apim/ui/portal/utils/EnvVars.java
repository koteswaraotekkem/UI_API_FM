package apim.ui.portal.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnvVars {

	public static String PORTAL_URL;
	public static String USERNAME;
	public static String PASSWORD;
	public static String BROWSER;

	public static String T1_PORTAL_URL;
	public static String T1_USERNAME;
	public static String T1_PASSWORD;

	public static String T2_PORTAL_URL;
	
	public static String TESTRAIL_BASE_URL;
	public static String TESTRAIL_USERNAME;
	public static String  TESTRAIL_PASSWORD; 
	
	 public static String TESTRAIL_STATUS_PASSED;
	 public static String TESTRAIL_STATUS_FAILED; 
	 public static String TESTRAIL_STATUS_NEEDUPDATING;
	 public static String TESTRAIL_STATUS_IGNORED;
	 public static String TESTRAIL_STATUS_SKIPPED;
	 
	 public static String TESTRAIL_TRIGGER;
	 public static String TESTRAIL_RUNID;
	
	 

	static {
		PORTAL_URL = getExtProperty("PORTAL_URL");
		USERNAME = getExtProperty("USERNAME");
		PASSWORD = getExtProperty("PASSWORD");
		BROWSER = getExtProperty("BROWSER");

		T1_PORTAL_URL = getExtProperty("T1_PORTAL_URL");
		T1_USERNAME = getExtProperty("T1_USERNAME");
		T1_PASSWORD = getExtProperty("T1_PASSWORD");

		T2_PORTAL_URL = getExtProperty("T2_PORTAL_URL");
		
		TESTRAIL_BASE_URL = getExtProperty("TESTRAIL_BASE_URL");
		TESTRAIL_USERNAME = getExtProperty("TESTRAIL_USERNAME");
		TESTRAIL_PASSWORD = getExtProperty("TESTRAIL_PASSWORD");
		TESTRAIL_STATUS_PASSED = getExtProperty("TESTRAIL_STATUS_PASSED");
		TESTRAIL_STATUS_FAILED = getExtProperty("TESTRAIL_STATUS_FAILED");
		TESTRAIL_STATUS_NEEDUPDATING = getExtProperty("TESTRAIL_STATUS_NEEDUPDATING");
		TESTRAIL_STATUS_IGNORED = getExtProperty("TESTRAIL_STATUS_IGNORED");
		TESTRAIL_STATUS_SKIPPED = getExtProperty("TESTRAIL_STATUS_SKIPPED");
		TESTRAIL_TRIGGER = getExtProperty("TESTRAIL_TRIGGER");
		TESTRAIL_RUNID = getExtProperty("TESTRAIL_RUNID");

	}

	/**
	 * Get environment variables
	 *
	 * @param propName - Name of property to get. Property name should be same at
	 *                 CI/CD jobs and build.xml files.
	 * @return - Property value.
	 */
	private static String getExtProperty(String propName) {
		String prop = System.getProperty(propName);

		if (prop == null || prop.equals("${" + propName + "}") || prop.isEmpty()) {
			log.debug("Param '" + propName + "' is not defined at CI/CD job and = '" + prop
					+ "'. Getting value from config.properties   vfr file.");
			prop = PortalUtils.getPropertyValue(propName);
			if (prop == null) {
				log.error("Param '" + propName + "' is missed (at CI/CD, "
						+ "config.properties) and will be set to null.");
			}
		}
		return prop;
	}

}
