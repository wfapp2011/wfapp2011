package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.user.client.ui.FlexTable;

/**
 * Class responsible for the Votes (Wahltabelle) sub-page.
 */
public class VotesTableGenerator {

	protected static double[][] VotesMatrix;

	/**
	 * Creates a Table with Data on who has voted which project with what priority.
	 * The Table specifies necessary visuals as well, so it is ready-to-add to a panel.
	 * 
	 * @return the FlexTable with students as rows, projects as columns and priorities in the fields
	 */
	public static FlexTable createVotesTable() {
		FlexTable VotesTable = new FlexTable();
		
		VotesTable.setWidth("100%");
		VotesTable.setCellSpacing(2);
	
		Project[] projects = AP5_main.DBProjects;
		Student[] students = AP5_main.DBStudents;
		
		for (int i=0; i<students.length; i++)
		{
			if (i==0) {
				for (int j=0; j<projects.length; j++)
				{
					VotesTable.setText(i, j+1, projects[j].projectID);
				}
			}
			
			VotesTable.setText(i+1, 0, students[i].name);
			
			int k=0;			
			for(int j=0; j<projects.length; j++)
			{
				if(VotesMatrix[i][k] == 0){
					VotesTable.setText(i+1, j+1, String.valueOf(0));
				}
				else{
					VotesTable.setText(i+1, j+1, String.valueOf(students[i].votes.length - (int)VotesMatrix[i][k] + 1));
				}
				k+=projects[j].maxStudents;
			}	
		}
		return VotesTable;
	}
}
