package de.uni_potsdam.hpi.wfapp2011.server;

import java.util.Timer;
import java.util.TimerTask;

public class SimpleSession {
	
	/**
	 * A Session a user has, while using the System.
	 * The Session terminates itself after 10min
	 */
	
	private boolean debug = false;
	
	private int password = 0;
	private String username = "";
	private Integer uid = null;
	private int delay = 300000;//600000;  // in millisec --> 10 min
	
	public SimpleSession(String name,String pwd, int id){
		/**
		 * Constructor and starts Timer with defined "self destroying delay"
		 */
		password = pwd.hashCode();
		username = name;
		uid = id;
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
	          public void run() {
	        	  if (debug) System.out.println("### TIMEOUT ###");
	              SessionManagement.getInstance().logout(uid);
	              if (debug) System.out.println(username+" loged out!");
	          }
	      }, delay);
	}

	public boolean testPWD(String pwd){
		/**
		 * Compare the hash of given pwd with saved hashed pwd
		 */
		return (password == pwd.hashCode());	
	}
	
	public String getUsername(){
		/**
		 * getter of the username
		 */
		return username;
	}
}