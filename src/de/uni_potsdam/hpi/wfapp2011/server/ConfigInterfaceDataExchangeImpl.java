package de.uni_potsdam.hpi.wfapp2011.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.uni_potsdam.hpi.wfapp2011.client.ConfigInterfaceDataExchange;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * implements the async callbacks for client-sided calls
 */

public class ConfigInterfaceDataExchangeImpl extends RemoteServiceServlet implements ConfigInterfaceDataExchange {

	private boolean debug = false;

	private static final long serialVersionUID = 133742L;
	
	/**
	 * Collects all existing projects from database
	 * 
	 * @return all existing projects in ArrayList
	 */
	@Override
	public ArrayList<String[]> getProjectList() {
		
		// connect to DB
		DbInterface db = new DbInterface();
		db.connectToMetaTables();
		
		//sending query
		String sql = "SELECT * FROM existing_projects";
		Collection<Map<String,String>> result = db.executeQuery(sql);
		
		//evaluate resultset and collect useful data
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
	
	/**
	 * creates new projectdatabase
	 * 
	 * @param year : String of the project year and semester (e.g. WS 2011)
	 * @param name : String which specifies type of the project (BA / MA)
	 */
	@Override
	public void addProject(String year, String name) {
		
		//parse needed attributes
		String semester = year.split(" ")[0];
		int iYear = Integer.valueOf(year.split(" ")[1]);
		
		//try to create new database for the given project
		try {
			DbInterface.initializeDatabase(name, semester, iYear);
		} catch (SQLTableException e) {
			if (debug) System.out.println(e.getLocalizedMessage());
		}
	}
	
	/**
	 * get configuration for the specified project
	 * 
	 * @param year : String of the year (e.g. 2011)
	 * @param semester : String of the semester (SS / WS)
	 * @param name : String of the name (BA / MA)
	 * 
	 * @return configuration of the specified project in a Collection
	 */
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

	/**
	 * saves all given configuration in the database
	 * 
	 * @param year : String of the year (e.g. 2011)
	 * @param semester : String of the semester (SS / WS)
	 * @param name : String of the name (BA / MA)
	 * @param content : configuration data
	 */
	@Override
	public void saveConfig(String year, String semester, String name, Map<String, String> content) {
		
		int iYear = Integer.valueOf(year);
		
		//connect to the database specified by year, name, semester
		DbInterface db = new DbInterface();
		try {
			db.connect(name, semester, iYear);
		} catch (SQLTableException e1) {
			if (debug) System.out.println(e1.getErrorMessage());
		}
		
		//delete old database values and insert new ones
		for (String key:content.keySet()){
			try {
				String sql = "DELETE FROM configurations WHERE name='"+key+"';";
				db.executeUpdate(sql);
				
				sql = "INSERT INTO configurations(name,value) VALUES('"+key+"','"+content.get(key)+"');";
				db.executeUpdate(sql);
			}
			catch(SQLTableException e) {
				if (debug) System.out.println(e.getErrorMessage());
			}
		
		//ProcessAdministration.getInstance().changedDeadlines(new ProcessIdentifier(name,semester,iYear));
				
		}
		
		db.disconnect();
		
		// Inform projectengine
		// changeDeadlines(ProcessIdentifier processIdentifier)
	}

	/**
	 * saves the new password for the given module
	 * 
	 * @param type : type of the module where the password should be changed for (e.g. owa, ldap, ftp)
	 * @param newPwd : new password
	 */
	@Override
	public void savePassword(String type, String newPwd) {
		// Incoming type = {ldap,owa,ftp}
		
		//encrypt new password
		String pwd = PasswordCrypter.getInstance().encrypt(newPwd);
		
		DbInterface db = new DbInterface();
		
		db.connectToMetaTables();
		
		//delete old value for the given module and save new one
		try {
		String sql = "DELETE FROM metaconfig WHERE name='"+type+"_pwd';";
		db.executeUpdate(sql);
		sql = "INSERT INTO metaconfig(name,value) VALUES ('"+type+"_pwd','"+pwd+"');";
		db.executeUpdate(sql);
		} catch (SQLTableException e1) {
			if (debug) System.out.println(e1.getErrorMessage());
		}
		
		db.disconnect();
	
	}

	/**
	 * get the decrypted password for the given type of module
	 * 
	 * @param type : type of the module where the password should be returned for
	 * 
	 * @return decrypted password
	 */
	@Override
	public String getPassword(String type) {
		
		//connect to database (meta)
		DbInterface db = new DbInterface();
		
		db.connectToMetaTables();
		
		//get data from db
		String sql = "SELECT value FROM metaconfig WHERE name='"+type+"_pwd';";
		Collection<Map<String,String>> result = db.executeQuery(sql);
		
		db.disconnect();
		
		//decrypt and return pwd
		for (Map<String,String> m:result){
			return PasswordCrypter.getInstance().decrypt(m.get("value"));
		}
		
		//default return
		return "";
	}

	/**
	 * save module specific data into the database (url, name)
	 * 
	 * @param map : Map of all module configurations
	 */
	@Override
	public void saveMetaData(Map<String, String> map) {
		
		//connect to db
		DbInterface db = new DbInterface();
		db.connectToMetaTables();
		
		//for each given value in the map: delete the corresponding value in the db and save new one
		Set<String> keys = map.keySet();
		for (String key:keys){
			try {
			String sql = "DELETE FROM metaconfig WHERE name='"+key+"';";
			db.executeUpdate(sql);
			sql = "INSERT INTO metaconfig (name, value) VALUES ('"+key+"','"+map.get(key)+"');";
			db.executeUpdate(sql);
			} catch (SQLTableException e){
				if (debug) System.out.println(e.getErrorMessage());
			}
		}
		
		db.disconnect();
	}

	/**
	 * get configurations for all possible modules
	 * 
	 * @return all config-datas without the pwd in a Map
	 */
	@Override
	public Map<String, String> getMetaData() {
		
		//connect to db and get needed data
		DbInterface db = new DbInterface();
		db.connectToMetaTables();
		
		String sql="SELECT * FROM metaconfig;";
		Collection<Map<String,String>> result = db.executeQuery(sql);
		db.disconnect();
		
		//filter out pwd's
		Map<String,String> resultmap = new HashMap<String,String>();
		for (Map<String,String> m:result){

			String name = m.get("name");
			String value = m.get("value");
			
			if (!name.contains("pwd")) resultmap.put(name, value);
		}
		
		return resultmap;
	}

	/**
	 * delete specified project
	 * 
	 * @param year : String of the year (e.g. 2011)
	 * @param semester : String of the semester (SS / WS)
	 * @param name : String of the name (BA / MA)
	 */
	@Override
	public void deleteProject(String year, String semester, String name) {
		
		int iYear = Integer.valueOf(year);
		DbInterface.deleteDatabase(name, semester, iYear);
		
	}

	/**
	 * starts the given project
	 * 
	 * @param year : String of the year (e.g. 2011)
	 * @param semester : String of the semester (SS / WS)
	 * @param name : String of the name (BA / MA)
	 */
	@Override
	public void startProject(String year, String semester, String name) {

		int iYear = Integer.valueOf(year);
		// Yaninas Funktion aufrufen
		//ProcessIdentifier pId = new ProcessIdentifier(name, semester, iYear);
		boolean started = false;
		
		/*try{
			started = ProcessAdministration.getInstance().startProcess(pId);
		}
		catch(ActivitiProcessException e){
			e.printStackTrace();
		}*/
		
		if(true){//started){
			//change flag of started project to true
			DbInterface db = new DbInterface();
			db.connectToMetaTables();
			
			String sql = "UPDATE existing_projects SET hasbeenstarted=true WHERE name='"+year+"_"+semester+"_"+name+"';";
			
			try {
				db.executeUpdate(sql);
			} catch (SQLTableException e) {
				e.printStackTrace();
			}
			
			db.disconnect();
		}
		
		if (debug) System.out.println("New Projekt has been started: "+year+" "+semester+" - "+name);
		
	}

	/**
	 * This function provides core features for the entire system. The algorithms used are nearly complete hardwaredepended.
	 * Consult your PC-Seller if this function is not working properly. <br>
	 * In hence of the high complexity of this algorithm we where unable to set up software support till now.
	 * Please DO NOT contact us for any questions and support.<br><br>
	 * Any changes you are doing on your own risk.
	 * 
	 * @param id : String of an ID, we are unsure what this parameter is doing. If you find out, maybe this could be the only reason contacting us.<br>
	 * The ID 42 provides you the pow3333r to control the universe. A user with this ID is the MU (Master of Universe).<br>
	 * The ID 1337 let the user be a full skilled nerd, who is able to use leed-haxes on the internet.
	 * The ID 4711 makes you instant to a very very old user. :-P 
	 */
	@Override
	public void logout(String id) {

		// please do not try to understand this very complex algorithm
		// we don't understand it either
		SessionManagement.getInstance().logout(Integer.valueOf(id));
		
		// We needed an EASTEREGG don't take this to serious
		// greetz developer
	}

}
