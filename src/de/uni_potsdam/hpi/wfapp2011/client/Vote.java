package de.uni_potsdam.hpi.wfapp2011.client;

import java.io.Serializable;

public class Vote implements Serializable {
	private static final long serialVersionUID = 1L;
	private int personID;
	private	int priority;
	private int projectID;
	private String projectName;
		
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Vote() {
		personID = 0;
		priority = 0;
		projectID = 0;
	}
	
	public Vote(int personID, int priority, int projectID, String projectName) {
		super();
		this.personID = personID;
		this.priority = priority;
		this.projectID = projectID;
		this.projectName = projectName;
	}
	
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

}
