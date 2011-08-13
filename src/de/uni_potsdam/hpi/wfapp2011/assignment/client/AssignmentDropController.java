package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import java.util.LinkedList;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.VerticalPanelDropController;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Special Drop Controller for this application: 
 * Saves changes temporarily, specifies visual changes.
 * 
 * @see VerticalPanelDropController
 */

public class AssignmentDropController extends VerticalPanelDropController {
	
	public AssignmentDropController(VerticalPanel dropTarget) {
		super(dropTarget);
	}
	
	/**
	  * Special onDrop method for this application: 
	  * Calls super method of onDrop, then changes the placement 
	  * of the dropped Student and refreshes warnings.
	  */
	public void onDrop(DragContext context){
		super.onDrop(context);

		for (Widget widget : context.selectedWidgets){
			moveStudent(widget);
		}
		
		createWarnings();
		
  	  	AssignmentGenerator.setChanged();
		AssignmentGenerator.createTableForExport();
	}

	/**
	 * Finds out which project a student label was dropped on, 
	 * changes the student's placement and displayed visuals correspondingly
	 * 
	 * @param widget Widget that is being moved
	 */
	
	private void moveStudent(Widget widget) {
	
		Project newProject = AssignmentGenerator.getProject(this.dropTarget);
		Student movedStudent = AssignmentGenerator.getStudent(widget);
		movedStudent.placement = newProject;
		
		int wish = movedStudent.findVote(newProject.projectID);
		widget.getElement().setInnerHTML("<html>" +
				"<body>" +
				"<table width=\"100%\" bgcolor="+ AssignmentGenerator.getWidgetColor(wish) +">" +
						"<tr><td>" +movedStudent.name + " </td>" +
						"<td><h3>" + wish + "</h3></td></tr>" +
				"</table>" +
				"</body>" +
				"</html>");
	}
	

	/**
	 * Refreshes the warnings displayed and changes project visuals 
	 * depending on whether they have a valid number of students.
	 */
	
	public static void createWarnings() {
		LinkedList<String> warnings = generateWarningList();
		
		AssignmentGenerator.setNumberOfWarnings(warnings.size());
		String html = "<html><body><table><tr><td>";
		while (warnings.size() > 0){
			html=html +warnings.getFirst() + "<br>";
			warnings.removeFirst();
		}
		html += "</td></tr></table></body></html>";
		
		AssignmentGenerator.refreshWarningList(new HTML(html));
	}

	/**
	 * Generates all currently applicable warnings by iterating over all projects
	 * and sets their background colors accordingly
	 * 
	 * @return the list of current warnings
	 */
	private static LinkedList<String> generateWarningList() {
		LinkedList<String> warnings = new LinkedList<String>(); 

		int prIndex = 0;
		while(prIndex < AP5_main.DBProjects.length){
			Project pr = AP5_main.DBProjects[prIndex];
			int noSt = pr.countStudents();
			
			//gray column and black warning if project is empty
			if (noSt == 0){
				warnings.add("<font color=\"black\"> Projekt " + pr.projectID + " ist nicht besetzt. </font>");
				AssignmentGenerator.getPanel(pr).setStyleName("project-Panel-Empty");
			}
			//red column and red warning if project has too few students
			else if (noSt < pr.minStudents){
				warnings.add("<font color=\"red\"> Projekt " + pr.projectID + " hat "+ (pr.minStudents - noSt) +" Studenten zu wenig! </font>");
				AssignmentGenerator.getPanel(pr).setStyleName("project-Panel-Warning");
			}
			//red column and red warning if project has too many students
			else if(noSt > pr.maxStudents){
				warnings.add("<font color=\"red\"> Projekt " + pr.projectID + " hat " + (noSt - pr.maxStudents) + " Studenten zu viel! </font>");
				AssignmentGenerator.getPanel(pr).setStyleName("project-Panel-Warning");
			}
			//green column and no warnings for all other projects
			else {
				AssignmentGenerator.getPanel(pr).setStyleName("project-Panel");
			}
			prIndex++;
		}
		return warnings;
	}
}
