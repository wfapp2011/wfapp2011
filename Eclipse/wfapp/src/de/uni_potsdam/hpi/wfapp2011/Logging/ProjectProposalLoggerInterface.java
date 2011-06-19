package de.uni_potsdam.hpi.wfapp2011.Logging;


/**
 * Interface for the Project Proposal Collection
 * 
 * This methods have to be implemented, so that the 
 * all necessary events during the Project Proposal 
 * Phase can be logged. 
 *
 */
public interface ProjectProposalLoggerInterface {
	public void logNewProjectProposal(String email, String projectName, String department);
	public void logChangedProjectProposal(String email, String projectName);
	public void logChangedProposalName(String email, String oldProjectName, String newProjectName);
	public void logFileUpload(String email, String projectName, String filename);
	public void logSelectedProjectsExport(String email, String processName);
	
}
