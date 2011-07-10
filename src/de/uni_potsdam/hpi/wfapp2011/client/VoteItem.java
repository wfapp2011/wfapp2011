package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class VoteItem extends Composite {
	private Topic topic;
	private Label labelTopicname;
	private AbsolutePanel absolutePanel;
	private HorizontalPanel horizontalPanel;
	private CheckBox checkBox;
	private VerticalPanel buttonPanel;
	private Label buttonUp;
	private Label buttonDown;

	public VoteItem(Topic newTopic) {
		topic = newTopic;		
		absolutePanel = new AbsolutePanel();
		initWidget(absolutePanel);
		absolutePanel.setSize("", "40px");
		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setStyleName("gwt-VoteItem");
		absolutePanel.add(horizontalPanel, 0, 0);
		horizontalPanel.setSize("100%", "40px");
		
		checkBox = new CheckBox("");
		horizontalPanel.add(checkBox);
		horizontalPanel.setCellWidth(checkBox, "20px");
		
		labelTopicname = new Label(topic.getName());
		labelTopicname.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				new TopicDetailPopUp(topic).show();
			}
		});
		horizontalPanel.add(labelTopicname);
		
		buttonPanel = new VerticalPanel();
		buttonPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.add(buttonPanel);
		horizontalPanel.setCellWidth(buttonPanel, "20px");
		buttonPanel.setSize("20px", "40px");
		
		buttonUp = new Label("▲");
		buttonPanel.add(buttonUp);
		buttonPanel.setCellHeight(buttonUp, "20px");
		buttonUp.setSize("20px", "20px");
		buttonUp.setStyleName("arrow-Button");
		
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
	
	public boolean isChecked()
	{
		return checkBox.getValue();
	}
	
	public void setChecked(boolean value)
	{
		checkBox.setValue(value);
	}
	
	public void addUpClickHandler(ClickHandler upClick)
	{
		buttonUp.addClickHandler(upClick);
	}
	
	public void addDownClickHandler(ClickHandler downClick)
	{
		buttonDown.addClickHandler(downClick);
	}
	
	public void setOrderable(boolean order)
	{
		buttonPanel.setVisible(order);
	}
}
