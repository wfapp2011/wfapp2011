package de.uni_potsdam.hpi.wfapp2011.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

public class Test2 {
	
	public static void main(String[] args){
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		processEngine = ProcessEngines.getDefaultProcessEngine();
		ProcessDefinitionQuery query = processEngine.getRepositoryService().
			createProcessDefinitionQuery().processDefinitionName("Pizzabestellung");
		ProcessDefinition testprocess = query.listPage(0, 10).get(0);
		String id = testprocess.getId();
		
		ProcessEngines.getProcessEngine("default").getRuntimeService().startProcessInstanceById(id);
	}
}
