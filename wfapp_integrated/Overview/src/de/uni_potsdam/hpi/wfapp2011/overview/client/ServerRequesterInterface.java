package de.uni_potsdam.hpi.wfapp2011.overview.client;

import com.google.gwt.user.client.rpc.RemoteService;
import de.uni_potsdam.hpi.wfapp2011.overview.shared.ProcessInformation;

public interface ServerRequesterInterface extends RemoteService{

	public ProcessInformation getInformations(String type, String semester, int year);
	
	public void logout(String id); 
	
}
