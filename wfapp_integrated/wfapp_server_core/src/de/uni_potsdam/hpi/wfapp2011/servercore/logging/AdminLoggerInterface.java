package de.uni_potsdam.hpi.wfapp2011.servercore.logging;

import java.util.Date;



/**
 * Interface for the AdminLogger
 * 
 * This methods must be implemented, so that the 
 * Admin-Component can log all necessary events. 
 * 
 * @author Jannik Marten, Yanina Yurchenko
 *
 */

public interface AdminLoggerInterface {
	public void logNewDeadlineEntry(String type, String semester, int year, String email, String deadlineType, Date deadline);

	public void logChangedDeadlineEntry(String type, String semester, int year, String email, String deadlineType, Date deadline);

	public void logStartedProcess(String type, String semester, int year, String email, String processName);		
	public void logVotingConditions(String type, String semester, int year, String email, String conditions);
		
	
}
