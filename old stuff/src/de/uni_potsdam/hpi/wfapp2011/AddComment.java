package de.uni_potsdam.hpi.wfapp2011;


import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni_potsdam.hpi.wfapp2011.data.*;

@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DummyDatabase db = DummyDatabase.getInstance();
		String proposalID = request.getParameter("projectID");
		ProjectProposal proposal = db.getProposal(proposalID);
		ArrayList<Comment> comments = proposal.getComments();
		Comment newComment = new Comment();
		// Author setzen!!!
		newComment.setAuthor(new Person("testPerson", "a@b.com"));
		newComment.setMessage(request.getParameter("commentMessage"));
		comments.add(newComment);
		proposal.setComments(comments);
		response.sendRedirect("showProject.jsp?projectID=" + proposalID);		
	}

}
