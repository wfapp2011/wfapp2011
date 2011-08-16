package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Client side interface that provides methods for data exchange between the application and the database.
 * 
 */

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

	public Project[] getProjects(String type, String semester, int year);

	public Student[] getStudents(String type, String semester, int year);

	void writePlacements(Student[] students, String type, String semester, int year);

	void sendAssignment(Student[] students,String type, String semester, int year);
	
	void logout(String id);
}
