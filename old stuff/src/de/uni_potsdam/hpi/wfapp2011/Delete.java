package de.uni_potsdam.hpi.wfapp2011;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni_potsdam.hpi.wfapp2011.data.*;


@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Delete() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DummyDatabase db = DummyDatabase.getInstance();
		String proposalToDelete = request.getParameter("projectID");
		db.deleteProjectProposal(proposalToDelete);
		response.sendRedirect("listOwnDepartment.jsp");
	}

}
