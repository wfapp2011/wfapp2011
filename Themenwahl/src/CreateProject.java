

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	static String DATE_FORMAT = "yyyy-MM-dd";
       
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
		
		// Create new project proposal and set fields
		ProjectProposal projectProp = new ProjectProposal();
		
		String projectName = (request.getParameter("projectName"));		
		String projectDescription = (request.getParameter("projectDescription"));
		String keywords = (request.getParameter("keywords"));
		String partnerName = (request.getParameter("partnerName"));
		String partnerDescription = (request.getParameter("partnerDescription"));
		Integer minStud = Integer.parseInt(request.getParameter("minStud"));
		Integer maxStud = Integer.parseInt(request.getParameter("maxStud"));
		
		projectProp.setProjectName(projectName);
		projectProp.setProjectDescription(projectDescription);
		projectProp.setKeywords(keywords);
		projectProp.setPartnerName(partnerName);
		projectProp.setPartnerDescription(partnerDescription);
		projectProp.setMinStud(minStud);
		projectProp.setMaxStud(maxStud);
		
	
		// Add contact persons
		ArrayList<Person> contactPersons = new ArrayList<Person>();
		Integer maxNumberOfContactPersons = Integer.parseInt(request.getParameter("maxNumberOfContactPersons"));
		String contactPersonName, contactPersonEmail;
		
		for (int i=1; i<=maxNumberOfContactPersons; i++){
			// check for existent contact persons due to flexible add/remove of contact persons 
			if ((request.getParameter("contactPersonName_"+i)!=null)&&(request.getParameter("contactPersonEmail_"+i)!=null)){
				contactPersonName = (request.getParameter("contactPersonName_"+i));
				contactPersonEmail = (request.getParameter("contactPersonEmail_"+i));
				// at least one field must not be empty!
				if (contactPersonName.trim().length()>0 || contactPersonEmail.trim().length()>0){
					Person contactPerson = new Person(contactPersonName, contactPersonEmail);			
					contactPersons.add(contactPerson);	
				}
			}
		}
		projectProp.setContactPersons(contactPersons);	
		

		// Add estimated begin
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		if (request.getParameter("estimatedBegin")!=null){	
			String dateString = request.getParameter("estimatedBegin");
			Date estimatedBegin = null;
			try {
				estimatedBegin = df.parse(dateString);
				projectProp.setEstimatedBegin(estimatedBegin);
			} catch (Exception e) {
				// ignore invalid Date
			}	
		}
	
		projectProp.setLastModifiedAt(new Date());
		// this should be done according to user logged in
		projectProp.setDepartment(db.getDepartments()[(new Random().nextInt(db.getDepartments().length))]);
		projectProp.setLastModifiedBy(new Person("Matthias Kunze", "matthias.kunze@hpi-web.de"));
		
		
		//TODO save project in real DB
		db.addProjectProposal(projectProp);
		
		//TODO log creation of project proposal in DB

		response.sendRedirect("listOwnDepartment.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
