package de.uni_potsdam.hpi.wfapp2011.overview.server;

import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni_potsdam.hpi.wfapp2011.database.DbInterface;

public class Login {
	public boolean created = false; 
	public static boolean debug = false;
	public int login(){
		if(!created){
			created = true;
			DbInterface.initializeMetaTables();
		}
		
		DbInterface db_idtest = new DbInterface();
		db_idtest.connectToMetaTables();
		Integer id = null;
		for (int i = 0; i<=100; i++){
			int temp = (int) (Math.random() * Integer.MAX_VALUE);
			if(debug) System.out.println("Neuer ID-Versuch: "+ temp);
			String sql_idtest = "SELECT id FROM onlineusers WHERE id = "+temp+";";
			
			Collection<Map<String,String>> result_idtest = db_idtest.executeQuery(sql_idtest);
			
			if (result_idtest.size() == 0){
				id = temp;
				if (debug) System.out.println("freie Id gefunden: "+id);
				break;
			}
		}
		db_idtest.disconnect();
		
		
		
		return id;
	}
}