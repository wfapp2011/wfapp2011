package de.uni_potsdam.hpi.wfapp2011.servercore.constants;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;

/**
 * Class to get general informations about a process.
 * 
 * @author Jannik Marten, Yanina Yurchenko
 *
 */

public class ProcessIdAdministration {
	
	/**
	 * Gets the executionId for the activiti instance of the process.<br/>
	 * Therefore it checks the configurations table of the database. 
	 *  
	 * @return the executionId, null if it can't get the execution id.
	 */
	public static String getExecutionId(String type, String semester, int year){
		DbInterface dbInterface = new DbInterface();
		String executionId = null;

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
		return executionId;
	}
	
	
}
