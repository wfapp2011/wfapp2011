package de.uni_potsdam.hpi.wfapp2011.Logging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.h2.jdbc.JdbcSQLException;

/**
 * Provides the functionality to interact with the H2-Database
 * 
 * 	DbInterface():			Loads the Jdbc-Driver for the H2-Database and initializes the TABLEOVERVIEW-Table
 * 	connect():				Connects this instance of the DbInterface to the H2-Database
 * 	disconnect():			Disconnects this instance of the DbInterface from the H2-Database
 * 	executeQuery(String):	Executes the given SQL-Query
 * 	executeUpdate(String):	Executes the given Update-Query
 */

public class DbInterface {
	
	private static boolean isInit = false;
	
	private Connection dbConnection;
	private Class driverClass;
	
	private String driver = "org.h2.Driver";
	private String server = "localhost";
	//private String port = "8080";
	private String dbName = "activiti";
	private String userName = "sa";
	private String pw = "";
	
	
	/**
	 * Constructor
	 * 
	 * Loads the Jdbc-Driver for H2-Databases and initializes the TABLEOVERVIEW-Table
	 */
	
	public DbInterface(){
		
		try {
			driverClass = Class.forName(driver);
		}
		catch (ClassNotFoundException e) {
			System.err.println("Class not found!");
			e.printStackTrace();
		}
		
		if(!isInit){
			this.connect();
			try{
				dbConnection.createStatement().executeUpdate("CREATE TABLE tableoverview (tableName CHAR(30), countAttributes INT, nameAttributes VARCHAR(255))");
			}
			catch(JdbcSQLException f){
				isInit = true;
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			this.disconnect();
			
			isInit = true;
		}
	}
	
	/**
	 * connect()
	 * 
	 * Connects this instance of DbInterface to the H2-Database
	 * if the Connection already exists this method does nothing
	 */
	
	public synchronized void connect(){
		try{
			if(dbConnection == null){
				dbConnection = DriverManager.getConnection("jdbc:h2:tcp://"+ server + /*":"+ port +*/ "/"+ dbName, userName, pw);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * disconnect()
	 * 
	 * Disconnects this instance of DbInterface from the H2-Database
	 * if there is no activ connection this method does nothing
	 */
	
	public synchronized void disconnect(){
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
	
	
	
	
	public synchronized final Collection<Map<String, String>> executeQuery(String q){
		Collection<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		String query = " "+ q.toLowerCase();
		if(query.endsWith(";")){
			query = query.substring(0, query.length()-1);
		}
		
		String[] parsedAttributes = query.substring(8 , query.indexOf(" from ")).split(",");
		String[] attributes = new String[parsedAttributes.length];
		
		for(int i=0; i < parsedAttributes.length; i++){
			attributes[i] = parsedAttributes[i].split(" ")[0];
		}
		
		if(attributes[0].equals("*")){
			String tablename = query.substring(query.indexOf(" from ") + 6, query.length());
			tablename = tablename.split(" where ")[0];
			
			String[] tableAttributes = this.getTableOverviewData(tablename);
			
			attributes = tableAttributes[2].split("___");
		}
		
		try{
			Statement stmt = dbConnection.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			
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
	
	public synchronized final void executeUpdate(String u) throws TableAlreadyExistsException{
		String update = u;//.toLowerCase();
		
		try{
			if(update.endsWith(";")){
				update = update.substring(0, update.length()-1);
			}
			
			Statement stmt = dbConnection.createStatement();
			
			stmt.executeUpdate(update);
		}
		catch(JdbcSQLException f){
			f.printStackTrace();
			throw new TableAlreadyExistsException();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		if(update.contains("create table")){
			String tableName = update.split(" ")[2];
			
			String[] attributesAndTypes = update.substring(update.indexOf("(") + 1, update.length()-1).split(",");
			String attr = "";
			
			for(int i=0; i< attributesAndTypes.length; i++){
				if(attributesAndTypes[i].startsWith(" ")){
					attr += attributesAndTypes[i].split(" ")[1];
					if(i!=attributesAndTypes.length-1) attr += "___";
				}
				else{
					attr += attributesAndTypes[i].split(" ")[0];
					if(i!=attributesAndTypes.length-1) attr += "___";
				}
			}
			this.executeUpdate("INSERT INTO tableoverview(tableName,countAttributes,nameAttributes) VALUES('"+ tableName +"',"+ attributesAndTypes.length +",'"+ attr +"')");
		}
	}
	
	private synchronized String[] getTableOverviewData(String tableName){
		String[] result = new String[3];
		
		try{
			Statement stmt = dbConnection.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM tableoverview;");
			resultSet.next();
			
			result[0] = resultSet.getString(1);
			result[1] = resultSet.getString(2);
			result[2] = resultSet.getString(3);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Connection getDbConnection() {
		return dbConnection;
	}
	
	public ResultSet executeQuery1(String sql){
		ResultSet resultset;
		try {
			Statement stmnt =dbConnection.createStatement();
			resultset = stmnt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultset = null;
		}
		return resultset;
		
	}
}
