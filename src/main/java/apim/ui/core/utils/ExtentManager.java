package apim.ui.core.utils;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import apim.testrail.TestRailTrigger;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExtentManager {
    private static ExtentReports extent;
    private static String reportFileName = "APIM-Portal-UI-Test-Automaton-Report"+".html";
    private static String fileSeperator = System.getProperty("file.separator");
    public static String reportFilepath = "testReport";
    private static String reportFileLocation =  reportFilepath +fileSeperator+ reportFileName;
  
 
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }
 
    //Create an extent report instance
    public static ExtentReports createInstance() {
    	ExtentManager extentManager  =new ExtentManager();
        String fileName = extentManager.getReportPath(reportFilepath);
       
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
 
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        //Set environment details
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("AUT", "QA");
 
        return extent;
    }
     
    //Create the report path
    private String getReportPath (String path) {
    	File testDirectory = new File(path);
        if (!testDirectory.exists()) {
        	if (testDirectory.mkdir()) {
                log.info("Directory: " + path + " is created!" );
                return reportFileLocation;
            } else {
                log.info("Failed to create directory: " + path);
                return this.getClass().getSimpleName();
            }
        } else {
            log.info("Directory already exists: " + path);
        }
		return reportFileLocation;
    }
 
}