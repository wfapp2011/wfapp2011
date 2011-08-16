package de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces;

import com.google.gwt.user.client.rpc.AsyncCallback;
/**
 * @author Katrin Honauer, Josefine Harzmann
 */
public interface TestdataInterfaceAsync {

	void initializeMetadata(AsyncCallback<Void> callback);
	void insertDepartments(AsyncCallback<Void> callback);
	void insertPersons(AsyncCallback<Void> callback);

}
