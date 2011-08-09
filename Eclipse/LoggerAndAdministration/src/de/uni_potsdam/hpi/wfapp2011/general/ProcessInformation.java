package de.uni_potsdam.hpi.wfapp2011.general;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ProcessInformation implements IsSerializable {
	private boolean activ;
	private String[] deadlinesString;
	private String numberOfVotes;
	private String numberOfProjectProposals;
	private String statistics;
	
	
	
	public boolean isActiv() {
		return activ;
	}
	public void setActiv(boolean activ) {
		this.activ = activ;
	}
	public String[] getDeadlinesString() {
		return deadlinesString;
	}
	public void setDeadlinesString(String[] deadlinesString) {
		this.deadlinesString = deadlinesString;
	}
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

	
}
