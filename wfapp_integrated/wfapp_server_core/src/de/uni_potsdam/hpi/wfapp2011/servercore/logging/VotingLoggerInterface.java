package de.uni_potsdam.hpi.wfapp2011.servercore.logging;



/**
 * Interface for the Voting Phase
 * 
 * This methods have to be implemented, so that the 
 * all necessary events during the Voting Phase can be logged. 
 *
 * @author Jannik Marten, Yanina Yurchenko
 * 
 */
public interface VotingLoggerInterface {
	public void logStudentLogin(String type, String semester, int year , String email)  ;
	public void logNewVote(String type, String semester, int year , String email, String[] projectNames)  ;
	public void logChangedVote(String type, String semester, int year , String email, String[] projectNames)  ;
}
