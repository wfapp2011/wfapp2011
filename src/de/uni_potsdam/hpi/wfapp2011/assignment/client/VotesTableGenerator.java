package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;

public class VotesTableGenerator {

	public static void createVotesTable(DockPanel dockPanel_1, FlexTable VotesTable) {
		dockPanel_1.add(VotesTable, DockPanel.WEST);
		dockPanel_1.setCellWidth(VotesTable, " ");
		VotesTable.setSize("100%", "");
		VotesTable.setText(0, 0, " ");
		VotesTable.setCellSpacing(2);
	
		Project[] TestProjects = TestData.TestProjects;
		Student[] TestStudents = new TestData().TestStudents;
		
		double[][] array = new TestData().array;
		
		for (int i=0; i<TestStudents.length; i++)
		{
			if (i==0) {
				for (int j=0; j<TestProjects.length; j++)
				{
					VotesTable.setText(i, j+1, TestProjects[j].ProjectID);
				}
			}
			
			VotesTable.setText(i+1, 0, TestStudents[i].firstname + " "+ TestStudents[i].lastname);
			
			int k=0;			
			for(int j=0; j<TestProjects.length; j++)
			{
				VotesTable.setText(i+1, j+1, String.valueOf((int)array[i][k]));
				k+=TestProjects[j].maxStudents;
			}	
		}
		
	}
}
