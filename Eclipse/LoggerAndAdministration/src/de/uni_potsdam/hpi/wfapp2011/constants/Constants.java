package de.uni_potsdam.hpi.wfapp2011.constants;

public class Constants {
	
	//###########################################################
	//# Variable Names for the deadlines in activiti, 			#
	//# which can be changed from outside of activiti			#
	//###########################################################
	public static String DEADLINE_PROPOSAL_COL_INPUT = "deadlineProposalCollectionInput";
	public static String DEADLINE_TOPICS_PUBL_INPUT = "deadlineTopicsPublicationInput";
	public static String DEADLINE_VOTING_INPUT = "deadlineVotingInput";
	public static String DEADLINE_MATCHING_INPUT = "deadlineMatchingInput";
	public static String DEADLINE_PROCESS_INPUT = "deadlineProcessInput";
	
	//###########################################################
	//# Variable Names for the deadlines in activiti. These 	#
	//# variables should only set while starting the process.	#
	//###########################################################
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
	public static String PROCESS_NOT_RUNNING = "noProcessRunning";
	
	//###########################################################
	//# key to save the process Instance Id in the database	 	#
	//###########################################################
	public static String PROCESS_ID_VARIABLE_NAME = "processInstanceId";
	
	
	
	public static String PROCESS_PHASE_VARIABLE_NAME = "phase";
	public static String DATE_ERROR = "DateError";
	public static String PROCESS_NAME = "DegreeProjectProcessNew4";

	
	
}
