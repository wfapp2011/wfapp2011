package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminLogger implements AdminLoggerInterface {
	private Logging logging;
	public AdminLogger(String type, String semester, int year) {
		logging = new Logging(type, semester, year);
	}
	
	public void logNewDeadlineEntry(String email, String deadlineType, Date deadline) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("deadlineType", deadlineType);
			jsonObject.put("deadline", deadline.getTime());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,"newDeadline", changedValues);
	}
	
	public void logChangedDeadlineEntry(String email, String deadlineType, Date deadline) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("deadlineType", deadlineType);
			jsonObject.put("deadline", deadline.getTime());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,"changedDeadline", changedValues);
	}
	
	public void logStartedProcess(String email, String processName) {
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
		logging.log(changeDate, email,"processStarted", changedValues);
	}
	
	public void logVotingConditions(String email, String conditions) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("conditions", conditions);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,"setVotingConditions", changedValues);
	}
	
	
	
}
