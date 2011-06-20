package de.uni_potsdam.hpi.wfapp2011;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni_potsdam.hpi.wfapp2011.data.DummyDatabase;
import de.uni_potsdam.hpi.wfapp2011.data.ProjectProposal;

@WebServlet("/AcceptForVoting")
public class AcceptForVoting extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AcceptForVoting() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<ProjectProposal> proposals = DummyDatabase.getInstance().getProjectProposals();
		String projectID = request.getParameter("projectID");
		for (ProjectProposal proposal : proposals){
			if (proposal.toString().equals(projectID)){
				DummyDatabase db = DummyDatabase.getInstance();
				System.out.println(db.isApproved(proposal));
				db.addApprovedProject(proposal);
				System.out.println(proposal.getProjectName());
				System.out.println(db.isApproved(proposal));
			}
		}	
		response.sendRedirect("listAllDepartments.jsp");
	}

}
