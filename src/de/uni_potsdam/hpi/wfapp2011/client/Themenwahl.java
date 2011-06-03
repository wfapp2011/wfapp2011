package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.NamedFrame;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Themenwahl implements EntryPoint {
	private RootPanel rootPanel;
	private HorizontalPanel menuPanel;
	private Label btnHome;
	private Label btnThemenbersicht;
	private Label btnThemenwahl;
	private Label btnMeineWahl;
	private Label btnStatistik;
	private Label btnLogout;
	private Frame frame;
	private ArrayList<Topic> Topics;
	private FlexTable topicTable;
	private HTML htmlFooter;
	private DockPanel dockPanel_1;
	
	public void onModuleLoad() {
		loadTopics();
		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "2000px");
		
		HTMLPanel htmlHeader = new HTMLPanel("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<div id=\"menu\">\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"191\">\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"156\">\r\n  \t\t\t</colgroup>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<img src=\"doktorhut.png\" width=\"191px\" height=\"151px\">\r\n\t\t\t</td>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<h1>Themenwahl</h1>\r\n\t\t\t\t<h2>Bachelorprojekt 2011</h1>\r\n\t\t\t</td>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<img src=\"HPI_Logo.png\" width=\"156px\" height=\"93px\">\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t\t</div>\r\n\t</body>\r\n</html>");
		rootPanel.add(htmlHeader, 0, 0);
		htmlHeader.setSize("100%", "151px");
		
		menuPanel = new HorizontalPanel();
		menuPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		rootPanel.add(menuPanel, 190, 110);
		menuPanel.setSize("437px", "41px");
		
		btnHome = new Label("Home");
		btnHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setVisible(true);
				topicTable.setVisible(false);
				frame.setUrl("https://google.de");
			}
		});
		menuPanel.add(btnHome);
		
		btnThemenbersicht = new Label("Themen\u00FCbersicht");
		btnThemenbersicht.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setVisible(false);
				topicTable.setVisible(true);
			}
		});
		menuPanel.add(btnThemenbersicht);
		
		btnThemenwahl = new Label("Themenwahl");
		menuPanel.add(btnThemenwahl);
		
		btnMeineWahl = new Label("Meine Wahl");
		menuPanel.add(btnMeineWahl);
		btnMeineWahl.setWidth("84px");
		
		btnStatistik = new Label("Statistik");
		menuPanel.add(btnStatistik);
		
		btnLogout = new Label("Logout");
		menuPanel.add(btnLogout);
		btnLogout.setWidth("44px");
		
		dockPanel_1 = new DockPanel();
		dockPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		dockPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		rootPanel.add(dockPanel_1, 0, 150);
		dockPanel_1.setSize("99%", "");
		
		htmlFooter = new HTML("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<hr>\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"100\">\r\n    \t\t\t<col width=\"100\">\r\n  \t\t\t</colgroup>\r\n  \t\t\t<td></td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"https://www.hpi.uni-potsdam.de/support/impressum.html\">Impressum</a>\r\n\t\t\t</td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"mailto:test@test.com?subject=Bug in Themenwahl\">Report a Bug</a>\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t</body>\r\n</html>", true);
		htmlFooter.setStyleName("hr");
		dockPanel_1.add(htmlFooter, DockPanel.SOUTH);
		
		createTopicTable();
				
		frame = new Frame("http://www.google.com");
		dockPanel_1.add(frame, DockPanel.NORTH);
		frame.setSize("100%", "412px");
		frame.setVisible(false);		
	}
	
	void loadTopics()
	{
		// Test Themen erstellen
		Topics = new ArrayList<Topic>();
		
		Topics.add(new Topic("1", "Test1", "T1", "Beschreibung", 4, 5, "Testlehrstuhl", ""));
		Topics.add(new Topic("2", "Test1", "T2", "Beschreibung", 3, 6, "Testlehrstuhl", ""));
		
		Topics.get(0).addcontactPerson(new Person(1, "Herr Paul Müller", "paul.mueller@test.de", "Worker", "DBS"));
		Topics.get(1).addcontactPerson(new Person(2, "Dr. summa cum fraude Karl Theodo zu Googleberg", "ktg@google.de", "teacher", ""));
		
		for (int i=0; i < 100; i++)
			Topics.add(new Topic(new Integer(i+3).toString(), "Test1", "T" + (i+3), "Beschreibung", 3, 6, "Testlehrstuhl", ""));
	}
	
	void createTopicTable()
	{
		// Create TopicTable and add it to the Dockpanel
		topicTable = new FlexTable();
		dockPanel_1.add(topicTable, DockPanel.CENTER);
		dockPanel_1.setCellWidth(topicTable, " ");
		
		// setup size of table, add header and setup row width
		topicTable.setSize("100%", "75px");
		topicTable.setText(0, 0, "K\u00FCrzel");
		topicTable.setText(0, 1, "Projektname");
		topicTable.setText(0, 2, "Beschreibung");
		HTMLTable.CellFormatter formatter = topicTable.getCellFormatter();
		formatter.setWidth(0, 0, "1%");
		formatter.setWidth(0, 1, "19%");
		formatter.setWidth(0, 2, "80%");
		
		// create an table entry for each topic in Topics		
		for(Topic i: Topics)
		{
			final Topic currentTopic = i;
			Label lblTopicName = new Label(i.getName());
			lblTopicName.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					showProjectDetail(currentTopic);
				}
			});
			topicTable.setText(Topics.indexOf(i)+1, 0, i.getProjectShortCut());
			topicTable.setWidget(Topics.indexOf(i)+1, 1, lblTopicName);
			topicTable.setText(Topics.indexOf(i)+1, 2, i.getProjectDescription());
		}
	}
	
	void showProjectDetail(Topic topic)
	{
		Window.alert("<h1>" + topic.getName() + "</h1>");
	}
}
