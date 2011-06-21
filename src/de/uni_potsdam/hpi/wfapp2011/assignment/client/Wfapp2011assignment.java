package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
/*import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;  
import com.gwtext.client.widgets.layout.BorderLayoutData;*/
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Wfapp2011assignment implements EntryPoint {
	private RootPanel rootPanel;
	private AbsolutePanel boundaryPanel;
	private HorizontalPanel menuPanel;
	private Label btnHome;
	private Label btnVotesTable;
	private Label btnAssignment;
	private Label btnStatistics;
	//private Label btnLogout;
	private FlexTable VotesTable;
	private Frame frame;
	private HTML htmlFooter;
	private DockPanel dockPanel_1;
	private HTML Statistics;
	private VerticalPanel warningPanel;
	private HorizontalPanel warningList;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
		rootPanel = RootPanel.get();
		//rootPanel.setSize("100%", "2000px");
		
		HTMLPanel htmlHeader = new HTMLPanel("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<div id=\"menu\">\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"191\">\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"156\">\r\n  \t\t\t</colgroup>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<img src=\"doktorhut.png\" width=\"191px\" height=\"151px\">\r\n\t\t\t</td>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<h1>Zuordnung</h1>\r\n\t\t\t\t<h2>Bachelorprojekt 2011</h1>\r\n\t\t\t</td>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<img src=\"HPI_Logo.png\" width=\"156px\" height=\"93px\">\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t\t</div>\r\n\t</body>\r\n</html>");
		rootPanel.add(htmlHeader, 0, 0);
		htmlHeader.setSize("100%", "151px");
		
		menuPanel = new HorizontalPanel();
		menuPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		rootPanel.add(menuPanel, 190, 110);
		menuPanel.setSize("437px", "41px");
		
		btnHome = new Label("Startseite");
		btnHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setVisible(true);
				VotesTable.setVisible(false);
				boundaryPanel.setVisible(false);
				Statistics.setVisible(false);
				frame.setUrl("Startsite.html");
			}
		});
		menuPanel.add(btnHome);
		
		btnVotesTable = new Label("Wahltabelle");
		btnVotesTable.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setVisible(false);
				VotesTable.setVisible(true);
				boundaryPanel.setVisible(false);
				Statistics.setVisible(false);
			}
		});
		menuPanel.add(btnVotesTable);	
		
		btnAssignment = new Label("Zuordnung");
		btnAssignment.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setVisible(false);
				VotesTable.setVisible(false);
				boundaryPanel.setVisible(true);
				Statistics.setVisible(false);
			}
		});
		menuPanel.add(btnAssignment);
		
		btnStatistics = new Label("Statistiken");
		btnStatistics.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setVisible(false);
				VotesTable.setVisible(false);
				boundaryPanel.setVisible(false);
				Statistics.setVisible(true);
			}
		});
		menuPanel.add(btnStatistics);
		
		dockPanel_1 = new DockPanel();
		dockPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		dockPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		rootPanel.add(dockPanel_1, 0, 150);
		dockPanel_1.setSize("99.7%","");
		
		boundaryPanel = new AbsolutePanel();
		//boundaryPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		dockPanel_1.add(boundaryPanel, DockPanel.NORTH);
	 	boundaryPanel.setSize("100%","520px");
		boundaryPanel.setVisible(false);
	 	
		htmlFooter = new HTML("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<hr>\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"100\">\r\n    \t\t\t<col width=\"100\">\r\n  \t\t\t</colgroup>\r\n  \t\t\t<td></td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"https://www.hpi.uni-potsdam.de/support/impressum.html\">Impressum</a>\r\n\t\t\t</td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"mailto:test@test.com?subject=Bug in Themenwahl\">Report a Bug</a>\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t</body>\r\n</html>", true);
		htmlFooter.setStyleName("hr");
		dockPanel_1.add(htmlFooter, DockPanel.SOUTH);
		
		frame = new Frame("Startsite.html");
		dockPanel_1.add(frame, DockPanel.NORTH);
		frame.setSize("100%", "412px");
		
		HungarianAlgorithm.initHg(TestData.TestProjects, new TestData().TestStudents);	
		
		VotesTable = new FlexTable();
		VotesTableGenerator.createVotesTable(dockPanel_1,VotesTable);
		VotesTable.setVisible(false);
		
		//add Warning-panel 
		warningPanel = new VerticalPanel();
        warningPanel.setSize("100%","");
        boundaryPanel.add(warningPanel);
        
        //add WarningLabel to Warning-Panel
        Label warningLabel = new Label("Warnungen anzeigen");
        warningLabel.setSize("30%", "");
        warningLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
		        warningList.setVisible(!warningList.isVisible());
			}
		});
        warningPanel.add(warningLabel);
        
        //add WarningList to Warning-Panel
        warningList = new HorizontalPanel();
        warningList.add(new HTML("Warnings!"));  
        warningList.setSize("100%","");
        warningPanel.add(warningList);
        warningList.setVisible(false);
		new AssignmentGenerator().setUpAssignmentPage(boundaryPanel);
		
		int students = HungarianAlgorithm.StudentList.length;
		Statistics = StatisticsCalculator.calculateStatistics(students);
		dockPanel_1.add(Statistics, DockPanel.NORTH);
		Statistics.setVisible(false);
	}
}
