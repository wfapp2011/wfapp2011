package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class ProjectProposalLogger implements ProjectProposalLoggerInterface {
	// refactoring has to be done!!
	private Logging logging;
	public ProjectProposalLogger() {
		logging = new Logging();
	}
	
	public void logNewProjectProposal(String email, String projectName, String department) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("projectName", projectName);
			jsonObject.put("department", department);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,"newProject", changedValues);
	}
	
	public void logChangedProjectProposal(String email, String projectName){
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
		logging.log(changeDate, email,"changedProject", changedValues);
	}
	
	public void logChangedProposalName(String email, String oldProjectName, String newProjectName){
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("oldProjectName", oldProjectName);
			jsonObject.put("newProjectName", newProjectName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,"changedProjectName", changedValues);
	}
	public void logFileUpload(String email, String projectName, String filename){
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("projectName", projectName);
			jsonObject.put("filename", filename);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String changedValues = null;
		changedValues = jsonObject.toString();
		logging.log(changeDate, email,"fileUpload", changedValues);
	}
	
	public void logSelectedProjectsExport(String email, String processName){
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
		logging.log(changeDate, email,"projectsExported", changedValues);
	}
}
