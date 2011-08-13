package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

/**
 * Asynchronous client side interface that provides methods for data exchange between the application and the database.
 */
public interface AssignmentDataExchangeServiceAsync {

	void getProjects(ProcessIdentifier pId, AsyncCallback<Project[]> callback);

	void getStudents(ProcessIdentifier pId, AsyncCallback<Student[]> asyncCallback);

	void writePlacements(Student[] students, ProcessIdentifier processIdentifier, AsyncCallback<Void> callback);

	void sendAssignment(Student[] students, ProcessIdentifier processIdentifier, AsyncCallback<Void> callback);
}
