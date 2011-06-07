package de.uni_potsdam.hpi.wfapp2011.Logging.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.Logging.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.Logging.LoggingReader;
import de.uni_potsdam.hpi.wfapp2011.Logging.ProjectProposalLogger;
import de.uni_potsdam.hpi.wfapp2011.Logging.TableAlreadyExistsException;


public class TestLoggingReader {
	@Before
	public void clearDatabase() throws TableAlreadyExistsException{
		DbInterface dbinterface = new DbInterface();
		dbinterface.connect();
		dbinterface.executeUpdate("DELEte from logtable");
		dbinterface.disconnect();
	}
	
	
	@Test
	public void testLoggingReader() {
		ProjectProposalLogger projectProposalLogger = new ProjectProposalLogger();
		projectProposalLogger.logNewProjectProposal("jannik@web","Extraction", "Meinel");
		projectProposalLogger.logNewProjectProposal("yanina@web", "Extraction3", "Meinel");
		projectProposalLogger.logChangedProjectProposal("meinel@hpi", "Extraction2");
		projectProposalLogger.logFileUpload("baudisch@web", "Extraction2", "description.pdf");
		LoggingReader loggingReader = new LoggingReader();
		assertEquals("Result", 2, loggingReader.getNumberOfProjectProposals());
	}
}
