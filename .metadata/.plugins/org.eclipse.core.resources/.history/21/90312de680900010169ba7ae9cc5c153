

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.*;

/**
 * Servlet implementation class createProject
 */
@WebServlet("/createProject")
public class CreateProject extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	DummyDatabase db = DummyDatabase.getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateProject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Read input from form
		// TODO validate input!
		String projectName = (request.getParameter("projectName"));		
		String projectDescription = (request.getParameter("projectDescription"));
		String keywords = (request.getParameter("keywords"));
		Integer minStud = Integer.parseInt(request.getParameter("minStud"));
		Integer maxStud = Integer.parseInt(request.getParameter("maxStud"));
		String partnerName = (request.getParameter("partnerName"));
		String partnerDescription = (request.getParameter("partnerName"));
		// TODO handle multiple contactPersons
		
		String contactPersonName = (request.getParameter("contactPersonName"));
		String contactPersonEmail = (request.getParameter("contactPersonEmail"));
		// TODO create datepicker
		//Date estimatedBegin = (request.getParameter("estimatedBegin"));
				
		// Create new ProjectProposal
		ProjectProposal projectProp = new ProjectProposal(projectName, minStud, maxStud);
		projectProp.setProjectDescription(projectDescription);
		projectProp.setKeywords(keywords);
		projectProp.setPartnerName(partnerName);
		projectProp.setPartnerDescription(partnerDescription);
		
		
		Person contactPerson1 = new Person(contactPersonName, contactPersonEmail);
		ArrayList<Person> contactPersons = new ArrayList<Person>();
		contactPersons.add(contactPerson1);
		projectProp.setContactPersons(contactPersons);
		projectProp.setLastModifiedAt(new Date());
		// this should be done according to user logged in
		projectProp.setDepartment(db.getDepartments()[(new Random().nextInt(db.getDepartments().length))]);
		projectProp.setLastModifiedBy(new Person("unbekannter Name", "unbekannt@hpi-web.de"));
		
		
		// TODO get from Database/Engine 
		Department dep = new Department("Business Process Technology");
		Person prof = new Person("Prof. Weske", "mathias.weske@hpi.uni-potsdam.de");
		dep.setProf(prof);
		projectProp.setDepartment(dep);
		
				
		//TODO save project in activiti-database
		db.addProjectProposal(projectProp);

		response.sendRedirect("listOwnDepartment.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
