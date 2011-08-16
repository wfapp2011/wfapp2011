package de.uni_potsdam.hpi.wfapp2011.proposals.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Department implements IsSerializable{

	private int id;
	private String name;
	private String symbol;
	private Person prof;
	
	public Department(){
	}
	
	public Department(String departmentName) {		
		this.setName(departmentName);
	}
	
	// standard getters and setters
	public void setProf(Person prof) {
		this.prof = prof;
	}
	
	public Person getProf() {
		return prof;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
