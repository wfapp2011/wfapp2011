package de.uni_potsdam.hpi.wfapp2011.database;

/**
 * 	Exception Class for Exceptions which occur while executing an update
 * 		-CREATE TABLE: table already exists
 * 		-DROP TABLE: table not exists
 * 
 * 	SQLTableExeption(String):	copies the error message from the JdbcSQLExeption into this instance
 * 	getErrorMessage():			get the saved error message
 */

public class SQLTableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String errorMessage;

	public SQLTableException(String error){
		errorMessage = error;
	}

	public String getErrorMessage(){
		return errorMessage;
	}
}