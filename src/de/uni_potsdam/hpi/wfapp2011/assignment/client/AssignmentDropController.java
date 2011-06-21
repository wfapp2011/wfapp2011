package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.VerticalPanelDropController;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AssignmentDropController extends VerticalPanelDropController {

	public AssignmentDropController(VerticalPanel dropTarget) {
		super(dropTarget);
	}
	
	//@Override
	public void onDrop(DragContext context){
		super.onDrop(context);
		for (Widget widget : context.selectedWidgets){
			Project newProject = AssignmentGenerator.PanelHashMap.get(dropTarget);
			int i = AssignmentGenerator.LabelHashMap.get(widget);
			Student movedStudent = HungarianAlgorithm.StudentList[i];
			HungarianAlgorithm.StudentList[i].placement=newProject;
			int wish = movedStudent.findVote(newProject.ProjectID);
			widget.getElement().setInnerHTML("<html>" +
					"<body>" +
					"<table bgcolor="+ AssignmentGenerator.widgetcolors[wish] +">" +
							"<tr><td>"+ movedStudent.firstname + "<br> " + movedStudent.lastname + " </td>" +
							"<td><h3>" + wish + "</h3></td></tr>" +
					"</table>" +
					"</body>" +
					"</html>");
		}
	}

		/*public static int[] createWarnings(int[][] assignment) {
			int prIndex = 0;
			int[] errors = new int[ProjectList.length];
			while(prIndex < ProjectList.length){
				Project pr = ProjectList[prIndex];
				if (pr.count(assignment) < pr.minStudents){
						System.out.println("Projekt "+ pr.ProjectID +
							" hat nicht genug Studenten!" );
						errors[prIndex]=pr.checkCorrectSize(assignment);
				}
				prIndex++;
			}
			return errors;
		}*/
}
