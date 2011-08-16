package de.uni_potsdam.hpi.wfapp2011.servercore.activiti;

/**
 * Interface, which defines the methods used to find out 
 * the current phase of the activiti process
 * 
 * @author Jannik Marten, Yanina Yurchenko
 * 
 */
public interface ProcessStatusInterface {
	
	public boolean isProjectProposalPhase();
	public boolean isFinalTopicDecisionPhase();
	public boolean isVotingPhase();
	public boolean isProjectMatchingPhase();
	
	public String getCurrentPhase();
}