package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

import de.uni_potsdam.hpi.wfapp2011.general.DateConverter;

public class ChangeDeadline {
	public void changeDeadline(String executionId, String variableName, GregorianCalendar newDeadline) {
		
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		processEngine.getRuntimeService().setVariable(executionId, variableName, DateConverter.dateToISO8601(newDeadline));
		//Set changed Variable for the Activiti Process, so that the timer event will check the time again.
		processEngine.getRuntimeService().setVariable(executionId, "changed", 1);
		processEngine.close();

	}
	
	public static void main(String[] args){
		ChangeDeadline activiti = new ChangeDeadline();
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MINUTE, 5);
		activiti.changeDeadline("810", "deadlineProposalCollectionInput", date );
	}
}
