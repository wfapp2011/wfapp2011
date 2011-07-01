package de.uni_potsdam.hpi.wfapp2011.assignment;

import java.util.Collection;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

//import junit.framework.*;

import de.uni_potsdam.hpi.wfapp2011.assignment.client.Project;
import de.uni_potsdam.hpi.wfapp2011.assignment.client.Student;
import de.uni_potsdam.hpi.wfapp2011.assignment.client.StudentGenerator;
import de.uni_potsdam.hpi.wfapp2011.assignment.server.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.assignment.server.SQLTableException;


public class InitializeTestData {
	
	private static String [] StudentNames = {
		"Adrian	Klinger",
		"Alexander Schulze",
		"Angelo	Haller",
		"Antonia Göbel",
		"Astrid	Thomschke",
		"Benjamin Reißaus",
		"Benjamin	Siegmund",
		"Catharina	Hahnfeld",
		"Cathleen	Ramson",
		"Christoph	Matthies",
		"Christoph	Oehlke",
		"Christopher	Schmidt",
		"Claudia	Exeler",
		"Conrad	Calmez",
		"Cristian	Godde",
		"Daniel	Hoffmann",
		"Daniel	Schäufele",
		"Dennis	Fuhrmann",
		"Dominic	Petrick",
		"Eric	Seckler",
		"Eric Bustrel	Guimatsia Zangue",
		"Fabian	Eckert",
		"Felix	Jankowski",
		"Felix	Kubicek",
		"Georg	Krüger",
		"Hannes	Würfel",
		"Hubert	Hesse",
		"Ingo	Richter",
		"Jakob	Zwiener",
		"Jan	Koßmann",
		"Jan-Peer	Rudolph",
		"Janek	Röhl",
		"Janek	Ummethum",
		"Jannik	Marten",
		"Jeffrey	Wichlitzky",
		"Johannes	Henning",
		"Johannes	Schirrmeister",
		"Jonas	Enderlein",
		"Julien	Bergner",
		"Kai	Rollmann",
		"Katrin	Honauer",
		"Leon(id)	Berov",
		"Ludwig	Kraatz",
		"Ludwig Wilhelm	Wall",
		"Lukas	Brand",
		"Lukas	Pirl",
		"Lukas	Schulze",
		"Magdalena	Noffke",
		"Mandy	Roick",
		"Marcel	Pursche",
		"Maria	Graber",
		"Maria Neise",
		"Marius	Knaust",
		"Markus	Dietsche",
		"Markus	Hinsche",
		"Martin	Fritzsche",
		"Martin	Schönberg",
		"Marvin	Keller",
		"Matthias	Bastian",
		"Max	Bothe",
		"Michael	Hopstock",
		"Nicolas	Fricke",
		"Patrick	Lühne",
		"Patrick	Rein",
		"Robert	Lehmann",
		"Robert	Schäfer",
		"Robin	Jörke",
		"Robin	Schreiber",
		"Stefan	Lehmann",
		"Stefanie	Birth",
		"Steffen	Grohsschmiedt",
		"Stephan	Wunderlich",
		"Tim 	Spankowski",
		"Tim	Sporleder",
		"Tino	Junge",
		"Tommy	Neubert",
		"Yanina	Yurchenko"};
	
	private static String [] ProjectIDs = {"0","1","2","3","4","5","6","7","8","9","10","11"};
	/*@Test
	public void InsertTestStudents(){
	
		DbInterface db = new DbInterface();
		
		try {
			db.connect("Ba", "SS", 2011);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		
		for (int i=0;i<StudentNames.length;i++){
			try {
				db.executeUpdate("INSERT INTO Person VALUES("+i+",'"+StudentNames[i]+"','test@mail.de','Student',0)");
				
			} catch (SQLTableException e) {
				e.printStackTrace();
			}
		}		
		
		assertTrue(true);
	}*/
	
	@Ignore
	@Test
	public void InsertTestVotes(){
		
		DbInterface db = new DbInterface();
		
		try {
			DbInterface.initializeDatabase("Ma","SS", 2011);
			db.connect("Ma", "SS", 2011);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		
		for (int i=0;i<StudentNames.length;i++){
			
			StudentGenerator.shuffle(ProjectIDs);
			
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
	
	@Test
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
