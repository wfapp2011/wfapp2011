package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ConfigurationInterface implements EntryPoint {
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
		
		MainMetaPanel main = new MainMetaPanel();
		verticalPanel.add(main);
		main.setWidth("100%");
		
		MainPanel main2 = new MainPanel();
		verticalPanel.add(main2);
		main.setWidth("100%");
						
		/*
		 * Footer foot = new Footer();
		 * verticalPanel.add(foot);
		 * main.setWidth("100%"); 
		 */
	}
}
