package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ConfigInterfaceDataExchangeAsync {
	public void getProjectList(AsyncCallback<ArrayList<String>> callback);
	public void addProject(String year, String name, AsyncCallback<Void> callback);
	public void getConfig(String year, String semester, String name, AsyncCallback<Collection<Map<String,String>>> callback);
	public void saveConfig(String year, String semester, String name, Map<String, String> content, AsyncCallback<Void> callback);
	public void savePassword(String type, String newPwd, AsyncCallback<Void> callback);
	public void getPassword(String type, AsyncCallback<String> callback);
	public void saveMetaData(Map<String, String> map, AsyncCallback<Void> callback);
	public void getMetaData(AsyncCallback<Map<String, String>> callback);
}