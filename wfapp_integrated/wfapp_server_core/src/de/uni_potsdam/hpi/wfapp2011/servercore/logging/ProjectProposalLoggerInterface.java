package de.uni_potsdam.hpi.wfapp2011.servercore.logging;



/**
 * Interface for the Project Proposal Collection
 * 
 * This methods have to be implemented, so that the 
 * all necessary events during the Project Proposal 
 * Phase can be logged. 
 *
 * @author Jannik Marten, Yanina Yurchenko
 * 
 */
public interface ProjectProposalLoggerInterface {
	public void logNewProjectProposal(String type, String semester, int year, String email, String projectName, String department);
	public void logChangedProjectProposal(String type, String semester, int year, String email, String projectName);
	public void logChangedProposalName(String type, String semester, int year, String email, String oldProjectName, String newProjectName) ;
	public void logFileUpload(String type, String semester, int year, String email, String projectName, String filename);
	public void logSelectedProjectsExport(String type, String semester, int year, String email, String processName) ;
	
}
