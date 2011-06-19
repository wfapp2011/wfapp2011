package de.uni_potsdam.hpi.wfapp2011.Logging;


/**
 * Interface for the Voting Phase
 * 
 * This methods have to be implemented, so that the 
 * all necessary events during the Voting Phase can be logged. 
 *
 */
public interface VotingLoggerInterface {
	public void logStudentLogin(String email) ;
	public void logNewVote(String email, String[] projectNames);
	public void logChangedVote(String email, String[] projectNames);
	
}
