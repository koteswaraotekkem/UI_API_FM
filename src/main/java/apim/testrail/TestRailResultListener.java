package apim.testrail;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestRailResultListener implements ITestListener {
  @Override
  public void onTestStart(ITestResult result) {}

  @Override
  public void onTestSuccess(ITestResult result) {
    TestRailUtil tRailUtil = new TestRailUtil();
    tRailUtil.updateTestCaseResult(result);
  }

  @Override
  public void onTestFailure(ITestResult result) {
    TestRailUtil tRailUtil = new TestRailUtil();
    tRailUtil.updateTestCaseResult(result);
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    TestRailUtil tRailUtil = new TestRailUtil();
    tRailUtil.updateTestCaseResult(result);
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  @Override
  public void onStart(ITestContext context) {
    TestRailUtil.resetResultData();
  }

  @Override
  public void onFinish(ITestContext context) {
    TestRailUtil.updateTestRailResult();
    TestRailUtil.resultsUpdate();
  }
}
