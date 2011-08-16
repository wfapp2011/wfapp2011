package de.uni_potsdam.hpi.wfapp2011.voting.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * creates a popup window which displays the details for topic
 * 
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 12.14
 * @see com.google.gwt.user.client.ui.PopupPanel
 */

public class TopicDetailPopUp extends PopupPanel {
	private TopicDetailView detailView;

	/**
	 * constructor for the popup window
	 *  
	 * @param newTopic displayed topic
	 */
	public TopicDetailPopUp(Topic newTopic) {
		//set the pop up to modal and activate auto close
		super(true, true);
		
		//add a TopicDetailView widget to the popup and connect the close button
		detailView = new TopicDetailView(newTopic);
		detailView.addCloseClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeChild();
		}});
		setWidget(detailView);
		detailView.setWidth("800px");
		
		center();
	}
	
	/**
	 *  close the popup window
	 */
	public void closeChild() {
		hide();
	}
}
