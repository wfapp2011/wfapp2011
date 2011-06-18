package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
/*import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.h2.util.IOUtils;
import org.restlet.engine.http.HttpResponse;
*/
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String configuredProcessEngineName = "Test";
		ProcessEngine hallo = ProcessEngines.getProcessEngine(configuredProcessEngineName);
		String test = new String("a");

	}
	public void testStartProcess() {
	 /*  try {
		   DefaultHttpClient dhc = new DefaultHttpClient();
		   HttpPost hp = new HttpPost("http://localhost:8080/activiti-rest/service/process-instance");
		   hp.setEntity(new StringEntity("{\"processDefinitionId\":\"helloworld:1\"}", "UTF-8"));
		   HttpResponse processResponse = (HttpResponse) dhc.execute(hp);
		 //  System.out.println(IOUtils.toString(processResponse.getEntity().getContent()));
		  // dhc.getConnectionManager().shutdown();*/
	}
	
	public static String httpPost(String urlStr, String[] paramName,
			String[] paramVal) throws Exception {
			  URL url = new URL(urlStr);
			  HttpURLConnection conn =
			      (HttpURLConnection) url.openConnection();
			  conn.setRequestMethod("POST");
			  conn.setDoOutput(true);
			  conn.setDoInput(true);
			  conn.setUseCaches(false);
			  conn.setAllowUserInteraction(false);
			  conn.setRequestProperty("Content-Type",
			      "application/x-www-form-urlencoded");

			  // Create the form content
			  OutputStream out = conn.getOutputStream();
			  Writer writer = new OutputStreamWriter(out, "UTF-8");
			  for (int i = 0; i < paramName.length; i++) {
			    writer.write(paramName[i]);
			    writer.write("=");
			    writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
			    writer.write("&");
			  }
			  writer.close();
			  out.close();

			  if (conn.getResponseCode() != 200) {
			    throw new IOException(conn.getResponseMessage());
			  }

			  // Buffer the result into a string
			  BufferedReader rd = new BufferedReader(
			      new InputStreamReader(conn.getInputStream()));
			  StringBuilder sb = new StringBuilder();
			  String line;
			  while ((line = rd.readLine()) != null) {
			    sb.append(line);
			  }
			  rd.close();

			  conn.disconnect();
			  return sb.toString();
			}

}
