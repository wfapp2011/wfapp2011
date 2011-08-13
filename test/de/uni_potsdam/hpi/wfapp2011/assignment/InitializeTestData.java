package de.uni_potsdam.hpi.wfapp2011.assignment;

import java.util.Collection;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

//import junit.framework.*;

import de.uni_potsdam.hpi.wfapp2011.assignment.client.Project;
import de.uni_potsdam.hpi.wfapp2011.assignment.client.Student;
import de.uni_potsdam.hpi.wfapp2011.assignment.server.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.assignment.server.SQLTableException;


public class InitializeTestData {
	
	private static int numberOfStudents = 77;
	
	private static String [] randomPreNames = {
		"Paul","Max","Tim","Stefan","Kai","Mathias","Gerd","Johannes","Florian","Tino","Alexander","Oliver",
		"Claudia","Julia","Katrin","Stefanie","Maria","Anna","Anke","Susanne","Mandy"};
	
	private static String [] randomLastNames = {
		"Fischer","Meier","Schmidt","Bauer","Wunder","Berger","Meister","M\u00fcller","Lehmann","Schulz"
	};
	
	private static String [] ProjectIDs = {"0","1","2","3","4","5","6","7","8","9","10","11","12"};
	
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
    
    
	//@Ignore
	@Test
	public void InsertTestStudents(){
	
		DbInterface db = new DbInterface();
		
		try {
			db.connect("Ba", "SS", 2011);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		

		for (int i=0;i<numberOfStudents;i++){

			try {
				//db.executeUpdate("INSERT INTO Person (PersonID, Name, Email, Role) VALUES("+i+",'"+StudentNames[i]+"','test@mail.de','Student')");
				String prename = randomPreNames[randomPreNames.length - (int) (Math.random() * randomPreNames.length)-1];
				String lastname = randomLastNames[randomLastNames.length - (int) (Math.random() * randomLastNames.length)-1];
				db.executeUpdate("INSERT INTO Person (PersonID, Name, Email, Role) VALUES("+i+",'"+prename+" "+lastname+"','test"+i+"@mail.de','Student')");

			} catch (SQLTableException e) {
				e.printStackTrace();
			}
		}	
		
		assertTrue(true);
	}
	
	@Ignore
	//@Test
	public void InsertTestVotes(){
		
		DbInterface db = new DbInterface();
		
		try {
			DbInterface.initializeDatabase("Ba","SS", 2011);
			db.connect("Ba", "SS", 2011);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		
		for (int i=0;i<numberOfStudents;i++){
			
			shuffle(ProjectIDs);
			
			for (int j=1;j<=5;j++){
				
				try {
					db.executeUpdate("INSERT INTO Vote VALUES("+i+","+j+","+ ProjectIDs[j-1] +")");
					
				} catch (SQLTableException e) {
					e.printStackTrace();
				}
			}
		}	
		assertTrue(true);

		
	}
	
	@Ignore
	//@Test
	public void TestDatabaseConnection() {
		
		DbInterface db = new DbInterface();
		try {
			db.connect("Ba", "SS", 2011);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}

		Collection <Map <String,String>> result = db.executeQuery("SELECT Projectname,MinStud,MaxStud FROM Projecttopic");
		int i = 0;
		for(Map<String,String> m : result){
			String ProjectID = m.get("projectname");
			int minStud = new Integer(m.get("minstud"));
			int maxStud = new Integer(m.get("maxstud"));
			//DBProjects[i] = new Project(ProjectID, minStud, maxStud);
			i++;
		}

		result = db.executeQuery("SELECT Name FROM Person WHERE Role='Student'");
		//Select Person.Name, Vote.Priority, Vote.ProjectID from Person, Vote Where Role='Student' AND Vote.PERSONID = PERSON.PERSONID 

		i = 0;
		for(Map<String,String> m : result){
			String [] name = m.get("name").split(" ");
			String firstname = name[0];
			String lastname = name[name.length-1];
			
			Collection <Map <String,String>> subresult = db.executeQuery("SELECT Priority, Projectname FROM Vote,Projecttopic  WHERE Vote.ProjectID = PROJECTTOPIC.TopicID AND VOTE.PERSONID = '" + i +"'");

			String[] votes = new String[5];//subresult.size()];

			for(Map<String,String> sm : subresult){
				int j = new Integer(sm.get("priority"));
				votes[j-1]=sm.get("projectname");
			}
			
			//DBStudents[i] = new Student(firstname, lastname, votes);
			i++;
		}
	}

}
