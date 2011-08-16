package de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.ProjectProposal;

public interface ProjectProposalInterface extends RemoteService{

	public ProjectProposal getProjectProposal(int projectID);	
	public ArrayList<ProjectProposal> getProjectProposals();
	public void setVotable(ProjectProposal project, Boolean votable);
	public void setPublicness(int projectID, Boolean publicness);
	
}
