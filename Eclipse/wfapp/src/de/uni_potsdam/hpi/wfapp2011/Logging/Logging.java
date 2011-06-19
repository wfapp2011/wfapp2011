package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;


/**
 * General Logging Class, which is able to write the Log-Data in the Database. 
 *
 */
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
	
	/**
	 * This Method logs the given data in the database
	 * 
	 * @param changeDate the time of the changes
	 * @param email the email from the user, who has done the changes
	 * @param description: describes, which event has triggered the logging
	 * @param changedValues: The values, which has been changed as a JSON-String
	 */
	
	public void log(Date changeDate, String email, String description, String changedValues) {
		String timeString = ((Long)changeDate.getTime()).toString();
		try {
			dbConnection.connect(type, semester, year);
			dbConnection.executeUpdate("INSERT INTO logTable(changeDate, person, changeDescription, changedValues) " +
					"VALUES ('"+ timeString+"','" +email+"','" +description+"','" + changedValues+"')");
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		dbConnection.disconnect();
	}
	

}
