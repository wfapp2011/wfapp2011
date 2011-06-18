package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;

public class ProcessAdministration implements ProcessAdministrationInterface {
	
	/** 
	 * This method starts a process instance of the given ProcessName.
	 * 
	 * */
	public String startProcess(String processName, 
			Date startProposalDate, 
			Date endProposalDate, Date startVotingDate, Date endVotingDate, Date endMatchingDate){
		System.out.println("Administration:Start  " +new Date().toString());
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
	
		String processInstanceId = "DateError";
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery().
			processDefinitionName(processName);
		
		ProcessDefinition processDefinition = query.orderByProcessDefinitionVersion().desc().listPage(0, 10).get(0);
		String id = processDefinition.getId();
		
		if (startProposalDate.before(endProposalDate) && 
				endProposalDate.before(startVotingDate) && 
				startVotingDate.before(endVotingDate) && 
				endVotingDate.before(endMatchingDate))
		{
			ProcessInstance processInstance = ProcessEngines.getProcessEngine("default").getRuntimeService().startProcessInstanceById(id);
			
			processInstanceId = processInstance.getId();
			processEngine.getRuntimeService().setVariable(processInstanceId, "changed", 2);
			processEngine.getRuntimeService().setVariable(processInstanceId, "vonProzessadministration", "gesetzt");
			processEngine.getRuntimeService().setVariable(processInstanceId, "defaultDeadlineProcess", dateToISO8601(endMatchingDate));
			System.out.println("Administration: DefaultDeadlineProcess: "+dateToISO8601(endMatchingDate));
			//Setting the changeable deadlines
			processEngine.getRuntimeService().setVariable(processInstanceId, "startProposalCollectionInput", dateToISO8601(startProposalDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, "deadlineProposalCollectionInput", dateToISO8601(endProposalDate));
			System.out.println("Administration: deadlineProposalCollectionInput "+ dateToISO8601(endProposalDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, "deadlineTopicsPublicationInput", dateToISO8601(startVotingDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, "deadlineVotingInput", dateToISO8601(endVotingDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, "deadlineProcessInput", dateToISO8601(endMatchingDate));
			//Setting the deafault-deadlines used for the timeEvents 
			
			processEngine.getRuntimeService().setVariable(processInstanceId, "startProposalCollection", dateToISO8601(startProposalDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, "deadlineProposalCollection", dateToISO8601(endProposalDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, "deadlineTopicsPublication", dateToISO8601(startVotingDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, "deadlineVoting", dateToISO8601(endVotingDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, "deadlineProcess", dateToISO8601(endMatchingDate));
			processEngine.close();
			System.out.println("Administration:Ende  " +new Date().toString()+ "\n");
		}
		return processInstanceId;
	}
	
	public String dateToISO8601(Date date) {
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return iso.format(date);
	}
	
	public static void main(String[] args){
		
		ProcessAdministration process = new ProcessAdministration();
		Date startCollection = new Date();
		Date deadlineCollection = new Date();
		Date deadlineTopics = new Date();
		Date deadlineVoting = new Date();
		Date deadlineProcess = new Date();
		System.out.println("Main-Methode: Deadline Collection: "+ process.dateToISO8601(deadlineCollection));
		startCollection.setMinutes(startCollection.getMinutes() + 5);
		deadlineCollection.setMinutes(deadlineCollection.getMinutes() + 10);
		deadlineTopics.setMinutes(deadlineTopics.getMinutes()+15);
		deadlineVoting.setMinutes(deadlineVoting.getMinutes()+ 20);
		deadlineProcess.setMinutes(deadlineProcess.getMinutes()+25);
		
		
		System.out.println("Ende Main: "+process.startProcess("DegreeProjectProcessNew2", startCollection, deadlineCollection, deadlineTopics, deadlineVoting, deadlineProcess));
		
	} 
	
	
	
}
