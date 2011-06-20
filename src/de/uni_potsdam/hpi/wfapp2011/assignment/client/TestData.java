package de.uni_potsdam.hpi.wfapp2011.assignment.client;

public class TestData {
		

	
	static Project[] TestProjects = 
	   {new Project("N1", 4, 8), 
		new Project("N2", 4, 8), 
		new Project("A1", 4, 8),
		new Project("W1", 4, 8), 
		new Project("D1", 4, 8),
		new Project("T1", 4, 8),
		new Project("M1", 4, 8),
		new Project("F1", 4, 8),
		new Project("F2", 4, 8),
		new Project("T2", 4, 8),
		new Project("E2", 4, 8),
		new Project("E1", 4, 8)};
	
	/*Student[] TestStudents =
	   {new Student("Stud1", " ", "N1", "N2", "A1", "W1", "D1"), 
		new Student("Stud2", " ", "N2", "A1", "W1", "D1", "N1"),
		new Student("Stud3", " ", "N1", "W1", "D1", "N2", "A1"),
		new Student("Stud4", " ", "A1", "N1", "W1", "D1", "N2"),
		new Student("Stud5", " ", "D1", "N1", "N2", "A1", "W1"),
		new Student("Stud6", " ", "N1", "N2", "A1", "W1", "D1"),
		new Student("Stud7", " ", "W1", "D1", "N1", "N2", "A1"),
		new Student("Stud8", " ", "W1", "N1", "N2", "A1", "D1"),
		new Student("Stud9", " ", "D1", "A1", "W1", "N2", "N1"),
		new Student("Stud10", " ", "N1", "D1", "N2", "W1", "A1"),
		new Student("Stud11", " ", "N1", "N2", "W1", "A1", "D1"),
		new Student("Stud12", " ", "D1", "N1", "N2", "W1", "A1"),
		new Student("Stud13", " ", "A1", "D1", "N2", "W1", "N1"),
		new Student("Stud14", " ", "N1", "D1", "N2", "W1", "A1"),
		new Student("Stud15", " ", "N1", "A1", "D1", "N2", "W1"),};
	*/
	
	//Generate List of Projects
	private static String[] ProjectList (){
		String[] ProjectList = new String[TestProjects.length];
		for (int j=0; j<TestProjects.length; j++){
			ProjectList[j]=TestProjects[j].ProjectID;
		}
		return ProjectList;
	}
		
	public Student[] TestStudents = new StudentGenerator(ProjectList()).getList();
	
	public double[][] array = HungarianAlgorithm.generateMatrix(TestProjects, TestStudents);

	
}
