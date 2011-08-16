package de.uni_potsdam.hpi.wfapp2011.servercore.constants;

import java.util.Locale;

/**
 * Class with static Variables, used in the project.  
 * @author Jannik Marten, Yanina Yurchenko
 *
 */

public class Constants {
	
	//###########################################################
	//# Variable Names for the deadlines in activiti, 			#
	//# which can be changed from outside of activiti			#
	//###########################################################
	public static String START_PROPOSAL_COL_INPUT = "startProposalCollectionInput";
	public static String DEADLINE_PROPOSAL_COL_INPUT = "deadlineProposalCollectionInput";
	public static String DEADLINE_TOPICS_PUBL_INPUT = "deadlineTopicsPublicationInput";
	public static String DEADLINE_VOTING_INPUT = "deadlineVotingInput";
	public static String DEADLINE_MATCHING_INPUT = "deadlineMatchingInput";
	public static String DEADLINE_PROCESS_INPUT = "deadlineProcessInput";
	
	//###########################################################
	//# Variable Names for the deadlines in activiti. These 	#
	//# variables should only set while starting the process.	#
	//###########################################################
	public static String START_PROPOSAL_COL = "startProposalCollection";
	public static String DEADLINE_PROPOSAL_COL = "deadlineProposalCollection";
	public static String DEADLINE_TOPICS_PUBL = "deadlineTopicsPublication";
	public static String DEADLINE_VOTING = "deadlineVoting";
	public static String DEADLINE_PROCESS = "deadlineProcess";
	public static String DEADLINE_MATCHING = "deadlineMatching";
	public static String DEFAULT_DEADLINE_PROCESS = "defaultDeadlineProcess";

	//###########################################################
	//# Names of the process phases. 						 	#
	//###########################################################
	public static String PROPOSALCOL = "ProposalCollection";
	public static String TOPICDECISION = "FinalTopicDecision";
	public static String VOTING = "Voting";
	public static String PROJECTMATCHING = "ProjectMatching";
	public static String SEND_INFORMATIONS = "sendInformations";
	public static String PROCESS_NOT_RUNNING = "noProcessRunning";
	
	/** Key to save the process Instance Id in the database. */
	public static String PROCESS_ID_VARIABLE_NAME = "processInstanceId";
	
	/** Date pattern used to parse a date from the configuration table in the database */
	public static String DATE_PATTERN = "d M yyyy";
	
	/** Locale to parse a date from the configuration table in the database */
	public static Locale DATE_LOCALE = Locale.ENGLISH;

	/** Name of the activiti process. <br/>
	 * Will be used to start an instance of the process. */
	public static String PROCESS_NAME = "DegreeProjectProcessNew4";
	
	
	//###########################################################
	//# Names of variables needed in activiti				 	#
	//###########################################################
	/** Name of the activiti variable, which indicates, if a deadlines is changed <br/>
	 * is 1, if a deadline changed, and 0 otherwise */
	public static String CHANGED_VARIABLE = "changed";
	
	/** Name of the activiti variable, which contains the current phase. */
	public static String PROCESS_PHASE_VARIABLE_NAME = "phase";
	
	/** Name of the variable in activiti, which contains the name of the current phase */
	public static String PROCESS_PHASE = "phase";
	
	/**  Name of the variable in activiti, which contains the first deadline. 
	 * This is used to initialize the other deadlines. */
	public static String DEADLINE_WAITING_SERVICE_TASK = "waitingServiceTaskDeadline";
	
	/** Name of the variable in activiti, which saves the time, to remind the students about their voting */
	public static String EMAIL_REMINDER_DATE = "emailReminderTime";
	
	
	
}
