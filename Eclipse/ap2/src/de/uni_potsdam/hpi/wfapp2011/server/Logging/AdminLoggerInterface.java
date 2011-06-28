package de.uni_potsdam.hpi.wfapp2011.server.Logging;

import java.util.Date;

import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifierException;

/**
 * Interface for the AdminLogger
 * 
 * This methods have to be implemented, so that the 
 * Admin-Component can log all necessary events. 
 *
 */

public interface AdminLoggerInterface {
	public void logNewDeadlineEntry(ProcessIdentifier processIdentifier, String email, String deadlineType, Date deadline)
		throws ProcessIdentifierException;
	public void logChangedDeadlineEntry(ProcessIdentifier processIdentifier, String email, String deadlineType, Date deadline)
		throws ProcessIdentifierException;
	public void logStartedProcess(ProcessIdentifier processIdentifier, String email, String processName)
		throws ProcessIdentifierException;
	public void logVotingConditions(ProcessIdentifier processIdentifier, String email, String conditions)
		throws ProcessIdentifierException;
	
}
