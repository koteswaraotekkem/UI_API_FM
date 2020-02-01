package apim.testrail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.ITestResult;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import apim.ui.portal.utils.EnvVars;
import apim.ui.portal.utils.PortalUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** @author koteswarao tekkem */
@Slf4j
public class TestRailUtil {

  @Setter private static String testRunId;
  private static String caseId;
  private static int tRailConFailureCount = 0;
  @Setter @Getter private static Map<String, List<String>> resultMessage = new HashMap<String, List<String>>();
  @Setter private static Map<String, Integer> resultOfTest = new HashMap<String, Integer>();
  @Setter private static Map<String, List<String>> listOfTests = new HashMap<String, List<String>>();
  private static JSONArray offlineResults;
  public static APIClient client;

	static {
		client = new APIClient(EnvVars.TESTRAIL_BASE_URL);
		client.setUser(EnvVars.TESTRAIL_USERNAME);
		client.setPassword(EnvVars.TESTRAIL_PASSWORD);
	}
  /**
   * Provides details of test runs having runid from testplan details
   *
   * @param id of test plan in testrail
   * @return List of test runs from test plan if id is testplanid
   */
  public static List<String> getTestRunsFromTestPlan(String id) {
    List<String> testRunsToExecute = new ArrayList<String>();
    int runsCount = 0;
    testRunId = id;
    try {

      Object c = client.sendGet("get_plan/" + id);
      Gson gson = new Gson();
      JsonElement jsonE = gson.toJsonTree(c);
      JsonObject jobj = jsonE.getAsJsonObject();
      JsonArray ja = jobj.getAsJsonArray("entries");
      log.info("Below are list of test runs of part test plan id " + id);
      for (int i = 0; i < ja.size(); i++) {
        JsonArray tjar = ja.get(i).getAsJsonObject().getAsJsonArray("runs");
        if (tjar.size() > 0) {
          for (int j = 0; j < tjar.size(); j++) {
            JsonObject jobjj = tjar.get(j).getAsJsonObject();
            String runid = jobjj.get("id").toString();
            String runName = jobjj.get("name").toString();
            log.info("RunID = " + runid + " , Run Name = \'" + runName + "\'");
            testRunsToExecute.add(runsCount++, runid);
          }
        }
      }

    } catch (Exception e) {
      if (e.getMessage().contains("plan_id is not a valid test plan")) {
        testRunsToExecute.add(0, id);
        return testRunsToExecute;
      }
      if (e.getMessage().equalsIgnoreCase("Read timed out")) {
        log.error("Timed out while trying to fetch Test Runs from Test Rail");
      }
      e.printStackTrace();
    }
    return testRunsToExecute;
  }

  /** Reset hash maps resultOfTest and resultMessage before each TestNG.xml execution */
  public static void resetResultData() {
    resultOfTest.clear();
    resultMessage.clear();
  }

