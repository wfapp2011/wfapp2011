package de.uni_potsdam.hpi.wfapp2011.assignment.client;

public class Student {
	public String firstname, lastname;
	public String[] votes;
	public Project placement;
	
	Student(String a, String b, String[] votes){
		this.firstname = a;
		this.lastname = b;
		this.votes = votes;
	}

	public int findVote(String ProjectID) {
		for (int i=0; i<votes.length; i++){
			if(votes[i]==ProjectID) return i + 1;
		}
		return 0;
	}
}
