package de.uni_potsdam.hpi.wfapp2011.voting.client;


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

/**
 * Composite widget which displays an overview of all project topics.
 * 
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 12.37
 * @see com.google.gwt.user.client.ui.Composite
 */
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

	/**
	 * constructor which sets the topic list and creates a table with the topics
	 * @param topics
	 */
	public Topictable (ArrayList<Topic> topics) {
		Topics = topics;
		createTopicTable();		
	}
	
	/**
	 *  refreshes the topic table
	 */
	public void refresh() {
		flexTable.removeAllRows();
		createHeader();
		createTableEntries();
	}
	
	private	void createTopicTable()
	{
		//create main panel
		verticalPanel = new VerticalPanel();
		verticalPanel.setWidth("100%");
		initWidget(verticalPanel);
		
		//create headline
		HTMLPanel panelHeadline = new HTMLPanel("<h1>Themen\u00FCbersicht:</h1>");
		verticalPanel.add(panelHeadline);
		//create table for the project topics
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
		//add topics to the table
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
		//show the detail view for one project
		final TopicDetailView topicDetail = new TopicDetailView(topic);
		topicDetail.addCloseClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeChild(topicDetail);
		}});
		verticalPanel.add(topicDetail);
		flexTable.setVisible(false);		
	}
	
	/**
	 * closes the detail window and shows the table
	 * @param Child
	 */
	public void closeChild(Widget Child)
	{
		verticalPanel.remove(Child);
		flexTable.setVisible(true);
	}
}
