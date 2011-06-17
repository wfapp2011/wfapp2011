package de.uni_potsdam.hpi.wfapp2011.data;

public class Department {

	private String name;
	private String symbol;
	private Person prof;
	
	
	public Department(String departmentName) {		
		this.setName(departmentName);
	}

	
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
	
}
