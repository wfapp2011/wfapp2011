package de.uni_potsdam.hpi.wfapp2011.Activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import de.uni_potsdam.hpi.wfapp2011.servercore.constants.Constants;
/**
 * This class is updating all necessary informations, when entering the Send Informations phase
 * @author Jannik Marten, Yanina Yurchenko
 *
 */
public class SendInformations implements JavaDelegate {

	/**
	 * This method is called from the activiti engine. <br/>
	 * It will reset the change variable of activiti, indicating, that the deadlines are the same.<br/>
	 * It will set the phase variable in activiti to the name of the current phase (Send Informations).
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable(Constants.CHANGED_VARIABLE, 0);
		execution.setVariable(Constants.PROCESS_PHASE, Constants.SEND_INFORMATIONS);
		
	}

}
