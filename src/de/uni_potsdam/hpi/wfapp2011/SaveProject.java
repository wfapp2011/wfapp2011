package de.uni_potsdam.hpi.wfapp2011;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;

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
		
		Map<String,String> parameters = new HashMap<String,String> ();
		Map<String,File> files = new HashMap<String,File> ();

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			
			// create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload();
	
			// parse the request
			try {
				FileItemIterator iter = upload.getItemIterator(request);
				
				while (iter.hasNext()) {
				    FileItemStream item = iter.next();
				    String field = item.getFieldName();
				    InputStream stream = item.openStream();
				    
				    // parse normal input
				    if (item.isFormField()) {
				    	String value = Streams.asString(stream);
				        System.out.println("Form field " + field + " with value " + value + " detected.");
				        parameters.put(field, value);				        
				    }
				    // parse file input
				    else if (item.getName().length() > 0){
				    	String filename = item.getName();
				    	System.out.println("File field " + field + " with file name "+ filename + " detected.");
	 
				    	//String uploadedStream 	= item.getContentType();
	
				        // store file
				        BufferedInputStream  is = null;
				        BufferedOutputStream os = null;
				        File file = new File (filename);
				        try {
				        	is = new BufferedInputStream( stream );
					        os = new BufferedOutputStream(new FileOutputStream(file));
					        byte[] buff = new byte[8192]; //check this!
					        int len;
					        while( 0 < (len = is.read(buff))){
					        	os.write( buff, 0, len );
					        }
					        //store file if read was successful
					        files.put(field, file);	
				        } 
				        finally {
				        	if( is != null ){
				        		is.close();
				        	}
				        	if( os != null ) {
				        		os.flush();
				        		os.close();
				        	}
				        }
				        
				        			        
				    }				
				}
			} 
			catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			saveProject(parameters, files);
			response.sendRedirect("listOwnDepartment.jsp");
			
		}
		else {
			System.out.println("Hat nicht geklappt");
		}
		
	}

	protected void saveProject (Map<String,String> parameters, Map<String,File> files){
	
	boolean projectIsNew = true;
	ProjectProposal projectToSave = new ProjectProposal();
	
	if (parameters.get("projectID") != null) {
		String projectID = parameters.get("projectID");	
		ArrayList<ProjectProposal> projectProposals = db.getProjectProposals();
		for (ProjectProposal project : projectProposals){
			if (project.toString().equals(projectID)){
				projectToSave = project;
				projectIsNew = false;
			}
		}
	}
	
	projectToSave.setProjectName(parameters.get("projectName"));
	projectToSave.setProjectDescription(parameters.get("projectDescription"));
	projectToSave.setKeywords(parameters.get("keywords"));
	projectToSave.setPartnerName(parameters.get("partnerName"));
	projectToSave.setPartnerDescription(parameters.get("partnerDescription"));
	projectToSave.setMinStud(Integer.parseInt(parameters.get("minStud")));
	projectToSave.setMaxStud(Integer.parseInt(parameters.get("maxStud")));
	
	// Add contact persons
	ArrayList<Person> contactPersons = new ArrayList<Person>();
	Integer maxNumberOfContactPersons = Integer.parseInt(parameters.get("maxNumberOfContactPersons"));
	String contactPersonName, contactPersonEmail;

	for (int i=1; i<=maxNumberOfContactPersons; i++){
		// check for existent contact persons due to flexible add/remove of contact persons 
		if ((parameters.get("contactPersonName_"+i)!= null)&&((parameters.get("contactPersonEmail_"+i))!=null)){
			contactPersonName = (parameters.get("contactPersonName_"+i));
			contactPersonEmail = (parameters.get("contactPersonEmail_"+i));
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
	if (parameters.get("estimatedBegin")!=null){	
		String dateString = parameters.get("estimatedBegin");
		Date estimatedBegin = null;
		try {
			estimatedBegin = df.parse(dateString);
			projectToSave.setEstimatedBegin(estimatedBegin);
		} catch (Exception e) {
			// ignore invalid Date
		}	
	}

	
	// Add files
	projectToSave.setProjectFile(files.get("projectFile"));

	
	
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

	
}

}
