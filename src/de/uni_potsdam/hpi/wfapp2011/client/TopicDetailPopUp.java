package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

public class TopicDetailPopUp extends PopupPanel implements ChildClosable{
	private TopicDetailView detailView;

	public TopicDetailPopUp(Topic newTopic) {
		super(true, true);
		
		detailView = new TopicDetailView(newTopic, this);
		setWidget(detailView);
		detailView.setWidth("800px");
		
		center();
	}
	
	public void closeChild(Widget Child) {
		hide();
	}
}
