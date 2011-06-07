package de.uni_potsdam.hpi.wfapp2011.Logging;

public interface ProjectProposalLoggerInterface {
	public void logNewProjectProposal(String email, String projectName, String department);
	public void logChangedProjectProposal(String email, String projectName);
	public void logChangedProposalName(String email, String oldProjectName, String newProjectName);
	public void logFileUpload(String email, String projectName, String filename);
	public void logSelectedProjectsExport(String email, String processName);
	
}
