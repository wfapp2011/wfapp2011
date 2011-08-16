package de.uni_potsdam.hpi.wfapp2011.servercore.general;

import java.util.ArrayList;

import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;

public class LdapModul {
	
	private static LdapModul instance;
	
	private ArrayList<String[]> accounts;
	
	private LdapModul(){
		DbInterface.initializeMetaTables();
		
		accounts = new DummyUsers().getAccounts();

	}
	
	public static LdapModul getInstance(){
		if(instance == null){
			instance = new LdapModul();
		}
		
		return instance;
	}
	
	public String getUserdata(String name){
		//##########################################################################
		//#																		   #
		//# returns the role of the given username or null if no username is found #
		//# possible roles: Student - Prof - Staff_[Fachgebiet] - Studienreferat   #
		//# Fachgebiet -> BPT,INTERNET,EPIC,HCI,CGS,OS,SWA,IS,MOD				   #
		//# multible roles are seperated with ,									   #
		//#																		   #
		//##########################################################################
		String role = null;
		
		for(String[] user : accounts){
			String username = user[0].split(" ")[0].toLowerCase() +"."+ user[0].split(" ")[1].toLowerCase();
			
			if(name.equals(username)){
				role = user[2].replace(" ", "");
				
				break;
			}
		}
		
		return role;
	}
}
