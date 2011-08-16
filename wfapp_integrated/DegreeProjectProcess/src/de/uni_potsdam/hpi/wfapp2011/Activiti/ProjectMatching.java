package de.uni_potsdam.hpi.wfapp2011.Activiti;

/**
 * This class is updating all necessary informations, when entering the Project Matching phase
 * @author Jannik Marten, Yanina Yurchenko
 *
 */
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import de.uni_potsdam.hpi.wfapp2011.servercore.constants.Constants;

public class ProjectMatching implements JavaDelegate {

	/**
	 * This method is called from the activiti engine. <br/>
	 * It will copy the deadline of the input variable into the deadline variable used for the activiti timer events.<br/>
	 * It will reset the changed variable in activiti, indicating, that the both variables containing the deadlines have the same deadline. <br/>
	 * It will set the phase variable in activiti to the name of the current phase (Project Matching).
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String deadline = (String)execution.getVariable(Constants.DEADLINE_MATCHING_INPUT);
		execution.setVariable(Constants.DEADLINE_MATCHING, deadline);
		execution.setVariable(Constants.CHANGED_VARIABLE, 0);
		execution.setVariable(Constants.PROCESS_PHASE, Constants.PROJECTMATCHING);
		
	}
	
}
