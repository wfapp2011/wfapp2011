package de.uni_potsdam.hpi.wfapp2011.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class KerberosModul {
	
	private static ArrayList<String[]> accounts;
	
	public static void createAccounts(){
		
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
				
				account[0] = line.split("#")[0];
				account[1] = line.split("#")[1];
					
				System.out.println(account[0] +" | "+ account[1]);
					
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
	
	public static boolean authenticate(String name, String pwd){
		boolean loginCorrect = false;
		
		for(String[] acc : accounts){
			if(acc[0].equals(name) && acc[1].equals(pwd)){
				loginCorrect = true;
			}
		}
		
		return loginCorrect;
	}
}
