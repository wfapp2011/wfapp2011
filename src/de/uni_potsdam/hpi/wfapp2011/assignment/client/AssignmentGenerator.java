package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import java.util.HashMap;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.HorizontalPanelDropController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.googlecode.gwtTableToExcel.client.TableToExcelClient;

/**
 * Class responsible for display and drag-and-drop functionality of the Assignment (Zuordnung) sub-page.
 */
public class AssignmentGenerator implements MouseListener{
	
	//Elements that need to be accessed by various methods
	private static Label btnSave;
	private static boolean changed;
	private static Label warningLabel;
	private static HorizontalPanel warningList;
	private static FlexTable ExportTable;
	private PickupDragController widgetDragController;
	
	//Dictionaries that connect our visuals with our data
	private static HashMap<HTML, PopupPanel> WidgetPopupMap = new HashMap<HTML, PopupPanel>();
	private static HashMap<VerticalPanel, Project> PanelProjectMap = new HashMap<VerticalPanel, Project>();
	private static HashMap<Project, VerticalPanel> ProjectPanelMap = new HashMap<Project, VerticalPanel>();
	private static HashMap<HTML, Student> WidgetStudentMap = new HashMap<HTML, Student>();

	
	public void setUpAssignmentPage(AbsolutePanel assignmentPanel) {
		//add Assignment-Menu
		HorizontalPanel assignmentMenuPanel = new HorizontalPanel();
		assignmentMenuPanel.setWidth("100%");
        assignmentPanel.add(assignmentMenuPanel);
		
		createWarningsDropDown(assignmentMenuPanel);  
        createExportButton(assignmentMenuPanel);    
		createSaveButton(assignmentMenuPanel);
		createFinishButton(assignmentMenuPanel);
		
		setUpDragAndDrop(assignmentPanel);
		
        AssignmentDropController.createWarnings();
	}

	/**
	 * Implements all Drag & Drop functionality for display and modification of the assignment.
	 * 
	 * @param assignmentPanel the Absolute Panel that the drag and drop application will be implemented on
	 */
	private void setUpDragAndDrop(AbsolutePanel assignmentPanel) {
		// initialize our column drag controller
	    PickupDragController dragController = new PickupDragController(assignmentPanel, false);
		dragController.setBehaviorConstrainedToBoundaryPanel(true);
	    dragController.setBehaviorMultipleSelection(false);

	    // initialize our widget drag controller
	    widgetDragController = new PickupDragController(assignmentPanel, false);
	    widgetDragController.setBehaviorMultipleSelection(false);

	    // initialize horizontal panel to hold our columns
	    HorizontalPanel horizontalPanel = new HorizontalPanel();
	    horizontalPanel.setSpacing(1);
	    horizontalPanel.setWidth("100%");
	    assignmentPanel.add(horizontalPanel);

	    // initialize our column drop controller
	    HorizontalPanelDropController columnDropController = new HorizontalPanelDropController(
	        horizontalPanel);
	    dragController.registerDropController(columnDropController);

	    // initialize ProjectHashMap
		HashMap<String, VerticalPanel> ProjectHashMap = new HashMap<String, VerticalPanel>();
		
	    for (int col = 0; col < AP5_main.DBProjects.length; col++) {
	      // initialize a vertical panel to hold the heading and a second vertical panel
	      VerticalPanel columnCompositePanel = new VerticalPanel();

	      // initialize inner vertical panel to hold individual widgets
	      VerticalPanel verticalPanel = new VerticalPanel(); 
	      verticalPanel.setSpacing(1);
	      verticalPanel.setSize("40px","20px");
	      horizontalPanel.add(columnCompositePanel);
	      PanelProjectMap.put(verticalPanel, AP5_main.DBProjects[col]);
	      ProjectPanelMap.put(AP5_main.DBProjects[col], verticalPanel);

	      // initialize a widget drop controller for the current column
	      AssignmentDropController widgetDropController = new AssignmentDropController(verticalPanel);
	      widgetDragController.registerDropController(widgetDropController);

	      // Put together the column pieces
	      Project project = AP5_main.DBProjects[col];
	      Label heading = new Label(project.projectID + " ("+ project.minStudents + "|" + project.maxStudents+")");
	      columnCompositePanel.add(heading);
	      columnCompositePanel.add(verticalPanel);
	      columnCompositePanel.addStyleName("project-Panel");
	      ProjectHashMap.put(project.projectID, verticalPanel);	      

	      // make the column draggable by its heading
	      dragController.makeDraggable(columnCompositePanel, heading);
	    }
	   
    	for (Student student : AP5_main.DBStudents) {
	        // make a widget for each student
	    	int wish = student.findVote(student.placement.projectID);
	        HTML widget = new HTML("<table width=\"100%\" bgcolor="+ getWidgetColor(wish) +">" +
	        				"<tr><td>" + student.name + " </td>" +
	        				"<td><h3>" + wish + "</h3></td></tr></table>");
	        WidgetStudentMap.put(widget, student);
	        
			// prepare pop-ups to show when mouse enters the widget
	        widget.addMouseListener(this);
	        PopupPanel pop = new PopupPanel(true);
			pop.setWidget(new HTML(student.votes[0] + " <br> " + 
					student.votes[1] + " <br> " + 
					student.votes[2] + " <br> " + 
					student.votes[3] + " <br> " + 
					student.votes[4] ));
			WidgetPopupMap.put(widget,pop);
	
			// add the widget to the correct column
	        String ProjectID = student.placement.projectID;
	        ProjectHashMap.get(ProjectID).add(widget);

	        // make the widget draggable
	        widgetDragController.makeDraggable(widget);
        }
	}

