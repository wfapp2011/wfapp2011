package de.uni_potsdam.hpi.wfapp2011.Logging;

public interface MatchingLoggerInterface {
	public void logMatchingExecuted(String processName);
	public void logChangedMatching(String email, String projectName);
	public void logMatchingCompleted(String email);
}
