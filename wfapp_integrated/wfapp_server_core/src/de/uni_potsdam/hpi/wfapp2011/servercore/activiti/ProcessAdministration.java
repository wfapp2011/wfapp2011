package de.uni_potsdam.hpi.wfapp2011.servercore.activiti;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import de.uni_potsdam.hpi.wfapp2011.servercore.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.servercore.constants.ProcessIdAdministration;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.general.DateConverter;



/**
 * This class allows to start a activiti process instance with the given deadlines.
 * The deadlines can be changed as well using this class. 
 * 
 * @author Jannik Marten, Yanina Yurchenko
 * @see ProcessAdministrationInterface
 *
 */

public class ProcessAdministration implements ProcessAdministrationInterface {
	private static ProcessAdministration theInstance = null;
	private static DbInterface dbInterface;
	private static boolean debug = false;
	
	private ProcessAdministration() {
		// Implementation as a Singleton!
		// So do not allow to create instances of this class
	}
	
	/**
	 * get the Singleton Instance of ProcessAdministration.<br/>
	 * Creates an Instance if it doesn't exist yet. 
	 * 
	 * @return the++- -Instance of ProcessAdministration
	 */
	public synchronized static ProcessAdministration getInstance() {
		if(theInstance == null){
			theInstance = new ProcessAdministration();
			dbInterface = new DbInterface();
		}
		return theInstance;
	}
	
	/**
	 * Starts an Activiti-Process-Instance for the given ProcessIdentifier. <br/>
	 * Therefore it loads the deadlines from the database. 
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series 
	 * @return boolean, which indicates, if the process could be successfully started <br/>
	 * 				returns false, if dates are missing, or in the wrong order.
	 * @throws ActivitiProcessException will be thrown if an Activiti-Process-Instance 
	 * 			for the given ProcessIdentifier already exists. 
	 */
	public boolean startProcess(String type, String semester, int year) throws ActivitiProcessException {
		boolean result = false;

		if(hasExistingProcess(type, semester, year)){
			throw new ActivitiProcessException("Process already exists");
		}
		else {
			HashMap<String, Date> mapDeadlines = loadDeadlinesFromDatabase(type, semester, year);
			result = startProcess(type, semester, year,
					Constants.PROCESS_NAME,
					(Date)mapDeadlines.get(Constants.DEADLINE_PROPOSAL_COL),
					(Date)mapDeadlines.get(Constants.DEADLINE_TOPICS_PUBL),
					(Date)mapDeadlines.get(Constants.DEADLINE_VOTING),
					(Date)mapDeadlines.get(Constants.DEADLINE_MATCHING),
					(Date)mapDeadlines.get(Constants.DEADLINE_PROCESS));
		}
		return result;
	}

