package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("VotingDatabaseService")
public interface VotingDatabaseService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static VotingDatabaseServiceAsync instance;
		public static VotingDatabaseServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(VotingDatabaseService.class);
			}
			return instance;
		}
	}
	
	public ArrayList<Topic> loadTopics(String type, String semester, int year);
	public ArrayList<Vote> loadVotes(String type, String semester, int year, String user);
	public void saveVotes(String type, String semester, int year, ArrayList<Vote> votings);
	public void deleteVotes(String type, String semester, int year, String user);
	public ArrayList<Map<String,Integer>> getStatistic(String type, String semester, int year, int priority);
	public int numberOfVotes(String type, String semester, int year);
	public boolean confirmPassword(String user, String pwd);
}
