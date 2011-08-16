package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous client side interface that provides methods for data exchange between the application and the database.
 */
public interface AssignmentDataExchangeServiceAsync {

	void getProjects(String type, String semester, int year,
			AsyncCallback<Project[]> callback);

	void getStudents(String type, String semester, int year,
			AsyncCallback<Student[]> asyncCallback);

	void writePlacements(Student[] students, String type, String semester,
			int year, AsyncCallback<Void> callback);

	void sendAssignment(Student[] students, String type, String semester,
			int year, AsyncCallback<Void> callback);

	void logout(String id, AsyncCallback<Void> callback);
}
