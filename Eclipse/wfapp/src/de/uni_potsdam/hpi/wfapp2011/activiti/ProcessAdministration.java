package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;

import de.uni_potsdam.hpi.wfapp2011.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.general.DateConverter;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

public class ProcessAdministration implements ProcessAdministrationInterface {
	
	/**
	 * This methods starts the Activiti process.
	 * It will use the Standard Process definied in the Constants as PROCESS_NAME
	 */
	public String startProcess(
			GregorianCalendar endProposalDate, 
			GregorianCalendar startVotingDate, 
			GregorianCalendar endVotingDate, 
			GregorianCalendar endMatchingDate,
			GregorianCalendar endProcessDate){
		return startProcess(Constants.PROCESS_NAME, endProposalDate, startVotingDate, endVotingDate, endMatchingDate, endProcessDate);
	}
	
	/** 
	 * This method starts a process instance of the given ProcessName.
	 * 
	 * @return It returns the process instance ID of the started Instance. 
	 * 
	 * */
	public String startProcess(String processName, 
			GregorianCalendar endProposalDate, 
			GregorianCalendar startVotingDate, 
			GregorianCalendar endVotingDate, 
			GregorianCalendar endMatchingDate,
			GregorianCalendar endProcessDate)
	{
		String processInstanceId = Constants.DATE_ERROR;

		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		//Get all process definitions with the given process name
		ProcessDefinitionQuery query = processEngine.getRepositoryService().
			createProcessDefinitionQuery().processDefinitionName(processName);
		
		// Get only the latest process definition 
		ProcessDefinition processDefinition = query.orderByProcessDefinitionVersion().desc().listPage(0, 10).get(0);
		String id = processDefinition.getId();
		
		// make sure that the dates have the correct order.
		if (	endProposalDate.before(startVotingDate) && 
				startVotingDate.before(endVotingDate) && 
				endVotingDate.before(endMatchingDate) &&
				endMatchingDate.before(endProcessDate))
		{
			ProcessInstance processInstance = ProcessEngines.getProcessEngine("default").getRuntimeService().startProcessInstanceById(id);
			processInstanceId = processInstance.getId();
			
			//Setting the Default Deadline of the whole process, used for the boundary Events
			runtimeService.setVariable(processInstanceId, Constants.DEFAULT_DEADLINE_PROCESS, DateConverter.dateToISO8601(endProcessDate));
		
			//Setting the deadlines, which will be changeable from outside the process. 

			runtimeService.setVariable(processInstanceId, Constants.DEADLINE_PROPOSAL_COL_INPUT, DateConverter.dateToISO8601(endProposalDate));
			runtimeService.setVariable(processInstanceId, Constants.DEADLINE_TOPICS_PUBL_INPUT, DateConverter.dateToISO8601(startVotingDate));
			runtimeService.setVariable(processInstanceId, Constants.DEADLINE_VOTING_INPUT, DateConverter.dateToISO8601(endVotingDate));
			runtimeService.setVariable(processInstanceId, Constants.DEADLINE_PROCESS_INPUT, DateConverter.dateToISO8601(endMatchingDate));
			
			//Setting the deadlines used for the timeEvents. Changes will be copied in the service task. 
			runtimeService.setVariable(processInstanceId, Constants.DEADLINE_PROPOSAL_COL, DateConverter.dateToISO8601(endProposalDate));
			runtimeService.setVariable(processInstanceId, Constants.DEADLINE_TOPICS_PUBL, DateConverter.dateToISO8601(startVotingDate));
			runtimeService.setVariable(processInstanceId, Constants.DEADLINE_VOTING, DateConverter.dateToISO8601(endVotingDate));
			runtimeService.setVariable(processInstanceId, Constants.DEADLINE_MATCHING, DateConverter.dateToISO8601(endMatchingDate));
			
			runtimeService.setVariable(processInstanceId, Constants.DEADLINE_PROCESS, DateConverter.dateToISO8601(endProcessDate));
			
			processEngine.close();
		}
		return processInstanceId;
	}
	
	
	/**
	 * This method changes the deadline for the given Process to the given dates.
	 * 
	 * If the given dates are in the past or are behind the current dateline, 
	 * the deadline will not be changed.
	 * 
	 * @param processIdentifier ProcessIdentifier (type, semester, year) to get the current process
	 * @param endProposalDate the deadline for the departments to create project proposals.
	 * @param endTopicPublication the deadline to choose the projects, which will be offered
	 * @param endVotingDate the deadline for the voting of the students.
	 * @param endMatchingDate the deadline to execute the Matching
	 * @param endProcessDate the deadline of the whole Process
	 * @return For each deadline it returns if it was successfall or not. 
	 * 			the order is the same like in the method definition.
	 */
	
	
	public boolean[] changeDeadlines(ProcessIdentifier processIdentifier, 
			GregorianCalendar endProposalDate, 
			GregorianCalendar endTopicPublication, 
			GregorianCalendar endVotingDate,
			GregorianCalendar endMatchingDate,
			GregorianCalendar endProcessDate )
	{
		boolean[] success = new boolean[5];

		success[0] = changeDeadline(processIdentifier, Constants.DEADLINE_PROPOSAL_COL_INPUT, endProposalDate);
		success[1] = changeDeadline(processIdentifier, Constants.DEADLINE_TOPICS_PUBL_INPUT, endTopicPublication);
		success[2] = changeDeadline(processIdentifier, Constants.DEADLINE_VOTING_INPUT, endVotingDate);
		success[3] = changeDeadline(processIdentifier, Constants.DEADLINE_MATCHING, endMatchingDate);
		success[4] = changeDeadline(processIdentifier, Constants.DEADLINE_PROCESS_INPUT, endMatchingDate);
		return success;
	}
	
	
	
	
	private boolean changeDeadline(ProcessIdentifier processIdentifier, String variableName, GregorianCalendar newDeadlineDate) {
		String executionId = processIdentifier.getExecutionId();
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		
		String newDeadline = DateConverter.dateToISO8601(newDeadlineDate);
		String oldDeadline = (String) processEngine.getRuntimeService().getVariable(executionId, variableName);
		
		if (oldDeadline.compareTo(newDeadline) < 0 && newDeadlineDate.before(new GregorianCalendar())) {
			return false;
		}
		
		processEngine.getRuntimeService().setVariable(executionId, variableName, DateConverter.dateToISO8601(newDeadlineDate));
		
		//Set changed Variable for the Activiti Process, so that the timer event will check the time again.
		processEngine.getRuntimeService().setVariable(executionId, "changed", 1);
		processEngine.close();
		return true;
	}
	
