package de.uni_potsdam.hpi.wfapp2011.constants;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.uni_potsdam.hpi.wfapp2011.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;


public class ProcessIdAdministration {
	
	/**
	 * This Methods gets the executionId for the activiti instance of the process. 
	 * @return the executionId, null if it can't get the execution id.
	 */
	public static String getExecutionId(ProcessIdentifier pId){
		DbInterface dbInterface = new DbInterface();
		String executionId = null;
		if (!pId.isComplete()){
			return null;
		}
		String query = "SELECT value FROM configurations WHERE name = '"+Constants.PROCESS_ID_VARIABLE_NAME+"';";
		try {
			dbInterface.connect(pId.getType(), pId.getSemester(), pId.getYear());
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
		return executionId;
	}
	
	public static void setExecutionId(ProcessIdentifier pId) {
		
	}
	
}
