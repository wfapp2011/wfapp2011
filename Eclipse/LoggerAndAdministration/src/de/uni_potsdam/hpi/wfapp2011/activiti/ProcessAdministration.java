package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;

import de.uni_potsdam.hpi.wfapp2011.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.constants.ProcessIdAdministration;
import de.uni_potsdam.hpi.wfapp2011.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.general.DateConverter;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

/**
 * This class allows to start a activiti process instance with the given deadlines
 * The deadlines can be changed as well using this class. 
 */

public class ProcessAdministration implements ProcessAdministrationInterface {
	private static ProcessAdministration theInstance = null;
	private static DbInterface dbInterface;
	
	private ProcessAdministration() {
		
	}
	
	/**
	 * get the Singleton Instance of ProcessAdministration.
	 * 
	 * @return the Singleton Instance of ProcessAdministration
	 */
	public synchronized static ProcessAdministration getInstance() {
		if(theInstance == null){
			theInstance = new ProcessAdministration();
			dbInterface = new DbInterface();
		}
		return theInstance;
	}
	
	/**
	 * Starts an Activiti-Process-Instance for the given ProcessIdentifier
	 * @param processIdentifier: Identifies the type, semester and year for which the process-instance will be created. 
	 * @throws ActivitiProcessException will be thrown if an Activiti-Process-Instance 
	 * 			for the given ProcessIdentifier exists already. 
	 * @throws SQLTableException 
	 */
	public boolean startProcess(ProcessIdentifier processIdentifier) throws ActivitiProcessException {
		boolean result = false;
		if(hasExistingProcess(processIdentifier)){
			throw new ActivitiProcessException("Process already exists");
		}
		else {
			HashMap<String, Date> mapDeadlines = loadDeadlinesFromDatabase(processIdentifier);
			result = startProcess(processIdentifier,
					Constants.PROCESS_NAME,
					(Date)mapDeadlines.get(Constants.DEADLINE_PROPOSAL_COL),
					(Date)mapDeadlines.get(Constants.DEADLINE_TOPICS_PUBL),
					(Date)mapDeadlines.get(Constants.DEADLINE_VOTING),
					(Date)mapDeadlines.get(Constants.DEADLINE_MATCHING),
					(Date)mapDeadlines.get(Constants.DEADLINE_PROCESS));
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public HashMap<String, Date> loadDeadlinesFromDatabase(
			ProcessIdentifier processIdentifier) {
		//##################################################
		//# Look up the deadlines set in the database      #
		//##################################################
		HashMap<String, Date> mapDeadlines = new HashMap<String, Date>();
		String sql = "SELECT * FROM configurations WHERE name = '"+ Constants.DEADLINE_PROPOSAL_COL+"' OR " +
				"name = '"+ Constants.DEADLINE_TOPICS_PUBL+"' OR name = '"+Constants.DEADLINE_VOTING+"' OR name = '"+
				Constants.DEADLINE_MATCHING+"' OR name = '"+Constants.DEADLINE_PROCESS+"'";
		
		try {
			dbInterface.connect(processIdentifier.getType(), processIdentifier.getSemester(), processIdentifier.getYear());
			ResultSet resultSet = dbInterface.executeQueryDirectly(sql);
			while(resultSet.next()){
				//###############################################				
				//# Parse String into Date     					#
				//###############################################
				DateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
				Date deadline = df.parse(resultSet.getString(2));
				deadline.setHours(23);
				deadline.setMinutes(59);
				deadline.setSeconds(59);
				mapDeadlines.put(resultSet.getString(1), deadline);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SQLTableException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dbInterface.disconnect();
		return mapDeadlines;
	}
	
	
	/**
	 * This Method updates the deadlines for the Activiti-Process
	 * It will use the deadlines set in the database
	 * 
	 * @param ProcessIdentifier, which identifies the process to start (type, semester, year)
	 * @return boolean[], which indicates which deadlines could be changed successfully.
	 * 			- false if the newDeadlines is in the past.
	 * 			- 		if the newDeadline is before the oldDeadline
	 * 			-		if the oldDeadline is in the past.  
	 */
	public boolean[] changedDeadlines(ProcessIdentifier processIdentifier){
		HashMap<String, Date> mapDeadlines = loadDeadlinesFromDatabase(processIdentifier);
		boolean[] success = new boolean[5];
		Date endProposalDate = mapDeadlines.get(Constants.DEADLINE_PROPOSAL_COL);
		Date endTopicPublication = mapDeadlines.get(Constants.DEADLINE_TOPICS_PUBL);
		Date endVotingDate = mapDeadlines.get(Constants.DEADLINE_VOTING);
		Date endMatchingDate = mapDeadlines.get(Constants.DEADLINE_MATCHING);
		Date endProcessDate = mapDeadlines.get(Constants.DEADLINE_PROCESS);
		
		success[0] = changeDeadline(processIdentifier, Constants.DEADLINE_PROPOSAL_COL_INPUT, endProposalDate);
		success[1] = changeDeadline(processIdentifier, Constants.DEADLINE_TOPICS_PUBL_INPUT, endTopicPublication);
		success[2] = changeDeadline(processIdentifier, Constants.DEADLINE_VOTING_INPUT, endVotingDate);
		success[3] = changeDeadline(processIdentifier, Constants.DEADLINE_MATCHING_INPUT, endMatchingDate);
		success[4] = changeDeadline(processIdentifier, Constants.DEADLINE_PROCESS_INPUT, endProcessDate);
		return success;
	}

	/** 
	 * This method starts a process instance of the given ProcessName.
	 * 
	 * @param processIdentifier: identifies the process (type, semester, year), which should be started.
	 * @param String processName: The name of the process in activiti, which should be started. 
	 * @param endProposalDate: date/time, when the Proposal Collection Phase will finish.
	 * @param startVotingDate: date/time up to which the final Topics have to be choosen and the voting phase starts.
	 * @param endVotingDate: date/time, which specifies the end of the voting phase
	 * @param endMatchingDate: date/time up to which the project matching has to be finished. 
	 * @param endProcessDate: date/time to finish the whole process. 
	 * 
	 * @return boolean, which indicates, if the process could start successfull. 
	 */
	public boolean startProcess(
			ProcessIdentifier processIdentifier,
			String processName, 
			Date endProposalDate, 
			Date startVotingDate, 
			Date endVotingDate, 
			Date endMatchingDate,
			Date endProcessDate)
	{
		boolean successfull = false;
		String instanceId = null;
		
		//###################################################
		//# Get the default Process Engine of Activiti. 	#
		//# Therefore the activiti.cfg.xml from Activiti 	#
		//# has to be on the Classpath						#
		//###################################################
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		//###########################################################
		//# Get all process definitions with the given process name	#
		//###########################################################
		ProcessDefinitionQuery query = processEngine.getRepositoryService().
					createProcessDefinitionQuery().processDefinitionName(processName);
		
		//###########################################################
		//# Get only the latest process definition 					#
		//###########################################################
		ProcessDefinition processDefinition = query.orderByProcessDefinitionVersion().desc().listPage(0, 10).get(0);
		String id = processDefinition.getId();
		
		//###########################################################
		//#    make sure that the dates have the correct order.		#
		//###########################################################
		if (	endProposalDate.before(startVotingDate) && 
				startVotingDate.before(endVotingDate) && 
				endVotingDate.before(endMatchingDate) &&
				endMatchingDate.before(endProcessDate))
		{
			ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceById(id);
			instanceId = processInstance.getId();
			if (instanceId != null){
				//###########################################################
				//# Setting the deadlines, which will be changeable 		#
				//# from outside the process. 								#
				//###########################################################
				runtimeService.setVariable(instanceId, Constants.DEADLINE_PROPOSAL_COL_INPUT, DateConverter.dateToISO8601(endProposalDate));
				runtimeService.setVariable(instanceId, Constants.DEADLINE_TOPICS_PUBL_INPUT, DateConverter.dateToISO8601(startVotingDate));
				runtimeService.setVariable(instanceId, Constants.DEADLINE_VOTING_INPUT, DateConverter.dateToISO8601(endVotingDate));
				runtimeService.setVariable(instanceId, Constants.DEADLINE_MATCHING_INPUT, DateConverter.dateToISO8601(endMatchingDate));
				runtimeService.setVariable(instanceId, Constants.DEADLINE_PROCESS_INPUT, DateConverter.dateToISO8601(endProcessDate));
				
				//###########################################################
				//#	Setting the deadlines used for the timeEvents. 			#
				//# Changes of these variables are not allowed.				#
				//# Changes will be done indirectly through a service task	#
				//###########################################################
				runtimeService.setVariable(instanceId, Constants.DEADLINE_PROPOSAL_COL, DateConverter.dateToISO8601(endProposalDate));
				runtimeService.setVariable(instanceId, Constants.DEADLINE_TOPICS_PUBL, DateConverter.dateToISO8601(startVotingDate));
				runtimeService.setVariable(instanceId, Constants.DEADLINE_VOTING, DateConverter.dateToISO8601(endVotingDate));
				runtimeService.setVariable(instanceId, Constants.DEADLINE_MATCHING, DateConverter.dateToISO8601(endMatchingDate));
				
				//###########################################################
				//# Setting the Default Deadline of the whole process.		#
				//# Used for the boundary Events							#
				//###########################################################
				runtimeService.setVariable(instanceId, Constants.DEADLINE_PROCESS, DateConverter.dateToISO8601(endProcessDate));
				runtimeService.setVariable(instanceId, Constants.DEFAULT_DEADLINE_PROCESS, DateConverter.dateToISO8601(endProcessDate));
				
				try {
					dbInterface.connect(processIdentifier.getType(), processIdentifier.getSemester(), processIdentifier.getYear());
					String sql = "INSERT INTO configurations (name, value) VALUES ('"+Constants.PROCESS_ID_VARIABLE_NAME +"', '"+instanceId+"');";
					dbInterface.executeUpdate(sql);
					successfull = true;
				} catch (SQLTableException e) {
					e.printStackTrace();
				}
			}
			dbInterface.disconnect();
		}
		return successfull;
	}
	
	
	/**
	 * This method changes the specified deadline for the given Process to the given date.
	 * 
	 * If the given dates are in the past or are behind the current dateline, 
	 * the deadline will not be changed.
	 * 
	 * @param processIdentifier ProcessIdentifier (type, semester, year) to get the current process
	 * @param variableName Name of the Variable (in Activiti), which should be changed.
	 * @param newDeadlineDate: The Date to which the deadline should be changed
	 * @return boolean - true if the changing of the deadline was successfull. 
	 * 			false if the new deadline is in the past
	 * 			false if the new deadline is before the old one
	 * 			false if the old deadline is in the past.
	 */
	
	private boolean changeDeadline(ProcessIdentifier processIdentifier, String variableName, Date newDeadlineDate) {
		String executionId = ProcessIdAdministration.getExecutionId(processIdentifier);
		if (executionId == null){
			return false;
		}
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		RuntimeService runtime = processEngine.getRuntimeService();
		String oldDeadline;
		try {
			oldDeadline = (String) runtime.getVariable(executionId, variableName);
		} catch(ActivitiException e) {
			return false;
		}
		Date oldDeadlineDate = DateConverter.ISO8601ToDate(oldDeadline);
		if (newDeadlineDate.before(oldDeadlineDate) || newDeadlineDate.before(new Date()) || oldDeadlineDate.before(new Date())) {
			return false;
		}
		
		//###########################################################
		//# Set new variable in Activiti							#  
		//# Set changed Variable for the Activiti Process,			#
		//#  so that the timer event will check the time again.   	#
		//###########################################################
		processEngine.getRuntimeService().setVariable(executionId, variableName, DateConverter.dateToISO8601(newDeadlineDate));
		processEngine.getRuntimeService().setVariable(executionId, "changed", 1);
		
		return true;
	}
	
	/**
	 * Checks if there is already a Activiti-Process-Instance for the given ProcessIdentifier
	 * 
	 * @param processIdentifier Identifies the type, semester and year which should be checked.
	 * @return 	- true if there is already a Activiti-Process-Instance
	 * 			- false if there is no Activiti-Process-Instance 
	 */
	private boolean hasExistingProcess(ProcessIdentifier processIdentifier){
		boolean hasProcess = false;
		
		//###########################################################
		//# Check in database if there is already a Instance-Id		#
		//###########################################################
		String sql = "SELECT * FROM configurations WHERE name = '"+Constants.PROCESS_ID_VARIABLE_NAME+"';";
		try {
			dbInterface.connect(processIdentifier.getType(), processIdentifier.getSemester(), processIdentifier.getYear());
			ResultSet resultSet = dbInterface.executeQueryDirectly(sql);
			if(resultSet.next()){
				hasProcess = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		dbInterface.disconnect();
		return hasProcess;
	}
	
	/**
	 * Main function of the ProcessAdministration
	 * This function starts a process for the next 25 Minutes
	 * @param args No arguments expected.
	 */
	public static void main(String[] args){
		ProcessIdentifier processIdentifier = new  ProcessIdentifier("Ba", "SS", 2011);
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
		
		System.out.println("Ende Main: "+
				process.startProcess(processIdentifier, 
						"DegreeProjectProcessNew4", 
						deadlineCollection.getTime(),
						deadlineTopics.getTime(), 
						deadlineVoting.getTime(), 
						deadlineMatching.getTime(), 
						deadlineProcess.getTime()));
	} 
}