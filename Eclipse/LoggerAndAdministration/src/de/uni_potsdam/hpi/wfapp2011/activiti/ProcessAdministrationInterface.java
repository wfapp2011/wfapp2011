package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.util.Date;
import java.util.HashMap;

import de.uni_potsdam.hpi.wfapp2011.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

public interface ProcessAdministrationInterface {
	
	public boolean startProcess(ProcessIdentifier processIdentifier)  throws ActivitiProcessException, SQLTableException;
	public boolean[] changedDeadlines(ProcessIdentifier processIdentifier);
	public HashMap<String, Date> loadDeadlinesFromDatabase(ProcessIdentifier processIdentifier);
	
}
