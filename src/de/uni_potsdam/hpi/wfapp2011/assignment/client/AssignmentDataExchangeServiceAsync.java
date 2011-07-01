package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AssignmentDataExchangeServiceAsync {

	public void getProjects(AsyncCallback<Project[]> callback);

}
