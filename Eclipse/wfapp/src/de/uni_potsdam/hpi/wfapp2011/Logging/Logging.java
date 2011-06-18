package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

public class Logging {
	private DbInterface dbConnection;
	
	public Logging() {
		dbConnection = new DbInterface();
		dbConnection.connect();
	}
	
	public void log(Date changeDate, String email, String description, String changedValues) {
		String timeString = ((Long)changeDate.getTime()).toString();
		try {
			dbConnection.executeUpdate("INSERT INTO logTable(changeDate, person, changeDescription, changedValues) VALUES ('"+ timeString+"','" +email+"','" +description+"','" + changedValues+"')");
		} catch (TableAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}