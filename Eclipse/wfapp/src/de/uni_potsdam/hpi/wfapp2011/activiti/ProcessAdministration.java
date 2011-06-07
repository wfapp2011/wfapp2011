package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

public class ProcessAdministration implements ProcessAdministrationInterface {
	
	/** 
	 * This method starts a process instance of the given ProcessName.
	 * 
	 * */
	public void startProcess(String processName, Date startProposalDate, 
			Date endProposalDate, Date startVotingDate, Date endVotingDate, Date endMatchingDate){
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery().
			processDefinitionName(processName);
		
		ProcessDefinition processDefinition = query.listPage(0, 10).get(0);
		String id = processDefinition.getId();
		
		ProcessEngines.getProcessEngine("default").getRuntimeService().startProcessInstanceById(id);
	}
	
	
	
	
}
