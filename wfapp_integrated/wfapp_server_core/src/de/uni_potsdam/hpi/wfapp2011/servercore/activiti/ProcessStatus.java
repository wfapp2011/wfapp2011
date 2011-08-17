package de.uni_potsdam.hpi.wfapp2011.servercore.activiti;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.uni_potsdam.hpi.wfapp2011.servercore.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.servercore.constants.ProcessIdAdministration;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;


/**
 * This class provides the functionality to get the current 
 * phase of a given activiti process instance. 
 * 
 * @author Jannik Marten, Yanina Yurchenko
 */
// TODO: This class has to change, if the activiti process is working inside the webapp
public class ProcessStatus implements ProcessStatusInterface {
//	private ProcessEngine processEngine;
	private String executionId;
	private DbInterface db;
	private String type;
	private String semester;
	private int year;
	
	
	public ProcessStatus(String type, String semester, int year){
//		processEngine = ProcessEngines.getDefaultProcessEngine();
		executionId = ProcessIdAdministration.getExecutionId(type, semester, year);
		
		db = new DbInterface();
		this.type = type;
		this.semester = semester;
		this.year = year;
		
	}
	
	public boolean isProjectProposalPhase(){
		// TODO: Replace if Activiti is working inside the webapp
		// return (readProcessPhaseVariable(executionId).equals(Constants.PROPOSALCOL));
		// ckecks the database to see the current process phase
		Date deadline = readDeadlineFromDatabase(Constants.DEADLINE_PROPOSAL_COL);
		if(deadline != null){
			return (new Date()).before(deadline);
		}
		return false;
		
	}

	
	public boolean isFinalTopicDecisionPhase(){
		// TODO: Replace if Activiti is working inside the webapp
		// return (readProcessPhaseVariable(executionId).equals(Constants.TOPICDECISION));
		
		// ckecks the database to see the current process phase
		return isDateBetween(Constants.DEADLINE_PROPOSAL_COL, Constants.DEADLINE_TOPICS_PUBL);
	}
	
	public boolean isVotingPhase(){
		// TODO: Replace if Activiti is working inside the webapp
		// return (readProcessPhaseVariable(executionId).equals(Constants.VOTING));
		
		// ckecks the database to see the current process phase
		return isDateBetween(Constants.DEADLINE_TOPICS_PUBL, Constants.DEADLINE_VOTING);
		
	}
	
	public boolean isProjectMatchingPhase(){
		// TODO: Replace if Activiti is working inside the webapp
		//return (readProcessPhaseVariable(executionId).equals(Constants.PROJECTMATCHING));
		
		// ckecks the database to see the current process phase
		return isDateBetween(Constants.DEADLINE_VOTING, Constants.DEADLINE_MATCHING);
	}
	
	/**
	 * This Method allows to get the current phase of the process instance. 
	 * 
	 * @return String, which identifies the phase. 
	 * 			The exact Strings are  defined in the Constants Class {@link Constants}
	 * 
	 */
	public String getCurrentPhase(){
		return readProcessPhaseVariable(executionId);
	}
	
	/**
	 * This method gets the name of current phase for the given execution-ID.<br/>
	 * If there is no active process instance for the executionId, it will return {@link Constants#PROCESS_NOT_RUNNING}
	 * 
	 * @param executionId : Id of the activiti process instance.<br/>
	 * @return the name of the current phase, specified in {@link Constants} 
	 */
	private String readProcessPhaseVariable(String executionId) {
		// TODO: Replace if Activiti is working inside the webapp
		String phase = "Currently not implemented";
//		try {
//			phase = (String) processEngine.getRuntimeService().getVariable(executionId, Constants.PROCESS_PHASE_VARIABLE_NAME);
//		} catch (ActivitiException e) {
//			phase = Constants.PROCESS_NOT_RUNNING; 
//		}
		return phase;
	}
	
	/**
	 * This methods reads the deadline from the database. <br/>
	 * 
	 * @return the Date of the deadline of the specified deadline type. <br/>
	 * 			returns null, if there is no deadline in database.
	 */
	
	@SuppressWarnings("deprecation")
	private Date readDeadlineFromDatabase(String deadlineType){
		
		try {
			db.connect(type, semester, year);
			String sql = "SELECT NAME, VALUE FROM configurations WHERE NAME = '"+deadlineType+ "'";
			ResultSet result = db.executeQueryDirectly(sql);
			String deadline = null;
			while(result.next()){
				deadline = result.getString(2);
			}
			db.disconnect();
			if(deadline != null) {
				SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_PATTERN, Constants.DATE_LOCALE);
				Date deadlineDate = df.parse(deadline);
				deadlineDate.setHours(23);
				deadlineDate.setMinutes(59);
				deadlineDate.setSeconds(59);
				return deadlineDate;
			}
		} catch (SQLTableException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		db.disconnect();
		return null;
	}
	
	/**
	 * checks if the current Date is between two other dates, which are specified by their keys. <br/>
	 * With this keys the dates are saved in the configurations table in the database.
	 * @param deadlineTypeBeginn : Name of the key for the date at the begin of the period.
	 * @param deadlineTypeEnd : Name of the key for the date at the end of the period.
	 * @return true if the current Date is between the two dates.
	 * 			returns false if the current date is not in the period.
	 * 			returns false if there are no dates for the keys in the database.
	 */
	private boolean isDateBetween(String deadlineTypeBeginn, String deadlineTypeEnd){
		Date begin = readDeadlineFromDatabase(deadlineTypeBeginn);
		Date end = readDeadlineFromDatabase(deadlineTypeEnd);
		Date today = new Date();
		if(begin != null && end != null){
			return (today.after(begin) && today.before(end));
		}
		return false;
	}
	
}