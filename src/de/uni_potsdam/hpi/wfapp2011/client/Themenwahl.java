package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

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
	private VerticalPanel mainPanel;
	private Frame frame;
	private ArrayList<Topic> Topics;
	private Topictable topicTable;
	private HTML htmlFooter;
	private VotingView vVoting;
	private MyVotingView myVoting;
	private StatisticView vStatistic;

	
	public void onModuleLoad() {
		loadTopics();
		rootPanel = RootPanel.get();
		
		HTMLPanel htmlHeader = new HTMLPanel("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<div id=\"menu\">\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"191\">\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"156\">\r\n  \t\t\t</colgroup>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<img src=\"doktorhut.png\" width=\"191px\" height=\"151px\">\r\n\t\t\t</td>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<h1>Themenwahl</h1>\r\n\t\t\t\t<h2>Bachelorprojekt 2011</h1>\r\n\t\t\t</td>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<img src=\"HPI_Logo.png\" width=\"156px\" height=\"93px\">\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t\t</div>\r\n\t</body>\r\n</html>");
		rootPanel.add(htmlHeader, 0, 0);
		htmlHeader.setSize("100%", "151px");
		
		menuPanel = new HorizontalPanel();
		menuPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		rootPanel.add(menuPanel, 190, 110);
		menuPanel.setSize("450px", "41px");
		
		btnHome = new Label("Home");
		btnHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showElement(frame);				
				frame.setUrl("https://google.de");
			}
		});
		menuPanel.add(btnHome);
		
		btnThemenbersicht = new Label("Themen\u00FCbersicht");
		btnThemenbersicht.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showElement(topicTable);
			}
		});
		menuPanel.add(btnThemenbersicht);
		
		btnThemenwahl = new Label("Themenwahl");
		btnThemenwahl.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showElement(vVoting);
			}
		});
		menuPanel.add(btnThemenwahl);
		
		btnMeineWahl = new Label("Meine Wahl");
		btnMeineWahl.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showElement(myVoting);
			}
		});
		btnMeineWahl.setWidth("100px");
		menuPanel.add(btnMeineWahl);
		
		btnStatistik = new Label("Statistik");
		btnStatistik.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showElement(vStatistic);
			}
		});
		menuPanel.add(btnStatistik);
		
		btnLogout = new Label("Logout");
		menuPanel.add(btnLogout);
		btnLogout.setWidth("44px");
		
		mainPanel = new VerticalPanel();
		rootPanel.add(mainPanel, 0, 150);
		mainPanel.setWidth("99%");
				
		topicTable = new Topictable(Topics);
		mainPanel.add(topicTable);
		topicTable.setWidth("100%");
		mainPanel.setCellWidth(topicTable, " ");
						
		frame = new Frame("http://www.google.com");
		mainPanel.add(frame);
		frame.setSize("100%", "412px");
		frame.setVisible(false);
		
		vVoting = new VotingView();
		mainPanel.add(vVoting);
		vVoting.setWidth("100%");
		vVoting.setVisible(false);
		
		myVoting = new MyVotingView();
		mainPanel.add(myVoting);
		myVoting.setWidth("100%");
		myVoting.setVisible(false);
		
		vStatistic = new StatisticView();
		mainPanel.add(vStatistic);
		vStatistic.setWidth("100%");
		vStatistic.setVisible(false);
		
		htmlFooter = new HTML("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<hr>\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"100\">\r\n    \t\t\t<col width=\"100\">\r\n  \t\t\t</colgroup>\r\n  \t\t\t<td></td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"https://www.hpi.uni-potsdam.de/support/impressum.html\">Impressum</a>\r\n\t\t\t</td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"mailto:test@test.com?subject=Bug in Themenwahl\">Report a Bug</a>\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t</body>\r\n</html>", true);
		htmlFooter.setStyleName("hr");
		mainPanel.add(htmlFooter);
		htmlFooter.setWidth("100%");
	}
	
	private void loadTopics()
	{
		// Test Themen erstellen
		Topics = new ArrayList<Topic>();
		
		Topics.add(new Topic("1", "Multitoe II: whole‐body interaction with a multi‐touch floor", "T1",	"In 2009/2010, the HCI bachelor project multitoe" +
													"created multitoe, the interactive floor"+
													"with 'toe size' tracking resolution (google"+
													"'multitoe' to see what came out of it). The"+
													"moderate size of their prototype, however,"+
													"limited the interaction to feet."+
													"This year, the actual 8m2 installation in the"+
													"multi display lab of the new building will be"+
													"ready for you. Using a JVC 12 mega pixel projector"+
													"and our 13 mega pixel camera, you"+
													"and get things running at full scale.", 4, 5, "HCI", "http://www.google.de"));
		Topics.add(new Topic("2", "Test2", "T2", "Beschreibung", 3, 6, "Testlehrstuhl", ""));
		
		Topics.get(0).addcontactPerson(new Person(1, "Herr Paul M\u00FCller", "paul.mueller@test.de", "Worker", "DBS"));
		Topics.get(0).addcontactPerson(new Person(2, "Dr. summa cum fraude Karl Theodor zu Googleberg", "ktg@google.de", "teacher", ""));
		
		for (int i=0; i < 100; i++)
			Topics.add(new Topic(new Integer(i+3).toString(), "Test" + (i+3), "T" + (i+3), "Beschreibung", 3, 6, "Testlehrstuhl", ""));
	}
	
	private void showElement(Widget w)
	{
		frame.setVisible(false);
		topicTable.setVisible(false);
		vVoting.setVisible(false);
		myVoting.setVisible(false);
		vStatistic.setVisible(false);
		
		w.setVisible(true);
	}
}
