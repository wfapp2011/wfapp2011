package de.uni_potsdam.hpi.wfapp2011.servercore.logger.test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.logging.LoggingReader;
import de.uni_potsdam.hpi.wfapp2011.servercore.logging.ProjectProposalLogger;


public class TestLoggingReader {
	private int year;
	private String type;
	private String semester;
	private DbInterface dbinterface;
	
	@Before
	public void clearDatabase() throws SQLTableException{
		year = new GregorianCalendar().get(Calendar.YEAR)+20;
		type = "Ba";
		semester = "SS";
		
		dbinterface = new DbInterface();
		DbInterface.initializeDatabase(type, semester, year);
		dbinterface.connect(type, semester, year);
		dbinterface.executeUpdate("DELEte from logtable");
		dbinterface.disconnect();
	}
	
	
	@Test
	public void testLoggingReader() {
		LoggingReader loggingReader = new LoggingReader(type, semester, year);
		assertEquals("Before Logging", 0, loggingReader.getNumberOfProjectProposals());
		
		ProjectProposalLogger.getInstance().logNewProjectProposal(type, semester, year, "email@example.com","Project1", "Professor2");
		assertEquals("Before Logging", 1, loggingReader.getNumberOfProjectProposals());
		
		ProjectProposalLogger.getInstance().logNewProjectProposal(type, semester, year, "mail@example.com", "Project2", "Professor1");
		assertEquals("Before Logging", 2, loggingReader.getNumberOfProjectProposals());
		
		ProjectProposalLogger.getInstance().logChangedProjectProposal(type, semester, year, "newMail@example.com", "Project1");
		
		ProjectProposalLogger.getInstance().logFileUpload(type, semester, year, "Professor1", "Project2", "description.pdf");
		assertEquals("Result", 2, loggingReader.getNumberOfProjectProposals());
		
		
	}
	
	@After 
	public void deleteDatabase() {
		DbInterface.deleteDatabase(type, semester, year);
	}
}

