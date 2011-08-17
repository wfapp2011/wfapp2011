package de.uni_potsdam.hpi.wfapp2011.login.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("LoginExchange")
public interface LoginExchange extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static LoginExchangeAsync instance;
		public static LoginExchangeAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(LoginExchange.class);
			}
			return instance;
		}
	}
	
	public void login(String username, String password, String id);
}