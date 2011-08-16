package de.uni_potsdam.hpi.wfapp2011.servercore.logging;

import java.util.Date;

import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;


/**
 * Logging Class, which is able to write the Log-Data in the Database.
 * 
 * @author Jannik Marten, Yanina Yurchenko
 *
 */
public class Logging {
	private static Logging theInstance;
	private DbInterface dbConnection;
	
	public static Logging getInstance(){
		if(theInstance == null){
			theInstance = new Logging();
			theInstance.dbConnection = new DbInterface(); 
		}
		return theInstance;
	}
	
//	public Logging(String type, String semester, int year) {
//		dbConnection = new DbInterface();
//		this.type = type;
//		this.semester = semester;
//		this.year = year;
//	}
	
	/**
	 * inserts the given Log-data in the database
	 * 
	 * @param processIdentifier: ProcessIdentifier, which identifies the belonging process
	 * @param changeDate the time of the changes
	 * @param email the HPI-Email-Address from the user, who has done the changes
	 * @param description: describes, which event has triggered the logging
	 * @param changedValues: The values, which has been changed as a JSON-String
	 * @throws ProcessIdentifierException if the processIdentifier is not valid
	 */
	
	public void log(String type, String semester, int year, Date changeDate, String email, String description, String changedValues)  {
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
