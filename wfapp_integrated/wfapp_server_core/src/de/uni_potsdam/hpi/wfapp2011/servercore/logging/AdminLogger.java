package de.uni_potsdam.hpi.wfapp2011.servercore.logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import de.uni_potsdam.hpi.wfapp2011.servercore.constants.JSONFields;
import de.uni_potsdam.hpi.wfapp2011.servercore.general.DateConverter;

/**
 * Logger for the Admin Component. <br/>
 * 
 * This class is used to log events from the Admin Component. <br/>
 * It converts the changed data into a JSON Object,
 * so that they can be saved together in the Log-Table
 * 
 * @author Jannik Marten, Yanina Yurchenko
 * @see AdminLoggerInterface
 */

public class AdminLogger implements AdminLoggerInterface {
	private static AdminLogger theInstance;
	private Logging logging;
	
	private AdminLogger() {}
	
	public static AdminLogger getInstance(){
		if(theInstance == null){
			theInstance = new AdminLogger();
			theInstance.logging = Logging.getInstance(); 
		}
		return theInstance;
	}
	
	/**
	 * Logs a new Deadline, which has been set by the Admin.
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series 
	 * @param email : The HPI-Email-Address of the user, who changed the deadline;
	 * @param deadlineType : The deadline, which has been set (defined in the Class Constants);
	 * @param deadline : the Date to which the deadline was changed; 
	 */
	public void logNewDeadlineEntry(String type, String semester, int year, String email, String deadlineType, Date deadline) 
			  {
		Date changeDate = new Date();
		JSONObject jsonObject = createDeadlineJSON(deadlineType, deadline);
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log( type, semester, year, changeDate, email, LogDescriptions.NEW_DEADLINE, changedValues);
	}
	
	/**
	 * Logs a Deadline, which has been changed by the Admin.
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series 
	 * @param email: The HPI-Email-Address of the user, who changed the deadline;
	 * @param deadlineType : The deadline, which has been set (defined in the Class Constants);
	 * @param deadline : the Date to which the deadline was changed; 
	 */
	public void logChangedDeadlineEntry(String type, String semester, int year, String email, String deadlineType, Date deadline) {
		Date changeDate = new Date();
		JSONObject jsonObject = createDeadlineJSON(deadlineType, deadline);
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log( type, semester, year, changeDate, email,LogDescriptions.CHANGED_DEADLINE, changedValues);
	}

	/**
	 * Logs the start of a process
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series 
	 * @param email : The HPI-Email-Address of the user, who changed the deadline;
	 * @param processName : The process Name, which has been started 
	 */
	public void logStartedProcess(String type, String semester, int year, String email, String processName) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.PROCESS_NAME, processName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(type, semester, year, changeDate, email,LogDescriptions.PROCESS_STARTED, changedValues);
	}
	
	/**
	 * Logs a voting condition, which was added to a process
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series 
	 * @param email : The HPI-Email-Address of the user, who added the condition
	 * @param conditions : a String, which describes the conditions
	 */
	
	public void logVotingConditions(String type, String semester, int year, String email, String conditions) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.CONDITIONS, conditions);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log( type, semester, year, changeDate, email,LogDescriptions.SET_VOTING_CONDITIONS, changedValues);
	}
	
	/**
	 * creates a JSON Object, which can be stored in the Log-Table. <br/>
	 * It contains the deadline Type and the String of the date of the new deadline. 
	 * 
	 * @param deadlineType : The deadline, which has been set (defined in the Class {@link Constants});
	 * @param deadline : the Date to which the deadline was changed; 
	 * @return a JSON-Object with the process name
	 */
	private JSONObject createDeadlineJSON(String deadlineType, Date deadline) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.DEADLINE_TYPE, deadlineType);
			jsonObject.put(JSONFields.DEADLINE, DateConverter.dateToISO8601(deadline));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
