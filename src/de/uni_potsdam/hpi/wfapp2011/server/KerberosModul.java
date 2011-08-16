package de.uni_potsdam.hpi.wfapp2011.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;

/**
 * Implemented as a singleton <br>
 * provides the functionality to verify userdata via a specified Kerberos <br>
 * 
 * <B>CARE:</B> till now only a faked module (verification via txt-file on LFS)
 * -> comments are very "easy"
 */
public class KerberosModul {
	
	private boolean debug = false;
	
	private static KerberosModul instance;
	
	private static ArrayList<String[]> accounts;
	
	private KerberosModul(){
		
		//load account informations from txt-file
		DbInterface.initializeMetaTables();
		
		accounts = new ArrayList<String[]>();
		
		try{
			String directory = System.getProperty("user.dir");
			char sep = File.separatorChar;
			File accountlist = new File(directory + sep + "accountList.txt");
			
			BufferedReader reader = new BufferedReader(new FileReader(accountlist));
				
			String line = reader.readLine();
				
			while(line != null){
				String[] account = new String[2];
				
				account[0] = (line.split("#")[0].split(" ")[0] +"."+ line.split("#")[0].split(" ")[1]).toLowerCase();
				account[1] = line.split("#")[1];
					
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
			
			if(acc[0].equals(name) && acc[1].equals(pwd)){
				if (debug) System.out.println("OK");
				
				return true;
			}
			
			if (debug) System.out.println("FAIL");
		}
		
		return false;
	}
}