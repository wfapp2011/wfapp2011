package de.uni_potsdam.hpi.wfapp2011.proposals.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Person implements IsSerializable {

	private int id;
	private String name;
	private String email;

	public Person() {
	}

	public Person(String name, String email) {
		this.setName(name);
		this.setEmail(email);
	}

	public String getNameAndMailOrDefault() {
		String cp_name = "kein Name angegeben";
		String cp_email = "keine E-Mail-Adresse angegeben";
		if (name.trim().length() > 0) {
			cp_name = name;
		}
		if (email.trim().length() > 0) {
			cp_email = email;
		}
		return cp_name + ", " + cp_email;
	}

	// standard getters and setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
