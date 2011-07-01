package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import java.util.HashMap;
import java.util.Set;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.HorizontalPanelDropController;
import com.allen_sauer.gwt.dnd.client.drop.VerticalPanelDropController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class AssignmentGenerator implements MouseListener{
	private static final HashMap<HTML, PopupPanel> PopupMap = new HashMap<HTML, PopupPanel>();
	public static HashMap<VerticalPanel, Project> PanelProjectHashMap = new HashMap<VerticalPanel, Project>();
	public static HashMap<Project, VerticalPanel> ProjectPanelHashMap = new HashMap<Project, VerticalPanel>();
	public static HashMap<HTML, Integer> LabelHashMap = new HashMap<HTML, Integer>();
	public static String[] widgetcolors = {"#FF3300","white","#FFFF66","#FFCC66","#FF9933","#FF6600"};
	private Widget currentwidget;
	public static FlexTable AssignmentTable;
	private PickupDragController widgetDragController;
	
	public void setUpAssignmentPage(AbsolutePanel boundaryPanel) {
		//invisible FlexTable for Excel Export
		AssignmentTable = createTableForExport(); 
		AssignmentTable.setVisible(false);
		
		// initialize our column drag controller
	    PickupDragController dragController = new PickupDragController(boundaryPanel, false);
		dragController.setBehaviorConstrainedToBoundaryPanel(true);
	    dragController.setBehaviorMultipleSelection(false);

	    // initialize our widget drag controller
	    widgetDragController = new PickupDragController(boundaryPanel, false);
	    widgetDragController.setBehaviorMultipleSelection(false);

	    // initialize horizontal panel to hold our columns
	    HorizontalPanel horizontalPanel = new HorizontalPanel();
	    horizontalPanel.setSpacing(1);
	    horizontalPanel.setSize("100%","");
	    boundaryPanel.add(horizontalPanel);

	    // initialize our column drop controller
	    HorizontalPanelDropController columnDropController = new HorizontalPanelDropController(
	        horizontalPanel);
	    dragController.registerDropController(columnDropController);

	    // initialize ProjectHashMap
		HashMap<String, VerticalPanel> ProjectHashMap = new HashMap<String, VerticalPanel>();
		
	    for (int col = 0; col < GetData.DBProjects.length; col++) {
	      // initialize a vertical panel to hold the heading and a second vertical panel
	      VerticalPanel columnCompositePanel = new VerticalPanel();

	      // initialize inner vertical panel to hold individual widgets
	      VerticalPanel verticalPanel = new VerticalPanel(); //VerticalPanelWithSpacer()
	      verticalPanel.setSpacing(1);
	      verticalPanel.setSize("50px","20px");
	      horizontalPanel.add(columnCompositePanel);
	      PanelProjectHashMap.put(verticalPanel, GetData.DBProjects[col]);
	      ProjectPanelHashMap.put(GetData.DBProjects[col], verticalPanel);

	      // initialize a widget drop controller for the current column
	      AssignmentDropController widgetDropController = new AssignmentDropController(verticalPanel);
	      widgetDragController.registerDropController(widgetDropController);

	      // Put together the column pieces
	      Project project = GetData.DBProjects[col];
	      Label heading = new Label(project.ProjectID + " ("+ project.minStudents + "|" + project.maxStudents+")");
	      columnCompositePanel.add(heading);
	      columnCompositePanel.add(verticalPanel);
	      columnCompositePanel.addStyleName("project-Panel");
	      ProjectHashMap.put(project.ProjectID, verticalPanel);	      

	      // make the column draggable by its heading
	      dragController.makeDraggable(columnCompositePanel, heading);
	    }
	   
	    int [][] assignment = HungarianAlgorithm.getAssignment();

	    for (int i = 0; i < HungarianAlgorithm.StudentList.length; i++) {
	        // initialize a student-widget
	    	Student student = HungarianAlgorithm.StudentList[i];
	    	int wish = student.findVote(HungarianAlgorithm.lookUpProject(assignment[i][1]).ProjectID);
	        HTML widget = new HTML("<html><body><table width=\"100%\" bgcolor="+ widgetcolors[wish] +">" +
	        				"<tr><td>"+student.firstname +" <br> " + student.lastname + " </td>" +
	        				"<td><h3>" + wish + "</h3></td></tr></table></body></html>");
	        LabelHashMap.put(widget, i);
	        
	        widget.addMouseListener(this);
			PopupPanel pop = new PopupPanel(true);
			pop.setWidget(new HTML(student.votes[0] + " <br> " + 
					student.votes[1] + " <br> " + 
					student.votes[2] + " <br> " + 
					student.votes[3] + " <br> " + 
					student.votes[4] ));
			PopupMap.put(widget,pop);
	
	        String ProjectID = HungarianAlgorithm.StudentList[i].placement.ProjectID;
			//widget.setSize(ProjectHashMap.get(ProjectID).setWidth(width)+"px", "");
	        ProjectHashMap.get(ProjectID).add(widget);

	        // make the widget draggable
	        widgetDragController.makeDraggable(widget);
	        
	      }
	    
	}

	public void onMouseDown(Widget sender, int x, int y) {		
	}

	public void onMouseEnter(Widget sender) {
		if (currentwidget != sender){
			if (currentwidget != null) PopupMap.get(currentwidget).hide();
			PopupMap.get(sender).setPopupPosition(sender.getAbsoluteLeft()+sender.getOffsetWidth(),sender.getAbsoluteTop());
			PopupMap.get(sender).show();	
			currentwidget = sender;
		}			
	}

	public void onMouseLeave(Widget sender) {		
	}

	public void onMouseMove(Widget sender, int x, int y) {	
	}

	public void onMouseUp(Widget sender, int x, int y) {	
	}

	public static void createCSV() {
		String csvString = new String();
		for(Student student : HungarianAlgorithm.StudentList){
			csvString += student.firstname + " " + student.lastname + ";" +
				(student.placement.ProjectID + "(" + student.findVote(student.placement.ProjectID) + ")\n");
		}
		System.out.println(csvString);
	}
	
	private FlexTable createTableForExport() {
		FlexTable AssignmentTable = new FlexTable();
		Student[] students = GetData.DBStudents;
		Project[] projects = GetData.DBProjects;
		
		for (int column=0; column<projects.length; column++)
		{		
			AssignmentTable.setText(0, column, projects[column].ProjectID);
			int row = 1;
			for	(Student student : students){
				if (student.placement == projects[column]){
					AssignmentTable.setText(row, column, student.firstname + " " + student.lastname);
					row++;
				}
			}
		}
		return AssignmentTable;
	}
	
	public void disableStudentDragging(){
		for(HTML widget : LabelHashMap.keySet()){
			widgetDragController.makeNotDraggable(widget);
			//System.out.println("Funktioniert noch nicht!");
		}
		System.out.println("done");
	}
}
