package de.uni_potsdam.hpi.wfapp2011.activiti.Testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.server.activiti.ProcessStatus;
import de.uni_potsdam.hpi.wfapp2011.server.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.server.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.server.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifier;


public class TestProcessStatus {
	DbInterface dbInterface;
	ProcessIdentifier process;
	
	@Before
	public void setUp() {
		dbInterface = new DbInterface();
		process = new ProcessIdentifier("Ba", "SS", 2014);
	}
	
	@After
	public void clear() throws SQLTableException {
		
		String sql = "DELETE FROM configurations WHERE name = '"+Constants.PROCESS_ID_VARIABLE_NAME+"'";
		dbInterface.connect(process.getType(), process.getSemester(), process.getYear());
		dbInterface.executeUpdate(sql);
		dbInterface.disconnect();
	}
	
	
	
	/**
	 * tests if it works to get the process status, while there is no actviti process running
	 * @throws SQLTableException
	 */
	@Test 
	public void testProcessStatus() throws SQLTableException {
		
		DbInterface.initializeMetaTables();
		DbInterface.initializeDatabase(process.getType(), process.getSemester(), process.getYear());
		
		String sql = "INSERT INTO configurations (name, value) VALUES ('"+Constants.PROCESS_ID_VARIABLE_NAME+"', 4910 )";
		
		dbInterface.connect(process.getType(), process.getSemester(), process.getYear());
		dbInterface.executeUpdate(sql);
		dbInterface.disconnect();
		
		ProcessStatus processStatus = new ProcessStatus(process);
		assertEquals("Process is not running",processStatus.getCurrentPhase(), Constants.PROCESS_NOT_RUNNING);
		assertFalse(processStatus.isFinalTopicDecisionPhase());
		assertFalse(processStatus.isProjectMatchingPhase());
		assertFalse(processStatus.isProjectProposalPhase());
		assertFalse(processStatus.isVotingPhase());
	}
}
