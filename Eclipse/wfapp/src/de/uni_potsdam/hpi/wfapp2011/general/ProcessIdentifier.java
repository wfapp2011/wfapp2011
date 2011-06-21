package de.uni_potsdam.hpi.wfapp2011.general;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.uni_potsdam.hpi.wfapp2011.Logging.DbInterface;

public class ProcessIdentifier {
	private DbInterface dbInterface;
	private String type;
	private String semester;
	private int year;
	private String executionId;
	
	public ProcessIdentifier(String type, String semester, int year){
		this.type = type;
		this.semester = semester;
		this.year = year;
		dbInterface = new DbInterface();
	}
	
	public ProcessIdentifier(String type, String semester, int year, String executionId){
		this.type = type;
		this.semester = semester;
		this.year = year;
		dbInterface = new DbInterface();
		this.executionId = executionId;
	}
	
	/**
	 * This Methods gets the executionId for the activiti instance of the process. 
	 * @return the executionId, null if it can't get the execution id.
	 */
	public String getExecutionId(){
		if(executionId == null ){
			// Has to replaced, if the table is known, where to save it. 
			String query = "SELECT property FROM table1 WHERE name = 'executionId'";
			try {
				dbInterface.connect(type, semester, year);
				ResultSet resultSet = dbInterface.executeQueryDirectly(query);
				while(resultSet.next()){
					executionId = resultSet.getString(1);
				}	
				dbInterface.disconnect();
			} catch (SQLException e) {
				System.out.println("Error getting the ExecutionId "+ e.getMessage());
				e.printStackTrace();
			}	
		}
		return executionId;
	}
	
	

}
