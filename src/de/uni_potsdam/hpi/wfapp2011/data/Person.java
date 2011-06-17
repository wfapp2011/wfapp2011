package de.uni_potsdam.hpi.wfapp2011.data;

public class Person {

	private String name;
	private String email;
	
	public Person(String name, String email){
		this.setName(name);
		this.setEmail(email);
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
