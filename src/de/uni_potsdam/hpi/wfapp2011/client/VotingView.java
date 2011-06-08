package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class VotingView extends Composite {

	public VotingView() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		HTMLPanel panel = new HTMLPanel("<h1>Wahl:</h1>");
		verticalPanel.add(panel);
	}

}
