package de.uni_potsdam.hpi.wfapp2011.Logging;

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
