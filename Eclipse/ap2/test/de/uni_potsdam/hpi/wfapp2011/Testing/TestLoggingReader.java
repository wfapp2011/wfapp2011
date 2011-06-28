package de.uni_potsdam.hpi.wfapp2011.Testing;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.server.Logging.LoggingReader;
import de.uni_potsdam.hpi.wfapp2011.server.Logging.ProjectProposalLogger;
import de.uni_potsdam.hpi.wfapp2011.server.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.server.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.server.database.TableAlreadyExistsException;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifierException;


public class TestLoggingReader {
	private int year;
	ProcessIdentifier pId;
	
	@Before
	public void clearDatabase() throws TableAlreadyExistsException, SQLTableException{
		year = new GregorianCalendar().get(Calendar.YEAR)+2;
		pId = new ProcessIdentifier("Ba", "SS", year);
		DbInterface dbinterface = new DbInterface();
		DbInterface.initializeDatabase(pId.getType(), pId.getSemester(), pId.getYear());
		dbinterface.connect(pId.getType(), pId.getSemester(), pId.getYear());
		dbinterface.executeUpdate("DELEte from logtable");
		dbinterface.disconnect();
	}
	
	
	@Test
	public void testLoggingReader() throws ProcessIdentifierException {
		LoggingReader loggingReader = new LoggingReader(pId.getType(), pId.getSemester(), pId.getYear());
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

