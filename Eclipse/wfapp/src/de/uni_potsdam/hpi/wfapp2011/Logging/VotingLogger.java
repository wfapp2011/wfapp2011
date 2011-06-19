package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Logger for the Voting Phase of the process
 * 
 * This class is used to log events from the 
 * Voting-Phase of the process. 
 * It converts the changed data into a JSON Object,
 * so that they can be saved together in the Log-Table
 *
 */
public class VotingLogger implements VotingLoggerInterface {
	private Logging logging;
	
	public VotingLogger(String type, String semester, int year) {
		logging = new Logging(type, semester, year);
	}
	
	
	public void logStudentLogin(String email) {
		Date changeDate = new Date();
		logging.log(changeDate, email,LogDescriptions.STUDENT_LOGIN, "");
	}
	
	/**
	 * Logs a new vote of the specified student
	 * @param email: HPI-Email of the student.
	 * @param projectNames: a String-Array with the votings.
	 * 					the first entry has the highest priority
	 */
	public void logNewVote(String email, String[] projectNames) {
		Date changeDate = new Date();
		String changedValues = createVotingJSON(projectNames).toString();
		logging.log(changeDate, email,LogDescriptions.NEW_VOTING, changedValues);
	}
	
	/**
	 * Logs a changed vote of the specified student
	 * @param email: HPI-Email of the student.
	 * @param projectNames: a String-Array with the votings.
	 * 					the first entry has the highest priority
	 */
	public void logChangedVote(String email, String[] projectNames) {
		Date changeDate = new Date();
		String changedValues = createVotingJSON(projectNames).toString();
		logging.log(changeDate, email,LogDescriptions.CHANGED_VOTING, changedValues);
	}

	
	private JSONObject createVotingJSON(String[] projectNames) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("projectNames", projectNames);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
}
