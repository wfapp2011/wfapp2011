package de.uni_potsdam.hpi.wfapp2011.general;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.uni_potsdam.hpi.wfapp2011.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.database.SQLTableException;

/**
 * This class provides the functionality to identify a specific process.
 *
 */

public class ProcessIdentifier {
	private DbInterface dbInterface;
	private String type = null;
	private String semester = null;
	private int year = 0;
	private String executionId;
	
	public ProcessIdentifier(String type, String semester, int year){
		setType(type);
		setSemester(semester);
		setYear(year);
		dbInterface = new DbInterface();
	}
	
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of the process
	 * Only allows the Strings "Ba" or "Ma"
	 * @param semester "Ba" or "Ma"
	 */
	public void setType(String type) {
		if(type == "Ba" || type == "Ma"){
			this.type = type;
		}
	}

	public String getSemester() {
		return semester;
	}

	/**
	 * Sets the semester
	 * Only allows the Strings "SS" or "WS"
	 * @param semester "SS" or "WS"
	 */
	public void setSemester(String semester) {
		if(semester == "SS" || semester == "WS"){
			this.semester = semester;
		}
	}

	public int getYear() {
		return year;
	}

	/**
	 * Sets the year of the process
	 * @param year: The year which should be set
	 * 			Only allow 1 year in past and 5 years in future. 
	 */
	public void setYear(int year) {
		int currentYear= new GregorianCalendar().get(Calendar.YEAR); 
		if(year >= currentYear - 1 && year <= currentYear + 5 ){
			this.year = year;
		}
	}
	
	/**
	 * Checks if the Object has all required values
	 * @return boolean: true, if type, semester and type have allowed values;
	 */
	public boolean isComplete(){
		return !(year == 0 || semester == null || type == null);
	}

	/**
	 * This Methods gets the executionId for the activiti instance of the process. 
	 * @return the executionId, null if it can't get the execution id.
	 */
	public String getExecutionId(){
		if (!isComplete()){
			return null;
		}
		if(executionId == null){
			String query = "SELECT value FROM configurations WHERE name = '"+Constants.PROCESS_ID_VARIABLE_NAME+"';";
			try {
				dbInterface.connect(type, semester, year);
				ResultSet resultSet = dbInterface.executeQueryDirectly(query);
				while(resultSet.next()){
					executionId = resultSet.getString(1);
				}	
			} catch (SQLException e) {
				System.out.println("Error getting the ExecutionId "+ e.getMessage());
				e.printStackTrace();
			} catch (SQLTableException e) {
				e.printStackTrace();
			}
			dbInterface.disconnect();
		}
		return executionId;
	}
	
	public void setExecutionId(String id){
		executionId = id;
	}
}
