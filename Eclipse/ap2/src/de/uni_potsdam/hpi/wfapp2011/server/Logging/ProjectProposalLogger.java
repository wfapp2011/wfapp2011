package de.uni_potsdam.hpi.wfapp2011.server.Logging;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import de.uni_potsdam.hpi.wfapp2011.server.constants.JSONFields;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifierException;

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
	private static ProjectProposalLogger theInstance;
	private Logging logging;
	
	private ProjectProposalLogger() {}
	
	public static ProjectProposalLogger getInstance() {
		if(theInstance == null){
			theInstance = new ProjectProposalLogger();
			theInstance.logging = Logging.getInstance();
		}
		return theInstance;
	}

	/**
	 * logs, that a new Project Proposal has been created.
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email the HPI email address of the user, who created the project proposal
	 * @param projectName the Name of the created project
	 * @param department the department, which created the project proposal
	 * @throws ProcessIdentifierException if the processIdentifier is not valid 
	 */
	public void logNewProjectProposal(ProcessIdentifier processIdentifier, String email, String projectName, String department) throws ProcessIdentifierException {
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.PROJECT_NAME, projectName);
			jsonObject.put(JSONFields.DEPARTMENT, department);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = jsonObject.toString();
		logging.log(processIdentifier, changeDate, email,LogDescriptions.NEW_PROJECT, changedValues);
	}
	
	/**
	 * logs, that a Project Proposal has been updated.
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email the HPI email address of the user, who updated the project proposal
	 * @param projectName the Name of the created project
	 * @throws ProcessIdentifierException if the processIdentifier is not valid 
	 */
	public void logChangedProjectProposal(ProcessIdentifier processIdentifier, String email, String projectName) throws ProcessIdentifierException{
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.PROJECT_NAME, projectName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = jsonObject.toString();
		logging.log(processIdentifier, changeDate, email,LogDescriptions.CHANGED_PROJECT, changedValues);
	}
	
	/**
	 * logs, that a Project Proposal has been renamed.
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email the HPI email address of the user, who updated the project proposal
	 * @param oldProjectName the Name the project proposal has had before
	 * @param newProjectName the new name of the project proposal
	 * @throws ProcessIdentifierException if the processIdentifier is not valid 
	 */
	public void logChangedProposalName(ProcessIdentifier processIdentifier, String email, String oldProjectName, String newProjectName) throws ProcessIdentifierException{
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.OLD_PROJECT_NAME, oldProjectName);
			jsonObject.put(JSONFields.NEW_PROJECT_NAME, newProjectName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = jsonObject.toString();
		logging.log(processIdentifier, changeDate, email,LogDescriptions.CHANGED_PROJECT_NAME, changedValues);
	}
	
	/**
	 * logs, that a file has been uploaded for the given project Name
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email the HPI email address of the user, who uploaded the file
	 * @param projectName the Name of the project for which the file has been uploaded
	 * @param filename the filename of the uploaded file
	 * @throws ProcessIdentifierException if the processIdentifier is not valid 
	 */
	public void logFileUpload(ProcessIdentifier processIdentifier, String email, String projectName, String filename) throws ProcessIdentifierException{
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.PROJECT_NAME, projectName);
			jsonObject.put(JSONFields.FILENAME, filename);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = jsonObject.toString();
		logging.log(processIdentifier, changeDate, email,LogDescriptions.FILE_UPLOAD, changedValues);
	}
	
	
	/**
	 * logs, that the project proposals have been exported, 
	 * so that they can be used for the voting phase
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param email:  the HPI email address of the user, who exported the project
	 * @param processName: name of the procecss 
	 * @throws ProcessIdentifierException if the processIdentifier is not valid 
	 */
	public void logSelectedProjectsExport(ProcessIdentifier processIdentifier, String email, String processName) throws ProcessIdentifierException{
		Date changeDate = new Date();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(JSONFields.PROCESS_NAME, processName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String changedValues = jsonObject.toString();
		logging.log(processIdentifier, changeDate, email,LogDescriptions.PROJECTS_EXPORTED, changedValues);
	}
}