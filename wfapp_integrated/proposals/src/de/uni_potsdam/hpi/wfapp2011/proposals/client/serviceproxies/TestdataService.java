package de.uni_potsdam.hpi.wfapp2011.proposals.client.serviceproxies;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.TestdataInterface;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.TestdataInterfaceAsync;

public class TestdataService implements TestdataInterfaceAsync {

	TestdataInterfaceAsync testdataProvider = (TestdataInterfaceAsync) GWT.create(TestdataInterface.class);
	ServiceDefTarget endpoint = (ServiceDefTarget) testdataProvider;
	
	public TestdataService() {
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL()+ "testdata");
	}

	public void initializeMetadata(AsyncCallback<Void> callback){
		testdataProvider.initializeMetadata(callback);
	}
	
	public void insertDepartments(AsyncCallback<Void> callback){
		testdataProvider.insertDepartments(callback);	
	}
	
	public void insertPersons(AsyncCallback<Void> callback){
		testdataProvider.insertPersons(callback);	
	}
	
}