	public static void main(String[] args){
		
		ProcessAdministration process = new ProcessAdministration();
		
		GregorianCalendar deadlineCollection = new GregorianCalendar();
		GregorianCalendar deadlineTopics = new GregorianCalendar();
		GregorianCalendar deadlineVoting = new GregorianCalendar();
		GregorianCalendar deadlineMatching = new GregorianCalendar();
		GregorianCalendar deadlineProcess = new GregorianCalendar();
		

		deadlineCollection.add(Calendar.MINUTE, +5);
		deadlineTopics.add(Calendar.MINUTE, +10);
		deadlineVoting.add(Calendar.MINUTE, +15);
		deadlineMatching.add(Calendar.MINUTE, +20);
		deadlineProcess.add(Calendar.MINUTE, +25);
		
		System.out.println("Ende Main: "+process.startProcess("DegreeProjectProcessNew2", deadlineCollection, deadlineTopics, deadlineVoting, deadlineMatching, deadlineProcess));
		
	} 
	
	
/*	public static void main(String[] args){
		ProcessAdministration activiti = new ProcessAdministration();
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MINUTE, -5);
		date.add(Calendar.MINUTE, 5);
		ProcessIdentifier processIdentifier = new ProcessIdentifier("Ba", "SS", 2011, "6010");
		activiti.changeDeadline(processIdentifier, "deadlineProposalCollectionInput", date );
	}
	*/
	
}
