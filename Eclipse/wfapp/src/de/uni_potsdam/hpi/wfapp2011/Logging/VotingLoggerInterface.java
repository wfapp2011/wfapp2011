package de.uni_potsdam.hpi.wfapp2011.Logging;

public interface VotingLoggerInterface {
	public void logStudentLogin(String email) ;
	public void logNewVote(String email, String[] projectNames);
	public void logChangedVote(String email, String[] projectNames);
	
}
