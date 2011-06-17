package de.uni_potsdam.hpi.wfapp2011;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni_potsdam.hpi.wfapp2011.data.*;

@WebServlet("/SetPublicness")
public class SetPublicness extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetPublicness() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<ProjectProposal> proposals = DummyDatabase.getInstance().getProjectProposals();
		String projectID = request.getParameter("projectID");
		String checked = request.getParameter("checked");
		for (ProjectProposal proposal : proposals){
			if (proposal.toString().equals(projectID)){
				proposal.setIsPublic(Boolean.valueOf(checked));
			}
		}
		response.sendRedirect("listOwnDepartment.jsp");
	}

}
