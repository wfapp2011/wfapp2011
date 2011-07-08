package de.uni_potsdam.hpi.wfapp2011.overview.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;

import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

public interface StatisticProviderInterface extends RemoteService{
	
	String getNumberOfProposals(ProcessIdentifier pId);
	String getNumberOfVotings(ProcessIdentifier pId);
	String[] getDeadlines(ProcessIdentifier pId);
	Collection<String[]> getLogEntries(ProcessIdentifier pId);

}
