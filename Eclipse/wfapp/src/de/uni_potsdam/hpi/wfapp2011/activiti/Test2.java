package de.uni_potsdam.hpi.wfapp2011.activiti;

import org.activiti.engine.ProcessEngines;
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
		ProcessEngines.getProcessEngine("default").getRuntimeService().startProcessInstanceById("Pizzabestellung:1:118");
		//Test2 test = new Test2();
		//test.testconnection();
	}
}
