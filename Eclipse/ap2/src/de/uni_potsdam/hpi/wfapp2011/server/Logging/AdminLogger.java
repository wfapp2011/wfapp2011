package de.uni_potsdam.hpi.wfapp2011.server.Logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import de.uni_potsdam.hpi.wfapp2011.server.constants.JSONFields;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifierException;

/**
 * Logger for the Admin Component
 * 
 * This class is used to log events from the 
 * Admin Component. 
 * It converts the changed data into a JSON Object,
 * so that they can be saved together in the Log-Table
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
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email: The HPI-Email-Address of the user, who changed the deadline;
	 * @param deadlineType: The deadline, which has been set (definied in the Class Constants);
	 * @param deadline: the Date to which the deadline was changed; 
	 * @throws ProcessIdentifierException if the processIdentifier is not valid 
	 */
	public void logNewDeadlineEntry(ProcessIdentifier processIdentifier, String email, String deadlineType, Date deadline) 
			throws ProcessIdentifierException  {
		Date changeDate = new Date();
		JSONObject jsonObject = createDeadlineJSON(deadlineType, deadline);
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(processIdentifier, changeDate, email, LogDescriptions.NEW_DEADLINE, changedValues);
	}
	
	/**
	 * Logs a Deadline, which has been changed by the Admin.
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email: The HPI-Email-Address of the user, who changed the deadline;
	 * @param deadlineType: The deadline, which has been set (definied in the Class Constants);
	 * @param deadline: the Date to which the deadline was changed; 
	 * @throws ProcessIdentifierException if the processIdentifier is not valid
	 */
	public void logChangedDeadlineEntry(ProcessIdentifier processIdentifier, String email, String deadlineType, Date deadline) 
			throws ProcessIdentifierException {
		Date changeDate = new Date();
		JSONObject jsonObject = createDeadlineJSON(deadlineType, deadline);
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(processIdentifier, changeDate, email,LogDescriptions.CHANGED_DEADLINE, changedValues);
	}

	/**
	 * Logs the start of a process
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email: The HPI-Email-Address of the user, who changed the deadline;
	 * @param processName: The process Name, which has been started 
	 * @throws ProcessIdentifierException if the processIdentifier is not valid
	 */
	public void logStartedProcess(ProcessIdentifier processIdentifier, String email, String processName)
			throws ProcessIdentifierException {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.PROCESS_NAME, processName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(processIdentifier, changeDate, email,LogDescriptions.PROCESS_STARTED, changedValues);
	}
	
	/**
	 * Logs a voting condition, which was added to a process
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email: The HPI-Email-Address of the user, who added the condition
	 * @param conditions: a String, which describes the conditions
	 * @throws ProcessIdentifierException if the processIdentifier is not valid
	 */
	
	public void logVotingConditions(ProcessIdentifier processIdentifier, String email, String conditions) 
			throws ProcessIdentifierException {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.CONDITIONS, conditions);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(processIdentifier, changeDate, email,LogDescriptions.SET_VOTING_CONDITIONS, changedValues);
	}
	
	/**
	 * creates a JSON Object, which can be stored in the Log-Table
	 * @param deadlineType: The deadline, which has been set (definied in the Class Constants);
	 * @param deadline: the Date to which the deadline was changed; 
	 * @return a JSON-Object with the process name
	 */
	private JSONObject createDeadlineJSON(String deadlineType, Date deadline) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.DEADLINE_TYPE, deadlineType);
			jsonObject.put(JSONFields.DEADLINE, deadline.getTime());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
