package de.uni_potsdam.hpi.wfapp2011.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

import de.uni_potsdam.hpi.wfapp2011.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

public class ProcessStatus implements ProcessStatusInterface {
	private ProcessEngine processEngine;
	private String executionId;
	
	public ProcessStatus(ProcessIdentifier process){
		processEngine = ProcessEngines.getDefaultProcessEngine();
		executionId = process.getExecutionId();
	}
	
	public boolean isProjectProposalPhase(){
		return (readVariable(executionId).equals(Constants.PROPOSALCOL));
	}
	
	public boolean isFinalTopicDecisionPhase(){
		return (readVariable(executionId).equals(Constants.TOPICDECISION));
	}
	
	public boolean isVotingPhase(){
		return (readVariable(executionId).equals(Constants.VOTING));
	}
	
	public boolean isProjectMatchingPhase(){
		return (readVariable(executionId).equals(Constants.PROJECTMATCHING));
	}
	
	/**
	 * This Method allows to get the current phase of the process instance. 
	 * 
	 * The exact variables are definied in the Constants Class
	 * 
	 */
	
	public String getCurrentPhase(){
		return readVariable(executionId);
	}
	
	
	private String readVariable(String executionId) {
		String phase = (String) processEngine.getRuntimeService().getVariable(executionId, Constants.PROCESS_PHASE);
		return phase;

	}
	
	
	/**
	 * Main Method for first testing
	 * @param args no arguments expected
	 */
	public static void main(String[] args){
		ProcessIdentifier process = new ProcessIdentifier("Ba", "SS", 2011);
		ProcessStatus activiti = new ProcessStatus(process);
		String executionId = "4910";
		System.out.println("ProjectProposalPhase: "+activiti.isProjectProposalPhase());
		System.out.println("FinalTopicDecsionPhase: "+activiti.isFinalTopicDecisionPhase());
		System.out.println("VotingPhase: "+ activiti.isVotingPhase());
		System.out.println("ProjectMatchingPhase: "+ activiti.isProjectMatchingPhase());
		System.out.println(activiti.readVariable(executionId));
	}
}