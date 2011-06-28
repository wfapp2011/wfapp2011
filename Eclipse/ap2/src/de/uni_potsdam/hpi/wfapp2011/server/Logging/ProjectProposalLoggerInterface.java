package de.uni_potsdam.hpi.wfapp2011.server.Logging;

import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifierException;


/**
 * Interface for the Project Proposal Collection
 * 
 * This methods have to be implemented, so that the 
 * all necessary events during the Project Proposal 
 * Phase can be logged. 
 *
 */
public interface ProjectProposalLoggerInterface {
	public void logNewProjectProposal(ProcessIdentifier processIdentifier, String email, String projectName, String department) throws ProcessIdentifierException;
	public void logChangedProjectProposal(ProcessIdentifier processIdentifier, String email, String projectName) throws ProcessIdentifierException;
	public void logChangedProposalName(ProcessIdentifier processIdentifier, String email, String oldProjectName, String newProjectName) throws ProcessIdentifierException;
	public void logFileUpload(ProcessIdentifier processIdentifier, String email, String projectName, String filename) throws ProcessIdentifierException;
	public void logSelectedProjectsExport(ProcessIdentifier processIdentifier, String email, String processName) throws ProcessIdentifierException;
	
}
