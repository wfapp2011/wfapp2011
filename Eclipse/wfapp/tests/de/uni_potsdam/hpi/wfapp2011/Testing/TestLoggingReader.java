package de.uni_potsdam.hpi.wfapp2011.Testing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.Logging.LoggingReader;
import de.uni_potsdam.hpi.wfapp2011.Logging.ProjectProposalLogger;
import de.uni_potsdam.hpi.wfapp2011.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.database.TableAlreadyExistsException;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifierException;


public class TestLoggingReader {
	@Before
	public void clearDatabase() throws TableAlreadyExistsException, SQLTableException{
		DbInterface dbinterface = new DbInterface();
		DbInterface.initializeDatabase("Ba", "SS", 2014);
		dbinterface.connect("Ba", "SS", 2014);
		dbinterface.executeUpdate("DELEte from logtable");
		dbinterface.disconnect();
	}
	
	
	@Test
	public void testLoggingReader() throws ProcessIdentifierException {
		ProcessIdentifier pId = new ProcessIdentifier("Ba", "SS", 2014);
		LoggingReader loggingReader = new LoggingReader("Ba", "SS", 2014);
		assertEquals("Before Logging", 0, loggingReader.getNumberOfProjectProposals());
		
		ProjectProposalLogger.getInstance().logNewProjectProposal(pId, "email@example.com","Extraction", "Professor2");
		assertEquals("Before Logging", 1, loggingReader.getNumberOfProjectProposals());
		
		ProjectProposalLogger.getInstance().logNewProjectProposal(pId, "mail@example.com", "ExampleProject", "Professor1");
		assertEquals("Before Logging", 2, loggingReader.getNumberOfProjectProposals());
		
		ProjectProposalLogger.getInstance().logChangedProjectProposal(pId, "newMail@example.com", "Extraction");
		
		ProjectProposalLogger.getInstance().logFileUpload(pId, "baudisch", "Extraction", "description.pdf");
		assertEquals("Result", 2, loggingReader.getNumberOfProjectProposals());
		
		
	}
}

