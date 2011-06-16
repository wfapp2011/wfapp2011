package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class VotingView extends Composite {
	private VerticalPanel verticalPanel;
	private HTMLPanel panelTitle;
	private HorizontalPanel horizontalPanel;
	private ScrollPanel scrollPanel;
	private ScrollPanel scrollPanel_1;
	private VerticalPanel verticalPanel_1;
	private VerticalPanel verticalPanel_2;
	private ArrayList<Topic> Topics;

	public VotingView(ArrayList<Topic> TopicList) {		
		Topics = TopicList;		
		verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		panelTitle = new HTMLPanel("<h1>Projekt-Wahl:</h1>");
		verticalPanel.add(panelTitle);
		verticalPanel.setCellHeight(panelTitle, "41px");
		
		horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		verticalPanel.setCellHeight(horizontalPanel, "100%");
		
		scrollPanel = new ScrollPanel();
		horizontalPanel.add(scrollPanel);
		scrollPanel.setSize("100%", "600px");
		
		verticalPanel_1 = new VerticalPanel();
		scrollPanel.setWidget(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");
		
		scrollPanel_1 = new ScrollPanel();
		horizontalPanel.add(scrollPanel_1);
		scrollPanel_1.setSize("100%", "600px");
		
		verticalPanel_2 = new VerticalPanel();
		scrollPanel_1.setWidget(verticalPanel_2);
		verticalPanel_2.setSize("100%", "600px");
		
		for (Topic i: Topics) {
			verticalPanel_1.add(new VoteItem(i));
			verticalPanel_2.add(new VoteItem(i));
		}
	}

}
