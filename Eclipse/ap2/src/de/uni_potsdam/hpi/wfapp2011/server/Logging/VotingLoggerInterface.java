package de.uni_potsdam.hpi.wfapp2011.server.Logging;

import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifierException;


/**
 * Interface for the Voting Phase
 * 
 * This methods have to be implemented, so that the 
 * all necessary events during the Voting Phase can be logged. 
 *
 */
public interface VotingLoggerInterface {
	public void logStudentLogin(ProcessIdentifier processIdentifier, String email) throws ProcessIdentifierException ;
	public void logNewVote(ProcessIdentifier processIdentifier, String email, String[] projectNames) throws ProcessIdentifierException;
	public void logChangedVote(ProcessIdentifier processIdentifier, String email, String[] projectNames) throws ProcessIdentifierException;
	
}
