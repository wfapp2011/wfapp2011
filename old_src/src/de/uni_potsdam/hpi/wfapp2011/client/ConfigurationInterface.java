package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ConfigurationInterface implements EntryPoint {
	
	private PopupPanel temp;
	private LoginPopUp content;

	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		
		VerticalPanel verticalPanel = new VerticalPanel();
		rootPanel.add(verticalPanel, 0, 0);
		verticalPanel.setSize("100%", "100%");
		
		/*
		 * Header head = new Header(input);
		 * verticalPanel.add(head);
		 * main.setWidth("100%"); 
		 */
		
		/*MainMetaPanel metaconfig = new MainMetaPanel();
		verticalPanel.add(metaconfig);
		metaconfig.setWidth("100%");*/
		
		MainPanel projectconfig = new MainPanel();
		verticalPanel.add(projectconfig);
		projectconfig.setWidth("100%");
		
		Button btnLogin = new Button("Login");
		btnLogin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				temp = new PopupPanel();
				content = new LoginPopUp();
				
				Button save = new Button();
				save.setText("Einloggen");
				save.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						content.tryToLogin();
						temp.hide();
					}
				});
				
				content.getButtonBar().add(save);
				temp.add(content);
				temp.center();
			}
		});
		verticalPanel.add(btnLogin);
						
		/*
		 * Footer foot = new Footer();
		 * verticalPanel.add(foot);
		 * main.setWidth("100%"); 
		 */
	}
}
