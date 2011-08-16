package de.uni_potsdam.hpi.wfapp2011.login.server;

import de.uni_potsdam.hpi.wfapp2011.login.client.loginExchange;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of the login functionality
 */
public class loginExchangeImpl extends RemoteServiceServlet implements loginExchange {

	private static final long serialVersionUID = -6192300362761316705L;

	private boolean debug = false;
	
	/**
	 * login(String,String,String)
	 * performs the login
	 * 
	 * @param username : String which specifice the username of the user
	 * @param password : String which represents the password of the user
	 * @param id : String which specifice the user-id of the user
	 */
	@Override
	public void login(String username, String password, String id) {
		//register user in the database
		if (debug) System.out.println("Entering login");
		DbInterface db = new DbInterface();	
		db.connectToMetaTables();
		
		if (debug) System.out.println("Trage user in Db ein: "+ id);
		String sql = "INSERT INTO onlineusers (id, username, password) VALUES ("+Integer.valueOf(id)+",'"+username+"','"+PasswordCrypter.getInstance().encrypt(password)+"');";
		try {
			db.executeUpdate(sql);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		db.disconnect();
		
		if (debug) System.out.println("Verlasse login");
	}
}
