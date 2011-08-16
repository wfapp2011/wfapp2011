package de.uni_potsdam.hpi.wfapp2011.login.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LoginPage implements EntryPoint {
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		
		PopupPanel popup = new PopupPanel();
		Composite content = new LoginPopupContent();
		popup.add(content);
		
		rootPanel.add(popup);
		
		popup.center();
		
		
	}
}
