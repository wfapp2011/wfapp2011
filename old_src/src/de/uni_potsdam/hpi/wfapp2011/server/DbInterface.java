package de.uni_potsdam.hpi.wfapp2011.server;

//###########
//# imports	#
//###########
import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.h2.jdbc.JdbcSQLException;

/**
 * Provides the functionality to interact with the H2-Database
 * 
 * !!! For easier parsing all SQL queries are converted into lower case Strings !!!
 * 
 * 	DbInterface():					Loads the Jdbc-Driver for the H2-Database
 * 	initializeDatabase(String, String, int):Initializes the database
 *  initializeMetaTables():		Creates the meta-information database and all of its tables
 * 	connect(String, String, int):			Connects this instance of the DbInterface to the H2-Database
 * 	disconnect():					Disconnects this instance of the DbInterface from the H2-Database
 * 	executeQuery(String):			Executes the given SQL-Query
 * 	executeUpdate(String):			Executes the given Update-Query
 * 	executeQueryDirectly(String):	Executes the given query directly (any Jdbc-Stuff has to be handled by the user)
 */

public class DbInterface {
	
	private Connection dbConnection;
	private static Class driverClass;
	
	//#########################
	//# database informations #
	//#########################
	private static String driver = "org.h2.Driver";
	private static String server = "localhost";
		//private static String port = "8082";
	private static String userName = "sa";
	private static String pw = "";
	
	/**
	 * Constructor
	 * 
	 * Loads the Jdbc-Driver for H2-Databases
	 */
	
	public DbInterface(){
		
		//########################
		//#						 #
		//# load the Jdbc-Driver #
		//#						 #
		//########################
		try {
			driverClass = Class.forName(driver);
		}
		catch (ClassNotFoundException e) {
			System.err.println("Class not found!");
			e.printStackTrace();
		}
	}
	
	/**
	 * initializeDatabase(String, String, int)
	 * 
	 * Initializes the database
	 * 
	 * @param String type: Ba/Ma
	 * @param String semester: SS/WS
	 * @param int: the year which the database should created for
	 * @throws SQLTableException
	 */
	
