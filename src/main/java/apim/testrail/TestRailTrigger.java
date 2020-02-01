package apim.testrail;

import apim.testrail.GenerateTestngXml;
import apim.ui.portal.utils.EnvVars;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestRailTrigger {

	public static void main(String[] args) {
		String TestPlanID = EnvVars.TESTRAIL_RUNID;
		if (EnvVars.TESTRAIL_TRIGGER.equalsIgnoreCase("yes") && TestPlanID != null
				&& !TestPlanID.isEmpty()) {
			log.info("Starting the execution of tests in TestPlanID " + TestPlanID);
			GenerateTestngXml.testRun(TestPlanID);

		}
	}

}
