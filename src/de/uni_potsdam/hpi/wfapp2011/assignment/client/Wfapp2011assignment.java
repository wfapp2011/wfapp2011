package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import java.util.HashMap;
import java.util.Random;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.*;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Wfapp2011assignment implements EntryPoint, MouseListener {
	private static final HashMap<HTML, PopupPanel> PopupMap = new HashMap<HTML, PopupPanel>();
	private Widget currentwidget;
	private RootPanel rootPanel;
	private AbsolutePanel boundaryPanel;
	private HorizontalPanel menuPanel;
	private Label btnHome;
	private Label btnVotesTable;
	private Label btnAssignment;
	private Label btnStatistik;
	private Label btnLogout;
	private FlexTable VotesTable;
	private Frame frame;
	private HTML htmlFooter;
	private DockPanel dockPanel_1;
	
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
				//frame.setUrl("Wunschmatrix");
			}
		});
		menuPanel.add(btnVotesTable);	
		
		btnAssignment = new Label("Zuordnung");
		btnAssignment.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setVisible(false);
				VotesTable.setVisible(false);
				boundaryPanel.setVisible(true);
			}
		});
		menuPanel.add(btnAssignment);
		
		dockPanel_1 = new DockPanel();
		dockPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		dockPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		rootPanel.add(dockPanel_1, 0, 150);
		dockPanel_1.setSize("99.7%","");
		
		boundaryPanel = new AbsolutePanel();
		//boundaryPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		dockPanel_1.add(boundaryPanel, DockPanel.NORTH);
	 	boundaryPanel.setSize("100%","500px");
	 	
		htmlFooter = new HTML("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<hr>\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"100\">\r\n    \t\t\t<col width=\"100\">\r\n  \t\t\t</colgroup>\r\n  \t\t\t<td></td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"https://www.hpi.uni-potsdam.de/support/impressum.html\">Impressum</a>\r\n\t\t\t</td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"mailto:test@test.com?subject=Bug in Themenwahl\">Report a Bug</a>\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t</body>\r\n</html>", true);
		htmlFooter.setStyleName("hr");
		dockPanel_1.add(htmlFooter, DockPanel.SOUTH);
		
		frame = new Frame("Startsite.html");
		dockPanel_1.add(frame, DockPanel.NORTH);
		frame.setSize("100%", "412px");
		
		HungarianAlgorithm.initHg(TestData.TestProjects, new TestData().TestStudents);		
		setUpAssignmentPage();
		
		VotesTable = new FlexTable();
		VotesTableGenerator.createVotesTable(dockPanel_1,VotesTable);
		VotesTable.setVisible(false);
		boundaryPanel.setVisible(false);

	}
	
	private void setUpAssignmentPage() {

		// initialize our column drag controller
	    PickupDragController dragController = new PickupDragController(boundaryPanel, false);
		dragController.setBehaviorConstrainedToBoundaryPanel(true);
	    dragController.setBehaviorMultipleSelection(false);

	    // initialize our widget drag controller
	    PickupDragController widgetDragController = new PickupDragController(boundaryPanel, false);
	    widgetDragController.setBehaviorMultipleSelection(false);

	    // initialize horizontal panel to hold our columns
	    HorizontalPanel horizontalPanel = new HorizontalPanel();
	    horizontalPanel.setSpacing(1);
	    horizontalPanel.setSize("100%","");
	    boundaryPanel.add(horizontalPanel,0,0);

	    // initialize our column drop controller
	    HorizontalPanelDropController columnDropController = new HorizontalPanelDropController(
	        horizontalPanel);
	    dragController.registerDropController(columnDropController);

	    // initialize ProjectHashMap
		HashMap<String, VerticalPanel> ProjectHashMap = new HashMap<String, VerticalPanel>();
		
	    for (int col = 0; col < HungarianAlgorithm.ProjectList.length; col++) {
	      // initialize a vertical panel to hold the heading and a second vertical panel
	      VerticalPanel columnCompositePanel = new VerticalPanel();

	      // initialize inner vertical panel to hold individual widgets
	      VerticalPanel verticalPanel = new VerticalPanel(); //VerticalPanelWithSpacer()
	      verticalPanel.setSpacing(0);
	      horizontalPanel.add(columnCompositePanel);

	      // initialize a widget drop controller for the current column
	      VerticalPanelDropController widgetDropController = new VerticalPanelDropController(verticalPanel);
	      widgetDragController.registerDropController(widgetDropController);

	      // Put together the column pieces
	      String ProjectID = HungarianAlgorithm.ProjectList[col].ProjectID;
	      Label heading = new Label(ProjectID);
	      columnCompositePanel.add(heading);
	      columnCompositePanel.add(verticalPanel);
	      ProjectHashMap.put(ProjectID, verticalPanel);	      

	      // make the column draggable by its heading
	      dragController.makeDraggable(columnCompositePanel, heading);
	    }
	   
	    int [][] assignment = HungarianAlgorithm.getAssignment();

	    for (int i = 0; i < HungarianAlgorithm.StudentList.length; i++) {
	        // initialize a student-widget
	    	Student student = HungarianAlgorithm.StudentList[i];
	    	int wish = (int) HungarianAlgorithm.VotesMatrix[assignment[i][0]][assignment[i][1]];
	    	wish = (student.votes.length - wish) + 1;
	        HTML widget = new HTML("<html><body><table><tr><td>"+student.firstname +" <br> " + student.lastname + " </td><td><h3>" + wish + "</h3></td></tr></table></body></html>");
	        //widget.setHeight(3 + "em");

	        widget.addMouseListener(this);
			PopupPanel pop = new PopupPanel(true);
			pop.setWidget(new HTML(student.votes[0] + " <br> " + 
					student.votes[1] + " <br> " + 
					student.votes[2] + " <br> " + 
					student.votes[3] + " <br> " + 
					student.votes[4] ));
			PopupMap.put(widget,pop);
	
	        String ProjectID = HungarianAlgorithm.lookUpProject(assignment[i][1]).ProjectID;
	        ProjectHashMap.get(ProjectID).add(widget);

	        // make the widget draggable
	        widgetDragController.makeDraggable(widget);
	      }

	}

	public void onMouseEnter(Widget sender) {
		if (currentwidget == null) { 
			PopupMap.get(sender).setPopupPosition(sender.getAbsoluteLeft()+40,sender.getAbsoluteTop()+30);
			PopupMap.get(sender).show();	
			currentwidget = sender ;}
		if (currentwidget != sender){
			PopupMap.get(currentwidget).hide();
			PopupMap.get(sender).setPopupPosition(sender.getAbsoluteLeft()+40,sender.getAbsoluteTop()+30);
			PopupMap.get(sender).show();		
			currentwidget = sender;
		}	
	}

	public void onMouseLeave(Widget sender) {
	}
	
	public void onMouseDown(Widget sender, int x, int y) {
	}

	public void onMouseMove(Widget sender, int x, int y) {
	}

	public void onMouseUp(Widget sender, int x, int y) {
	}
		
}
