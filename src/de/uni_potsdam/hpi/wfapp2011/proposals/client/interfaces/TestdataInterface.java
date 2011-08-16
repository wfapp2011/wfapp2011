package de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces;

import com.google.gwt.user.client.rpc.RemoteService;
/**
 * @author Katrin Honauer, Josefine Harzmann
 */
public interface TestdataInterface extends RemoteService{
	
	public void initializeMetadata();
	public void insertDepartments();
	public void insertPersons();
	
}
