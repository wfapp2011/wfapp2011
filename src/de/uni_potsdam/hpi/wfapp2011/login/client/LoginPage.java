package de.uni_potsdam.hpi.wfapp2011.login.client;

import com.gargoylesoftware.htmlunit.javascript.host.Location;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

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
