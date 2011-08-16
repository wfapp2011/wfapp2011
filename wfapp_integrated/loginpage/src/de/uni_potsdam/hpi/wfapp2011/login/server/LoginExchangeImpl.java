package de.uni_potsdam.hpi.wfapp2011.login.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_potsdam.hpi.wfapp2011.login.client.LoginExchange;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.general.PasswordCrypter;

public class LoginExchangeImpl extends RemoteServiceServlet implements LoginExchange {

	private static final long serialVersionUID = -6192300362761316705L;

	private boolean debug = false;
	
	@Override
	public void login(String username, String password, String id) {
		if (debug) System.out.println("Entering login");
		DbInterface db = new DbInterface();	
		db.connectToMetaTables();
		
		if (debug) System.out.println("Trage user in Db ein: "+ id);
		String sql = "INSERT INTO onlineusers (id, username, password) VALUES ("+Integer.valueOf(id)+",'"+username+"','"+PasswordCrypter.getInstance().encrypt(password)+"');";
		try {
			
			db.executeUpdate(sql);
			if(debug)System.out.println("Execute SQL Statement");
		} catch (SQLTableException e) {
			if(debug) System.out.println("SQL Tabel Exception");
			e.printStackTrace();
		}
		db.disconnect();
		
		if (debug) System.out.println("Verlasse login");
	}
}
