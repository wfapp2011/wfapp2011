package de.uni_potsdam.hpi.wfapp2011.proposals.client.serviceproxies;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.ProjectProposal;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.ProjectProposalInterface;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.ProjectProposalInterfaceAsync;
/**
 * ProposalService is a proxy for RPCs concerning proposals.
 * 
 * @author Katrin Honauer, Josefine Harzmann
 */
public class ProposalService implements ProjectProposalInterfaceAsync {

	ProjectProposalInterfaceAsync proposalProvider = (ProjectProposalInterfaceAsync) GWT.create(ProjectProposalInterface.class);
	ServiceDefTarget endpoint = (ServiceDefTarget) proposalProvider;

	public ProposalService() {
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL()+ "projectProposal");
	}

	public void getProjectProposal(int projectID, AsyncCallback<ProjectProposal> callback) {
		proposalProvider.getProjectProposal(projectID, callback);
	}
	
	public void getProjectProposals(AsyncCallback<ArrayList<ProjectProposal>> callback) {
		proposalProvider.getProjectProposals(callback);
	} 

	public void setVotable(ProjectProposal project, Boolean votable, AsyncCallback<Void> callback) {
		proposalProvider.setVotable(project, votable, callback);		
	}

	public void setPublicness(int projectID, Boolean publicness, AsyncCallback<Void> callback) {
		proposalProvider.setPublicness(projectID, publicness, callback);	
	}

	public void getPDF(ProjectProposal proposal,
			AsyncCallback<Void> asyncCallback) {
		proposalProvider.getPDF(proposal, asyncCallback);
		
	}
}
