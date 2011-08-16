package de.uni_potsdam.hpi.wfapp2011.proposals.server;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.ProcessInfo;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.TestdataInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.logging.ProjectProposalLogger;

public class TestdataProvider  extends RemoteServiceServlet implements TestdataInterface{

	ProjectProposalLogger logger = ProjectProposalLogger.getInstance();
	
	private static final long serialVersionUID = 1L;
	private DbInterface dbConnection;
	private String type, semester;
	private int year;

	public TestdataProvider() {
		dbConnection = new DbInterface();
		// TODO: Replace with URL
		type = ProcessInfo.getTypeFromURL("");
		semester = ProcessInfo.getSemesterFromURL("");
		year = ProcessInfo.getYearFromURL("");
	}
	
	/**
	 * Initializes the Database.
	 */
	public void initializeMetadata(){
		DbInterface.initializeMetaTables();
		try {
			DbInterface.initializeDatabase(type, semester, year);
		} catch (SQLTableException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Fills the department table in the Database.
	 */
	public void insertDepartments(){
		String qInsertDepartments = getInitialDepartments();	
		try {
			dbConnection.connect(type, semester, year);		
			dbConnection.executeUpdate(qInsertDepartments);
		} catch (SQLTableException e) {
			e.printStackTrace();
		} 
		dbConnection.disconnect();
	}
	
	/**
	 * Adds some persons (profs and wimis) into 
	 * person table in the Database.
	 */
	public void insertPersons(){
		String qInsertPersons = getInitialPersons();
		try {
			dbConnection.connect(type, semester, year);		
			dbConnection.executeUpdate(qInsertPersons);
		} catch (SQLTableException e) {
			e.printStackTrace();
		} 
		dbConnection.disconnect();
	}
	
	private String getInitialDepartments(){		
		String query =  "INSERT INTO DEPARTMENT "+ 
						"VALUES (1, 'Enterprise Platform and Integration Concepts', 'Z', 1);"+
						"INSERT INTO DEPARTMENT "+ 
						"VALUES (2, 'Internet-Technologien und -Systeme', 'M', 2);"+
						"INSERT INTO DEPARTMENT "+ 
						"VALUES (3, 'Human Computer Interaction', 'B', 3);"+
						"INSERT INTO DEPARTMENT "+ 
						"VALUES (4, 'Computergrafische Systeme', 'D', 4);"+
						"INSERT INTO DEPARTMENT "+ 
						"VALUES (5, 'Betriebssysteme und Middleware', 'P', 5);"+
						"INSERT INTO DEPARTMENT "+ 
						"VALUES (6, 'Business Process Technology', 'W', 6);"+
						"INSERT INTO DEPARTMENT "+ 
						"VALUES (7, 'Software-Architekturen', 'H', 7);"+
						"INSERT INTO DEPARTMENT "+ 
						"VALUES (8, 'Informationssysteme', 'N', 8);"+
						"INSERT INTO DEPARTMENT "+ 
						"VALUES (9, 'Systemanalyse und Modellierung', 'G', 9);";
		return query;	
	}
	
	public String getInitialPersons(){	
		String query =  "INSERT INTO PERSON "+ 
						"VALUES (1, 'Prof. Dr. Christoph Meinel', '', 'Prof', 1, null);"+
						"INSERT INTO PERSON "+
						"VALUES (2, 'Prof. Dr. Patrick Baudisch', '', 'Prof', 2, null);"+				
						"INSERT INTO PERSON "+
						"VALUES (3, 'Prof. Dr. Jürgen Döllner', '', 'Prof', 3, null);"+			
						"INSERT INTO PERSON "+
						"VALUES (6, 'Prof. Dr. Mathias Weske', 'mathias.weske@hpi.uni-potsdam.de', 'Prof', 6, null);"+		
						"INSERT INTO PERSON "+
						"VALUES (20, 'Matthias Kunze', 'matthias.kunze@hpi.uni-potsdam.de', 'WiMi', 6, null);"+
						"INSERT INTO PERSON "+
						"VALUES (21, 'Matthias Weidlich', 'matthias.weidlich@hpi.uni-potsdam.de', 'WiMi', 6, null);";
		return query;
	}
}
