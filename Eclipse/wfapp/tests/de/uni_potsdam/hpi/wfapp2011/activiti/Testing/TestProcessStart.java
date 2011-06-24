package de.uni_potsdam.hpi.wfapp2011.activiti.Testing;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.Logging.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.Logging.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.activiti.ActivitiProcessException;
import de.uni_potsdam.hpi.wfapp2011.activiti.ProcessAdministration;
import de.uni_potsdam.hpi.wfapp2011.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

public class TestProcessStart {
	private ProcessAdministration process;
	private DbInterface dbInterface;
	private ProcessIdentifier processIdentifier;
	private GregorianCalendar deadlineCollection;
	private GregorianCalendar deadlineTopics;
	private GregorianCalendar deadlineVoting;
	private GregorianCalendar deadlineMatching;
	private GregorianCalendar deadlineProcess;
	
	
	
	@Before
	public void setUp() throws SQLTableException{
		process = ProcessAdministration.getInstance();
		processIdentifier = new ProcessIdentifier("Ba", "SS", 2012);
		dbInterface = new DbInterface();
		DbInterface.initializeMetaTables();
		DbInterface.initializeDatabase("Ba", "SS", 2012);
		dbInterface.connect(processIdentifier.getType(), processIdentifier.getSemester(), processIdentifier.getYear());
		String sql = "DELETE FROM configurations WHERE name = '"+Constants.DEADLINE_PROPOSAL_COL+"' OR " +
		"name = '"+ Constants.DEADLINE_TOPICS_PUBL+"' OR name = '"+Constants.DEADLINE_VOTING+"' OR name = '"+
		Constants.DEADLINE_MATCHING+"' OR name = '"+Constants.DEADLINE_PROCESS+"' OR name = '"+
		Constants.PROCESS_ID_VARIABLE_NAME+"'";
		System.out.println(sql);
		dbInterface.executeUpdate(sql);
		
		dbInterface.disconnect();
		
		deadlineCollection = new GregorianCalendar();
		deadlineTopics = new GregorianCalendar();
		deadlineVoting = new GregorianCalendar();
		deadlineMatching = new GregorianCalendar();
		deadlineProcess = new GregorianCalendar();
		
		deadlineCollection.add(Calendar.MINUTE, +2);
		deadlineTopics.add(Calendar.MINUTE, +4);
		deadlineVoting.add(Calendar.MINUTE, +6);
		deadlineMatching.add(Calendar.MINUTE, +8);
		deadlineProcess.add(Calendar.MINUTE, +10);
	}
	
/*	@Test
	public void testProcessStart(){
	
		boolean processInstanceId = process.startProcess(processIdentifier, "DegreeProjectProcessNew2", deadlineCollection.getTime(), deadlineTopics.getTime(), deadlineVoting.getTime(), deadlineMatching.getTime(), deadlineProcess.getTime());
		System.out.println("New Instance ID: "+processInstanceId);
		assertTrue(processInstanceId);
	}
	*/
	@Test 
	public void testProcessStartInterface() throws SQLTableException, ActivitiProcessException {
		dbInterface.connect(processIdentifier.getType(), processIdentifier.getSemester(), processIdentifier.getYear());
		
		DateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
		String sql = "INSERT INTO configurations (name, value) VALUES ('"+Constants.DEADLINE_PROPOSAL_COL+"','"+df.format(deadlineCollection.getTime())+"');";
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
		
		assertTrue(process.startProcess(processIdentifier));
		
	}
}
