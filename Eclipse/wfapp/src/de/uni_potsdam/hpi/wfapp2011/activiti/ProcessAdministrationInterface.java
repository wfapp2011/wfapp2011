package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.util.GregorianCalendar;

import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

public interface ProcessAdministrationInterface {
	
	
	public String startProcess(
			GregorianCalendar endProposalDate, 
			GregorianCalendar startVotingDate, 
			GregorianCalendar endVotingDate, 
			GregorianCalendar endMatchingDate,
			GregorianCalendar endProcessDate);
	
	public String startProcess(String processName,
			GregorianCalendar startProposalDate, 
			GregorianCalendar endProposalDate, 
			GregorianCalendar startVotingDate, 
			GregorianCalendar endVotingDate, 
			GregorianCalendar endMatchingDate);
	
	public boolean[] changeDeadlines(
			ProcessIdentifier processIdentifier, 
			GregorianCalendar endProposalDate, 
			GregorianCalendar endTopicPublication, 
			GregorianCalendar endVotingDate,
			GregorianCalendar endMatchingDate,
			GregorianCalendar endProcessDate );
}