	/**
	 * Creates a button that shows/hides the list of warnings when clicked.
	 * 
	 * @param assignmentMenuPanel the HorizontalPanel that the drop-down list should be added to.
	 */
	private void createWarningsDropDown(HorizontalPanel assignmentMenuPanel) {
		//add Warning-panel 
		VerticalPanel warningPanel = new VerticalPanel();
        assignmentMenuPanel.add(warningPanel);
        
        //add WarningLabel to Warning-Panel
        warningLabel = new Label("Warnungen anzeigen");
        warningLabel.setWidth("400px");
        warningLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
		        warningList.setVisible(!warningList.isVisible());
			}
		});
        warningPanel.add(warningLabel);
        
        //add WarningList to Warning-Panel
        warningList = new HorizontalPanel();
        warningPanel.add(warningList);
        warningList.setVisible(false);
	}

	/**
	 * Creates a button and implements its functionality to export the current assignment as an Excel Table.
	 * 
	 * @param assignmentMenuPanel the HorizontalPanel that the button should be added to.
	 */
	private void createExportButton(HorizontalPanel assignmentMenuPanel) {
		//invisible FlexTable for Excel Export
		ExportTable = new FlexTable();
		ExportTable.setVisible(false);
		
		//using gwt-table-to-excel.jar
		TableToExcelClient assignmentAsExcel = new TableToExcelClient(ExportTable,"Excel Export","AssignmentTable "+ AP5_main.processTitle);
		assignmentMenuPanel.add(assignmentAsExcel.build());
		createTableForExport();
	}

	/**
	 * Sets the contents of <code>ExportTable</code> to the current assignment.
	 */
	public static void createTableForExport() {
		Student[] students = AP5_main.DBStudents;
		Project[] projects = AP5_main.DBProjects;
		
		ExportTable.clear(true);
		
		for (int column=0; column<projects.length; column++)
		{		
			ExportTable.setText(0, column, projects[column].projectID);
			int row = 1;
			for	(Student student : students){
				if (student.placement == projects[column]){
					ExportTable.setText(row, column, student.name);
					row++;
				}
			}
		}
	}
	
	/**
	 * Creates a button and implements its functionality to write the currently displayed assignment
	 * into the database.
	 * 
	 * @param assignmentMenuPanel the HorizontalPanel that the button should be added to.
	 */
	private void createSaveButton(final HorizontalPanel assignmentMenuPanel) {
		btnSave = new Label("Speichern");
		assignmentMenuPanel.add(btnSave);
		btnSave.addClickHandler(new ClickHandler(){
	        public void onClick(ClickEvent event){
	        	
	        	AP5_main.assignmentDataInterface.writePlacements(AP5_main.DBStudents,AP5_main.processId, new AsyncCallback<Void>() {
	        		public void onFailure(Throwable caught) {
	        			AP5_main.displayFailurePage(assignmentMenuPanel);
					}

				    public void onSuccess(Void v) {
				    	btnSave.removeStyleName("btnSave-unsaved");
				    }
				});
	        }
		});
	}

	/**
	 * Creates a button and implements its functionality to finish the assignment phase (after confirmation
	 * in form of a popup), that is to send emails out to the students with information on their placement 
	 * and to disable draggability of the students.
	 * 
	 * @param assignmentMenuPanel the HorizontalPanel that the button should be added to.
	 */
	private void createFinishButton(HorizontalPanel assignmentMenuPanel) {
		Label btnFinish = new Label("Abschlie\u00dfen");
		assignmentMenuPanel.add(btnFinish);
		  
		btnFinish.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				//make a popup that requests the user to confirm
				final PopupPanel confirmationPopup = new PopupPanel();
				confirmationPopup.setGlassEnabled(true);
				
				//make a cancel button
				ClickListener CancelListener = new ClickListener(){
					public void onClick(Widget sender){
						confirmationPopup.hide();
					}
				};
				Button cancelButton = new Button("Abbrechen", CancelListener);
				cancelButton.setWidth("90px");
				
				//make a confirmation button
				ClickListener ConfirmListener = new ClickListener(){
					public void onClick(Widget sender){
						confirmationPopup.hide();
						disableStudentDragging();
		   
						for(Project p : AP5_main.DBProjects){
							Student[] studsInP = p.findStudents();
							AP5_main.assignmentDataInterface.sendAssignment(studsInP, AP5_main.processId, new AsyncCallback<Void>() {
								public void onFailure(Throwable caught) {
									System.out.println("sending Emails failed");
								}
								public void onSuccess(Void v) {
		   				   	  		System.out.println("sending Emails complete");
								}
							});
						}
					}
				};
				Button confirmButton = new Button("OK", ConfirmListener);
				confirmButton.setWidth("90px");

				// the message to be shown in the pop-up
				HTML popupMessage = new HTML("<b> <h3 align=\"center\"> ACHTUNG! </h3>" +
						"Wollen Sie die derzeitige Zuordnung wirklich abschlie\u00dfen? <br>" +
						"<ul><li>Es werden E-Mails gem\u00e4\u00df der aktuellen Zuordnung an die jeweiligen Studenten " +
						"und Betreuer eines Projektes versendet. </li> " +
						"<li> Die Zuordnung wird abgeschlossen, auch wenn diese m\u00f6glicherweise noch Fehler enth\u00e4lt. </li>" +
				  		"<li>Nachtr\u00e4gliches Editieren ist nicht mehr m\u00f6glich! </li></ul></b>");
				
				// set together the confirmation pop-up 
				HorizontalPanel buttonHolder = new HorizontalPanel();
				buttonHolder.add(confirmButton);
				buttonHolder.add(cancelButton);
				
				VerticalPanel popupContents = new VerticalPanel();
				popupContents.setHorizontalAlignment(popupContents.ALIGN_CENTER);
				popupContents.add(popupMessage);
				popupContents.add(buttonHolder);
				
				confirmationPopup.add(popupContents);
				confirmationPopup.center();
		    }
		});
	}
	
	/**
	 * Sets the number of warnings that exist for the current assignment.
	 * @param i	the number of warnings.
	 */
	public static void setNumberOfWarnings(int i){
		warningLabel.setText("Warnungen anzeigen ("+ i +")");
	}
	
	/**
	 * Disables the possibility to drag students to different projects.
	 * 
	 * Used when assignment phase is over, so dragging can be disabled when refreshing the page.
	 */
	private void disableStudentDragging(){
		for(HTML widget : WidgetStudentMap.keySet()){
			widgetDragController.makeNotDraggable(widget);
		}
	}
	
	/**
	 * Finds the Project that is represented by a given panel.
	 * 
	 * @param panel an InsertPanel from which we want to know what Project it stands for
	 * @return	the Project the InsertPanel stands for
	 */
	public static Project getProject(InsertPanel panel){
		return PanelProjectMap.get(panel);
	}

	/**
	 * Finds the Panel that represents a given project.
	 * 
	 * @param project	the Project whose panel we want to find
	 * @return the Widget that stands for the Project
	 */
	public static Widget getPanel(Project project){
		return ProjectPanelMap.get(project).getParent();
	}

	/**
	 * For a vote priority, get the color in that student labels should be shown
	 * if this vote priority is the one fulfilled.
	 * 
	 * Changes to these color only need to be made here.
	 *
	 * @param wish the vote priority
	 * @return the color in which it shall be shown
	 */
	public static String getWidgetColor(int wish) {
		String[] widgetcolors = {"#FF3300","white","#FFFF66","#FFCC66","#FF9933","#FF6600"};
		return widgetcolors[wish];
	}
	
	/**
	 * Finds the Student that is represented by a given Widget.
	 * 
	 * @param w the Widget 
	 * @return the Student object it represents
	 */
	public static Student getStudent(Widget w){
		return WidgetStudentMap.get(w);
	}

	/**
	 * Refreshes the List of warnings that are displayed in the <code>warningList</code>.
	 * 
	 * @param html the HTML-Panel that contains all warnings
	 */
	public static void refreshWarningList(HTML html) {
		warningList.clear();
		warningList.add(html);		
	}
	
	/**
	 * Sets whether the assignment has changed since the last calculation.
	 * If so, the statistics have to be recalculated next time the page is loaded.
	 */
	public static void setChanged() {
		btnSave.addStyleName("btnSave-unsaved");
		changed = true;
	}
	
	/**
	 * @return whether the assignment has changed since the last statistics calculation
	 */
	public static boolean hasChanged(){
		return changed;
	}
	
	//***************************************************************//
	//These are implementations for methods required by MouseListener//
	//***************************************************************//
	
	//@Override
	public void onMouseDown(Widget sender, int x, int y) {		
	}

	//@Override
	/**
	 * Show all votes of a Student as a little Pop-Up when pointing the mouse on the widget that stands for it. 
	 */
	public void onMouseEnter(Widget sender) {
		WidgetPopupMap.get(sender).setPopupPosition(sender.getAbsoluteLeft()+sender.getOffsetWidth(),sender.getAbsoluteTop());
		WidgetPopupMap.get(sender).show();	
	}

	//@Override
	public void onMouseLeave(Widget sender) {
		WidgetPopupMap.get(sender).hide();
	}

	//@Override
	public void onMouseMove(Widget sender, int x, int y) {	
	}

	//@Override
	public void onMouseUp(Widget sender, int x, int y) {	
	}
}
