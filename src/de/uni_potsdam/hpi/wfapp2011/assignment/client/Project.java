package de.uni_potsdam.hpi.wfapp2011.assignment.client;

public class Project {
	String ProjectID;
	int minStudents;
	int maxStudents;
	int rankPoints;
	
	Project(String a, int b, int c){
		this.ProjectID = a;
		this.minStudents = b;
		this.maxStudents = c;
	}
	
	public int count(int[][] assignment) {
		int count = 0;
		for(int i = 0; i<assignment.length; i++){
			if (HungarianAlgorithm.lookUpProject(assignment[i][1]) == this) count++;
		}
		return count;
	}

	public int checkCorrectSize(int[][] assignment) {
		return minStudents-count(assignment);
	}
}
