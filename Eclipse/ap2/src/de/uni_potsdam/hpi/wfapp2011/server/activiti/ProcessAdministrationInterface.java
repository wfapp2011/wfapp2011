package de.uni_potsdam.hpi.wfapp2011.server.activiti;

import de.uni_potsdam.hpi.wfapp2011.server.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifier;

public interface ProcessAdministrationInterface {
	
	public boolean startProcess(ProcessIdentifier processIdentifier)  throws ActivitiProcessException, SQLTableException;
	public boolean[] changedDeadlines(ProcessIdentifier processIdentifier);
	
}
