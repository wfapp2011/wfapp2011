package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
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
import com.googlecode.gwtTableToExcel.client.TableToExcelClient;


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
	private Label btnLogout;
	private DockPanel VotesPanel;
	private Button finishAssignment;
	private Frame frame;
	private HTML htmlFooter;
	private DockPanel dockPanel_1;
	private HTML Statistics;
	private VerticalPanel warningPanel;
	private Label excelExportButton;
	public static Label warningLabel;
	public static HorizontalPanel warningList;
	
	/******
	 *  Imports from other Packages
	 */
	
	public String projecttype = "Bachelorprojekt 2011";
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		

		
		//AssignmentDataExchangeServiceAsync assignmentDataInterface = GWT.create(AssignmentDataExchangeService.class);

		//System.out.println("gwt create");

		//public void getData() {
			//Initialize Interface-RemoteService
			/*if (assignmentDataInterface == null){
				assignmentDataInterface = GWT.create(AssignmentDataInterface.class);
			}*/
			
			//AsyncCallback<Project[]> callback = new AsyncCallback<Project[]>() {
			/*assignmentDataInterface.getProjects(new AsyncCallback<Project[]>() {
				public void onFailure(Throwable caught) {
			        // TODO: Do something with errors.
					System.out.println("Fail");
					wait=false;
			      }

			      public void onSuccess(Project[] result) {
			    	  wait=false;
			    	  GetData.DBProjects = result;
			    	  System.out.println("success");
			    	  buildMain();
			      }
			});
	}
			
			//assignmentDataInterface.getProjects(callback);
		//}*/
			buildMain();
	}
		

	private void buildMain() {
		rootPanel = RootPanel.get();
		//rootPanel.setSize("100%", "2000px");
		
		HTMLPanel htmlHeader = new HTMLPanel("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<div id=\"menu\">\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"191\">\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"156\">\r\n  \t\t\t</colgroup>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<img src=\"doktorhut.png\" width=\"191px\" height=\"151px\">\r\n\t\t\t</td>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<h1>Zuordnung</h1>\r\n\t\t\t\t<h2>"+projecttype+"</h1>\r\n\t\t\t</td>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<img src=\"HPI_Logo.png\" width=\"156px\" height=\"93px\">\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t\t</div>\r\n\t</body>\r\n</html>");
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
				VotesPanel.setVisible(false);
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
				VotesPanel.setVisible(true);
				boundaryPanel.setVisible(false);
				Statistics.setVisible(false);
			}
		});
		menuPanel.add(btnVotesTable);	
		
		btnAssignment = new Label("Zuordnung");
		btnAssignment.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setVisible(false);
				VotesPanel.setVisible(false);
				boundaryPanel.setVisible(true);
				Statistics.setVisible(false);
			}
		});
		menuPanel.add(btnAssignment);
		
		btnStatistics = new Label("Statistiken");
		btnStatistics.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setVisible(false);
				VotesPanel.setVisible(false);
				boundaryPanel.setVisible(false);
				if(StatisticsCalculator.changed){
					dockPanel_1.remove(Statistics);
					Statistics = StatisticsCalculator.calculateStatistics(HungarianAlgorithm.StudentList.length);
					dockPanel_1.add(Statistics, DockPanel.NORTH);
				}
				Statistics.setVisible(true);
			}
		});
		menuPanel.add(btnStatistics);
		
		btnLogout = new Label("Logout");
		btnLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		menuPanel.add(btnLogout);
		
		dockPanel_1 = new DockPanel();
		dockPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		dockPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		rootPanel.add(dockPanel_1, 0, 150);
		dockPanel_1.setSize("99.7%","");
		
		boundaryPanel = new AbsolutePanel();
		//boundaryPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		dockPanel_1.add(boundaryPanel, DockPanel.NORTH);
	 	boundaryPanel.setSize("100%","100%");
		boundaryPanel.setVisible(false);
	 	
		htmlFooter = new HTML("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<hr>\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"100\">\r\n    \t\t\t<col width=\"100\">\r\n  \t\t\t</colgroup>\r\n  \t\t\t<td></td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"https://www.hpi.uni-potsdam.de/support/impressum.html\">Impressum</a>\r\n\t\t\t</td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"mailto:test@test.com?subject=Bug in Themenwahl\">Report a Bug</a>\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t</body>\r\n</html>", true);
		htmlFooter.setStyleName("hr");
		dockPanel_1.add(htmlFooter, DockPanel.SOUTH);
		
		frame = new Frame("Startsite.html");
		dockPanel_1.add(frame, DockPanel.NORTH);
		frame.setSize("100%", "412px");
		
		HungarianAlgorithm.initHg(GetData.DBProjects, GetData.DBStudents);	
		
		VotesPanel = new DockPanel();
		FlexTable VotesTable = new FlexTable();
		VotesTableGenerator.createVotesTable(VotesPanel,VotesTable);
        TableToExcelClient votesTableAsExcel = new TableToExcelClient(VotesTable,"Excel Export","VotesTable "+projecttype);
        VotesPanel.setSize("100%", "");
		VotesPanel.add(votesTableAsExcel.build(), DockPanel.NORTH);
		dockPanel_1.add(VotesPanel, DockPanel.CENTER);
		VotesPanel.setVisible(false);

		
		//add Assignment-Menu
		HorizontalPanel assignmentMenuPanel = new HorizontalPanel();
		assignmentMenuPanel.setSize("100%","");
        boundaryPanel.add(assignmentMenuPanel);
		
		//add Warning-panel 
		warningPanel = new VerticalPanel();
        assignmentMenuPanel.add(warningPanel);
        
        //add WarningLabel to Warning-Panel
        warningLabel = new Label("Warnungen anzeigen");
        warningLabel.setSize(htmlFooter.getOffsetWidth() - 280 + "px", "");
        warningLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
		        warningList.setVisible(!warningList.isVisible());
			}
		});
        warningPanel.add(warningLabel);
        
        //add WarningList to Warning-Panel
        warningList = new HorizontalPanel();
        warningList.setSize("","");
        warningPanel.add(warningList);
        warningList.setVisible(false);
		final AssignmentGenerator assignmentGenerator = new AssignmentGenerator();
		assignmentGenerator.setUpAssignmentPage(boundaryPanel);
        AssignmentDropController.createWarnings();
       
        //Excel-Export for the current assignment
        TableToExcelClient assignmentAsExcel = new TableToExcelClient(AssignmentGenerator.AssignmentTable,"Excel Export","AssignmentTable "+projecttype);
		assignmentMenuPanel.add(assignmentAsExcel.build());
            
        Label finishButton = new Label("Abschlie\u00dfen");
        assignmentMenuPanel.add(finishButton);
        finishButton.addClickHandler(new ClickHandler(){
        	public void onClick(ClickEvent event){
        		assignmentGenerator.disableStudentDragging();
        	}
        });
		
		int studentNumber = HungarianAlgorithm.StudentList.length;
		Statistics = StatisticsCalculator.calculateStatistics(studentNumber);
		dockPanel_1.add(Statistics, DockPanel.NORTH);
		Statistics.setVisible(false);
	}
}
