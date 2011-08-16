package de.uni_potsdam.hpi.wfapp2011.servercore.logging;



/**
 * Interface for the Matching Logger
 * 
 * This methods have to be implemented, so that the 
 * Matching-Component can log all necessary events. 
 *
 * @author Jannik Marten, Yanina Yurchenko
 *
 */
public interface MatchingLoggerInterface {
	public void logMatchingExecuted(String type, String semester, int year, String email, String processName);
	public void logChangedMatching(String type, String semester, int year, String email, String projectName);
	public void logMatchingCompleted(String type, String semester, int year, String email) ;
}
