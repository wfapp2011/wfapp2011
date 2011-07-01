package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("AssignmentDataExchangeService")
public interface AssignmentDataExchangeService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static AssignmentDataExchangeServiceAsync instance;
		public static AssignmentDataExchangeServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(AssignmentDataExchangeService.class);
			}
			return instance;
		}
	}
	
	public Project[] getProjects();

}
