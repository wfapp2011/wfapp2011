package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import java.util.LinkedList;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.VerticalPanelDropController;
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
		createWarnings();	
	}

		public static void createWarnings() {
			int prIndex = 0;
			int[] errors = new int[TestData.TestProjects.length];
			while(prIndex < TestData.TestProjects.length){
				Project pr = TestData.TestProjects[prIndex];
				int noSt = pr.countStudents();
				if (noSt < pr.minStudents){
					System.out.println("Warnung: Projekt " + pr.ProjectID + " hat nicht genug Studenten!");
					errors[prIndex]= noSt - pr.minStudents;
					warnings.add(" Projekt " + pr.ProjectID + " hat nicht genug Studenten!");
				}
				else if(noSt > pr.maxStudents){
					System.out.println("Warnung: Projekt " + pr.ProjectID + " hat zu viele Studenten!");
					errors[prIndex]= noSt - pr.maxStudents;
					warnings.add(" Projekt " + pr.ProjectID + " hat zu viele Studenten!");
				}
				prIndex++;
			}
			Wfapp2011assignment.warningLabel.setText("Warnungen anzeigen ("+warnings.size() +")");
			String html = "<html><body><table><tr><td><font color=\"red\">";
			while (warnings.size() > 0){
				html=html +warnings.getFirst() + "<br>";
				warnings.removeFirst();
			}
			html=html+"</font></td></tr></table></body></html>";
			Wfapp2011assignment.warningList.clear();
			Wfapp2011assignment.warningList.add(new HTML(html));
		}
}
