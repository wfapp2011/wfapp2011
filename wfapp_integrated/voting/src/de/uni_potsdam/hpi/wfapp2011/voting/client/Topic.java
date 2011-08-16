package de.uni_potsdam.hpi.wfapp2011.voting.client;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <code>Topic</code> is a class which saves the information of one Topic. It is used
 * to send results of a database request per remote procedure call. Therefore it implements <code>Serializable</code>
 * 
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 11.54
 * @see java.io.Serializable
 */


public class Topic implements Serializable {
	private static final long serialVersionUID = 1L;
	private static int shortDescriptionLength = 150;
	private int projectID;
	private String name;
	private String projectShortCut;
	private String projectDescription;
	private int minStud;
	private int maxStud;
	private String department;
	private String file;
	private ArrayList<Person> contactPerson;

	/**
	 * standard constructor necessary for the <code>Serializable</code> interface
	 */
	public Topic(){
		projectID = 0;
		name = "";
		projectShortCut = "";
		projectDescription = "";
		minStud = 0;
		maxStud = 0;
		department = "";
		file = "";
		contactPerson = new ArrayList<Person>();
	}

	/**
	 * constructor which set  private attribute
	 * @param Name name of a project
	 */
	public Topic(String Name){
		projectID = 0;
		name = Name;
		projectShortCut = "";
		projectDescription = "";
		minStud = 0;
		maxStud = 0;
		department = "";
		file = "";
		contactPerson = new ArrayList<Person>();
	}
	
	/**
	 * constructor which sets all private attributes
	 * 
	 * @param id projectID
	 * @param Name project name
	 * @param Shortcut project shortcut
	 * @param description project description
	 * @param min_stud minimum of students in this project
	 * @param max_stud maximum of students in this project
	 * @param Department the department of this project
	 * @param pdf the pdf for this project
	 */
	public Topic(int id, String Name, String Shortcut, String description, int min_stud, int max_stud,
			String Department, String pdf){
		projectID = id;
		name = Name;
		projectShortCut = Shortcut;
		projectDescription = description;
		minStud = min_stud;
		maxStud = max_stud;
		department = Department;
		file = pdf;
		contactPerson = new ArrayList<Person>();
	}
	
	
	
	public ArrayList<Person> getcontactPerson() {
		return contactPerson;
	}
	
	/**
	 * add a contact person for a project
	 * 
	 * @param contactperson
	 */
	public void addcontactPerson(Person contactperson) {
		contactPerson.add(contactperson);
	}
	
	/**
	 * remove a contact person for a project
	 * 
	 * @param contactperson
	 */
	public void removecontactPerson(Person contactperson) {
		while (contactPerson.contains(contactperson))
			contactPerson.remove(contactPerson.indexOf(contactperson));
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectShortCut() {
		return projectShortCut;
	}

	public void setProjectShortCut(String projectShortCut) {
		this.projectShortCut = projectShortCut;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectShortDescription(){
		if (projectDescription.length() < shortDescriptionLength)
			return projectDescription;
		else {
			return projectDescription.substring(0, shortDescriptionLength) + "...";
		}
		
	}
	public int getMinStud() {
		return minStud;
	}

	public void setMinStud(int minStud) {
		this.minStud = minStud;
	}

	public int getMaxStud() {
		return maxStud;
	}

	public void setMaxStud(int maxStud) {
		this.maxStud = maxStud;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	

}
