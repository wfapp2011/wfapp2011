package de.uni_potsdam.hpi.wfapp2011.servercore.activiti.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.servercore.activiti.ActivitiProcessException;
import de.uni_potsdam.hpi.wfapp2011.servercore.activiti.ProcessAdministration;
import de.uni_potsdam.hpi.wfapp2011.servercore.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;



public class TestProcessStart {
	private ProcessAdministration process;
	private DbInterface dbInterface;
	private GregorianCalendar deadlineCollection;
	private GregorianCalendar deadlineTopics;
	private GregorianCalendar deadlineVoting;
	private GregorianCalendar deadlineMatching;
	private GregorianCalendar deadlineProcess;
	private String sql;
	
	private String type = "Ba";
	private String semester = "SS";
	private int year;
	
	@Before
	public void setUp() throws SQLTableException{
		process = ProcessAdministration.getInstance();
		year = (new GregorianCalendar()).get(Calendar.YEAR) + 20;
		
		dbInterface = new DbInterface();
		DbInterface.initializeMetaTables();
		DbInterface.initializeDatabase(type, semester, year);
		
		dbInterface.connect(type, semester, year);
		//###########################################################
		//# Delete old date values and processId in database		#
		//###########################################################
		String sql = "DELETE FROM configurations WHERE name = '"+Constants.DEADLINE_PROPOSAL_COL+"' OR " +
				"name = '"+ Constants.DEADLINE_TOPICS_PUBL+"' OR name = '"+Constants.DEADLINE_VOTING+"' OR name = '"+
				Constants.DEADLINE_MATCHING+"' OR name = '"+Constants.DEADLINE_PROCESS+"' OR name = '"+
				Constants.PROCESS_ID_VARIABLE_NAME+"'";
		
		dbInterface.executeUpdate(sql);
		dbInterface.disconnect();
		
		//###########################################################
		//# Set Dates for the process								#
		//###########################################################
		deadlineCollection = new GregorianCalendar();
		deadlineTopics = new GregorianCalendar();
		deadlineVoting = new GregorianCalendar();
		deadlineMatching = new GregorianCalendar();
		deadlineProcess = new GregorianCalendar();
		
		deadlineCollection.add(Calendar.DATE, +1);
		deadlineTopics.add(Calendar.DATE, +2);
		deadlineVoting.add(Calendar.DATE, +3);
		deadlineMatching.add(Calendar.DATE, +4);
		deadlineProcess.add(Calendar.DATE, +5);
	}
	/**
	 * This Test checks, if the process can be started correctly
	 */
	
	@Test
	public void testProcessStart(){
	
		boolean processStartSuccessfull = process.startProcess(type, semester, year, 
												"DegreeProjectProcessNew4", 
												deadlineCollection.getTime(), 
												deadlineTopics.getTime(), 
												deadlineVoting.getTime(), 
												deadlineMatching.getTime(), 
												deadlineProcess.getTime());
		assertTrue(processStartSuccessfull);
	}
	
