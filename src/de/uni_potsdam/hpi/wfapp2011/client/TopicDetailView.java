package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TopicDetailView extends Composite {
	private Topictable parent;
	private FlexTable flexTable;
	private final Topic topic;
	private VerticalPanel verticalPanel;
	private Label lblTitle;
	private HTML htmlDescription;
	private Label lblDescription;
	private Button btnZurueck;
	
	public TopicDetailView(Topic newTopic, Topictable Parent) {
		topic = newTopic;
		parent = Parent;
		verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");
		
		lblTitle = new Label(topic.getName());
		lblTitle.setStyleName("gwt-LabelHeadline");
		verticalPanel.add(lblTitle);
		verticalPanel.setCellHeight(lblTitle, "41px");
		
		//create detail Table
		flexTable = new FlexTable();
		flexTable.setBorderWidth(0);
		verticalPanel.add(flexTable);
		verticalPanel.setCellHeight(flexTable, "100px");
		flexTable.setWidth("100%");
		createTableEntries();
		
		lblDescription = new Label("Beschreibung:");
		lblDescription.setStyleName("gwt-Label2");
		verticalPanel.add(lblDescription);
		verticalPanel.setCellHeight(lblDescription, "30px");
		
		htmlDescription = new HTML(newTopic.getProjectDescription(), true);
		verticalPanel.add(htmlDescription);
		
		btnZurueck = new Button("Zur\u00FCck");
		btnZurueck.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeWindow();
			}
		});
		verticalPanel.add(btnZurueck);
	}
	
	private void createTableEntries() {
		flexTable.setText(0, 0, "Projektk\u00FCrzel:");
		flexTable.setText(1, 0, "Lehrstuhl:");
		flexTable.setText(2, 0, "minimale Studentenanzahl:");
		flexTable.setText(3, 0, "maximale Studentenanzahl:");
		flexTable.setText(4, 0, "PDF:");
		flexTable.setText(5, 0, "Kontaktpersonen:");
		
		flexTable.setText(0, 1, topic.getProjectShortCut());
		flexTable.setText(1, 1, topic.getDepartment());
		flexTable.setText(2, 1, new Integer(topic.getMinStud()).toString());
		flexTable.setText(3, 1, new Integer(topic.getMaxStud()).toString());
		Label lblPDF = new Label("Download");
		lblPDF.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open(topic.getFile(), "", null);
			}
		});
		flexTable.setWidget(4, 1, lblPDF);
		
		HTMLTable.CellFormatter formatter = flexTable.getCellFormatter();
		formatter.setWidth(0, 0, "240px");
		formatter.setWidth(1, 0, "*");
		for (int i = 0; i<6; i++)
			formatter.addStyleName(i, 0, "gwt-Label2");
		
		
		int i = 0;
		for (Person person: topic.getcontactPerson()) {
			final Person personEntry = person;
			Label lblPerson = new Label(person.getName());
			lblPerson.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					Window.open("mailto:"+personEntry.getEmail()+"?subject="+topic.getName(), "", null);
				}
			});
			if (i>0)
				flexTable.setText(5+i, 0, "");
			flexTable.setWidget(5+i, 1, lblPerson);
			i++;
		}			
	}
	
	private void closeWindow() {
		parent.closeProjectDetail();
	}
}
