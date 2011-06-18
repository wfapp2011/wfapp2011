package de.uni_potsdam.hpi.wfapp2011.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

public class ActivitiConnection {

	
	public String readVariable(String executionId, String variableName) {
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		String phase = (String) processEngine.getRuntimeService().getVariable(executionId, variableName);
		processEngine.close();
		return phase;

	}
	
	public static void main(String[] args){
		ActivitiConnection activiti = new ActivitiConnection();
		System.out.println(activiti.readVariable("510", "phase"));
	}
}
