package de.uni_potsdam.hpi.wfapp2011.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class SessionManagement {
	
	/**
	 * Singleton class!
	 * A Session Management system to control and give access to existing sessions.
	 * Has a private collection to hold the different Sessions 
	 */
	
	private static SessionManagement theInstance = null;
	private Collection<SimpleSession> activeSessions = null;

	private SessionManagement()
		{
		/**
		 * Constructor of Singleton
		 * Creating a new collection of active Sessions
		 */
		  activeSessions = new ArrayList<SimpleSession>();
		}
	
	private SimpleSession findSession(String username){
		/**
		 * private function to find a usersession in activesession list
		 * returning a SimpleSession 
		 */
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

	public synchronized static SessionManagement getInstance() 
			{
			/**
			 * getter of SessionManagement instance 
			 */
				if (theInstance == null)
						theInstance = new SessionManagement();
				return theInstance;			
			}

	public boolean login(String username, String pwd, String id){
		/**
		 * first: trying weather user is already logged in
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
		
	public boolean isLoggedIn(String username){
		/**
		 * performs lookup in activeSessionlist and testing if user is logged in
		 */
		if (findSession(username) != null)
			return true;
				
		return false;		
	}

	public boolean confirmPwd(String username, String pwd){
		/**
		 * tries to find user
		 * performs a test of the user pwd, if user were logged in  
		 */
		SimpleSession temp = findSession(username);
		if (temp == null)
			return false;
		
		return temp.testPWD(pwd);		
	}

	public void logout(String username){
		/**
		 * simply finds the activeSession, then deleting it from the sessionlist
		 * if user isn't logged in, nothing to do
		 */
		SimpleSession temp = findSession(username);
		if (temp != null)
			activeSessions.remove(temp);
	}
	
	public void logout (int id){
		
		DbInterface db = new DbInterface();
		db.connectToMetaTables();
		
		String sql = "SELECT username FROM onlineUsers WHERE id ="+id+";";
		
		Collection<Map<String,String>> result = db.executeQuery(sql);
		
		for (Map<String,String> m: result) {
			logout(m.get("username"));
		}
		
		sql = "DELETE FROM onlineUser WHERE id ="+id+";";
		
		try {
			db.executeUpdate(sql);
		} catch (SQLTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.disconnect();
	}
}