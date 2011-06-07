package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.util.Date;

public interface ProcessAdministrationInterface {
	public void startProcess(String processName, Date startProposalDate, Date endProposalDate, Date startVotingDate, Date endVotingDate, Date endMatchingDate);
	
}
