package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class MatchingLogger implements MatchingLoggerInterface {
	// refactoring has to be done!!
	private Logging logging;
	
	public MatchingLogger(String type, String semester, int year) {
		logging = new Logging(type, semester, year);
	}
	
	public void logMatchingExecuted(String processName) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("processName", processName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, "System","matchingExecuted", changedValues);
	}
	
	
	public void logChangedMatching(String email, String projectName) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("projectName", projectName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,"matchingChanged", changedValues);
	}
	
	public void logMatchingCompleted(String email) {
		Date changeDate = new Date();
		logging.log(changeDate, email,"matchingCompleted", "");
	}	
}
