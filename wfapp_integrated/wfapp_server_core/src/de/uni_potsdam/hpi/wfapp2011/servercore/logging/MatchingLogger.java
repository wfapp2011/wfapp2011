package de.uni_potsdam.hpi.wfapp2011.servercore.logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import de.uni_potsdam.hpi.wfapp2011.servercore.constants.JSONFields;

/**
 * Logger for the Matching Phase of the process
 * 
 * This class is used to log events from the 
 * Matching-Phase of the process. 
 * It converts the changed data into a JSON Object,
 * so that they can be saved together in the Log-Table
 *
 * @author Jannik Marten, Yanina Yurchenko
 * 
 */

public class MatchingLogger implements MatchingLoggerInterface {
	private static MatchingLogger theInstance;
	private Logging logging;
	
	private MatchingLogger() {}
	
	public static MatchingLogger getInstance(){
		if (theInstance == null){
			theInstance = new MatchingLogger();
			theInstance.logging = Logging.getInstance();
		}
		return theInstance;
	}
	
	/**
	 * Logs the execution of the matching algorithm for the given process name.
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email : The HPI-Email-Address of the user, who executed the matching
	 * @throws ProcessIdentifierException if the processIdentifier is not valid 
	 */
	public void logMatchingExecuted(String type, String semester, int year, String email, String processName) {
		Date changeDate = new Date();
		String changedValues = createProcessNameJSON(processName).toString();
		logging.log(type, semester, year, changeDate, email, LogDescriptions.MATCHING_EXECUTED, changedValues);
	}

	/**
	 * Logs all changes manual changes of the matching
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email: The HPI-Email-Address of the user, who changed the matching
	 * @throws ProcessIdentifierException if the processIdentifier is not valid 
	 */
	public void logChangedMatching(String type, String semester, int year, String email, String projectName)  {
		Date changeDate = new Date();
		String changedValues = createProjectNameJSON(projectName).toString();
		logging.log( type, semester, year, changeDate, email, LogDescriptions.MATCHING_CHANGED, changedValues);
	}
	
	/**
	 * 
	 * Logs, if the Matching is completed, so that the next phase can start
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @throws ProcessIdentifierException if the processIdentifier is not valid 
	 */
	public void logMatchingCompleted(String type, String semester, int year, String email)  {
		Date changeDate = new Date();
		logging.log(type, semester, year, changeDate, email, LogDescriptions.MATCHING_COMPLETED, "");
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
