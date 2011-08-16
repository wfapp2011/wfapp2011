package de.uni_potsdam.hpi.wfapp2011.servercore.general;


import java.util.ArrayList;

import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;

/**
 * Implemented as a singleton <br>
 * provides the functionality to verify userdata via a specified Kerberos <br>
 * 
 * <B>CARE:</B> till now only a faked module
 * -> comments are very "easy"
 */
public class KerberosModul {
	
	private boolean debug = false;
	
	private static KerberosModul instance;
	
	private ArrayList<String[]> accounts;
	
	private KerberosModul(){
	
		DbInterface.initializeMetaTables();
		
		accounts = new DummyUsers().getAccounts();	
	
	}
	
	public static KerberosModul getInstance(){
		if(instance == null){
			instance = new KerberosModul();
		}
		
		return instance;
	}
	
	/**
	 * checks if input account is a valid account 
	 */
	public boolean authenticate(String name, String pwd){
		if (debug) System.out.println("#####\nTeste Kerberos: "+ name +" "+ pwd +"\n#####");
		
		for(String[] acc : accounts){
			if (debug) System.out.println(acc[0] +" | "+ acc[1]);
			
			if(acc[0].replace(" ", ".").toLowerCase().equals(name) && acc[1].equals(pwd)){
				if (debug) System.out.println("OK");
				
				return true;
			}
			
			if (debug) System.out.println("FAIL");
		}
		
		return false;
	}
}