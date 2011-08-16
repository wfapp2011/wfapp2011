package de.uni_potsdam.hpi.wfapp2011.servercore.activiti;

import java.util.Date;
import java.util.HashMap;

import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;

/**
 * Interface which defines the methods to manage the process. <br/>
 * 
 * @author Jannik Marten, Yanina Yurchenko
 * 
 *
 */

public interface ProcessAdministrationInterface {
	
	public boolean startProcess(String type, String semester, int year)  throws ActivitiProcessException, SQLTableException;
	public boolean[] changedDeadlines(String type, String semester, int year);
	public HashMap<String, Date> loadDeadlinesFromDatabase(String type, String semester, int year);
	
}
