package de.uni_potsdam.hpi.wfapp2011.login.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginExchangeAsync {

	public void login(String username, String password, String id, AsyncCallback<Void> callback);

}
