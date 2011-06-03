package de.uni_potsdam.hpi.wfapp2011.client;

public class Person {
	private int personID;
	private String name;
	private String email;
	private String role;
	private String department;
	
	public Person(){
		personID = 0;
		name = "";
		email = "";
		role = "";
		department = "";
	}
	
	public Person(int id, String Name, String Email, String Role, String dempartment){
		personID = id;
		name = Name;
		role = Role;
		email = Email;
	}

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
}
