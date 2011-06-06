package de.uni_potsdam.hpi.wfapp2011.activiti;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log implements LogInterface  {
	public FileWriter txtoutput; 
	public PrintWriter outputWriter;
	
	public Log() throws IOException {
		txtoutput = new FileWriter("output.txt");
		outputWriter = new PrintWriter(txtoutput);
	}
	@Override
	public boolean logNewVoting(String userID, String Name, String voting) {
		outputWriter.println(userID+ " "+ Name+ voting);
		return false;
	}

	@Override
	public boolean logChangedVoting(String userID, String userName,
			String voting) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	public static void main(String [ ] args) throws IOException {
		Log hallo = new Log();
		hallo.logNewVoting("1234", "Evening", "Morning");
		hallo.outputWriter.flush();
		hallo.outputWriter.close();
		System.out.println("Hallo");
	}
}