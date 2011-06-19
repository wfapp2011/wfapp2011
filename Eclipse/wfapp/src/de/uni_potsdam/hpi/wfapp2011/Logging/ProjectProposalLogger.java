package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Logger for the Project Proposal Phase of the process
 * 
 * This class is used to log events from the 
 * Project Proposal Phase of the process. 
 * It converts the changed data into a JSON Object,
 * so that they can be saved together in the Log-Table
 *
 */

public class ProjectProposalLogger implements ProjectProposalLoggerInterface {
	private Logging logging;
	
	public ProjectProposalLogger(String type, String semester, int year) {
		logging = new Logging(type, semester, year);
	}
	
	/**
	 * logs, that a new Project Proposal has been created.
	 * @param email the HPI email address of the user, who created the project proposal
	 * @param projectName the Name of the created project
	 * @param department the department, which created the project proposal
	 */
	public void logNewProjectProposal(String email, String projectName, String department) {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("projectName", projectName);
			jsonObject.put("department", department);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = jsonObject.toString();
		logging.log(changeDate, email,LogDescriptions.NEW_PROJECT, changedValues);
	}
	
	/**
	 * logs, that a Project Proposal has been updated.
	 * @param email the HPI email address of the user, who updated the project proposal
	 * @param projectName the Name of the created project
	 */
	public void logChangedProjectProposal(String email, String projectName){
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("projectName", projectName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = jsonObject.toString();
		logging.log(changeDate, email,LogDescriptions.CHANGED_PROJECT, changedValues);
	}
	
	/**
	 * logs, that a Project Proposal has been renamed.
	 * @param email the HPI email address of the user, who updated the project proposal
	 * @param oldProjectName the Name the project proposal has had before
	 * @param newProjectName the new name of the project proposal
	 */
	public void logChangedProposalName(String email, String oldProjectName, String newProjectName){
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("oldProjectName", oldProjectName);
			jsonObject.put("newProjectName", newProjectName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = jsonObject.toString();
		logging.log(changeDate, email,LogDescriptions.CHANGED_PROJECT_NAME, changedValues);
	}
	
	/**
	 * logs, that a file has been uploaded for the given project Name
	 * @param email the HPI email address of the user, who uploaded the file
	 * @param projectName the Name of the project for which the file has been uploaded
	 * @param filename the filename of the uploaded file
	 */
	public void logFileUpload(String email, String projectName, String filename){
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("projectName", projectName);
			jsonObject.put("filename", filename);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = jsonObject.toString();
		logging.log(changeDate, email,LogDescriptions.FILE_UPLOAD, changedValues);
	}
	
	
	/**
	 * logs, that the project proposals have been exported, 
	 * so that they can be used for the voting phase
	 * @param email:  the HPI email address of the user, who exported the project
	 * @param processName: name of the procecss 
	 */
	public void logSelectedProjectsExport(String email, String processName){
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("processName", processName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = jsonObject.toString();
		logging.log(changeDate, email,LogDescriptions.PROJECTS_EXPORTED, changedValues);
	}
}
