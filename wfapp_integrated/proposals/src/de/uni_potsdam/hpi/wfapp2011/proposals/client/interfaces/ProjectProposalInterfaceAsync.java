package de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.ProjectProposal;

public interface ProjectProposalInterfaceAsync {

	void getProjectProposal(int projectID, AsyncCallback<ProjectProposal> callback);
	void getProjectProposals(AsyncCallback<ArrayList<ProjectProposal>> callback);
	void setVotable(ProjectProposal project, Boolean votable, AsyncCallback<Void> callback);
	void setPublicness(int projectID, Boolean publicness, AsyncCallback<Void> callback);
	
}
