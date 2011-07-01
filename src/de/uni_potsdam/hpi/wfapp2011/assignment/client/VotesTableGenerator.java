package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;


public class VotesTableGenerator {

	public static void createVotesTable(DockPanel parent, FlexTable VotesTable) {
		parent.add(VotesTable, DockPanel.WEST);
		parent.setCellWidth(VotesTable, " ");
		VotesTable.setSize("100%", "");
		VotesTable.setText(0, 0, " ");
		VotesTable.setCellSpacing(2);
	
		Project[] projects = GetData.DBProjects;
		Student[] students = GetData.DBStudents;
		
		double[][] array = new GetData().array;
		
		for (int i=0; i<students.length; i++)
		{
			if (i==0) {
				for (int j=0; j<projects.length; j++)
				{
					VotesTable.setText(i, j+1, projects[j].ProjectID);
				}
			}
			
			VotesTable.setText(i+1, 0, students[i].firstname + " "+ students[i].lastname);
			
			int k=0;			
			for(int j=0; j<projects.length; j++)
			{
				if(array[i][k] == 0){
					VotesTable.setText(i+1, j+1, String.valueOf(0));
				}
				else{
					VotesTable.setText(i+1, j+1, String.valueOf(students[i].votes.length - (int)array[i][k] + 1));
				}
				k+=projects[j].maxStudents;
			}	
		}
		
	}
}
