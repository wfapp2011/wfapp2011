package de.uni_potsdam.hpi.wfapp2011.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class KerberosModul {
	
	private static KerberosModul instance;
	
	private static ArrayList<String[]> accounts;
	
	private KerberosModul(){
		
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
	
	public boolean authenticate(String name, String pwd){
		System.out.println("#####\nTeste Kerberos: "+ name +" "+ pwd +"\n#####");
		
		for(String[] acc : accounts){
			System.out.println(acc[0] +" | "+ acc[1]);
			
			if(acc[0].equals(name) && acc[1].equals(pwd)){
				System.out.println("OK");
				
				return true;
			}
			
			System.out.println("FAIL");
		}
		
		return false;
	}
}