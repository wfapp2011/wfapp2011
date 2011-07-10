package de.uni_potsdam.hpi.wfapp2011.client;


import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLPanel;

public class Topictable extends Composite {
	private VerticalPanel verticalPanel;
	private FlexTable flexTable;
	private ArrayList<Topic> Topics;

	public ArrayList<Topic> getTopics() {
		return Topics;
	}

	public void setTopics(ArrayList<Topic> topics) {
		Topics = topics;
	}

	public Topictable (ArrayList<Topic> topics) {
		Topics = topics;
		createTopicTable();		
	}
	
	public void refresh() {
		flexTable.removeAllRows();
		createHeader();
		createTableEntries();
	}
	
	private	void createTopicTable()
	{
		verticalPanel = new VerticalPanel();
		verticalPanel.setWidth("100%");
		initWidget(verticalPanel);
		
		HTMLPanel panelHeadline = new HTMLPanel("<h1>Themen\u00FCbersicht:</h1>");
		verticalPanel.add(panelHeadline);
		flexTable = new FlexTable();
		flexTable.setSize("100%", "");
		verticalPanel.add(flexTable);
		verticalPanel.setCellHeight(flexTable, "");		
		
		setupTableStyle();
		createHeader();
		createTableEntries();		
	}
	
	private void setupTableStyle()
	{
		flexTable.addStyleName("topicList");
		
		HTMLTable.CellFormatter formatter = flexTable.getCellFormatter();
		formatter.setWidth(0, 0, "1%");
		formatter.setWidth(0, 1, "30%");
		formatter.setWidth(0, 2, "*");
	}
	
	private void createHeader()
	{
		flexTable.setText(0, 0, "K\u00FCrzel");
		flexTable.setText(0, 1, "Projektname");
		flexTable.setText(0, 2, "Beschreibung");
		
		HTMLTable.CellFormatter formatter = flexTable.getCellFormatter();
		
		for (int i = 0; i<3; i++)
			formatter.addStyleName(0, i, "topicListHeader");
	}
	
	private void createTableEntries()
	{
		for(Topic i: Topics)
		{
			final Topic currentTopic = i;
			Label lblTopicName = new Label(i.getName());
			lblTopicName.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					showProjectDetail(currentTopic);
				}
			});
			flexTable.setText(Topics.indexOf(i)+1, 0, i.getProjectShortCut());
			flexTable.setWidget(Topics.indexOf(i)+1, 1, lblTopicName);
			flexTable.setText(Topics.indexOf(i)+1, 2, i.getProjectShortDescription());
		}
	}
	
	private void showProjectDetail(Topic topic)
	{
		final TopicDetailView topicDetail = new TopicDetailView(topic);
		topicDetail.addCloseClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeChild(topicDetail);
		}});
		verticalPanel.add(topicDetail);
		flexTable.setVisible(false);		
	}
	
	public void closeChild(Widget Child)
	{
		verticalPanel.remove(Child);
		flexTable.setVisible(true);
	}
}
