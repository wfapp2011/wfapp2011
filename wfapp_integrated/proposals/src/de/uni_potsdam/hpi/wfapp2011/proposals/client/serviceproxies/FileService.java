package de.uni_potsdam.hpi.wfapp2011.proposals.client.serviceproxies;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.FileInfo;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.FileInterface;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.FileInterfaceAsync;

public class FileService implements FileInterfaceAsync {

	FileInterfaceAsync fileProvider = (FileInterfaceAsync) GWT.create(FileInterface.class);
	ServiceDefTarget endpoint = (ServiceDefTarget) fileProvider;

	public FileService() {
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL()+ "files");
	}	
	
	public void getAllFiles(int projectID,	AsyncCallback<ArrayList<FileInfo>> callback){
		fileProvider.getAllFiles(projectID,	callback);
	}
}
