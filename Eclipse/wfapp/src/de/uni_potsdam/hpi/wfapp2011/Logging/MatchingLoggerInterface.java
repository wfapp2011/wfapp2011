package de.uni_potsdam.hpi.wfapp2011.Logging;


/**
 * Interface for the Matching Logger
 * 
 * This methods have to be implemented, so that the 
 * Matching-Component can log all necessary events. 
 *
 */
public interface MatchingLoggerInterface {
	public void logMatchingExecuted(String processName);
	public void logChangedMatching(String email, String projectName);
	public void logMatchingCompleted(String email);
}
