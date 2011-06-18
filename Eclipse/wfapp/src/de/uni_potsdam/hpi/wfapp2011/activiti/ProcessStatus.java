package de.uni_potsdam.hpi.wfapp2011.activiti;

public interface ProcessStatus {
	public boolean isProjectProposalPhase(String executionId);
	public boolean isFinalTopicDecisionPhase(String executionId);
	public boolean isVotingPhase(String executionId);
	public boolean isProjectMatchingPhase(String executionId);
}
