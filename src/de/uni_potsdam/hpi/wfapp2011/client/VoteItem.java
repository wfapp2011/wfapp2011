package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class VoteItem extends Composite {
	private Topic topic;
	private Label labelTopicname;
	private AbsolutePanel absolutePanel;
	private HorizontalPanel horizontalPanel;

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
		
		labelTopicname = new Label(topic.getName());
		labelTopicname.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				new TopicDetailPopUp(topic).show();
			}
		});
		horizontalPanel.add(labelTopicname);		
	}
	
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}
