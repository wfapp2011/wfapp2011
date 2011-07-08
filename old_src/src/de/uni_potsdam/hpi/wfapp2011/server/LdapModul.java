package de.uni_potsdam.hpi.wfapp2011.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LdapModul {
	
	private static LdapModul instance;
	
	private static ArrayList<String[]> accounts;
	
	private LdapModul(){
		DbInterface.initializeMetaTables();
		
		accounts = new ArrayList<String[]>();
		
		try{
			String directory = System.getProperty("user.dir");
			char sep = File.separatorChar;
			File accountlist = new File(directory + sep + "accountList.txt");
			
			BufferedReader reader = new BufferedReader(new FileReader(accountlist));
				
			String line = reader.readLine();
				
			while(line != null){
				String[] account = new String[3];
				
				account[0] = line.split("#")[0];
				account[1] = line.split("#")[1];
				account[2] = line.split("#")[2];
					
				accounts.add(account);
			
				line = reader.readLine();
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException g){
			g.printStackTrace();
		}
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
		//#																		   #
		//##########################################################################
		String role = null;
		
		for(String[] user : accounts){
			String username = user[0].split(" ")[0].toLowerCase() +"."+ user[0].split(" ")[1].toLowerCase();
			
			if(name.equals(username)){
				role = user[2];
				
				break;
			}
		}
		
		return role;
	}
}
