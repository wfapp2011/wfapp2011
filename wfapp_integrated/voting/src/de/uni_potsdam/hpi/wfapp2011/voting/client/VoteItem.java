package de.uni_potsdam.hpi.wfapp2011.voting.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Composite widget which is one entry in the topic voting list widget
 * 
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 13.19
 * @see com.google.gwt.user.client.ui.Composite
 */
public class VoteItem extends Composite {
	private Topic topic;
	private Label labelTopicname;
	private AbsolutePanel absolutePanel;
	private HorizontalPanel horizontalPanel;
	private CheckBox checkBox;
	private VerticalPanel buttonPanel;
	private Label buttonUp;
	private Label buttonDown;

	/**
	 * constructor of the entry.
	 * It creates the widgets and sets the topic
	 * @param newTopic displayed topic
	 */
	public VoteItem(Topic newTopic) {
		//create main panel
		topic = newTopic;		
		absolutePanel = new AbsolutePanel();
		initWidget(absolutePanel);
		absolutePanel.setSize("", "40px");
		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setStyleName("gwt-VoteItem");
		absolutePanel.add(horizontalPanel, 0, 0);
		horizontalPanel.setSize("100%", "40px");
	
		//create checkbox for the topic selection
		checkBox = new CheckBox("");
		horizontalPanel.add(checkBox);
		horizontalPanel.setCellWidth(checkBox, "20px");
		
		//create the topic name with link to a detail popup
		labelTopicname = new Label(topic.getName());
		labelTopicname.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				new TopicDetailPopUp(topic).show();
			}
		});
		horizontalPanel.add(labelTopicname);
		
		//create button panel
		buttonPanel = new VerticalPanel();
		buttonPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.add(buttonPanel);
		horizontalPanel.setCellWidth(buttonPanel, "20px");
		buttonPanel.setSize("20px", "40px");
		
		//create up button
		buttonUp = new Label("▲");
		buttonPanel.add(buttonUp);
		buttonPanel.setCellHeight(buttonUp, "20px");
		buttonUp.setSize("20px", "20px");
		buttonUp.setStyleName("arrow-Button");
		
		//create down button
		buttonDown = new Label("▼");
		buttonPanel.add(buttonDown);
		buttonPanel.setCellHeight(buttonDown, "20px");
		buttonDown.setSize("20px", "20px");
		buttonDown.setStyleName("arrow-Button");
	}
	
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	/**
	 * returns true if the topic is selected
	 * @return boolean
	 */
	public boolean isChecked()
	{
		return checkBox.getValue();
	}
	
	public void setChecked(boolean value)
	{
		checkBox.setValue(value);
	}
	
	/**
	 * adds the clickhandler for the up button 
	 * @param upClick click handler
	 */
	public void addUpClickHandler(ClickHandler upClick)
	{
		buttonUp.addClickHandler(upClick);
	}
	
	/**
	 * adds the clickhandler for the down button 
	 * @param downClick click handler
	 */
	public void addDownClickHandler(ClickHandler downClick)
	{
		buttonDown.addClickHandler(downClick);
	}
	
	 /**
	  * sets the visibility of the up and down button
	  * @param order boolean
	  */
	public void setOrderable(boolean order)
	{
		buttonPanel.setVisible(order);
	}
}
