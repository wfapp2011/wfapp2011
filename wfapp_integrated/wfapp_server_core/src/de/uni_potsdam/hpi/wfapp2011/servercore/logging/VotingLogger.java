package de.uni_potsdam.hpi.wfapp2011.servercore.logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import de.uni_potsdam.hpi.wfapp2011.servercore.constants.JSONFields;


/**
 * Logger for the Voting Phase of the process
 * 
 * This class is used to log events from the 
 * Voting-Phase of the process. 
 * It converts the changed data into a JSON Object,
 * so that they can be saved together in the Log-Table
 *
 * @author Jannik Marten, Yanina Yurchenko
 * 
 */
public class VotingLogger implements VotingLoggerInterface {
	private static VotingLogger theInstance;
	private Logging logging;
	
	private VotingLogger() {}
	
	public static VotingLogger getInstance(){
		if(theInstance == null){
			theInstance = new VotingLogger();
			theInstance.logging = Logging.getInstance();
		}
		return theInstance;
	}
		
	/**
	 * Logs a new Student login
	 * @param email: The HPI-Email-Address of the student.
	 * @throws ProcessIdentifierException if the processIdentifier is not valid  
	 */
	public void logStudentLogin(String type, String semester, int year, String email)   {
		Date changeDate = new Date();
		logging.log(type,  semester,  year, changeDate, email,LogDescriptions.STUDENT_LOGIN, "");
	}
	
	/**
	 * Logs a new vote of the specified student
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email: HPI-Email of the student.
	 * @param projectNames: a String-Array with the votings.
	 * 					the first entry has the highest priority
	 * @throws ProcessIdentifierException if the processIdentifier is not valid  
	 */
	public void logNewVote(String type, String semester, int year, String email, String[] projectNames)   {
		Date changeDate = new Date();
		String changedValues = createVotingJSON(projectNames).toString();
		logging.log( type,  semester, year, changeDate, email,LogDescriptions.NEW_VOTING, changedValues);
	}
	
	/**
	 * Logs a changed vote of the specified student
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email: HPI-Email of the student.
	 * @param projectNames: a String-Array with the votings.
	 * 					the first entry has the highest priority
	 * @throws ProcessIdentifierException if the processIdentifier is not valid 
	 */
	public void logChangedVote(String type, String semester, int year , String email, String[] projectNames)   {
		Date changeDate = new Date();
		String changedValues = createVotingJSON(projectNames).toString();
		logging.log( type,  semester, year, changeDate, email,LogDescriptions.CHANGED_VOTING, changedValues);
	}

	/**
	 * Creates a JSON-Object with the Project-Names a Student has voted.
	 * @param projectNames: The names of the voted projects
	 * 						On the first position is the project with the highest priority
	 * @return JSON-Object with the Project-Names.
	 */
	private JSONObject createVotingJSON(String[] projectNames) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.PROJECT_NAMES, projectNames);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
}
