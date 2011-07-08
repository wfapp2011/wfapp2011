package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import de.uni_potsdam.hpi.wfapp2011.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

/**
 * Class to get log entries and some other informations from the log-Table in the database.
 *
 */

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
	
	public LoggingReader(ProcessIdentifier pId){
		dbConnection = new DbInterface();
		this.type = pId.getType();
		this.semester = pId.getSemester();
		this.year = pId.getYear();
	}
	
	/**
	 * counts the Number of students which have already voted.
	 * @return int with the number of votings;
	 * 			returns -1 in case of an Error.
	 */
	
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
			numberOfVotings = -1;
			e.printStackTrace();
		} catch (SQLTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return numberOfVotings;
	}
	
	/**
	 * counts the courrent number of project proposals
	 * @return int with the number of project proposals;
	 * 			returns -1 in case of an error.
	 */
	
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
			numberOfVotings =-1;
			e.printStackTrace();
		} catch (SQLTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return numberOfVotings;
	}
	/**
	 *  This Method returns all Votings of the specified student.
	 * 
	 * @param email the regular HPI-Email-Address of the student, whose votings should be returned. 
	 * @return a Collection, which contains all votings of the specified Student.
	 * 				returns an empty Collection, if there are no Votings.
	 * 				The String-Array is a Logtable-Entry and contains the 
	 *  			changed-Date, person, description and the Votings as JSON
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
		} catch (SQLTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return votingsOfStudent;
	}
	
	/**
	 * All log-Data inside the given period.
	 * @param fromDate: start of the period
	 * @param untilDate: end of the period
	 * @return a Collection all Log-Entries inside the period.
	 * 			returns an empty Collection, if there are no Log-Entries
	 * 			One Logtable-Entry is a String-Array, which 
	 * 			contains the changed-Date, person, description
	 * 			and changed Data as a JSON-String 
	 */
	
	public Collection<String[]> getLog(Date fromDate, Date untilDate){
		String sql = "SELECT * FROM logTable " +
			"WHERE CAST(changeDate AS BIGINT) >= '"+fromDate.getTime() +
			"' AND CAST(changeDate AS BIGINT) <= '"+untilDate.getTime()+"';";
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
		} catch (SQLTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return log;
	}
}
