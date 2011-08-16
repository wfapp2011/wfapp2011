package de.uni_potsdam.hpi.wfapp2011.overview.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * An instance of this class contains information about a concrete process. <br/>
 * This includes information about the state of activity of this process and also, if active - of its deadlines, current phase and statistics.
 * 
 * @author Jannik Marten, Yanina Yurchenko
 *
 */

public class ProcessInformation implements IsSerializable {
	private boolean activ;
	private String[] deadlinesString;
	private String numberOfVotes;
	private String numberOfProjectProposals;
	private String statistics;
	private boolean isProjectProposalPhase;
	private boolean isVotingPhase;
	private boolean isMatchingPhase;
	
	 
	//state of activity
	public boolean isActiv() {
		return activ;
	}
	public void setActiv(boolean activ) {
		this.activ = activ;
	}
	
	//deadlines
	public String[] getDeadlinesString() {
		return deadlinesString;
	}
	public void setDeadlinesString(String[] deadlinesString) {
		this.deadlinesString = deadlinesString;
	}
	
	//statistics
	public String getNumberOfVotes() {
		return numberOfVotes;
	}
	public void setNumberOfVotes(String numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	public String getNumberOfProjectProposals() {
		return numberOfProjectProposals;
	}
	public void setNumberOfProjectProposals(String numberOfProjectProposals) {
		this.numberOfProjectProposals = numberOfProjectProposals;
	}
	public String getStatistics() {
		return statistics;
	}
	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}
	
	//ProcessPhase handling
	public void setIsProposalPhase(boolean isPhase) {
		this.isProjectProposalPhase = isPhase;
	}
	public boolean isProjectProposalPhase() {
		return isProjectProposalPhase;
	}
	public void setIsVotingPhase(boolean isPhase) {
		this.isVotingPhase = isPhase;
	}
	public boolean isVotingPhase() {
		return isVotingPhase;
	}
	public void setIisMatchingPhase(boolean isPhase) {
		this.isMatchingPhase = isPhase;
	}
	public boolean isMatchingPhase() {
		return isMatchingPhase;
	}

	
}
