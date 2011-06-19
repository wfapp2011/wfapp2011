package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class LoggingReader {
	private DbInterface dbConnection;
	String type;
	String semester; 
	int year;
	
	
	public LoggingReader(String type, String semester, int year) {
		dbConnection = new DbInterface();
		this.type = type;
		this.semester = semester;
		this.year = year;
	}
	
	public int getNumberOfVotings() {
		int numberOfVotings = -1;
		String sql = "SELECT COUNT(*) as number FROM logTable WHERE changedescription = 'newVoting';";
		
		try {
			dbConnection.connect(type, semester, year);
			ResultSet resultset = dbConnection.executeQueryDirectly(sql);
			if(resultset.next()) {
				numberOfVotings = resultset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return numberOfVotings;
	}
	
	public int getNumberOfProjectProposals() {
		int numberOfVotings = -1;
		String sql = "SELECT COUNT(*) as number FROM logTable WHERE changedescription = 'newProject'";
		
		try {
			dbConnection.connect(type, semester, year);
			ResultSet resultset = dbConnection.executeQueryDirectly(sql);
			if(resultset.next()) {
				numberOfVotings = resultset.getInt(1);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return numberOfVotings;
	}
	/**
	 *  This Method returns all Votings of the specified student.
	 *  The email is the regular HPI-Email-Address. 
	 *  The String-Array is a Logtable-Entry and contains the 
	 *  changed-Date, person, description and the Votings as JSON
	 * 
	 * @param email of the user(HPI-Email-Address), whose votings should be returned. 
	 * @return a Collection, which contains all votings of the specified Student.The String[] contains the Log-Data for one Voting
	 */
	public Collection<String[]> getVotingsOf(String email) {
		
		String sql = "SELECT * FROM logTable WHERE " +
			"(descriptions = 'newVoting' OR descriptions = 'changedVoting') AND person = '" +email+"'";
		Collection<String[]> votingsOfStudent = new ArrayList<String[]>();
		
		try {
			dbConnection.connect(type, semester, year);
			ResultSet resultset = dbConnection.executeQueryDirectly(sql);
			while(resultset.next()){
				String[] tuple = new String[4];
				for(int i = 0; i<4; i++){
					tuple[i] = resultset.getString(i+1);
				}
				votingsOfStudent.add(tuple);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return votingsOfStudent;
	}
	
	
	
	public Collection<String[]> getLog(Date fromDate, Date untilDate){
		String sql = "SELECT * FROM tableLog " +
			"WHERE CAST(changedDate AS BIGINT) >= '"+fromDate.getTime() +
			"' AND CAST(changedDate AS BIGINT) <= '"+untilDate.getTime()+"'";
		Collection<String[]> log = new ArrayList<String[]>();
		try {
			dbConnection.connect(type, semester, year);
			ResultSet resultset = dbConnection.executeQueryDirectly(sql);
			while(resultset.next()){
				String[] tuple = new String[4];
				for(int i = 0; i<4; i++){
					tuple[i] = resultset.getString(i+1);
				}
				log.add(tuple);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return log;
	}
}
