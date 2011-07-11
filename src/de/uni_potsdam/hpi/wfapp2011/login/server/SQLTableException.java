package de.uni_potsdam.hpi.wfapp2011.login.server;

/**
 * 	Exception Class for Exceptions which occur while executing an update
 * 		-CREATE TABLE: table already exists
 * 		-DROP TABLE: table not exists
 * 
 * 	SQLTableExeption(String):	copies the error message from the JdbcSQLExeption into this instance
 * 	getErrorMessage():			get the saved error message
 */

public class SQLTableException extends Exception {
	
	String errorMessage;
	
	public SQLTableException(String error){
		errorMessage = error;
	}
	
	public String getErrorMessage(){
		return errorMessage;
	}
}
