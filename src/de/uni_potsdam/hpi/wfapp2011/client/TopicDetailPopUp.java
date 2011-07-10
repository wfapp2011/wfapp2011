package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PopupPanel;

public class TopicDetailPopUp extends PopupPanel {
	private TopicDetailView detailView;

	public TopicDetailPopUp(Topic newTopic) {
		super(true, true);
		
		detailView = new TopicDetailView(newTopic);
		detailView.addCloseClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeChild();
		}});
		setWidget(detailView);
		detailView.setWidth("800px");
		
		center();
	}
	
	public void closeChild() {
		hide();
	}
}
