package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import de.uni_potsdam.hpi.wfapp2011.constants.JSONFields;

/**
 * Logger for the Matching Phase of the process
 * 
 * This class is used to log events from the 
 * Matching-Phase of the process. 
 * It converts the changed data into a JSON Object,
 * so that they can be saved together in the Log-Table
 *
 */

public class MatchingLogger implements MatchingLoggerInterface {
	private Logging logging;
	
	public MatchingLogger(String type, String semester, int year) {
		logging = new Logging(type, semester, year);
	}
	
	/**
	 * Logs the execution of the matching algorithm for the given process name.
	 * @param email: The HPI-Email-Address of the user, who executed the matching
	 */
	public void logMatchingExecuted(String email, String processName) {
		Date changeDate = new Date();
		String changedValues = createProcessNameJSON(processName).toString();
		logging.log(changeDate, email, LogDescriptions.MATCHING_EXECUTED, changedValues);
	}

	/**
	 * Logs all changes manual changes of the matching
	 * @param email: The HPI-Email-Address of the user, who changed the matching
	 */
	public void logChangedMatching(String email, String projectName) {
		Date changeDate = new Date();
		String changedValues = createProjectNameJSON(projectName).toString();
		logging.log(changeDate, email, LogDescriptions.MATCHING_CHANGED, changedValues);
	}
	
	/**
	 * Logs, if the Matching is completed, so that the next phase can start
	 */
	public void logMatchingCompleted(String email) {
		Date changeDate = new Date();
		logging.log(changeDate, email, LogDescriptions.MATCHING_COMPLETED, "");
	}	
	
	
	/**
	 * creates a JSON Object, which can be stored in the Log-Table
	 * @param processName
	 * @return a JSON-Object with the process name
	 */
	private JSONObject createProcessNameJSON(String processName) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.PROCESS_NAME, processName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * Creates a JSON Object, which can be stored in the Log-Table
	 * @param projectName 
	 * @return a JSON-Object with the project name
	 */
	private JSONObject createProjectNameJSON(String projectName) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.PROJECT_NAME, projectName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
