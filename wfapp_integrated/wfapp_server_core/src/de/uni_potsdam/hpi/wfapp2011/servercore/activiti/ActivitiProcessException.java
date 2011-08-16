package de.uni_potsdam.hpi.wfapp2011.servercore.activiti;

/**
 * This exception will be thrown, if an activiti process instance can't be started. 
 * @author Jannik Marten, Yanina Yurchenko
 *
 */

public class ActivitiProcessException extends Exception {

	private static final long serialVersionUID = 5355955316970287131L;
	String errorMessage;
	
	public ActivitiProcessException(String error){
		errorMessage = error;
	}
	
	public String getErrorMessage(){
		return errorMessage;
	}
}
