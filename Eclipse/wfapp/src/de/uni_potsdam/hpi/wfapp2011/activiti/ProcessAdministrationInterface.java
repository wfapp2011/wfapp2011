package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.util.GregorianCalendar;

public interface ProcessAdministrationInterface {
	
	public String startProcess(String processName,
							GregorianCalendar startProposalDate, 
							GregorianCalendar endProposalDate, 
							GregorianCalendar startVotingDate, 
							GregorianCalendar endVotingDate, 
							GregorianCalendar endMatchingDate);
	
}