	/**
	 * This test checks, if a process can be started and deadlines can be changed. <br/>
	 * As well it checks, that no errors will appear while trying to change deadlines
	 * for a process which doesn't exists. 
	 * @throws SQLTableException: Will be thrown if it can't connect to database or can't execute the queries
	 * @throws ActivitiProcessException: Will be thrown if the processIdentifier is not correct.
	 */
	@Test 
	public void testProcessStartInterface() throws SQLTableException, ActivitiProcessException {
		dbInterface.connect(type, semester, year);
		
		writeDatesIntoDatabase();
		
		assertTrue(process.startProcess(type, semester, year));
		//###########################################################
		//# Change the deadlines and write them in the database		#
		//###########################################################
		postponeDeadlines();
		updateDatesInDatabase();
		
		//###########################################################
		//# Check, if the deadlines could be changed in activiti	#
		//###########################################################
		boolean[] result = process.changedDeadlines(type, semester, year);
		for(int i=0; i < result.length; i++){
			assertTrue(result[i]);
		}
		
		//###########################################################
		//# Change Process instance ID, 							#
		//# so that an instance will not be found					#
		//###########################################################
		dbInterface.connect(type, semester, year);
		sql = "UPDATE configurations SET value = 2101 WHERE name = '"+Constants.PROCESS_ID_VARIABLE_NAME+"';";
		dbInterface.executeUpdate(sql);
		postponeDeadlines();
		dbInterface.disconnect();
		
		//############################################################
		//# Check, if the deadlines could not be changed in activiti #
		//############################################################
	
		result = process.changedDeadlines(type, semester, year);
		for(int i=0; i < result.length; i++){
			assertFalse(result[i]);
		}
	}
	/**
	 * Checks if the changing of deadlines without an activiti-process instance will work without exceptions. 
	 */
	@Test
	public void changeDeadlineForNonExistingProcess() {
		try {
			dbInterface.connect(type, semester, year);
			String sql = "UPDATE configurations SET value = 2101 WHERE name = '"+Constants.PROCESS_ID_VARIABLE_NAME+"';";
			dbInterface.executeUpdate(sql);
			process.changedDeadlines(type, semester, year);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		dbInterface.disconnect();
		
	}
	
	/**
	 * Postpone each deadline one day ahead
	 */
	private void postponeDeadlines() {
		deadlineCollection.add(Calendar.DATE, +1);
		deadlineTopics.add(Calendar.DATE, +1);
		deadlineVoting.add(Calendar.DATE, +1);
		deadlineMatching.add(Calendar.DATE, +1);
		deadlineProcess.add(Calendar.DATE, +1);
	}

	/**
	 * Insert the new deadlines into the database
	 * They will be used to start the activiti-process.
	 * @throws SQLTableException: Will be thrown if the deadlines cannot be inserted.
	 */
	private void writeDatesIntoDatabase() throws SQLTableException {
		DateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
		sql = "INSERT INTO configurations (name, value) VALUES ('"+Constants.DEADLINE_PROPOSAL_COL+"','"+df.format(deadlineCollection.getTime())+"');";
		dbInterface.executeUpdate(sql);
		sql = "INSERT INTO configurations (name, value) VALUES ('"+Constants.DEADLINE_TOPICS_PUBL+"','"+df.format(deadlineTopics.getTime())+"');";
		dbInterface.executeUpdate(sql);
		sql = "INSERT INTO configurations (name, value) VALUES ('"+Constants.DEADLINE_VOTING+"','"+df.format(deadlineVoting.getTime())+"');";
		System.out.println(sql);
		dbInterface.executeUpdate(sql);
		sql = "INSERT INTO configurations (name, value) VALUES ('"+Constants.DEADLINE_MATCHING+"','"+df.format(deadlineMatching.getTime())+"');";
		dbInterface.executeUpdate(sql);
		sql = "INSERT INTO configurations (name, value) VALUES ('"+Constants.DEADLINE_PROCESS+"','"+df.format(deadlineProcess.getTime())+"');";
		dbInterface.executeUpdate(sql);
	}
	
	/**
	 * Updates the Deadlines in the database
	 * They will be used to change the deadlines of the activiti process. 
	 * @throws SQLTableException: Will be thrown, if the deadlines cannot be changed. 
	 */
	private void updateDatesInDatabase() throws SQLTableException {
		DateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
		sql = "UPDATE configurations SET VALUE = '"+df.format(deadlineCollection.getTime())+"' WHERE NAME = '"+Constants.DEADLINE_PROPOSAL_COL+"';"; //   VALUES ('"+Constants.DEADLINE_PROPOSAL_COL+"','"+df.format(deadlineCollection.getTime())+"');";
		dbInterface.executeUpdate(sql);
		sql = "UPDATE configurations SET VALUE = '"+df.format(deadlineTopics.getTime())+"' WHERE NAME = '"+Constants.DEADLINE_TOPICS_PUBL+"';";
		dbInterface.executeUpdate(sql);
		sql = "UPDATE configurations SET VALUE = '"+df.format(deadlineVoting.getTime())+"' WHERE NAME = '"+Constants.DEADLINE_VOTING+"';";
		System.out.println(sql);
		dbInterface.executeUpdate(sql);
		sql = "UPDATE configurations SET VALUE = '"+df.format(deadlineMatching.getTime())+"' WHERE NAME = '"+Constants.DEADLINE_MATCHING+"';";
		dbInterface.executeUpdate(sql);
		sql = "UPDATE configurations SET VALUE = '"+df.format(deadlineProcess.getTime())+"' WHERE NAME = '"+Constants.DEADLINE_PROCESS+"';";
		dbInterface.executeUpdate(sql);
	}	
	
	@After
	public void deleteDatabase() {
		DbInterface.deleteDatabase(type, semester, year);
	}
	

}
