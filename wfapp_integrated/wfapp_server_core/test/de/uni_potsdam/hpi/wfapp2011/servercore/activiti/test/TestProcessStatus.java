package de.uni_potsdam.hpi.wfapp2011.servercore.activiti.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.servercore.activiti.ProcessStatus;
import de.uni_potsdam.hpi.wfapp2011.servercore.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;


public class TestProcessStatus {
	DbInterface dbInterface;
	private String type;
	private String semester;
	private int year;
	
	@Before
	public void setUp() {
		dbInterface = new DbInterface();
		type = "Ba";
		semester = "SS";
		year = (new GregorianCalendar()).get(Calendar.YEAR) + 20;
	}
	
	@After
	public void clear() throws SQLTableException {
		
		String sql = "DELETE FROM configurations WHERE name = '"+Constants.PROCESS_ID_VARIABLE_NAME+"'";
		dbInterface.connect(type, semester, year);
		dbInterface.executeUpdate(sql);
		dbInterface.disconnect();
		DbInterface.deleteDatabase(type, semester, year);
	}
	
	
	
	/**
	 * tests if it works to get the process status, while there is no actviti process running
	 * @throws SQLTableException
	 */
	@Test 
	public void testProcessStatus() throws SQLTableException {
		
		DbInterface.initializeMetaTables();
		DbInterface.initializeDatabase(type, semester, year);
		
		String sql = "INSERT INTO configurations (name, value) VALUES ('"+Constants.PROCESS_ID_VARIABLE_NAME+"', 4910 )";
		
		dbInterface.connect(type, semester, year);
		dbInterface.executeUpdate(sql);
		dbInterface.disconnect();
		
		ProcessStatus processStatus = new ProcessStatus(type, semester, year);
		assertEquals("Process is not running",processStatus.getCurrentPhase(), Constants.PROCESS_NOT_RUNNING);
		assertFalse(processStatus.isFinalTopicDecisionPhase());
		assertFalse(processStatus.isProjectMatchingPhase());
		assertFalse(processStatus.isProjectProposalPhase());
		assertFalse(processStatus.isVotingPhase());
	}
}
