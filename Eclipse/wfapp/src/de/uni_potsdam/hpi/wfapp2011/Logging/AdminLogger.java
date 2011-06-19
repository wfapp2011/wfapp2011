package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Logger for the Admin Component
 * 
 * This class is used to log events from the 
 * Admin Component. 
 * It converts the changed data into a JSON Object,
 * so that they can be saved together in the Log-Table
 *
 */

public class AdminLogger implements AdminLoggerInterface {
	private Logging logging;
	
	public AdminLogger(String type, String semester, int year) {
		logging = new Logging(type, semester, year);
	}
	
	/**
	 * This method logs a new Deadline, which was set by the Admin.
	 * 
	 */
	public void logNewDeadlineEntry(String email, String deadlineType, Date deadline) {
		Date changeDate = new Date();
		JSONObject jsonObject = createDeadlineJSON(deadlineType, deadline);
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email, LogDescriptions.NEW_DEADLINE, changedValues);
	}
	
	
	/**
	 * This methed logs a Deadline, which was changed by the Admin
	 */
	public void logChangedDeadlineEntry(String email, String deadlineType, Date deadline) {
		Date changeDate = new Date();
		JSONObject jsonObject = createDeadlineJSON(deadlineType, deadline);
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,LogDescriptions.CHANGED_DEADLINE, changedValues);
	}

	
	public void logStartedProcess(String email, String processName) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("processName", processName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,LogDescriptions.PROCESS_STARTED, changedValues);
	}
	
	public void logVotingConditions(String email, String conditions) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("conditions", conditions);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,LogDescriptions.SET_VOTING_CONDITIONS, changedValues);
	}
	
	private JSONObject createDeadlineJSON(String deadlineType, Date deadline) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("deadlineType", deadlineType);
			jsonObject.put("deadline", deadline.getTime());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	
}