	public static void initializeDatabase(String type, String semester, int year) throws SQLTableException{
		
		//########################################################
		//#														 #
		//# check if the given values are correct database-names #
		//#														 #
		//########################################################
		if(!checkDatabaseValues(type,semester)){
			throw new SQLTableException("Wether type or semester was no valid value!");
		}
		else{
		//#####################################
		//#									  #
		//# Connect to the specified database #
		//#									  #
		//#####################################
			Connection con = null;
			try{
				con = DriverManager.getConnection("jdbc:h2:tcp://"+ server + /*":"+ port +*/ "/databases/" + String.valueOf(year) +"_"+ semester +"_"+ type, userName, pw);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		
		//###################################################################################
		//#						    														#
		//# if type, semester and year specify an already existing database than do nothing #
		//#																					#
		//###################################################################################
			if(databaseExists(String.valueOf(year) +"_"+ semester +"_"+ type)){
				return;
			}
		
		//#######################################################
		//#							  							#
		//# Creates all needed tables if this is a new database #
		//#							  							#
		//#######################################################
		
			try {
				Connection metaConnection = DriverManager.getConnection("jdbc:h2:tcp://"+ server + /*":"+ port +*/ "/databases/metatables", userName, pw);
				
				Statement stmt = metaConnection.createStatement();
				stmt.executeUpdate("INSERT INTO existing_projects(name) VALUES('"+ String.valueOf(year) +"_"+ semester +"_"+ type +"');");
			
				metaConnection.close();
			}
			catch(SQLException f){
				f.printStackTrace();
			}
		
			TableCreater creater = new TableCreater(con);
			creater.create();
		
		
			try{
				con.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * initializeMetaTabelles()
	 * 
	 * if needed this method creates the meta-information database
	 */
	public static void initializeMetaTables(){
		Connection con = null;
		
		//################################
		//#								 #
		//# Connect to the meta database #
		//#								 #
		//################################
		try{
			con = DriverManager.getConnection("jdbc:h2:tcp://"+ server + /*":"+ port +*/ "/databases/metatables", userName, pw);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		//#######################################################
		//#							  							#
		//# Creates all needed tables if this is a new database #
		//#							  							#
		//#######################################################
		boolean newDatabase = true;
		
		try{
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE table_name= 'EXISTING_PROJECTS';");
		
			if(result.next()){
				newDatabase = false;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		if(newDatabase){
			TableCreater creater = new TableCreater(con);
			creater.createMetaTables();
		}
		
		try{
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * connectToMetaTabelles()
	 * 
	 * connects this instance to the meta-database
	 */
	public void connectToMetaTables(){
		try{
			dbConnection = DriverManager.getConnection("jdbc:h2:tcp://"+ server + /*":"+ port +*/ "/databases/metatables", userName, pw);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * connect(String, String, int)
	 * 
	 * Connects this instance of DbInterface to the H2-Database
	 * if the Connection already exists this method does nothing
	 * 
	 * @param String type: Ba/Ma
	 * @param String semester: SS/WS
	 * @param int: the year of the project series
	 * @throws SQLTableException
	 */
	
	public synchronized void connect(String type, String semester, int year) throws SQLTableException{
		
		//###########################################################################################
		//#														 								    #
		//# check if the given values are correct database-names and if the database already exists #
		//#														 								    #
		//###########################################################################################
		if(!checkDatabaseValues(type,semester)){
			throw new SQLTableException("Wether type or semester was no valid value!");
		}
		else if(!databaseExists(String.valueOf(year) +"_"+ semester +"_"+ type)){
			throw new SQLTableException("This database dont exists! Please call 'initializeDatabase first!'");
		}
		else{
		
		//########################################
		//#										 #
		//# try to connect to the given database #
		//#										 #
		//########################################
			try{
				if(dbConnection == null){
					dbConnection = DriverManager.getConnection("jdbc:h2:tcp://"+ server + /*":"+ port +*/ "/databases/" + String.valueOf(year) +"_"+ semester +"_"+ type, userName, pw);
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * disconnect()
	 * 
	 * Disconnects this instance of DbInterface from the H2-Database
	 * if there is no active connection this method does nothing
	 */
	
	public synchronized void disconnect(){
		
		//##########################################################
		//#														   #
		//# disconnect from the database and delete the connection #
		//#														   #
		//##########################################################
		try{
			if(dbConnection != null){
				dbConnection.close();
				dbConnection = null;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * executeQuery(String)
	 * 
	 * Restrictions: 	If you use '*' in your SELECT-clause, please DONT use nested queries (SELECT * FROM (SELECT ...)).
	 * 					If you want to use nested queries please use explicit attribute-names in your SELECT-clause.
	 * 
	 * @param String q: the Query which should be executed on the database 
	 * @return Collection of maps,
	 * 			each map holds one tuple of the result, where the attribute-name is the key and the attribute-value is the value of the map
	 */
	
	public synchronized final Collection<Map<String, String>> executeQuery(String q){
		Collection<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		//############################################################################
		//#																			 #
		//# convert query into lower case string and delete ';' from the queries end #
		//#																			 #
		//############################################################################
		String query = " "+ q.toLowerCase();
		if(query.endsWith(";")){
			query = query.substring(0, query.length()-1);
		}
		
		//##############################################
		//#											   #
		//# parse the attribute names out of the query #
		//#											   #
		//##############################################
		String[] parsedAttributes = query.substring(8 , query.indexOf(" from ")).split(",");
		String[] attributes = new String[parsedAttributes.length];
		
		for(int i=0; i < parsedAttributes.length; i++){
			while(parsedAttributes[i].startsWith(" ")){
				parsedAttributes[i] = parsedAttributes[i].substring(1, parsedAttributes[i].length());
			}
			attributes[i] = parsedAttributes[i].split(" ")[0];
		}
		
		//########################################################################################
		//#																						 #
		//# if '*' is used instead of explicit names, get attribute names from meta-informations #
		//#																						 #
		//########################################################################################
		if(attributes[0].equals("*")){
			String tablename = query.substring(query.indexOf(" from ") + 6, query.length());
			tablename = tablename.split(" where ")[0];
			
			String tableAttributes = this.getTableOverviewData(tablename);
			
			attributes = tableAttributes.split("___");
		}
		
		//###########################################################################
		//#																			#
		//# execute the given query and convert the result to an collection of maps #
		//#																			#
		//###########################################################################
		try{
			Statement stmt = dbConnection.createStatement();
			ResultSet resultSet = stmt.executeQuery(q);
			
			while (resultSet.next()){
				HashMap<String, String> tuple = new HashMap<String, String>();
				
				for(int j=0; j<attributes.length; j++){
					tuple.put(attributes[j], resultSet.getString(attributes[j]));
				}
				
				result.add(tuple);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * executeUpdate(String)
	 * 
	 * @param String u:	the update which should be performed on the database
	 * @throws SQLTableException: if the update fails, this exception will be thrown
	 */
	
	public synchronized final void executeUpdate(String u) throws SQLTableException{
		
		//######################
		//#					   #
		//# execute the update #
		//#					   #
		//######################
		try{
			Statement stmt = dbConnection.createStatement();
			
			stmt.executeUpdate(u);
		}
		catch(JdbcSQLException f){
			SQLTableException ex = new SQLTableException(f.getOriginalMessage());
			
			throw ex;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * executeQueryDirectly(String)
	 * @param String query:	the query which should be executed on the database
	 * @return ResultSet
	 * @throws SQLException
	 */
	public synchronized ResultSet executeQueryDirectly(String query) throws SQLException{
		
		//##############################################
		//#											   #
		//# Create the Statement and execute the query #
		//#											   #
		//##############################################
		Statement stmt = dbConnection.createStatement();
		
		return stmt.executeQuery(query);
	}
	
	/**
	 * deleteDatabase(String, String, int)
	 * Deletes the specified database from the metainformations and renames the database into oldname_DELETED_AT_timestamp
	 * 
	 * @param String type: Ba/Ma
	 * @param String semester: SS/WS
	 * @param int: the year of the project series
	 */
	public static void deleteDatabase(String type, String semester, int year){
		try{
			Connection metaConnection = DriverManager.getConnection("jdbc:h2:tcp://"+ server + /*":"+ port +*/ "/databases/metatables", userName, pw);
			
			//###########################################################
			//#															#
			//# delete the specified database from the metainformations #
			//#															#
			//###########################################################
			Statement stmt = metaConnection.createStatement();
			stmt.executeUpdate("DELETE FROM existing_projects WHERE name='"+ String.valueOf(year) +"_"+ semester +"_"+ type +"';");
			
			metaConnection.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		//###########################
		//#							#
		//# rename the old database #
		//#							#
		//###########################
		String date = (new Date()).toString().replace(" ", "_").replace(":", "-");
		
		try{
			Connection oldDatabase = DriverManager.getConnection("jdbc:h2:tcp://"+ server + /*":"+ port +*/ "/databases/" + String.valueOf(year) +"_"+ semester +"_"+ type, userName, pw);
			Connection newDatabase = DriverManager.getConnection("jdbc:h2:tcp://"+ server + /*":"+ port +*/ "/databases/" + "DELETET_"+ String.valueOf(year) +"_"+ semester +"_"+ type +"_AT_"+ date, userName, pw);
			
			TableCreater creater = new TableCreater(oldDatabase);
			creater.copyDB(newDatabase);
			
			oldDatabase.close();
			newDatabase.close();
			
			char sep = File.separatorChar;
			String directory = System.getProperty("user.dir"); 
			
			
			File oldDb = new File(directory + sep + "databases" + sep + String.valueOf(year) +"_"+ semester +"_"+ type +".h2.db");
			
			if(oldDb.exists()) oldDb.delete();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private synchronized String getTableOverviewData(String tableName){
		
		//########################################
		//#										 #
		//# get all data for the given tablename #
		//#										 #
		//########################################
		String result = "";
		
		try{
			Statement stmt = dbConnection.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT column_name FROM information_schema.columns WHERE table_name= '"+ tableName.toUpperCase() +"'");
			
			resultSet.next();
			result += resultSet.getString(1).toLowerCase();
			
			while(resultSet.next()){
				result += "___"+ resultSet.getString(1).toLowerCase();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static synchronized boolean checkDatabaseValues(String type, String semester){
		boolean typeOK = false;
		boolean semesterOK = false;
		
		if(type.equals("Ba") || type.equals("Ma")){
			typeOK = true;
		}
		if(semester.equals("SS") || semester.equals("WS")){
			semesterOK = true;
		}
		
		return (typeOK && semesterOK);
	}
	
	private static synchronized boolean databaseExists(String name){
		boolean exists = false;
		
		try {
			Connection metaConnection = DriverManager.getConnection("jdbc:h2:tcp://"+ server + /*":"+ port +*/ "/databases/metatables", userName, pw);
			
			Statement stmt = metaConnection.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM existing_projects WHERE name='"+ name +"';");
			
			if(result.next()){
				exists = true;
			}
			
			metaConnection.close();
		}
		catch(SQLException f){
			f.printStackTrace();
		}
		
		return exists;
	}
}
