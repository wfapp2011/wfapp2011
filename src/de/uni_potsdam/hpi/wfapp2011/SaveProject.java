package de.uni_potsdam.hpi.wfapp2011;

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

import de.uni_potsdam.hpi.wfapp2011.data.*;

/**
 * Servlet implementation class saveProject
 */
@WebServlet("/saveProject")
public class SaveProject extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	DummyDatabase db = DummyDatabase.getInstance();
	static String DATE_FORMAT = "yyyy-MM-dd";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveProject() {
        super();
        // TODO Auto-generated constructor stub
    }

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean projectIsNew = true;
		ProjectProposal projectToSave = new ProjectProposal();
		
		if (request.getParameter("projectID") != null) {
			String projectID = request.getParameter("projectID");	
			ArrayList<ProjectProposal> projectProposals = db.getProjectProposals();
			for (ProjectProposal project : projectProposals){
				if (project.toString().equals(projectID)){
					projectToSave = project;
					projectIsNew = false;
				}
			}
		}
		
		
		String projectName = (request.getParameter("projectName"));		
		String projectDescription = (request.getParameter("projectDescription"));
		String keywords = (request.getParameter("keywords"));
		String partnerName = (request.getParameter("partnerName"));
		String partnerDescription = (request.getParameter("partnerDescription"));
		Integer minStud = Integer.parseInt(request.getParameter("minStud"));
		Integer maxStud = Integer.parseInt(request.getParameter("maxStud"));
		
		projectToSave.setProjectName(projectName);
		projectToSave.setProjectDescription(projectDescription);
		projectToSave.setKeywords(keywords);
		projectToSave.setPartnerName(partnerName);
		projectToSave.setPartnerDescription(partnerDescription);
		projectToSave.setMinStud(minStud);
		projectToSave.setMaxStud(maxStud);
		
	
		// Add contact persons
		ArrayList<Person> contactPersons = new ArrayList<Person>();
		Integer maxNumberOfContactPersons = Integer.parseInt(request.getParameter("maxNumberOfContactPersons"));
		String contactPersonName, contactPersonEmail;
		
		for (int i=1; i<=maxNumberOfContactPersons; i++){
			// check for existent contact persons due to flexible add/remove of contact persons 
			if ((request.getParameter("contactPersonName_"+i)!=null)&&(request.getParameter("contactPersonEmail_"+i)!=null)){
				contactPersonName = (request.getParameter("contactPersonName_"+i));
				contactPersonEmail = (request.getParameter("contactPersonEmail_"+i));
				if (contactPersonName.equals("Name")) contactPersonName = "";
				if (contactPersonEmail.equals("E-Mail-Adresse")) contactPersonEmail = "";
				// at least one field must not be empty!
				if (contactPersonName.trim().length()>0 || contactPersonEmail.trim().length()>0){
					Person contactPerson = new Person(contactPersonName, contactPersonEmail);			
					contactPersons.add(contactPerson);	
				}
			}
		}
		projectToSave.setContactPersons(contactPersons);	
		

		// Add estimated begin
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		if (request.getParameter("estimatedBegin")!=null){	
			String dateString = request.getParameter("estimatedBegin");
			Date estimatedBegin = null;
			try {
				estimatedBegin = df.parse(dateString);
				projectToSave.setEstimatedBegin(estimatedBegin);
			} catch (Exception e) {
				// ignore invalid Date
			}	
		}
	
		if (projectIsNew){
			projectToSave.setDepartment(db.getDepartments()[(new Random().nextInt(db.getDepartments().length))]);
		}
		
		projectToSave.setLastModifiedAt(new Date());
		// TODO this should be done according to user logged in
		projectToSave.setLastModifiedBy(new Person("Matthias Kunze", "matthias.kunze@hpi-web.de"));
		
		
	
		//TODO save project in activiti-database
		if (projectIsNew){
			db.addProjectProposal(projectToSave);
		}
			
		//TODO log change of project proposal in DB
		
		response.sendRedirect("listOwnDepartment.jsp");
	}

}
