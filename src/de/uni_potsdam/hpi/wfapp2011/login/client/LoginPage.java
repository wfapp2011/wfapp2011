package de.uni_potsdam.hpi.wfapp2011.login.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LoginPage implements EntryPoint {
	
	/**
	 * renders the login content
	 */
	public void onModuleLoad() {
		//create all needed widgets and render them
		RootPanel rootPanel = RootPanel.get();
		
		PopupPanel popup = new PopupPanel();
		Composite content = new LoginPopupContent();
		popup.add(content);
		
		rootPanel.add(popup);
		
		/*Button btnRedirect = new Button("redirect");
		btnRedirect.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.Location.assign("/adminconfig/ConfigurationInterface.html");
			}
		});
		rootPanel.add(btnRedirect);*/
		
		popup.center();
		
		
	}
}
