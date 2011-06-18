package de.uni_potsdam.hpi.wfapp2011.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

import de.uni_potsdam.hpi.wfapp2011.constants.Constants;

public class ActivitiConnection implements ProcessStatus {
	private ProcessEngine processEngine;
	
	public ActivitiConnection(){
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}
	
	public boolean isProjectProposalPhase(String executionId){
		return (readVariable(executionId, Constants.PROCESS_PHASE).equals(Constants.PROPOSALCOL));
	}
	
	public boolean isFinalTopicDecisionPhase(String executionId){
		return (readVariable(executionId, Constants.PROCESS_PHASE).equals(Constants.TOPICDECISION));
	}
	
	public boolean isVotingPhase(String executionId){
		return (readVariable(executionId, Constants.PROCESS_PHASE).equals(Constants.VOTING));
	}
	
	public boolean isProjectMatchingPhase(String executionId){
		return (readVariable(executionId, Constants.PROCESS_PHASE).equals(Constants.PROJECTMATCHING));
	}
	
	
	public String readVariable(String executionId, String variableName) {
		//ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		String phase = (String) processEngine.getRuntimeService().getVariable(executionId, variableName);
		//processEngine.close();
		return phase;

	}
	
	public static void main(String[] args){
		ActivitiConnection activiti = new ActivitiConnection();
		String executionId = "4910";
		System.out.println("ProjectProposalPhase: "+activiti.isProjectProposalPhase(executionId));
		System.out.println("FinalTopicDecsionPhase: "+activiti.isFinalTopicDecisionPhase(executionId));
		System.out.println("VotingPhase: "+ activiti.isVotingPhase(executionId));
		System.out.println("ProjectMatchingPhase: "+ activiti.isProjectMatchingPhase(executionId));
		System.out.println(activiti.readVariable(executionId, "phase"));
	}
}
