package de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Comment;

public interface CommentInterface extends RemoteService{

	public Comment submitComment(String comment, int proposalId);
	public List<Comment> getComments(int proposalId);
	
}
