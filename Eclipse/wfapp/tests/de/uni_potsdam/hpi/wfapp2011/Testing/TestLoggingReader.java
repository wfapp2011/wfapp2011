package de.uni_potsdam.hpi.wfapp2011.Testing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.Logging.LoggingReader;
import de.uni_potsdam.hpi.wfapp2011.Logging.ProjectProposalLogger;
import de.uni_potsdam.hpi.wfapp2011.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.database.TableAlreadyExistsException;


public class TestLoggingReader {
	@Before
	public void clearDatabase() throws TableAlreadyExistsException, SQLTableException{
		DbInterface dbinterface = new DbInterface();
		DbInterface.initializeDatabase("Ba", "SS", 2011);
		dbinterface.connect("Ba", "SS", 2011);
		dbinterface.executeUpdate("DELEte from logtable");
		dbinterface.disconnect();
	}
	
	
	@Test
	public void testLoggingReader() {
		ProjectProposalLogger projectProposalLogger = new ProjectProposalLogger("Ba", "SS", 2011);
		LoggingReader loggingReader = new LoggingReader("Ba", "SS", 2011);
		assertEquals("Before Logging", 0, loggingReader.getNumberOfProjectProposals());
		
		projectProposalLogger.logNewProjectProposal("email@example.com","Extraction", "Professor2");
		assertEquals("Before Logging", 1, loggingReader.getNumberOfProjectProposals());
		
		projectProposalLogger.logNewProjectProposal("mail@example.com", "ExampleProject", "Professor1");
		assertEquals("Before Logging", 2, loggingReader.getNumberOfProjectProposals());
		
		projectProposalLogger.logChangedProjectProposal("newMail@example.com", "Extraction");
		
		projectProposalLogger.logFileUpload("baudisch", "Extraction", "description.pdf");
		assertEquals("Result", 2, loggingReader.getNumberOfProjectProposals());
		
		
	}
}

