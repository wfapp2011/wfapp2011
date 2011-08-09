package de.uni_potsdam.hpi.wfapp2011.overview.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessInformation;

public interface StatisticProviderInterfaceAsync {

	void getNumberOfProposals(ProcessIdentifier pId, AsyncCallback<String> callback);
	void getNumberOfVotings(ProcessIdentifier pId, AsyncCallback<String> callback);
	void getDeadlines(ProcessIdentifier pId, AsyncCallback<String[]> callback);
	void getLogEntries(ProcessIdentifier pId,
			AsyncCallback<Collection<String[]>> callback);
	void getInformations(ProcessIdentifier pId,
			AsyncCallback<ProcessInformation> callback);

}
