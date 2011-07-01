package de.uni_potsdam.hpi.wfapp2011.assignment.client;

public class StudentGenerator {
	String[] ProjectList;
	
	StudentGenerator(String[] PL){
		ProjectList = PL;
	}
	//***********************//
	//Shuffle!				 //
	//***********************//	
    
    // swaps array elements i and j
    public static void exch(String[] a, int i, int j) {
        String swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // take as input an array of strings and rearrange them in random order
    public static void shuffle(String[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N-i));   // between i and N-1
            exch(a, i, r);
        }
    }
    
    //**********************//
    
  //Generate RandomStudents
	public Student[] getList (){
	int numberOfStudents = 37;
	Student[] TestStudents = new Student[numberOfStudents];
	for (int i=0; i<numberOfStudents; i++){
		shuffle(ProjectList);
		String[] votes = {ProjectList[0],ProjectList[1],ProjectList[2],ProjectList[3],ProjectList[4]};
		Student aStudent = new Student("Stud "+(i+1), "TestName ", votes);
		TestStudents[i]=aStudent;
		}
	return TestStudents;
	}
}
