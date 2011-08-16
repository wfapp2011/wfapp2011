package de.uni_potsdam.hpi.wfapp2011.server;

// #Imports#
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Singleton class<br>
 * A Session Management system to control and give access to existing sessions.
 * Has a private collection to hold the different Sessions 
 */
public class SessionManagement {
	
	private boolean debug = false;
	
	private static SessionManagement theInstance = null;
	private Collection<SimpleSession> activeSessions = null;

	/**
	 * Constructor of Singleton. <br>
	 * Creating a new collection of active Sessions
	 */
	private SessionManagement()
		{
		  activeSessions = new ArrayList<SimpleSession>();
		}
	
	/**
	 * private function to find a usersession in activesession list<br>
	 * returning a SimpleSession 
	 */
	private SimpleSession findSession(String username){
		Iterator<SimpleSession> iterator = activeSessions.iterator();
		SimpleSession temp = null;
		SimpleSession answer = null;
		
		while (iterator.hasNext()){
			temp = iterator.next();
			if (temp.getUsername().equals(username))
				answer = temp;
		}
		return answer;
	}

	/**
	 * Singleton utility function
	 */
	public synchronized static SessionManagement getInstance() 
			{
				if (theInstance == null)
						theInstance = new SessionManagement();
				return theInstance;			
			}

	/**
	 * logs in the user specified by the given input	
	 * @param username : String of the username
	 * @param pwd : String of the plain pwd
	 * @param id : String of the userid (given in the USERCookie)
	 * @return boolean value if the login was successful
	 */
	public boolean login(String username, String pwd, String id){
		
		/*
		 * first: trying weather user is already logged in <br>
		 * second: if not first, then test username and pwd on kbr, if true --> creating Session
		 * else: return false (failed to login)
		 */
		
		if (isLoggedIn(username))
			return true;
		
		if (KerberosModul.getInstance().authenticate(username,pwd)){
			activeSessions.add(new SimpleSession(username, pwd, Integer.valueOf(id)));
			return true;
		}
		
		return false;
	}
	
	/**
	 * checks weather a user is logged in or not
	 * @param username : String of the username, should be checked
	 * @return bool value of the test
	 */
	public boolean isLoggedIn(String username){
		if (findSession(username) != null)
			return true;
				
		return false;		
	}

	/**
	 * check the password (confirm)
	 * @param username : String of the username
	 * @param pwd : String of the plain pwd
	 * @return bool value of the test
	 */
	public boolean confirmPwd(String username, String pwd){
		/*
		 * tries to find user
		 * performs a test of the user pwd, if user were logged in  
		 */
		SimpleSession temp = findSession(username);
		if (temp == null)
			return false;
		
		return temp.testPWD(pwd);		
	}

	/**
	 * logs out a given user
	 * @param username : String of the username
	 */
	public void logout(String username){
		/*
		 * simply finds the activeSession, then deleting it from the sessionlist
		 * if user isn't logged in, nothing to do
		 */
		SimpleSession temp = findSession(username);
		if (temp != null)
			activeSessions.remove(temp);
	}
	
	/**
	 * logs out a given userID
	 * @param id : Integer of the userID
	 */
	public void logout (int id){
		
		if (debug) System.out.println("Logge aus: "+ id);
		
		// Connect to database
		DbInterface db = new DbInterface();
		db.connectToMetaTables();
		
		// lookup the username according to the id
		String sql = "SELECT username FROM onlineUsers WHERE id ="+id+";";
		
		Collection<Map<String,String>> result = db.executeQuery(sql);
		
		for (Map<String,String> m: result) {
			// perform a username logout
			logout(m.get("username"));
		}
		
		sql = "DELETE FROM onlineUsers WHERE id ="+id+";";
		
		try {
			db.executeUpdate(sql);
		} catch (SQLTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getErrorMessage());
		}
		
		db.disconnect();
	}
}