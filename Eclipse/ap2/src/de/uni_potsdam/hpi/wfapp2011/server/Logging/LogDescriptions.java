package de.uni_potsdam.hpi.wfapp2011.server.Logging;

import java.util.HashMap;

public class LogDescriptions {
	public static final HashMap<String, String[]> logDescriptions = new HashMap<String, String[]>();
	public static final String[] newDeadline = {"deadlineType", "deadline"};
	public static final String[] changedDeadline = {"deadlineType", "deadline"};
	public static final String[] processStarted= {"processName"};
	public static final String[] setVotingConditions = {"conditions"};
	public static final String[] newProject = {"projectName", "department"};
	public static final String[] changedProject = {"projectName"};
	public static final String[] changedProjectName = {"olProjectName", "newProjectName"};
	public static final String[] fileUpload = {"projectName", "filename"};
	public static final String[] projectsExported = {"processName"};
	public static final String[] studentLogin = {};
	public static final String[] newVoting = {"projectNames"};
	public static final String[] changedVoting = {"projectNames"};
	public static final String[] matchingExecuted = {"processName"};
	public static final String[] matchingChanged = {"email", "projectName"};
	public static final String[] matchingCompleted = {};
	
	
	// Variables describing the different types of Events, which will be logged
	public static String NEW_DEADLINE = "newDeadline";
	public static String CHANGED_DEADLINE = "changedDeadline";
	public static String PROCESS_STARTED = "processStarted";
	public static String SET_VOTING_CONDITIONS = "setVotingConditions";
	public static String MATCHING_EXECUTED = "matchingExecuted";
	public static String MATCHING_CHANGED = "matchingChanged";
	public static String MATCHING_COMPLETED = "matchingCompleted";
	public static String NEW_PROJECT = "newProject";
	public static String CHANGED_PROJECT = "changedProject";
	public static String CHANGED_PROJECT_NAME = "changedProjectName";
	public static String FILE_UPLOAD = "fileUpload";
	public static String PROJECTS_EXPORTED = "projectsExported";
	public static String STUDENT_LOGIN = "studentLogin";
	public static String NEW_VOTING = "newVoting";
	public static String CHANGED_VOTING = "changedVoting";
	
	
	
	
	static {
		logDescriptions.put("newDeadline", newDeadline);
		logDescriptions.put("changedDeadline", changedDeadline);
		logDescriptions.put("processStarted",processStarted );
		logDescriptions.put("setVotingConditions", setVotingConditions);
		logDescriptions.put("newProject", newProject);
		logDescriptions.put("changedProject", changedProject);
		logDescriptions.put("changedProjectName", changedProjectName);
		logDescriptions.put("fileUpload", fileUpload);
		logDescriptions.put("projectsExported", projectsExported);
		logDescriptions.put("studentLogin", studentLogin);
		logDescriptions.put("newVoting", newVoting);
		logDescriptions.put("changedVoting", changedVoting);
		logDescriptions.put("matchingExecuted", matchingExecuted);
		logDescriptions.put("matchingChanged", matchingChanged);
		logDescriptions.put("matchingCompleted", matchingCompleted);
	}
	
}
