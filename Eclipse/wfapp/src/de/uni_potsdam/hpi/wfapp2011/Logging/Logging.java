package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

public class Logging {
	private DbInterface dbConnection;
	String type;
	String semester; 
	int year;
	
	
	public Logging(String type, String semester, int year) {
		dbConnection = new DbInterface();
		this.type = type;
		this.semester = semester;
		this.year = year;
	}
	
	public void log(Date changeDate, String email, String description, String changedValues) {
		String timeString = ((Long)changeDate.getTime()).toString();
		try {
			dbConnection.connect(type, semester, year);
			dbConnection.executeUpdate("INSERT INTO logTable(changeDate, person, changeDescription, changedValues) VALUES ('"+ timeString+"','" +email+"','" +description+"','" + changedValues+"')");
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		dbConnection.disconnect();
	}
	

}
