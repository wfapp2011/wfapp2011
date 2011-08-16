package de.uni_potsdam.hpi.wfapp2011.voting.client;

// IMPORTS
import java.io.Serializable;

/**
 * <code>Person</code> is a class which saves the information of one person. It is used
 * to send results of a database request per remote procedure call. Therefore it implements <code>Serializable</code> 
 * 
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 11:23
 * @see java.io.Serializable
 */
public class Person implements Serializable{
	private static final long serialVersionUID = 1L;
	private int personID;
	private String name;
	private String email;
	private String role;
	private String department;
	
	/**
	 * standard constructor necessary for the <code>Serializable</code> interface
	 */
	public Person(){
		personID = 0;
		name = "";
		email = "";
		role = "";
		department = "";
	}
	
	/**
	 * constructor which sets all private attributes
	 * @param id user id
	 * @param Name user name
	 * @param Email e-mail adress
	 * @param Role role of the user
	 * @param dempartment department of the user
	 */
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
