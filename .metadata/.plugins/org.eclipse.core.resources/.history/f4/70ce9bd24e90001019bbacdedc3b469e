

import java.io.IOException;
import java.io.PrintWriter;
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
//@WebServlet("/createProject")
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
		
		
		ProjectProposal projectProp = new ProjectProposal(projectName, minStud, maxStud);
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
		
		//TODO save project in activiti-database
		db.addProjectProposal(projectProp);
		
		response.setContentType("text/html");
				
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
                                        "Transitional//EN\">\n" +
                "<HTML>\n" +
                "<HEAD><TITLE>Neues Projekt erstellt:</TITLE></HEAD>\n" +
                "<BODY>\n Neues Projekt erstellt: \n\n  Projektname: ");
		
		out.println(projectProp.getProjectName() + 
				"</BODY></HTML>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
