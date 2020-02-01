package apim.testrail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import apim.ui.core.utils.TestListener;
import lombok.extern.slf4j.Slf4j;


/** @author Koteswarao Tekkem */
@Slf4j
public class GenerateTestngXml {

  public static int totalTest = 0;
  private static String testRunId;

  /**
   * This is the main method that will be called from Test Repo with Test Run ID passed as argument
   *
   * @param id TestRunID or TestPlanID
   */
  public static void testRun(String id) {
    List<String> planList;
    GenerateTestngXml dt = new GenerateTestngXml();
    planList = TestRailUtil.getTestRunsFromTestPlan(id);
    Iterator runItr = planList.iterator();
    while (runItr.hasNext()) {
      testRunId = runItr.next().toString();
      Map<String, List<String>> testCasesList = TestRailUtil.getTestsOfRun(testRunId);
      totalTest = testCasesList.size();
      dt.runTestNGTest(testCasesList);
    }
  }

  /**
   * Created testng.xml with classes mentioned in argument and executes them. Creates physical copy
   * of testng file with name 'runTestng.xml'
   *
   * @param testngParams List of tests to execute
   */
  public void runTestNGTest(
      Map<String, List<String>> testngParams) { // Create an instance on TestNG
    TestNG myTestNG = new TestNG();

    // Create an instance of XML Suite and assign a name for it.
    XmlSuite mySuite = new XmlSuite();
    mySuite.setName("MySuite_" + testRunId);

    // Set Listeners
    List<String> listeners = new ArrayList<String>();
    listeners.add(0, TestRailResultListener.class.getCanonicalName());
    listeners.add(1, TestListener.class.getCanonicalName());
    mySuite.setListeners(listeners);

    // Create an instance of XmlTest and assign a name for it.
    XmlTest myTest = new XmlTest(mySuite);
    myTest.setName("MyTestRun_" + testRunId);
    myTest.setPreserveOrder(true);

    // Create a list which can contain the classes that you want to run.
    List<XmlClass> myClasses = new ArrayList<XmlClass>();
    List<XmlInclude> methods;
    for (Map.Entry<String, List<String>> entry : testngParams.entrySet()) {
      boolean foundClass = false;
      List<String> tlist = entry.getValue();
      XmlClass tempClass = new XmlClass(tlist.get(0));

      if (tlist.size() > 1) {
        if (!myClasses.isEmpty()) {
          for (int index = 0; index < myClasses.size(); index++) {

            if (myClasses.get(index).getName().equalsIgnoreCase(tempClass.getName())) {
              methods = myClasses.get(index).getIncludedMethods();
              for(int i =0;i<tlist.size()-1;i++){
                XmlInclude methodInclude = new XmlInclude(tlist.get(i+1));
                methodInclude.setDescription(entry.getKey());
                methods.add(methods.size(), methodInclude);
              }


              //            methods.get(methods.size()-1).setDescription(entry.getKey()); /*
              // Currently issue with TestNG to update method level description. Will revisit it,
              // once resolved from TestNG team. */
              tempClass = myClasses.get(index);
              tempClass.setIncludedMethods(methods);
              foundClass = true;
            }
          }
        }
        if (foundClass) {
          continue;
        } else {
          methods = new ArrayList<XmlInclude>();
          for(int i =0;i<tlist.size()-1;i++){
            XmlInclude methodInclude = new XmlInclude(tlist.get(i+1));
            methodInclude.setDescription(entry.getKey());
            methods.add(methods.size(), methodInclude);
          }
          tempClass.setIncludedMethods(methods);
          myClasses.add(tempClass);
        }
      }
    }

    //     Assign that to the XmlTest Object created earlier.
    myTest.setXmlClasses(myClasses);

    // Add the suite to the list of suites.
    List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
    mySuites.add(mySuite);

    // Set the list of Suites to the testNG object you created earlier.
    myTestNG.setXmlSuites(mySuites);
    mySuite.setFileName(testRunId + "_runTestng.xml");

    // Create physical XML file based on the virtual XML content
    for (XmlSuite suite : mySuites) {
      FileHelper.createXmlFile(suite, suite.getFileName());
    }

    myTestNG.run();

    log.info("Testng file created successfully.");

  }
}
