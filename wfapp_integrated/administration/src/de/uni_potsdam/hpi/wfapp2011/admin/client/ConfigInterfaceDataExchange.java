package de.uni_potsdam.hpi.wfapp2011.admin.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ConfigInterfaceDataExchange") 
public interface ConfigInterfaceDataExchange extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ConfigInterfaceDataExchangeAsync instance;
		public static ConfigInterfaceDataExchangeAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(ConfigInterfaceDataExchange.class);
			}
			return instance;
		}
	}
	public ArrayList<String[]> getProjectList();
	public void addProject(String year, String name);
	public Collection<Map<String,String>> getConfig(String year, String semester, String name);
	public void saveConfig(String year, String semester, String name, Map<String,String> content);
	public void savePassword(String type, String newPwd);
	public String getPassword(String type);
	public void saveMetaData(Map<String,String> map);
	public Map<String,String> getMetaData();
	public void deleteProject(String year, String semester, String name);
	public void startProject (String year, String semester, String name);
	public void logout(String id);
	public Collection<String[]> getLogEntries(int year, String semester, String type, Date fromDate, Date untilDate);
}
