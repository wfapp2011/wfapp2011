package de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Comment;
/**
 * @author Katrin Honauer, Josefine Harzmann
 */
public interface CommentInterfaceAsync {

	void submitComment(String comment, int proposalId, AsyncCallback<Comment> callback);
	void getComments(int proposalId, AsyncCallback<List<Comment>> callback);

}
