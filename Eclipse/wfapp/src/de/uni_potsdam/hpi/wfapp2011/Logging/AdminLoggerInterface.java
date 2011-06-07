package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.util.Date;

public interface AdminLoggerInterface {
	public void logNewDeadlineEntry(String email, String deadlineType, Date deadline);
	public void logChangedDeadlineEntry(String email, String deadlineType, Date deadline) ;
	public void logStartedProcess(String email, String processName);
	public void logVotingConditions(String email, String conditions);
	
}
