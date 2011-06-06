package de.uni_potsdam.hpi.wfapp2011.activiti;

public interface LogInterface {
	public boolean logNewVoting(String userID, String Name, String voting);
	public boolean logChangedVoting(String userID, String userName, String voting );
}
