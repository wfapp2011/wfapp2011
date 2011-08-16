package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import java.io.Serializable;

/**
 * Stores all data relevant to a Bachelor's / Master's project.
 */
public class Project implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String projectID;
	public int minStudents;
	public int maxStudents;
	
	/**
	 * Default constructor, creates an empty project.
	 */
	public Project(){
		this.projectID = "default";
		this.minStudents = 0;
		this.maxStudents = 0;
	}
	
	/**
	 * Standard constructor.
	 * 
	 * @param pID the String containing a name or an ID
	 * @param min minimum number of students required for the project
	 * @param max maximum number of students allowed in the project
	 */
	public Project(String pID, int min, int max){
		this.projectID = pID;
		this.minStudents = min;
		this.maxStudents = max;
	}
	
	/**
	 * Counts students currently assigned to this project by their placement attribute.
	 * 
	 * @return the number of students currently assigned to this project
	 */
	public int countStudents() {
		int count = 0;
		Student[] studList = AP5_main.DBStudents;
		for (int i=0; i<studList.length; i++){
			Student student = studList[i];
			if (student.placement == this) count++;
		}
		return count;
	}

	/**
	 * Find the students assigned to this project.
	 * 
	 * @return the <code>Student</code> objects associated with this project
	 */
	public Student[] findStudents() {
		Student[] foundStudents = new Student[this.countStudents()];
		int j=0;
		for (Student student : AP5_main.DBStudents){
			if (student.placement == this) {
				foundStudents[j] = student;
				j++;
			}
		}
		return foundStudents;
	}
}
