package de.uni_potsdam.hpi.wfapp2011.login.server;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import de.uni_potsdam.hpi.wfapp2011.login.client.loginExchange;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class loginExchangeImpl extends RemoteServiceServlet implements loginExchange {

	private static final long serialVersionUID = -6192300362761316705L;

	private boolean debug = true;
	
	@Override
	public void login(String username, String password, String id) {
		if (debug) System.out.println("Entering login");
		DbInterface db = new DbInterface();	
		db.connectToMetaTables();
		
		if (debug) System.out.println("Trage user in Db ein: "+ id);
		String sql = "INSERT INTO onlineusers (id, username, password) VALUES ("+Integer.valueOf(id)+",'"+username+"','"+PasswordCrypter.getInstance().encrypt(password)+"');";
		try {
			db.executeUpdate(sql);
		} catch (SQLTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.disconnect();
		
		if (debug) System.out.println("Verlasse login");
	}
}
