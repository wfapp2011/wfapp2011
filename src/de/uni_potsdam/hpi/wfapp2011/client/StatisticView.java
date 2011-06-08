package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class StatisticView extends Composite {

	public StatisticView() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		HTMLPanel panel = new HTMLPanel("<h1>Statistik:</h1>");
		verticalPanel.add(panel);
	}

}
