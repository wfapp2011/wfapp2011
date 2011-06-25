package de.uni_potsdam.hpi.wfapp2011.Logging;

import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifierException;


/**
 * Interface for the Matching Logger
 * 
 * This methods have to be implemented, so that the 
 * Matching-Component can log all necessary events. 
 *
 */
public interface MatchingLoggerInterface {
	public void logMatchingExecuted(ProcessIdentifier processIdentifier, String email, String processName) throws ProcessIdentifierException;
	public void logChangedMatching(ProcessIdentifier processIdentifier, String email, String projectName) throws ProcessIdentifierException;
	public void logMatchingCompleted(ProcessIdentifier processIdentifier, String email) throws ProcessIdentifierException;
}
