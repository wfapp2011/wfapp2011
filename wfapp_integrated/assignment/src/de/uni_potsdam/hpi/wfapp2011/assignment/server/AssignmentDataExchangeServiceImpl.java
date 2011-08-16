package de.uni_potsdam.hpi.wfapp2011.assignment.server;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_potsdam.hpi.wfapp2011.assignment.client.AssignmentDataExchangeService;
import de.uni_potsdam.hpi.wfapp2011.assignment.client.Project;
import de.uni_potsdam.hpi.wfapp2011.assignment.client.Student;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.email.SmtpEmailSender;
import de.uni_potsdam.hpi.wfapp2011.servercore.logging.MatchingLogger;
import de.uni_potsdam.hpi.wfapp2011.servercore.session.SessionManagement;

public class AssignmentDataExchangeServiceImpl extends RemoteServiceServlet 
implements AssignmentDataExchangeService {
	private static final long serialVersionUID = 1L;

	/**
	 * Reads the project Data out of the database.
	 * 
	 * @return an Array of Project objects representing all published projects of the current process
	 */
	public Project[] getProjects(String type, String semester, int year){
		DbInterface db = new DbInterface();
		try {
			db.connect(type, semester, year);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		Collection <Map <String,String>> result = 
			db.executeQuery("SELECT Projectname,MinStud,MaxStud FROM Projecttopic");
		Project[] DBProjects = new Project[result.size()];
		int i = 0;
		for(Map<String,String> m : result){
			String ProjectID = m.get("projectname");
			int minStud = new Integer(m.get("minstud"));
			int maxStud = new Integer(m.get("maxstud"));
			DBProjects[i] = new Project(ProjectID, minStud, maxStud);
			i++;
		}

		return DBProjects; 
	}

	/**
	 * Reads the student data out of the database.
	 * 
	 * @return an Array of Student objects representing all students stored in the given database
	 */
	public Student[] getStudents(String type, String semester, int year) {
		
		DbInterface db = new DbInterface();
		try {
			db.connect(type, semester, year);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		
		Collection <Map <String,String>> result = db.executeQuery("SELECT Name, Email, Placement " +
				"FROM Person WHERE Role='Student'"); 

		int i = 0;
		Student[] DBStudents = new Student[result.size()];
		for(Map<String,String> m : result){
			String name = m.get("name");
			String email = m.get("email");
			String placement = m.get("placement");

			Collection <Map <String,String>> subresult = db.executeQuery("SELECT Priority, Projectname " +
					"FROM Vote,Projecttopic  WHERE Vote.ProjectID = PROJECTTOPIC.TopicID AND VOTE.PERSONID = '"+ i +"'");

			String[] votes = new String[subresult.size()];

			for(Map<String,String> sm : subresult){
				int j = new Integer(sm.get("priority"));
				votes[j-1]=sm.get("projectname");
			}
			
			DBStudents[i] = new Student(name, email, votes, placement);
			i++;
		}
		
		return DBStudents;
	}

	/**
	 * Writes the current assignment into the database.
	 * For each student, the current project he/she is placed in is looked up and stored.
	 */
	public void writePlacements(Student[] students, String type, String semester, int year) {
		
		DbInterface db = new DbInterface();
		try {
			db.connect(type, semester, year);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		
		for(Student st: students){
			try {
				db.executeUpdate("UPDATE Person SET Placement = '"+ st.placement.projectID +
						"' WHERE Name = '"+ st.name + "' AND Email = '"+st.email+"'");
			} catch (SQLTableException e) {
				e.printStackTrace();
			}
			
		}
		
		MatchingLogger.getInstance().logChangedMatching(type, semester, year, "test@mail.com", year+"_"+semester+"_"+type);
		
	}
	
	/**
	 * Sends an Email to a list of students with information on their placement.
	 * 
	 * @param students	an Array of Student objects that belong to the same project, 
	 * 					NOTE: If Students are assigned to different projects, wrong information is sent.
	 */
	public void sendAssignment(Student[] students, String type, String semester, int year) {
		
		String emails = "";
		String names = "";
		
		for (int i=0; i<=students.length; i++){
			emails = emails + students[i].email + ";";
			names =  names + students[i].name + ";";
		}
		
		try {
			SmtpEmailSender.getInstance().sendMultipleEmails(
					emails, names, 
					"Bachelorprojekt", 
					"Sehr geehrte Studenten, \n\n" +
					"Sie wurden dem Projekt "+ students[0].placement.projectID +" zugeteilt. \n\n\n" +
					"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		MatchingLogger.getInstance().logMatchingCompleted(type, semester, year, "test@mail.com");

	}
	
	@Override
	public void logout(String id) {
		SessionManagement.getInstance().logout(Integer.valueOf(id));
	}
}
