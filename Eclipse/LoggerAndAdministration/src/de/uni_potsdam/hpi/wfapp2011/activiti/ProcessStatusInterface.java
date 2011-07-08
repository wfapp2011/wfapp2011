package de.uni_potsdam.hpi.wfapp2011.activiti;

public interface ProcessStatusInterface {
	
	public boolean isProjectProposalPhase();
	public boolean isFinalTopicDecisionPhase();
	public boolean isVotingPhase();
	public boolean isProjectMatchingPhase();
	
	public String getCurrentPhase();
}
