package apim.testrail;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestRailResultListener implements ITestListener {
  
  public void onTestStart(ITestResult result) {}

  
  public void onTestSuccess(ITestResult result) {
    TestRailUtil tRailUtil = new TestRailUtil();
    tRailUtil.updateTestCaseResult(result);
  }

  
  public void onTestFailure(ITestResult result) {
    TestRailUtil tRailUtil = new TestRailUtil();
    tRailUtil.updateTestCaseResult(result);
  }

  
  public void onTestSkipped(ITestResult result) {
    TestRailUtil tRailUtil = new TestRailUtil();
    tRailUtil.updateTestCaseResult(result);
  }

  
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  
  public void onStart(ITestContext context) {
    TestRailUtil.resetResultData();
  }

  
  public void onFinish(ITestContext context) {
    TestRailUtil.updateTestRailResult();
    TestRailUtil.resultsUpdate();
  }
}
