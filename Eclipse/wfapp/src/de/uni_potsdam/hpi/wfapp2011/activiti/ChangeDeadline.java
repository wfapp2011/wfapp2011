package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

public class ChangeDeadline {
	public void changeDeadline(String executionId, String variableName, Date newDeadline) {
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		processEngine.getRuntimeService().setVariable(executionId, variableName, dateToISO8601(newDeadline));
		processEngine.getRuntimeService().setVariable(executionId, "changed", 1);
		processEngine.close();

	}
	
	public String dateToISO8601(Date date) {
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return iso.format(date);
	}
	
	public static void main(String[] args){
		ChangeDeadline activiti = new ChangeDeadline();
		Date date = new Date();
		date.setMinutes(date.getMinutes()+5);
		activiti.changeDeadline("810", "deadlineProposalCollectionInput", date );
	}
}