  /**
   * Provides result details of testcase having testCaseId and test run id as passed
   *
   * @param runId Test Run ID
   * @param testCaseId Id of test case in testrail
   * @return test case results
   */
  public static JsonArray getTestCaseResults(String runId, String testCaseId) {
    try {
      Object resp = client.sendGet("get_results_for_case/" + runId + "/" + testCaseId);
      JsonElement latestResult = new Gson().toJsonTree(resp);
      return latestResult.getAsJsonArray();
    } catch (Exception e) {
      if (e.getMessage().equalsIgnoreCase("Read timed out")) {
        log.error("Timed out while trying to fetch test case results from Test Rail");
      }
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Returns list of classes to be executed as part of test run
   *
   * @param runID runID
   * @return Map with class details
   */
  public static Map<String, List<String>> getTestsOfRun(String runID) {
    Map<String, String> testsToExecute = new HashMap<String, String>();
    listOfTests = new HashMap<String, List<String>>();

    testRunId = runID;
    try {
    	
      Object c = client.sendGet("get_tests/" + runID);
      Gson gson = new Gson();
      JsonElement jsonE = gson.toJsonTree(c);
      JsonArray jArray = jsonE.getAsJsonArray();
      for (int i = 0; i < jArray.size(); i++) {
        try {
          List<String> testInfo = new ArrayList<String>();
          String automationStat =
              jArray.get(i).getAsJsonObject().get("custom_automationstat").toString();
          if (automationStat.equalsIgnoreCase("false")) {
            continue;
          }
          caseId = jArray.get(i).getAsJsonObject().get("case_id").getAsString();
          String testClass = jArray.get(i).getAsJsonObject().get("custom_testclass").getAsString();
          testInfo.add(0, testClass);
          try {
            String testMethod =
                jArray.get(i).getAsJsonObject().get("custom_testmethod").getAsString();
            String[] methodsList = testMethod.split(",");
            if (methodsList.length > 0) {
              for (int k = 1; k <= methodsList.length; k++) {
                testInfo.add(k, methodsList[k - 1]);
              }
            }
          } catch (Exception e) {
            log.info("No Method specification for testcase : " + caseId);
          }
          log.info(caseId + ", " + testClass);
          listOfTests.put(caseId, testInfo);
        } catch (NullPointerException exp) {
          if (jArray
              .get(i)
              .getAsJsonObject()
              .get("custom_manual_only")
              .toString()
              .equalsIgnoreCase("true")) {
            continue;
          } else {
            resultOfTest.put(caseId, Integer.parseInt(PortalUtils.getPropertyValue("TESTRAIL_STATUS_NEEDUPDATING")));
            List<String> incompleteTestMessage = new ArrayList<String>();
            incompleteTestMessage.add(
                0, "Testng Class field is empty or invalid. Please update the test case");
            resultMessage.put(caseId, incompleteTestMessage);
          }
        }
      }

    } catch (Exception e) {
      if (e.getMessage().equalsIgnoreCase("Read timed out")) {
        log.error("Timed out while trying to fetch Test Cases from Test Rail");
      }
      if (e.getMessage()
          .contains(
              "Field :run_id is not a valid test run")){
        log.error("Test Run ID Passed is inavlid");
      }
        e.printStackTrace();
    }
    int countofTests = testsToExecute.size();
    return listOfTests;
  }

  /** Updates execution result in test rail */
  public static void updateTestRailResult() {
    if (testRunId == null) {
      log.error("RunID is null . . Skipping result update to TestRail");
      return;
    }

    for (Map.Entry<String, Integer> entry : resultOfTest.entrySet()) {
      String testCaseId = entry.getKey();
      log.info("Updating result for test case : " + testCaseId);
      Map data = new HashMap();
      if (entry.getValue() == Integer.parseInt(PortalUtils.getPropertyValue("TESTRAIL_STATUS_NEEDUPDATING"))) {
        data.put("status_id", Integer.parseInt(PortalUtils.getPropertyValue("TESTRAIL_STATUS_NEEDUPDATING")));
        data.put(
            "comment",
            "\n" + resultMessage.get(testCaseId).toString().replaceAll("\\[|\\]|\n\\, ", ""));
      } else if (ITestResult.SUCCESS == entry.getValue()) {
        data.put("status_id", Integer.parseInt(PortalUtils.getPropertyValue("TESTRAIL_STATUS_PASSED")));
        data.put(
            "comment",
            "\n" + resultMessage.get(testCaseId).toString().replaceAll("\\[|\\]|\n\\, ", ""));
      } else if (ITestResult.FAILURE == entry.getValue()) {
        data.put("status_id", Integer.parseInt(PortalUtils.getPropertyValue("TESTRAIL_STATUS_FAILED")));
        data.put(
            "comment",
            "Test failed"
                + "\n"
                + resultMessage.get(testCaseId).toString().replaceAll("\\[|\\]|\n\\, ", ""));
      } else if (ITestResult.SKIP == entry.getValue()) {
        data.put("status_id", Integer.parseInt(PortalUtils.getPropertyValue("TESTRAIL_STATUS_IGNORED")));
        data.put(
            "comment",
            "\n" + resultMessage.get(testCaseId).toString().replaceAll("\\[|\\]|\n\\, ", ""));
      }
      try {
        JSONObject r =
            (JSONObject)
                client.sendPost("add_result_for_case/" + testRunId + "/" + testCaseId, data);
      } catch (Exception e) {
        tRailConFailureCount++;
        if (tRailConFailureCount == 1) {
          offlineResults = new JSONArray();
        }
        JSONObject offlineResult = new JSONObject();
        offlineResult.put("case_id", caseId);
        offlineResult.put("status_id", data.get("status_id"));
        offlineResult.put("comment", data.get("comment"));
        offlineResults.add(tRailConFailureCount - 1, offlineResult);
      }
    }
  }

  /**
   * Method to create json file to store results of tests offline in case of testrail connection
   * failure
   */
  public static void resultsUpdate() {
    if (offlineResults != null && !offlineResults.isEmpty()) {
      FileHelper.createJsonFile(testRunId, offlineResults);
    }
  }

  /**
   * Update test method result in testrail
   *
   * @param result result of executed method
   */
  public void updateTestCaseResult(ITestResult result) {

    /*
    Loop through result of each test case of testrun and store result in
    Map "resultMessage" for result comment and  "resultOfTest" for result status.
     */
    for (Map.Entry<String, List<String>> entry : listOfTests.entrySet()) {

      String currClassName = result.getInstanceName();
      String currMethodName = result.getName().split(currClassName + "\\.")[0];
      String classNameToCompare = entry.getValue().get(0);
      List<String> methodNamesToCompare = entry.getValue();
      /*
       Below condition to fetch testcaseid based on mapping class and method name
      */
      if (classNameToCompare.equalsIgnoreCase(currClassName)
          && methodNamesToCompare.contains(currMethodName)) {
        caseId = entry.getKey();
        String caseIdMismatchMsg = "";
        try {
          Method methodInExecution =
              result.getTestClass().getRealClass().getMethod(result.getName());

          String caseIdFromTestMethod;
          if (methodInExecution.isAnnotationPresent(TestCaseId.class)) {
            caseIdFromTestMethod =
                String.valueOf(methodInExecution.getAnnotation(TestCaseId.class).testCaseId());
            if (!caseId.equalsIgnoreCase(caseIdFromTestMethod)) {
              caseIdMismatchMsg =
                  "**Test Case ID in annotation 'TestCaseId' of test method is incorrect. Expected testcaseid : "
                      + caseId
                      + ". But found testcaseid : "
                      + caseIdFromTestMethod
                      + ". Please verify and rectify**";
            }

          } else caseIdMismatchMsg = "**Annotation 'TestCaseId' of test method is blank**";
        } catch (NoSuchMethodException e) {
          log.info("**Method name automation script and testrail are not in sync**");
        }

        StringBuilder resultComment = new StringBuilder();
        resultComment.append("\n \n **Class :** " + result.getTestClass().getName() + "\n");
        if (result.getStatus() == ITestResult.SUCCESS) {
          resultOfTest.put(caseId, result.getStatus());
          resultComment.append(
              "**Test Method :** "
                  + "\""
                  + currMethodName
                  + "\""
                  + " | **Status :** Passed \n"
                  + caseIdMismatchMsg
                  + "\n");
        } else if (result.getStatus() == ITestResult.FAILURE) {
          resultOfTest.put(caseId, result.getStatus());
          resultComment.append(
              "**Test Method :** "
                  + "\""
                  + currMethodName
                  + "\""
                  + " | **Status :** Failed \n"
                  + caseIdMismatchMsg
                  + "\n");
        } else if (result.getStatus() == ITestResult.SKIP) {
          Integer tempStatus = resultOfTest.get(caseId);
          if (tempStatus == null || tempStatus != ITestResult.FAILURE) {
            resultOfTest.put(caseId, result.getStatus());
          }
          resultComment.append(
              "**Test Method :** "
                  + "\""
                  + currMethodName
                  + "\""
                  + " | **Status :** Skipped \n"
                  + caseIdMismatchMsg
                  + "\n");
        }
        /*
         Below condition to update result comment with exception thrown for failed/skipped test
        */
        if (result.getThrowable() != null) {
          if (resultOfTest.get(caseId) != ITestResult.FAILURE) {
            resultOfTest.put(caseId, result.getStatus());
          }
          resultComment.append(result.getThrowable().toString() + "\n\n");
        }
        /*
         Below condition will add to list of "resultMessage" map if entry for current testcase exists
        */
        if (resultMessage.get(caseId) != null) {
          int sizeOfList = resultMessage.get(caseId).size();
          resultMessage.get(caseId).add(sizeOfList, resultComment.toString());
        } else {
          List<String> firstResultUpdate = new ArrayList<String>();
          firstResultUpdate.add(0, resultComment.toString());
          resultMessage.put(caseId, firstResultUpdate);
        }
        break;
      }
    }
  }
}
