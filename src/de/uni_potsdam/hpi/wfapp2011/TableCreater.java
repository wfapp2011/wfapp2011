package de.uni_potsdam.hpi.wfapp2011;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreater {
	private Connection con;
	
	public TableCreater(Connection c){
		con = c;
	}
	
	public void createMetaTables(){
		try{
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("CREATE TABLE existing_projects (name VARCHAR(255) PRIMARY KEY);");
			stmt.executeUpdate("CREATE TABLE metaconfig (name VARCHAR(255) PRIMARY KEY, value VARCHAR(255));");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void create(){
		try{
			Statement stmt = con.createStatement();
			
			//### AP1 ###
			stmt.executeUpdate("CREATE TABLE configurations (name VARCHAR(255) PRIMARY KEY, value VARCHAR(255));");
			
			//### AP2 ###
			stmt.executeUpdate("CREATE TABLE logTable (changeDate VARCHAR(255), person VARCHAR(255), changeDescription VARCHAR(255), changedValues CLOB, PRIMARY KEY(changeDate,person));");
			
			//### AP3 ###
			stmt.executeUpdate("CREATE TABLE projectProposal (projectID INT PRIMARY KEY, projectName VARCHAR(255), projectDescription CLOB, minStud INT, maxStud INT, partnerName VARCHAR(255), partnerDescription CLOB, estimatedBegin DATE, period VARCHAR(255), department INT, isDeleted BOOLEAN, isPublic BOOLEAN, isRejected BOOLEAN, keywords VARCHAR(255), lastModifiedAt DATE, lastModifiedBy INT);");
			
			stmt.executeUpdate("CREATE TABLE projectTopic (topicID INT PRIMARY KEY, projectName VARCHAR(255), projectDescription CLOB, minStud INT, maxStud INT, partnerName VARCHAR(255), partnerDescription CLOB, estimatedBegin DATE, period VARCHAR(255), department INT, keywords VARCHAR(255), projectFile INT, projectShorCut VARCHAR(255));");
			
			stmt.executeUpdate("CREATE TABLE files (projectID INT, url VARCHAR(255), isProjectFile BOOLEAN, PRIMARY KEY (projectID, url));");
			
			stmt.executeUpdate("CREATE TABLE person (personID INT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), role VARCHAR(255), department INT);");
			
			stmt.executeUpdate("CREATE TABLE contactPerson (personID INT, projectID INT, PRIMARY KEY(personID,projectID));");
			
			stmt.executeUpdate("CREATE TABLE comments (commentID INT PRIMARY KEY, projectID INT, message VARCHAR(255), author INT, date DATE);");
			
			stmt.executeUpdate("CREATE TABLE department (departmentID INT PRIMARY KEY, name VARCHAR(255), shortCut VARCHAR(3), professor INT);");
			
			//### AP4 ###
			stmt.executeUpdate("CREATE TABLE vote (personID INT, priority INT, projectID INT, PRIMARY KEY(personID,priority));");
				
			//### AP5 ###
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void reset(){
		try{
			Statement stmt = con.createStatement();
			
			//### AP1 ###
			stmt.executeUpdate("DROP TABLE IF EXISTS configurations;");
			
			//### AP2 ###
			stmt.executeUpdate("DROP TABLE IF EXISTS logTable;");
			
			//### AP3 ###
			stmt.executeUpdate("DROP TABLE IF EXISTS projectProposal;");
			
			stmt.executeUpdate("DROP TABLE IF EXISTS projectTopic;");
			
			stmt.executeUpdate("DROP TABLE IF EXISTS files;");
			
			stmt.executeUpdate("DROP TABLE IF EXISTS person;");
			
			stmt.executeUpdate("DROP TABLE IF EXISTS contactPerson;");
			
			stmt.executeUpdate("DROP TABLE IF EXISTS comments;");
			
			stmt.executeUpdate("DROP TABLE IF EXISTS department;");
			
			//### AP4 ###
			stmt.executeUpdate("DROP TABLE IF EXISTS vote;");
				
			//### AP5 ###
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
}
