package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import java.util.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uni_potsdam.hpi.wfapp2011.assignment.server.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.assignment.server.SQLTableException;

public class GetData {
		
	public static Project[] DBProjects = 
		{new Project("M1",5,8),
		new Project("M2",5,8),
		new Project("D1",5,8),
		new Project("D2",5,8),
		new Project("W1",5,8),
		new Project("W2",5,8),
		new Project("H1",5,8),
		new Project("H2",5,8),
		new Project("N1",5,8), 
		new Project("N2",4,7)};
	public static Student[] DBStudents = new StudentGenerator(DBProjectIdList()).getList();//{new Student("Peter","Müller", Tvotes),new Student("Tino","Junge", Tvotes),new Student("Tino","Junge", Tvotes),new Student("Tino","Junge", Tvotes),new Student("Tino","Junge", Tvotes)};
	public double[][] array = HungarianAlgorithm.VotesMatrix;
	
	//Generate List of ProjectIDs
	private static String[] DBProjectIdList (){
		String[] ProjectList = new String[DBProjects.length];
		for (int j=0; j<DBProjects.length; j++){
			ProjectList[j]=DBProjects[j].ProjectID;
		}
		return ProjectList;
	}
		
	/*private AssignmentDataInterfaceAsync assignmentDataInterface = GWT.create(AssignmentDataInterface.class);
	
	public void getData() {
		//Initialice Interface-RemoteService
		if (assignmentDataInterface == null){
			assignmentDataInterface = GWT.create(AssignmentDataInterface.class);
		}
		
		AsyncCallback<Project[]> callback = new AsyncCallback<Project[]>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
		      }

		      public void onSuccess(Project[] result) {
		    	  DBProjects = result;
		      }
		};
		
		assignmentDataInterface.getProjects(callback);
	} */
	
	/*public static void getData(){
				
		System.out.println("dbinterface");
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
			DBProjects[i] = new Project(ProjectID, minStud, maxStud);
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

			String[] votes = new String[subresult.size()];

			for(Map<String,String> sm : subresult){
				int j = new Integer(sm.get("priority"));
				votes[j-1]=sm.get("projectname");
			}
			
			DBStudents[i] = new Student(firstname, lastname, votes);
			i++;
		}
	}*/
}
