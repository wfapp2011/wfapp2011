package de.uni_potsdam.hpi.wfapp2011.servercore.session;
// #Imports#
import java.util.Timer;
import java.util.TimerTask;

/**
 * A Session a user has, while using the System.
 * The Session terminates itself after 10min
 */
public class SimpleSession {
	
	private boolean debug = false;
	
	private int password = 0;
	private String username = "";
	private Integer uid = null;
	private int delay = 600000;  // in millisec --> 10 min
	
	/**
	 * Constructor and starts Timer with defined "self destroying delay"
	 * @param name : String of the username
	 * @param pwd : String of the plain pwd
	 * @param id : Integer of the userID
	 */
	public SimpleSession(String name,String pwd, int id){

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
	/**
	 * Compare the hash of given pwd with saved hashed pwd
	 * @param pwd : String of the plain retyped pwd
	 */
	public boolean testPWD(String pwd){
		
		return (password == pwd.hashCode());	
	}
	
	public String getUsername(){
		
		return username;
	}
}