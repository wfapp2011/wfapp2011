package de.uni_potsdam.hpi.wfapp2011.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_potsdam.hpi.wfapp2011.client.Person;
import de.uni_potsdam.hpi.wfapp2011.client.Topic;
import de.uni_potsdam.hpi.wfapp2011.client.Vote;
import de.uni_potsdam.hpi.wfapp2011.client.VotingDatabaseService;

public class VotingDatabaseServiceImpl extends RemoteServiceServlet implements VotingDatabaseService {
	private static final long serialVersionUID = 1L;

	@Override
	public ArrayList<Topic> loadTopics(String type, String semester, int year) {
		ArrayList<Topic> result = new ArrayList<Topic>();
		
		DbInterface database = new DbInterface();
		try {
		
			database.connect(type, semester, year);
			Collection<Map<String, String>> topics = database.executeQuery("SELECT TOPICID, PROJECTNAME, PROJECTSHORTCUT, PROJECTDESCRIPTION, MINSTUD, MAXSTUD FROM PROJECTTOPIC ORDER BY PROJECTSHORTCUT");
				
			for (Map<String,String> entry: topics)
			{
				Topic newTopic = new Topic(	new Integer(entry.get("topicid")),
											entry.get("projectname"),
											entry.get("projectshortcut"),
											entry.get("projectdescription"),
											new Integer(entry.get("minstud")),
											new Integer(entry.get("maxstud")),
											"", "");
				
				Collection<Map<String,String>> contactPersons = database.executeQuery("SELECT PERSON.PERSONID, NAME, EMAIL, ROLE, DEPARTMENT FROM PERSON, CONTACTPERSON WHERE PERSON.PERSONID=CONTACTPERSON.PERSONID AND CONTACTPERSON.PROJECTID="+newTopic.getProjectID());
				for (Map<String,String> personEntry: contactPersons)
				{
					Person newPerson = new Person(	new Integer(personEntry.get("person.personid")),
													personEntry.get("name"),
													personEntry.get("email"),
													personEntry.get("role"),
													personEntry.get("department"));
					newTopic.addcontactPerson(newPerson);
				}
					
				Collection<Map<String,String>> projectFiles = database.executeQuery("SELECT URL FROM FILES WHERE ISPROJECTFILE=TRUE AND PROJECTID="+newTopic.getProjectID());
				for (Map<String,String> file: projectFiles)
				{
					newTopic.setFile(file.get("url"));		
				}
					
				Collection<Map<String,String>> department = database.executeQuery("SELECT NAME FROM DEPARTMENT, PROJECTTOPIC WHERE TOPICID="+newTopic.getProjectID()+" AND DEPARTMENT = DEPARTMENTID");
				for (Map<String,String> d: department)
				{
					newTopic.setDepartment(d.get("name"));
				}
				
				result.add(newTopic);			
			}
			
			database.disconnect();
		} catch (Exception e2) {
			System.out.printf("Error in loadTopics: %s\n", e2.toString());
			e2.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<Vote> loadVotes(String type, String semester, int year, String user) {
		ArrayList<Vote> result = new ArrayList<Vote>();
		
		DbInterface database = new DbInterface();
		try {
			database.connect(type, semester, year);
			Collection<Map<String,String>> votes = database.executeQuery("SELECT VOTE.PERSONID, PRIORITY, PROJECTID, PROJECTNAME FROM VOTE, PERSON, PROJECTTOPIC WHERE VOTE.PERSONID = PERSON.PERSONID AND VOTE.PROJECTID = PROJECTTOPIC.TOPICID AND NAME = '" + user +"'");
			
			for (Map<String,String> vote: votes)
				result.add(new Vote(new Integer(vote.get("vote.personid")), new Integer(vote.get("priority")), new Integer(vote.get("projectid")), vote.get("projectname")));
			
			database.disconnect();
		} catch (Exception e2) {
			System.out.printf("Error in loadVotes: %s\n", e2.toString());
			e2.printStackTrace();
		}		
		
		return result;
	}

	@Override
	public ArrayList<Map<String,Integer>> getStatistic(String type, String semester, int year, int priority) {
		ArrayList<Map<String,Integer>> result = new ArrayList<Map<String,Integer>>();
		
		DbInterface database = new DbInterface();
		try {
			database.connect(type, semester, year);
			Collection<Map<String,String>> votes;
			if (priority == 0) {
				votes = database.executeQuery("SELECT PROJECTNAME, COUNT(*) FROM VOTE, PROJECTTOPIC WHERE VOTE.PROJECTID = PROJECTTOPIC.TOPICID GROUP BY PROJECTID, PROJECTNAME ORDER BY COUNT(*) DESC, PROJECTSHORTCUT");
			} else {
				votes = database.executeQuery("SELECT PROJECTNAME, COUNT(*) FROM VOTE, PROJECTTOPIC WHERE VOTE.PROJECTID = PROJECTTOPIC.TOPICID AND PRIORITY = "+priority+" GROUP BY PROJECTID, PROJECTNAME ORDER BY COUNT(*) DESC, PROJECTSHORTCUT");
			}
			
			for (Map<String,String> vote: votes) {
				HashMap<String, Integer> temp = new HashMap<String,Integer>();
				temp.put(vote.get("projectname"), new Integer(vote.get("count(*)")));
				result.add(temp);
			}
			
			database.disconnect();
		} catch (Exception e2) {
			System.out.printf("Error in loadVotes: %s\n", e2.toString());
			e2.printStackTrace();
		}		
		
		return result;
	}

	@Override
	public void saveVotes(String type, String semester, int year, ArrayList<Vote> votings) {
		DbInterface database = new DbInterface();
		try {
			database.connect(type, semester, year);
			for (Vote vote: votings)
			{
				database.executeUpdate("DELETE FROM VOTE WHERE PERSONID = "+vote.getPersonID()+" AND PRIORITY = "+vote.getPriority());
				database.executeUpdate("INSERT INTO VOTE (PERSONID, PRIORITY, PROJECTID) VALUES ("+vote.getPersonID()+", "+vote.getPriority()+", "+vote.getProjectID()+")");
			}
			
			database.disconnect();
		} catch (Exception e2) {
			System.out.printf("Error in saveVotes: %s\n", e2.toString());
			e2.printStackTrace();
		}
	}

	@Override
	public void deleteVotes(String type, String semester, int year, String user) {
		DbInterface database = new DbInterface();
		
		try {
			database.connect(type, semester, year);
			Collection<Map<String,String>> persons = database.executeQuery("SELECT PERSONID FROM PERSON WHERE NAME = '"+user+"'");
			
			for (Map<String,String> person: persons) {
				database.executeUpdate("DELETE FROM VOTE WHERE VOTE.PERSONID = "+person.get("personid"));
			}
			database.disconnect();
			
			
		} catch (Exception e2) {
			System.out.printf("Error in deleteVotes: %s\n", e2.toString());
			e2.printStackTrace();
		}
	}

	@Override
	public int numberOfVotes(String type, String semester, int year) {
		// TODO Auto-generated method stub
		int maxVotes = 0;
		DbInterface database = new DbInterface();
		
		try{
			database.connect(type, semester, year);
			Collection<Map<String,String>> config = database.executeQuery("SELECT VALUE FROM CONFIGURATIONS WHERE NAME='VOTES'");
			for(Map<String, String> i : config){
				maxVotes = new Integer(i.get("value"));
			}
			database.disconnect();
		} catch(Exception e) {
			System.out.printf("Error in Configuration %s\n", e.toString());
			e.printStackTrace();
		}
		return maxVotes;
	}

	@Override
	public boolean confirmPassword(String user, String pwd) {
		return SessionManagement.getInstance().confirmPwd(user, pwd);
	}
}
