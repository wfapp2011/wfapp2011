package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import java.util.LinkedList;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.VerticalPanelDropController;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class AssignmentDropController extends VerticalPanelDropController {
	public static LinkedList<String> warnings = new LinkedList<String>(); 
	
	public AssignmentDropController(VerticalPanel dropTarget) {
		super(dropTarget);
	}
	
	//@Override
	public void onDrop(DragContext context){
		super.onDrop(context);

		for (Widget widget : context.selectedWidgets){
			Project newProject = AssignmentGenerator.PanelProjectHashMap.get(dropTarget);
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
		createWarnings();	
	}

	public static void createWarnings() {
		int prIndex = 0;
		
		while(prIndex < GetData.DBProjects.length){
			Project pr = GetData.DBProjects[prIndex];
			int noSt = pr.countStudents();
			if (noSt == 0){
				warnings.add("<font color=\"black\"> Projekt " + pr.ProjectID + " ist nicht besetzt. </font>");
				AssignmentGenerator.ProjectPanelHashMap.get(pr).getParent().setStyleName("project-Panel-Empty");
			}
			else if (noSt < pr.minStudents){
				warnings.add("<font color=\"red\"> Projekt " + pr.ProjectID + " hat "+ (pr.minStudents - noSt) +" Studenten zu wenig! </font>");
				AssignmentGenerator.ProjectPanelHashMap.get(pr).getParent().setStyleName("project-Panel-Warning");
			}
			else if(noSt > pr.maxStudents){
				warnings.add("<font color=\"red\"> Projekt " + pr.ProjectID + " hat " + (noSt - pr.maxStudents) + " Studenten zu viel! </font>");
				AssignmentGenerator.ProjectPanelHashMap.get(pr).getParent().setStyleName("project-Panel-Warning");
			}
			else {
				AssignmentGenerator.ProjectPanelHashMap.get(pr).getParent().setStyleName("project-Panel");
			}
			prIndex++;
		}
		
		Wfapp2011assignment.warningLabel.setText("Warnungen anzeigen ("+warnings.size() +")");
		String html = "<html><body><table><tr><td>";
		while (warnings.size() > 0){
			html=html +warnings.getFirst() + "<br>";
			warnings.removeFirst();
		}
		html=html+"</td></tr></table></body></html>";
		
		Wfapp2011assignment.warningList.clear();
		Wfapp2011assignment.warningList.add(new HTML(html));
		
		//refresh Statistics page
		StatisticsCalculator.setChanged();
	}
}
