package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import java.io.Serializable;

/**
 * Object representing a student and information on which project he/she is assigned to.
 * Relevant Data (name, email and preferred projects) is stored.
 */
public class Student implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String name, email;
	public String[] votes;
	public Project placement;
	private String placementID;
	
	/**
	 * Default constructor, creates an empty Student object.
	 */
	public Student() {
		this.name = "";
		this.email = "";
		this.votes = new String[1];
	}
	
	/**
	 * Standard constructor.
	 * 
	 * @param name 			a String representing the student's first name(s) and last name
	 * @param mail			a String representing the student's email address
	 * @param votes			an array of Strings (must be valid project IDs) with as many entries as votes were permitted
	 * @param placementID	the ID (String) of the project the student is currently assigned to, null if not placed yet
	 */
	public Student(String name, String mail, String[] votes, String placementID){
		this.name = name;
		this.email = mail;
		this.votes = votes;
		this.placementID = placementID;
	}
	
	/**
	 * Finds and stores the <code>Project</code> object that <code>placementID</code> references
	 */
	public void initPlacement(){
		if (this.placementID != null){
			for (Project pr : AP5_main.DBProjects){
				if(this.placementID.equals(pr.projectID)) this.placement = pr;
			}
		}
	}

	/**
	 * Finds out at which priority the student has voted a certain <code>Project</code>.
	 * 
	 * @param ProjectID the name / ID of the project that the vote should be found for
	 * @return the priority at which the project was voted or 0 if not present in the votes
	 */
	public int findVote(String ProjectID) {
		for (int i=0; i<votes.length; i++){
			if(votes[i].equals(ProjectID)) {
				return i + 1;
			}
		}
		return 0;
	}
}
