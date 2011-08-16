package de.uni_potsdam.hpi.wfapp2011.voting.client;

import java.io.Serializable;

/**
 * <code>Vote</code> is a class which saves the information of one vote. It is used
 * to send results of a database request per remote procedure call. Therefore it implements <code>Serializable</code> 
 * 
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 13.15
 * @see java.io.Serializable
 */
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

	/**
	 * standard constructor necessary for the <code>Serializable</code> interface
	 */
	public Vote() {
		personID = 0;
		priority = 0;
		projectID = 0;
	}
	
	/**
	 * constructor which sets all private attributes
	 * @param personID the ID of the person who voted
	 * @param priority priority of the vote
	 * @param projectID projectID of the vote
	 * @param projectName project name of the vote
	 */
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
