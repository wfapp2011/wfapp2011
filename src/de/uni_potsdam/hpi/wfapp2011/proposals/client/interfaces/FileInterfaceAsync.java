package de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.FileInfo;
/**
 * @author Katrin Honauer, Josefine Harzmann
 */
public interface FileInterfaceAsync {

	void getAllFiles(int projectID,	AsyncCallback<ArrayList<FileInfo>> callback);

}
