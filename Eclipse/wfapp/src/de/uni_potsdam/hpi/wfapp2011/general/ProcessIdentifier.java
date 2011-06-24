package de.uni_potsdam.hpi.wfapp2011.general;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.uni_potsdam.hpi.wfapp2011.Logging.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.Logging.SQLTableException;

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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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
			} catch (SQLTableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return executionId;
	}
	
	
	
	

}
