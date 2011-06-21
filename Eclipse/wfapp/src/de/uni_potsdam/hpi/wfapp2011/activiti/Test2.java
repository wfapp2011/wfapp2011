package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;

import de.uni_potsdam.hpi.wfapp2011.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.general.DateConverter;

public class Test2 {
	private ProcessInstance processInstance;
	
	private void changeDeadline(String executionId, String variableName, GregorianCalendar newDeadlineDate) {
		
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		String newDeadline = DateConverter.dateToISO8601(newDeadlineDate);
	//	processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionName("String");
		//HistoricProcessInstanceQuery test = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId("2510");
	//	ProcessInstance test = (ProcessInstance) processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		//	Date enddate = test.singleResult().
		//getRepositoryService().createProcessDefinitionQuery().processDefinitionName(processName).processDefinitionKey("String");
		String oldDeadline = (String) processEngine.getRuntimeService().getVariable(executionId, variableName);
		if (oldDeadline.compareTo(newDeadline) > 0 )
		processEngine.getRuntimeService().setVariable(executionId, variableName, DateConverter.dateToISO8601(newDeadlineDate));
		//Set changed Variable for the Activiti Process, so that the timer event will check the time again.
		processEngine.getRuntimeService().setVariable(executionId, "changed", 1);
		processEngine.close();
	}
	
	public static void main(String[] args){
		Test2 activiti = new Test2();
		GregorianCalendar date = new GregorianCalendar();
		GregorianCalendar startCollection = new GregorianCalendar();
		GregorianCalendar deadlineCollection = new GregorianCalendar();
		GregorianCalendar deadlineTopics = new GregorianCalendar();
		GregorianCalendar deadlineVoting = new GregorianCalendar();
		GregorianCalendar deadlineProcess = new GregorianCalendar();
		
		startCollection.add(Calendar.MINUTE, +5);
		deadlineCollection.add(Calendar.MINUTE, +10);
		deadlineTopics.add(Calendar.MINUTE, +15);
		deadlineVoting.add(Calendar.MINUTE, +20);
		deadlineProcess.add(Calendar.MINUTE, +25);
		
		String processInstanceId = activiti.startProcess("DegreeProjectProcessNew2", startCollection, deadlineCollection, deadlineTopics, deadlineVoting, deadlineProcess);
		GregorianCalendar date2 = new GregorianCalendar();
		date.add(Calendar.MINUTE, 5);
		activiti.changeDeadline(processInstanceId, Constants.START_PROPOSAL_COL_INPUT, date2 );
		System.out.println(activiti.processInstance.isEnded());
	}
	
	public String startProcess(String processName, 
			GregorianCalendar startProposalDate, 
			GregorianCalendar endProposalDate, 
			GregorianCalendar startVotingDate, 
			GregorianCalendar endVotingDate, 
			GregorianCalendar endMatchingDate)
	{
		String processInstanceId = Constants.DATE_ERROR;
		
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		
		//Get all process definitions with the given process name
		ProcessDefinitionQuery query = processEngine.getRepositoryService().
			createProcessDefinitionQuery().processDefinitionName(processName);
		
		// Get only the latest process definition 
		ProcessDefinition processDefinition = query.orderByProcessDefinitionVersion().desc().listPage(0, 10).get(0);
		String id = processDefinition.getId();
		
		// make sure that the dates have the correct order.
		if (startProposalDate.before(endProposalDate) && 
				endProposalDate.before(startVotingDate) && 
				startVotingDate.before(endVotingDate) && 
				endVotingDate.before(endMatchingDate))
		{
			processInstance = ProcessEngines.getProcessEngine("default").getRuntimeService().startProcessInstanceById(id);
			processInstanceId = processInstance.getId();
			
			//Setting the Default Deadline of the whole process, used for the boundary Events
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.DEFAULT_DEADLINE_PROCESS, DateConverter.dateToISO8601(endMatchingDate));
		
			//Setting the deadlines, which will be changeable from outside the process. 
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.START_PROPOSAL_COL_INPUT, DateConverter.dateToISO8601(startProposalDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.DEADLINE_PROPOSAL_COL_INPUT, DateConverter.dateToISO8601(endProposalDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.DEADLINE_TOPICS_PUBL_INPUT, DateConverter.dateToISO8601(startVotingDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.DEADLINE_VOTING_INPUT, DateConverter.dateToISO8601(endVotingDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.DEADLINE_PROCESS_INPUT, DateConverter.dateToISO8601(endMatchingDate));
			
			//Setting the deadlines used for the timeEvents. Changes will be copied in the service task. 
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.START_PROPOSAL_COL, DateConverter.dateToISO8601(startProposalDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.DEADLINE_PROPOSAL_COL, DateConverter.dateToISO8601(endProposalDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.DEADLINE_TOPICS_PUBL, DateConverter.dateToISO8601(startVotingDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.DEADLINE_VOTING, DateConverter.dateToISO8601(endVotingDate));
			processEngine.getRuntimeService().setVariable(processInstanceId, Constants.DEADLINE_PROCESS, DateConverter.dateToISO8601(endMatchingDate));
			
			processEngine.close();
		}
		return processInstanceId;
	}
	
	
	
	/*public static void main(String[] args){
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		processEngine = ProcessEngines.getDefaultProcessEngine();
		ProcessDefinitionQuery query = processEngine.getRepositoryService().
			createProcessDefinitionQuery().processDefinitionName("Pizzabestellung");
		ProcessDefinition testprocess = query.listPage(0, 10).get(0);
		String id = testprocess.getId();
		
		ProcessEngines.getProcessEngine("default").getRuntimeService().startProcessInstanceById(id);
	}*/
}