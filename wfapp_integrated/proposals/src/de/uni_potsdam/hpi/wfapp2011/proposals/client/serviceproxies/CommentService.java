package de.uni_potsdam.hpi.wfapp2011.proposals.client.serviceproxies;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Comment;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.CommentInterface;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.CommentInterfaceAsync;

public class CommentService implements CommentInterfaceAsync {

	CommentInterfaceAsync commentProvider = (CommentInterfaceAsync) GWT.create(CommentInterface.class);
	ServiceDefTarget endpoint = (ServiceDefTarget) commentProvider;

	public CommentService() {
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL()+ "comment");
	}

	public void submitComment(String comment, int proposalId, AsyncCallback<Comment> callback){
		commentProvider.submitComment(comment, proposalId, callback);
	}
	
	public void getComments(int proposalId, AsyncCallback<List<Comment>> callback){
		commentProvider.getComments(proposalId, callback);	
	}
}
