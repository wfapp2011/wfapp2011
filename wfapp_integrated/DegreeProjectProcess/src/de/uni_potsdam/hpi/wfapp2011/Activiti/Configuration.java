package de.uni_potsdam.hpi.wfapp2011.Activiti;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import de.uni_potsdam.hpi.wfapp2011.servercore.constants.Constants;

/**
 * <code> Configuration </code> is a Activiti Service Task.
 * It initializes all required process instance variables, needed for the deadlines. 
 * @author Jannik Marten, Yanina Yurchenko
 *
 */

public class Configuration implements JavaDelegate{

	/**
	 * this method will initialize the deadlines for the activiti process instance with a initDate,
	 * which will be 30 seconds in future. The real dates will be set by {@link ProcessAdministration}
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Start Configuration"+ new Date().toString());
		GregorianCalendar initializeDate = new GregorianCalendar();
		initializeDate.add(Calendar.SECOND, 30);
		String initDate = dateToISO8601(initializeDate);
		
		// initialize variables used for the activiti process
		
		//Variable to decide, if the time changed, while the timer event was already active. 
		execution.setVariable("changed", 0);
		
		execution.setVariable(Constants.DEADLINE_WAITING_SERVICE_TASK, initDate);
		execution.setVariable(Constants.DEFAULT_DEADLINE_PROCESS, initDate);	
		
		execution.setVariable(Constants.START_PROPOSAL_COL_INPUT, initDate);
		execution.setVariable(Constants.START_PROPOSAL_COL, initDate);

		execution.setVariable(Constants.DEADLINE_PROPOSAL_COL_INPUT, initDate);
		execution.setVariable(Constants.DEADLINE_PROPOSAL_COL, initDate);

		execution.setVariable(Constants.DEADLINE_TOPICS_PUBL_INPUT, initDate);
		execution.setVariable(Constants.DEADLINE_TOPICS_PUBL, initDate);
		
		execution.setVariable(Constants.DEADLINE_VOTING_INPUT, initDate);	
		execution.setVariable(Constants.DEADLINE_VOTING, initDate);	

		execution.setVariable(Constants.DEADLINE_MATCHING_INPUT, initDate);
		execution.setVariable(Constants.DEADLINE_MATCHING, initDate);
		
		execution.setVariable(Constants.DEADLINE_PROCESS, initDate);
		
		execution.setVariable(Constants.EMAIL_REMINDER_DATE, initDate);
		
	}
	
	/**
	 * Converts a date of type GregorianCalendar into a Date-String in ISO8601-Format
	 * @param date : the date which should be converted
	 * @return String of the given date in ISO8601-format.
	 */
	public String dateToISO8601(GregorianCalendar date) {
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return iso.format(date.getTime());
	}
}
