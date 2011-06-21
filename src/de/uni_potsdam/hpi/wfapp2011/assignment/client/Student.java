package de.uni_potsdam.hpi.wfapp2011.assignment.client;

public class Student {
	public String firstname, lastname;
	public String[] votes = new String[5];
	public Project placement;
	
	Student(String a, String b, String i1, String i2, String i3, String i4, String i5){
		this.firstname = a;
		this.lastname = b;
		this.votes[0]=i1;
		this.votes[1]=i2;
		this.votes[2]=i3;
		this.votes[3]=i4;
		this.votes[4]=i5;
	}

	public int findVote(String ProjectID) {
		for (int i=0; i<votes.length; i++){
			if(votes[i]==ProjectID) return i + 1;
		}
		return 0;
	}
}
