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