	/**
	 * Loads the deadlines for the given process.<br/>
	 * All deadlines have an end time of 23:59:59. <br/>
	 * If an deadline isn't set in the database the result will not contain this deadline. <br/>
	 * So the returned Hashmap will be empty if no deadlines exists in database.
	 * The deadlines in the database has to have the format specified in {@link Constants#DATE_PATTERN} 
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series 
	 * @return Hashmap<String, Date> with the name of the deadline specified in the 
	 * 			class {@link Constants} Constants as key and the date as value.
	 */
	@SuppressWarnings("deprecation")
	public HashMap<String, Date> loadDeadlinesFromDatabase(
			String type, String semester, int year) {
		//##################################################
		//# Look up the deadlines set in the database      #
		//##################################################
		HashMap<String, Date> mapDeadlines = new HashMap<String, Date>();
		String sql = "SELECT * FROM configurations WHERE name = '"+ Constants.DEADLINE_PROPOSAL_COL+"' OR " +
				"name = '"+ Constants.DEADLINE_TOPICS_PUBL+"' OR name = '"+Constants.DEADLINE_VOTING+"' OR name = '"+
				Constants.DEADLINE_MATCHING+"' OR name = '"+Constants.DEADLINE_PROCESS+"'";
		
		try {
			dbInterface.connect(type, semester, year);
			ResultSet resultSet = dbInterface.executeQueryDirectly(sql);
			while(resultSet.next()){
				//###############################################				
				//# Parse String into Date     					#
				//###############################################
				DateFormat df = new SimpleDateFormat(Constants.DATE_PATTERN, Constants.DATE_LOCALE);
				String deadlineString = resultSet.getString(2);
				if(debug) System.out.println("Deadlines are read: "+ deadlineString);
				Date deadline = df.parse(deadlineString);
				if(debug) System.out.println("Deadlines are successfully parsed");
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
		if(debug) System.out.println("Map with deadlines: "+mapDeadlines.toString());
		return mapDeadlines;
	}
	
	
	/**
	 * This Method updates the deadlines for the Activiti-Process <br/>
	 * Therefore it loads the deadlines from the database using {@link ProcessAdministration#loadDeadlinesFromDatabase(String, String, int)} <br/>
	 * Only if the new deadlines are in the future and are further than the current deadlines, it will be changed. <br/>
	 * <b>!!! The method does not check, if the deadlines are in the correct order. </b>
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series 
	 * @return boolean[], which indicates which deadlines could be changed successfully.
	 * 			- false if the newDeadlines is in the past.
	 * 			- 		if the newDeadline is before the oldDeadline
	 * 			-		if the oldDeadline is in the past.  
	 */
	public boolean[] changedDeadlines(String type, String semester, int year){
		HashMap<String, Date> mapDeadlines = loadDeadlinesFromDatabase(type, semester, year);
		boolean[] success = new boolean[5];
		Date endProposalDate = mapDeadlines.get(Constants.DEADLINE_PROPOSAL_COL);
		Date endTopicPublication = mapDeadlines.get(Constants.DEADLINE_TOPICS_PUBL);
		Date endVotingDate = mapDeadlines.get(Constants.DEADLINE_VOTING);
		Date endMatchingDate = mapDeadlines.get(Constants.DEADLINE_MATCHING);
		Date endProcessDate = mapDeadlines.get(Constants.DEADLINE_PROCESS);
		
		success[0] = changeDeadline(type, semester,  year, Constants.DEADLINE_PROPOSAL_COL_INPUT, endProposalDate);
		success[1] = changeDeadline(type, semester, year, Constants.DEADLINE_TOPICS_PUBL_INPUT, endTopicPublication);
		success[2] = changeDeadline(type, semester, year, Constants.DEADLINE_VOTING_INPUT, endVotingDate);
		success[3] = changeDeadline(type, semester, year, Constants.DEADLINE_MATCHING_INPUT, endMatchingDate);
		success[4] = changeDeadline(type, semester, year, Constants.DEADLINE_PROCESS_INPUT, endProcessDate);
		return success;
	}

	/** 
	 * This method starts a process instance of the given ProcessName.<br/>
	 * If one of the parameters is null, the process cannot be started. <br/>
	 * It uses the default process engine, which will be identified using the 
	 * activiti.cfg.xml on the classpath see {@link www.activiti.org/} <br/>
	 * <b> This function is still mockuped. So it will even return true, if not activiti-engine can be found </b>
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series 
	 * @param processName : The name of the process in activiti, which should be started. 
	 * @param endProposalDate : date/time, when the Proposal Collection Phase will finish.
	 * @param startVotingDate : date/time up to which the final Topics have to be choosen and the voting phase starts.
	 * @param endVotingDate : date/time, which specifies the end of the voting phase
	 * @param endMatchingDate : date/time up to which the project matching has to be finished. 
	 * @param endProcessDate : date/time to finish the whole process. 
	 * 
	 * @return boolean, which indicates, if the process was successfully started. <br/>
	 * 				<b> Please note: Mockuped, so that it will even return true, if no activiti engine can be found.</b>
	 */
	@SuppressWarnings("unused")
	public boolean startProcess(
			String type, String semester, int year,
			String processName, 
			Date endProposalDate, 
			Date startVotingDate, 
			Date endVotingDate, 
			Date endMatchingDate,
			Date endProcessDate)
	{
		boolean successfull = false;
		String instanceId = null;
		
		if(type == null || semester == null || processName == null || 
				endProcessDate == null || startVotingDate == null || endVotingDate == null || 
				endMatchingDate == null || endProcessDate == null) {
			if(debug) System.out.println("Couldn't start process, because a parameter was null");
			return successfull;
		}
			
		try {
			//###################################################
			//# Get the default Process Engine of Activiti. 	#
			//# Therefore the activiti.cfg.xml from Activiti 	#
			//# has to be on the Classpath						#
			//###################################################
			ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
			if(processEngine == null) {System.out.println("Can't get ProcessEngine"); return false;}
			RuntimeService runtimeService = processEngine.getRuntimeService();
			
			//###########################################################
			//# Get all process definitions with the given process name	#
			//###########################################################
			ProcessDefinitionQuery query = processEngine.getRepositoryService().
						createProcessDefinitionQuery().processDefinitionName(processName);
			
			//###########################################################
			//# Get only the latest process definition 					#
			//###########################################################
			ProcessDefinition processDefinition = query.orderByProcessDefinitionVersion().
						desc().listPage(0, 10).get(0);
			String id = processDefinition.getId();
			
			//###########################################################
			//#    make sure that the dates have the correct order.		#
			//###########################################################
			if (	endProposalDate.before(startVotingDate) && 
					startVotingDate.before(endVotingDate) && 
					endVotingDate.before(endMatchingDate) &&
					endMatchingDate.before(endProcessDate))
			{
			// TODO: Remove comments from the following block, if activiti is working
//				ProcessInstance processInstance = processEngine.
//							getRuntimeService().startProcessInstanceById(id);
//				instanceId = processInstance.getId();
//				if (instanceId != null){
//					//###########################################################
//					//# Setting the deadlines, which will be changeable 		#
//					//# from outside the process. 								#
//					//###########################################################
//					runtimeService.setVariable(instanceId, Constants.DEADLINE_PROPOSAL_COL_INPUT, DateConverter.dateToISO8601(endProposalDate));
//					runtimeService.setVariable(instanceId, Constants.DEADLINE_TOPICS_PUBL_INPUT, DateConverter.dateToISO8601(startVotingDate));
//					runtimeService.setVariable(instanceId, Constants.DEADLINE_VOTING_INPUT, DateConverter.dateToISO8601(endVotingDate));
//					runtimeService.setVariable(instanceId, Constants.DEADLINE_MATCHING_INPUT, DateConverter.dateToISO8601(endMatchingDate));
//					runtimeService.setVariable(instanceId, Constants.DEADLINE_PROCESS_INPUT, DateConverter.dateToISO8601(endProcessDate));
//					
//					//###########################################################
//					//#	Setting the deadlines used for the timeEvents. 			#
//					//# Changes of these variables are not allowed.				#
//					//# Changes will be done indirectly through a service task	#
//					//###########################################################
//					runtimeService.setVariable(instanceId, Constants.DEADLINE_PROPOSAL_COL, DateConverter.dateToISO8601(endProposalDate));
//					runtimeService.setVariable(instanceId, Constants.DEADLINE_TOPICS_PUBL, DateConverter.dateToISO8601(startVotingDate));
//					runtimeService.setVariable(instanceId, Constants.DEADLINE_VOTING, DateConverter.dateToISO8601(endVotingDate));
//					runtimeService.setVariable(instanceId, Constants.DEADLINE_MATCHING, DateConverter.dateToISO8601(endMatchingDate));
//					
//					// Set the time of the email Reminder Deadline to one week, before the voting phase ends. 
//					Date emailReminderDeadline = endVotingDate;
//					emailReminderDeadline.setTime((endVotingDate.getTime()) - 604800000); // number of milliseconds for one week. 
//					runtimeService.setVariable(instanceId, Constants.EMAIL_REMINDER_DATE, DateConverter.dateToISO8601(emailReminderDeadline));
//					
//					//###########################################################
//					//# Setting the Default Deadline of the whole process.		#
//					//# Used for the boundary Events							#
//					//###########################################################
//					runtimeService.setVariable(instanceId, Constants.DEADLINE_PROCESS, DateConverter.dateToISO8601(endProcessDate));
//					runtimeService.setVariable(instanceId, Constants.DEFAULT_DEADLINE_PROCESS, DateConverter.dateToISO8601(endProcessDate));
//					
//					try {
//						//###########################################################
//						//# 	Write process instance ID into database				#
//						//###########################################################
//						dbInterface.connect(type, semester, year);
//						String sql = "INSERT INTO configurations (name, value) VALUES ('"+Constants.PROCESS_ID_VARIABLE_NAME +"', '"+instanceId+"');";
//						dbInterface.executeUpdate(sql);
//						successfull = true;
//					} catch (SQLTableException e) {
//						e.printStackTrace();
//					}
//					dbInterface.disconnect();
//				}
					
			}
		} catch(ActivitiException e) {
			e.printStackTrace();
			System.out.println("Activiti Process exception, while trying to start an instance of the activiti-process");
		} 
		// TODO: Remove NullPointer catch, if Activiti engine can be accessed from webapp.
		  catch(NullPointerException e1){
			return true;
		}
		
		return successfull;
	}
	
	
	/**
	 * This method changes the specified deadline for the given Process to the given date. <br/>
	 * <b>NOTE: This method is still mockuped. So that it will even work, if no activiti process engine can be found </b>
	 * 
	 * If the given dates are in the past or behind the current deadline, 
	 * the deadline will not be changed.
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series 
	 * @param variableName : Name of the Variable (in Activiti), which should be changed.
	 * @param newDeadlineDate : The Date to which the deadline should be changed
	 * @return boolean - true if the changing of the deadline was successfully changed. 
	 * 			false if the new deadline is in the past
	 * 			false if the new deadline is before the old one
	 * 			false if the old deadline is in the past.
	 */
	
	private boolean changeDeadline(String type, String semester, int year, String variableName, Date newDeadlineDate) {
		String executionId = ProcessIdAdministration.getExecutionId(type, semester, year);
		if (executionId == null){
			return false;
		}
		try {
			ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
			RuntimeService runtime = processEngine.getRuntimeService();
			String oldDeadline;
			
			try {
				//###########################################################
				//# Get old deadline from the Activiti process instance		#
				//###########################################################
				oldDeadline = (String) runtime.getVariable(executionId, variableName);
			} catch(ActivitiException e) {
				// can't change the deadline, if it can't get the old one from activiti
				return false;
			}
			Date oldDeadlineDate = DateConverter.ISO8601ToDate(oldDeadline);
			if (newDeadlineDate.before(oldDeadlineDate) || newDeadlineDate.before(new Date()) || oldDeadlineDate.before(new Date())) {
				return false;
			}
			
			//###########################################################
			//# Set new variable (deadline) in Activiti					#  
			//# Set changed Variable for the Activiti Process,			#
			//#  so that the timer event will check the time again.   	#
			//###########################################################
			processEngine.getRuntimeService().setVariable(executionId, variableName, DateConverter.dateToISO8601(newDeadlineDate));
			processEngine.getRuntimeService().setVariable(executionId, "changed", 1);
		} //TODO: Remove NullPointer catch, if Activiti engine can be accessed from webapp. 
		  catch(NullPointerException e1) {
			return true;
		}	
		
		return true;
	}
	
	/**
	 * Checks if there is already a Activiti-Process-Instance. <br/>
	 * Therefore it checks the database, if there is an entry 
	 * for the processId in the configuration table.
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series 
	 * @param processIdentifier Identifies the type, semester and year which should be checked.
	 * @return 	- true if there is already a Activiti-Process-Instance
	 * 			- false if there is no Activiti-Process-Instance 
	 */
	private boolean hasExistingProcess(String type, String semester, int year){
		boolean hasProcess = false;
		
		//###########################################################
		//# Check in database if there is already a Instance-Id		#
		//###########################################################
		String sql = "SELECT * FROM configurations WHERE name = '"+Constants.PROCESS_ID_VARIABLE_NAME+"';";
		try {
			dbInterface.connect(  type, semester, year);
			//###########################################################
			//# Check, if a process id, is in the database				#
			//# otherwise the activiti process has not started yet. 	#
			//###########################################################
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
}