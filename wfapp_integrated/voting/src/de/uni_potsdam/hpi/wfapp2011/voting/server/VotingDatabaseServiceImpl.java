package de.uni_potsdam.hpi.wfapp2011.voting.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.session.SessionManagement;
import de.uni_potsdam.hpi.wfapp2011.voting.client.Person;
import de.uni_potsdam.hpi.wfapp2011.voting.client.Topic;
import de.uni_potsdam.hpi.wfapp2011.voting.client.Vote;
import de.uni_potsdam.hpi.wfapp2011.voting.client.VotingDatabaseService;

/**
 * implementation of the remote service servlet for the topic voting website. it handles the database request
 * 
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 14.53
 * @see de.uni_potsdam.hpi.wfapp2011.client.VotingDatabaseService
 */
public class VotingDatabaseServiceImpl extends RemoteServiceServlet implements VotingDatabaseService {
	private static final long serialVersionUID = 1L;

	@Override
	/**
	 * loads the project topics for a semester from the database
	 * 
	 * @param type bachelor or master project ("Ba"/"Ma")
	 * @param semester summer or winter semester ("SS"/"WS")
	 * @param year year of the semester
	 * @return ArrayList<Topic> 
	 */
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
	/**
	 * load the votes for a user
	 * 
	 * @param type bachelor or master project ("Ba"/"Ma")
	 * @param semester summer or winter semester ("SS"/"WS")
	 * @param year year of the semester
	 * @param sesionID session of the user which votes are loaded
	 * @return ArrayList<Vote> 
	 */
	public ArrayList<Vote> loadVotes(String type, String semester, int year, String sessionID) {
		ArrayList<Vote> result = new ArrayList<Vote>();
		DbInterface database = new DbInterface();
		String userName = "";
		
		try {
			database.connectToMetaTables();
			Collection<Map<String,String>> onlineUsers = database.executeQuery("SELECT USERNAME FROM ONLINEUSERS WHERE ID = '"+sessionID+"'");
			for (Map<String,String> i: onlineUsers) {
				userName = i.get("username");
			}
			if (userName.equals("")) {
				throw new Exception("Session expired!");
			}
			database.disconnect();
		} catch (Exception e) {
			System.out.printf("Error in saveVotes: %s\n", e.toString());
			e.printStackTrace();
		}
		

		try {
			database.connect(type, semester, year);
			Collection<Map<String,String>> votes = database.executeQuery("SELECT VOTE.PERSONID, PRIORITY, PROJECTID, PROJECTNAME FROM VOTE, PERSON, PROJECTTOPIC WHERE VOTE.PERSONID = PERSON.PERSONID AND VOTE.PROJECTID = PROJECTTOPIC.TOPICID AND NAME = '" + userName +"'");
			
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
	/**
	 * generates a vote statistic 
	 * 
	 * @param type bachelor or master project ("Ba"/"Ma")
	 * @param semester summer or winter semester ("SS"/"WS")
	 * @param year year of the semester
	 * @param priority priority of the votes 
	 * @return ArrayList<Map<String, Integer>>
	 */
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
	/**
	 * save the votes with there priority
	 * 
	 * @param type bachelor or master project ("Ba"/"Ma")
	 * @param semester summer or winter semester ("SS"/"WS")
	 * @param year year of the semester
	 * @param sessionID session of the user which votes are saved
	 * @param voting list of votes to save
	 */
	public void saveVotes(String type, String semester, int year, String sessionID, ArrayList<Vote> votings) {
		DbInterface database = new DbInterface();
		String userName = "";
		
		try {
			database.connectToMetaTables();
			Collection<Map<String,String>> onlineUsers = database.executeQuery("SELECT USERNAME FROM ONLINEUSERS WHERE ID = '"+sessionID+"'");
			for (Map<String,String> i: onlineUsers) {
				userName = i.get("username");
			}
			if (userName.equals("")) {
				throw new Exception("Session expired!");
			}
			database.disconnect();
		} catch (Exception e) {
			System.out.printf("Error in saveVotes: %s\n", e.toString());
			e.printStackTrace();
		}
		
		String personID = "";
		try {
			database.connect(type, semester, year);
			Collection<Map<String,String>> persons = database.executeQuery("SELECT PERSONID FROM PERSON WHERE NAME = '"+userName+"'");
			
			for (Map<String,String> i: persons) {
				personID = i.get("personid");
			}
			
			if (personID.equals("")) {
				throw new Exception("User not found!");
			}
			
			for (Vote vote: votings)
			{
				database.executeUpdate("DELETE FROM VOTE WHERE PERSONID = "+personID+" AND PRIORITY = "+vote.getPriority());
				database.executeUpdate("INSERT INTO VOTE (PERSONID, PRIORITY, PROJECTID) VALUES ("+personID+", "+vote.getPriority()+", "+vote.getProjectID()+")");
			}
			
			database.disconnect();
		} catch (Exception e2) {
			System.out.printf("Error in saveVotes: %s\n", e2.toString());
			System.out.printf("PersonID: %s\n", personID);
			e2.printStackTrace();
		}
	}

	@Override
	/**
	 * delete the votes of a user
	 * 
	 * @param type bachelor or master project ("Ba"/"Ma")
	 * @param semester summer or winter semester ("SS"/"WS")
	 * @param sessionID session of the user which votes are deleted
	 * @param year year of the semester
	 */
	public void deleteVotes(String type, String semester, int year, String sessionID) {
		DbInterface database = new DbInterface();
		
		String userName = "";
		
		try {
			database.connectToMetaTables();
			Collection<Map<String,String>> onlineUsers = database.executeQuery("SELECT USERNAME FROM ONLINEUSERS WHERE ID = '"+sessionID+"'");
			for (Map<String,String> i: onlineUsers) {
				userName = i.get("username");
			}
			if (userName.equals("")) {
				throw new Exception("Session expired!");
			}
			database.disconnect();
		} catch (Exception e) {
			System.out.printf("Error in saveVotes: %s\n", e.toString());
			e.printStackTrace();
		}
		
		try {
			database.connect(type, semester, year);
			Collection<Map<String,String>> persons = database.executeQuery("SELECT PERSONID FROM PERSON WHERE NAME = '"+userName+"'");
			
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
	/**
	 * returns the required number of votes
	 * 
	 * @param type bachelor or master project ("Ba"/"Ma")
	 * @param semester summer or winter semester ("SS"/"WS")
	 * @param year year of the semester
	 * @return int
	 */
	public int numberOfVotes(String type, String semester, int year) {
		int maxVotes = 0;
		DbInterface database = new DbInterface();
		
		try{
			database.connect(type, semester, year);
			Collection<Map<String,String>> config = database.executeQuery("SELECT VALUE FROM CONFIGURATIONS WHERE NAME='votes'");
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
	/**
	 * confirms the password of the user.
	 * used for verifying the user when he wants to save his votes 
	 * 
	 * @param sessionID session of the user which password is confirmed
	 * @param pwd password of the user
	 * @return boolean
	 */
	public boolean confirmPassword(String sessionID, String pwd) {
		DbInterface database = new DbInterface();
		String userName = "";

		try {
			database.connectToMetaTables();
			Collection<Map<String,String>> onlineUsers = database.executeQuery("SELECT USERNAME FROM ONLINEUSERS WHERE ID = '"+sessionID+"'");
			for (Map<String,String> i: onlineUsers) {
				userName = i.get("username");
			}
			if (userName.equals("")) {
				throw new Exception("Session expired!");
			}
			database.disconnect();
		} catch (Exception e) {
			System.out.printf("Error in saveVotes: %s\n", e.toString());
			e.printStackTrace();
		}
		
		return SessionManagement.getInstance().confirmPwd(userName, pwd);
	}

	@Override
	/**
	 * logs out the user
	 * 
	 * @param cookie sessionid of the user
	 */
	public void logout(String cookie) {
		// logout the user
		SessionManagement.getInstance().logout(Integer.valueOf(cookie));
	}
}
