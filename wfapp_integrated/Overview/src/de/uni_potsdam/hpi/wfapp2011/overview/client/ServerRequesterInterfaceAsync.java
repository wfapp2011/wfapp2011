package de.uni_potsdam.hpi.wfapp2011.overview.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.uni_potsdam.hpi.wfapp2011.overview.shared.ProcessInformation;

public interface ServerRequesterInterfaceAsync {

	void getInformations(String type, String semester, int year, AsyncCallback<ProcessInformation> asyncCallback);

	void logout(String id, AsyncCallback<Void> callback); 

}
