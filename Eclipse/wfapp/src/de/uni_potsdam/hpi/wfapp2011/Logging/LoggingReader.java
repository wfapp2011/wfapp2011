package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class LoggingReader {
	private DbInterface dbConnection;
	public LoggingReader() {
		dbConnection = new DbInterface();
		dbConnection.connect();
	}
	
	public int getNumberOfVotings() {
		//Collection<Map<String, String>> result =dbConnection.executeQuery("SELECT COUNT(*) as number FROM logTable WHERE changedescription = 'newVoting';");
		int numberOfVotings = -1;
		String sql = "SELECT COUNT(*) as number FROM logTable WHERE changedescription = 'newVoting';";
		ResultSet resultset = dbConnection.executeQuery1(sql);
		try {
			if(resultset.next()) {
				numberOfVotings = resultset.getInt(1);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberOfVotings;
//		@SuppressWarnings("unchecked")
//		Map<String, String> map = (Map<String, String>) (result.toArray())[0];
//		Integer numberOfVotings = new Integer(map.get("number"));
//		return numberOfVotings;
	}
	
	public int getNumberOfProjectProposals() {
		int numberOfVotings = -1;
		String sql = "SELECT COUNT(*) as number FROM logTable WHERE changedescription = 'newProject'";
		ResultSet resultset = dbConnection.executeQuery1(sql);
		try {
			if(resultset.next()) {
				numberOfVotings = resultset.getInt(1);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberOfVotings;
		
		/*Collection<Map<String, String>> result = dbConnection.executeQuery("SELECT COUNT(*) as number FROM logTable WHERE changedescription = 'newProject'");
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) (result.toArray())[0];
		Integer numberOfProjectProposals = new Integer(map.get("number"));
		return numberOfProjectProposals;*/
	}
	
	@SuppressWarnings("unchecked")
	public Collection<String[]> getVotingsOf(String email) {
		/** This Method returns all Votings of the specified student.
		 * The email is the regular HPI-Email-Address. 
		 * The String-Array is a Logtable-Entry and contains the changed-Date, person, description and the Votings as JSON **/
		String sql = "SELECT * FROM logTable WHERE " +
			"(descriptions = 'newVoting' OR descriptions = 'changedVoting') AND person = '" +email+"'";
		Collection<String[]> votingsOfStudent = new ArrayList<String[]>();
		ResultSet resultset = dbConnection.executeQuery1(sql);
		try {
			while(resultset.next()){
				String[] tuple = new String[4];
				for(int i = 0; i<4; i++){
					tuple[i] = resultset.getString(i+1);
				}
				votingsOfStudent.add(tuple);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return votingsOfStudent;
		
		
/*		Collection<Map<String, String>> result = dbConnection.executeQuery("SELECT * FROM logTable WHERE " +
				"(descriptions = 'newVoting' OR descriptions = 'changedVoting') AND person = '" +email+"'");
		Collection<String[]> votingsOfStudent = new ArrayList<String[]>();
		Iterator iterator = result.iterator();
		while(iterator.hasNext()){
			String date = ((Map<String, String>) iterator.next()).get("changedDate");
			String person = ((Map<String, String>) iterator.next()).get("person");
			String description = ((Map<String, String>) iterator.next()).get("changeDescription");
			String changedValue = ((Map<String, String>) iterator.next()).get("changedValues");
			String[] voting = {date, person, description, changedValue};
			votingsOfStudent.add(voting);		
		}
		return votingsOfStudent;*/
	}
	
	public Collection<String[]> getLog(Date fromDate, Date untilDate){
		String sql = "SELECT * FROM tableLog " +
		"WHERE CAST(changedDate AS BIGINT) >= '"+fromDate.getTime 	 	() +"' AND CAST(changedDate AS BIGINT) <= '"+untilDate.getTime()+"'";
		Collection<String[]> log = new ArrayList<String[]>();
		ResultSet resultset = dbConnection.executeQuery1(sql);
		try {
			while(resultset.next()){
				String[] tuple = new String[4];
				for(int i = 0; i<4; i++){
					tuple[i] = resultset.getString(i+1);
				}
				log.add(tuple);
			}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return log;
		
		
/*		Collection<Map<String, String>> result = dbConnection.executeQuery("SELECT * FROM tableLog " +
				"WHERE CAST(changedDate AS BIGINT) >= '"+fromDate.getTime 	 	() +"' AND CAST(changedDate AS BIGINT) <= '"+untilDate.getTime()+"'"); 
		Collection<String[]> log = new ArrayList<String[]>();
		Iterator iterator = result.iterator();
		while(iterator.hasNext()){
			String date = ((Map<String, String>) iterator.next()).get("changedDate");
			String person = ((Map<String, String>) iterator.next()).get("person");
			String description = ((Map<String, String>) iterator.next()).get("changeDescription");
			String changedValue = ((Map<String, String>) iterator.next()).get("changedValues");
			String[] logEntry= {date, person, description, changedValue};
			log.add(logEntry);		
		}
		return log;			
		*/
	}
	
	
	
}