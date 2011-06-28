package de.uni_potsdam.hpi.wfapp2011.server.activiti;

public class ActivitiProcessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String errorMessage;
	
	public ActivitiProcessException(String error){
		errorMessage = error;
	}
	
	public String getErrorMessage(){
		return errorMessage;
	}
}
