package de.uni_potsdam.hpi.wfapp2011.voting.client;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VotingDatabaseServiceAsync {
	public void loadTopics(String type, String semester, int year, AsyncCallback<ArrayList<Topic>> callback);
	public void loadVotes(String type, String semester, int year, String user, AsyncCallback<ArrayList<Vote>> callback);
	public void saveVotes(String type, String semester, int year, String user, ArrayList<Vote> votings, AsyncCallback<Void> callback);
	public void deleteVotes(String type, String semester, int year, String user, AsyncCallback<Void> callback);
	public void getStatistic(String type, String semester, int year, int priority, AsyncCallback<ArrayList<Map<String,Integer>>> callback);
	public void numberOfVotes(String type, String semester, int year, AsyncCallback<Integer> callback);
	public void confirmPassword(String user, String pwd, AsyncCallback<Boolean> callback);
	public void logout(String cookie, AsyncCallback<Void> asyncCallback);
}
