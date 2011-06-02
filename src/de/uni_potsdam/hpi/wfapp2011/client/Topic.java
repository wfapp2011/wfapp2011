package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;

public class Topic {
	private static int shortDescriptionLength = 150;
	private String projectID;
	private String name;
	private String projectShortCut;
	private String projectDescription;
	private int minStud;
	private int maxStud;
	private String department;
	private String file;
	private ArrayList<Person> contactPerson;
	
	/*public static ArrayList<Topic> loadTopics(DbInterface database, String Semester)
	{
		ArrayList<Topic> result = new ArrayList<Topic>();
		
		database.connect();
		Collection<Map<String, String>> topics = database.executeQuery("SELECT * FROM TOPIC WHERE SEMESTER='"+Semester+"'");
		
		
		for (Map<String,String> entry: topics)
		{
			Topic newTopic = new Topic(	entry.get("projectid"),
										entry.get("projectname"),
										entry.get("projectshortcut"),
										entry.get("projectdescription"),
										new Integer(entry.get("minstud")),
										new Integer(entry.get("maxstud")),
										entry.get("department"),
										entry.get("file"));
			
			Collection<Map<String,String>> contactPersons = database.executeQuery("SELECT P.PERSONID, P.NAME, P.EMAIL, P.ROLE, P.DEPARTMENT FROM PERSON P, CONTACTPERSON C WHERE P.PERSONID=C.PERSONID AND C.PROJECTID='"+newTopic.getProjectID()+"'");
			for (Map<String,String> personEntry: contactPersons)
			{
				Person newPerson = new Person(	new Integer(personEntry.get("personid")),
												personEntry.get("name"),
												personEntry.get("email"),
												personEntry.get("role"),
												personEntry.get("department"));
				newTopic.addcontactPerson(newPerson);
			}
			
			result.add(newTopic);			
		}
		
		database.disconnect();
		return result;
	}*/
	
	public Topic(){
		projectID = "";
		name = "";
		projectShortCut = "";
		projectDescription = "";
		minStud = 0;
		maxStud = 0;
		department = "";
		file = "";
		contactPerson = new ArrayList<Person>();
	}
	
	public Topic(String id, String Name, String Shortcut, String description, int min_stud, int max_stud,
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
	public void addcontactPerson(Person contactperson) {
		contactPerson.add(contactperson);
	}
	
	public void removecontactPerson(Person contactperson) {
		while (contactPerson.contains(contactperson))
			contactPerson.remove(contactPerson.indexOf(contactperson));
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
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
