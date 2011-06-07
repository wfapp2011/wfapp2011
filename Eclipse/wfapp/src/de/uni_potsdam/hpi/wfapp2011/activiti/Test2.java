package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class Test2 {
	
	public void testconnection() {
	try {	
	DefaultHttpClient dhc = new DefaultHttpClient();
	dhc.getCredentialsProvider().setCredentials(new AuthScope("localhost", 8080), new UsernamePasswordCredentials("kermit", "kermit"));
	HttpPost hp = new HttpPost("http://localhost:8080/activiti-rest/service/process-instance");
	hp.setEntity(new StringEntity("{\"processDefinitionId\":\"Pizzabestellung:1:118\"}", "UTF-8"));
	HttpResponse processResponse = dhc.execute(hp);
	System.out.println(IOUtils.toString(processResponse.getEntity().getContent()));
	dhc.getConnectionManager().shutdown();
	}
	catch (Exception e){
		e.printStackTrace();
	}
	
	}
	public static void main(String[] args){
		ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
		
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionName("Pizzabestellung");
		List<ProcessDefinition> list = query.listPage(0, 10);
		ProcessDefinition testprocess = list.get(0);
		String id = testprocess.getId();
		ProcessEngines.getProcessEngine("default").getRuntimeService().startProcessInstanceById(id);
		//Test2 test = new Test2();
		//test.testconnection();
	}
}
