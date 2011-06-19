package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class VotingLogger implements VotingLoggerInterface {
	// refactoring has to be done!!
	private Logging logging;
	public VotingLogger(String type, String semester, int year) {
		logging = new Logging(type, semester, year);
	}
	
	public void logStudentLogin(String email) {
		Date changeDate = new Date();
		logging.log(changeDate, email,"studentLogin", "");
	}
	
	public void logNewVote(String email, String[] projectNames) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("projectNames", projectNames);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		
		logging.log(changeDate, email,"newVoting", changedValues);
	}
	
	public void logChangedVote(String email, String[] projectNames) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("projectNames", projectNames);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,"changedVoting", changedValues);
	}
	
}
