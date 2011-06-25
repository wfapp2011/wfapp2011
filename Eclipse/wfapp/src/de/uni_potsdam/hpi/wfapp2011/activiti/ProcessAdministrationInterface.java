package de.uni_potsdam.hpi.wfapp2011.activiti;

import de.uni_potsdam.hpi.wfapp2011.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

public interface ProcessAdministrationInterface {
	
	public boolean startProcess(ProcessIdentifier processIdentifier)  throws ActivitiProcessException, SQLTableException;
	public boolean[] changedDeadlines(ProcessIdentifier processIdentifier);
	
}
