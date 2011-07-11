package de.uni_potsdam.hpi.wfapp2011.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.uni_potsdam.hpi.wfapp2011.client.ConfigInterfaceDataExchange;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ConfigInterfaceDataExchangeImpl extends RemoteServiceServlet implements ConfigInterfaceDataExchange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 133742L;

	@Override
	public ArrayList<String[]> getProjectList() {
		
		DbInterface db = new DbInterface();
		db.connectToMetaTables();
		
		String sql = "SELECT * FROM existing_projects";
		Collection<Map<String,String>> result = db.executeQuery(sql);
		
		ArrayList<String[]> projects = new ArrayList<String[]>();
		for (Map<String,String> m:result){
			String[] answer = new String[2];
			answer[0] = m.get("name").split("_")[0]+" "+m.get("name").split("_")[1]+" - "+m.get("name").split("_")[2];
			answer[1] = m.get("hasbeenstarted");
			projects.add(answer);
		}
		
		db.disconnect();		
		return projects;
	}

	@Override
	public void addProject(String year, String name) {
		
		String semester = year.split(" ")[0];
		int iYear = Integer.valueOf(year.split(" ")[1]);
		
		//System.out.println("Name: "+name+" - Semester: "+semester+" - Year: "+iYear);
		try {
			DbInterface.initializeDatabase(name, semester, iYear);
		} catch (SQLTableException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	@Override
	public Collection<Map<String,String>> getConfig(String year, String semester, String name) {
		
		int iYear = Integer.valueOf(year);
		
		DbInterface db = new DbInterface();
		try {
			db.connect(name, semester, iYear);
		} catch (SQLTableException e) {
			System.out.println(e.getErrorMessage());
		}
		
		String sql = "SELECT * FROM configurations";
		Collection<Map<String,String>> result = db.executeQuery(sql);
				
		db.disconnect();
		return result;
	}

	@Override
	public void saveConfig(String year, String semester, String name, Map<String, String> content) {
		
		int iYear = Integer.valueOf(year);
		
		DbInterface db = new DbInterface();
		try {
			db.connect(name, semester, iYear);
		} catch (SQLTableException e1) {
			System.out.println(e1.getErrorMessage());
		}
		
		for (String key:content.keySet()){
			try {
				String sql = "DELETE FROM configurations WHERE name='"+key+"';";
				db.executeUpdate(sql);
				
				sql = "INSERT INTO configurations(name,value) VALUES('"+key+"','"+content.get(key)+"');";
				db.executeUpdate(sql);
			}
			catch(SQLTableException e) {
				System.out.println(e.getErrorMessage());
			}
				
		}
		
		db.disconnect();
		
		// Inform projectengine
		// changeDeadlines(ProcessIdentifier processIdentifier)
	}

	@Override
	public void savePassword(String type, String newPwd) {
		// Incoming type = {ldap,owa,ftp}
		
		String pwd = PasswordCrypter.getInstance().encrypt(newPwd);
		
		DbInterface db = new DbInterface();
		
		db.connectToMetaTables();
		
		try {
		String sql = "DELETE FROM metaconfig WHERE name='"+type+"_pwd';";
		db.executeUpdate(sql);
		sql = "INSERT INTO metaconfig(name,value) VALUES ('"+type+"_pwd','"+pwd+"');";
		db.executeUpdate(sql);
		} catch (SQLTableException e1) {
			System.out.println(e1.getErrorMessage());
		}
		
		db.disconnect();
	
	}

	@Override
	public String getPassword(String type) {
		
		DbInterface db = new DbInterface();
		
		db.connectToMetaTables();
		
		String sql = "SELECT value FROM metaconfig WHERE name='"+type+"_pwd';";
		Collection<Map<String,String>> result = db.executeQuery(sql);
		
		db.disconnect();
		
		for (Map<String,String> m:result){
			return PasswordCrypter.getInstance().decrypt(m.get("value"));
		}
		
		return "";
	}

	@Override
	public void saveMetaData(Map<String, String> map) {
		DbInterface db = new DbInterface();
		db.connectToMetaTables();
		
		Set<String> keys = map.keySet();
		for (String key:keys){
			try {
			String sql = "DELETE FROM metaconfig WHERE name='"+key+"';";
			db.executeUpdate(sql);
			sql = "INSERT INTO metaconfig (name, value) VALUES ('"+key+"','"+map.get(key)+"');";
			db.executeUpdate(sql);
			} catch (SQLTableException e){
				System.out.println(e.getErrorMessage());
			}
		}
		
		db.disconnect();
	}

	@Override
	public Map<String, String> getMetaData() {
		
		DbInterface db = new DbInterface();
		db.connectToMetaTables();
		
		String sql="SELECT * FROM metaconfig;";
		Collection<Map<String,String>> result = db.executeQuery(sql);
		db.disconnect();
		
		Map<String,String> resultmap = new HashMap<String,String>();
		for (Map<String,String> m:result){

			String name = m.get("name");
			String value = m.get("value");
			
			if (!name.contains("pwd")) resultmap.put(name, value);
		}
		
		return resultmap;
	}

	@Override
	public void deleteProject(String year, String semester, String name) {
		
		int iYear = Integer.valueOf(year);
		DbInterface.deleteDatabase(name, semester, iYear);
		
	}

	@Override
	public void startProject(String year, String semester, String name) {

		int iYear = Integer.valueOf(year);
		// Yaninas Funktion aufrufen
		// startProcess(ProcessIdentifier processIdentifier)
		
		if(true/*Yaninas Funktion aufrufen*/){
			DbInterface db = new DbInterface();
			db.connectToMetaTables();
			
			String sql = "UPDATE existing_projects SET hasbeenstarted=true WHERE name='"+year+"_"+semester+"_"+name+"';";
			
			try {
				db.executeUpdate(sql);
			} catch (SQLTableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			db.disconnect();
		}
		
		System.out.println("New Projekt has been started: "+year+" "+semester+" - "+name);
		
	}

	@Override
	public void logout(String id) {

		SessionManagement.getInstance().logout(Integer.valueOf(id));
		
	}

}
