package de.uni_potsdam.hpi.wfapp2011;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
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

import de.uni_potsdam.hpi.wfapp2011.data.DummyDatabase;
import de.uni_potsdam.hpi.wfapp2011.data.Person;
import de.uni_potsdam.hpi.wfapp2011.data.ProjectProposal;
import de.uni_potsdam.hpi.wfapp2011.server.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.server.SQLTableException;

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
		
			parseRequestToSetParametersAndFiles(request, parameters, files);					
			saveProject(parameters, files);
			response.sendRedirect("listOwnDepartment.jsp");
			
		}
			
	protected void parseRequestToSetParametersAndFiles(HttpServletRequest request, Map<String, String> parameters, Map<String,File> files){

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			
			// create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload();
	
			// parse the request
			// TODO more exception handling!
			//try {

			 try {
				FileItemIterator iter = upload.getItemIterator(request);

				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					String field = item.getFieldName();
					InputStream stream = item.openStream();
					    
					// parse standard input
					if (item.isFormField()) {
						String value = Streams.asString(stream);
						parameters.put(field, value);				        
					}
					    
				    // parse file input
				    else if (item.getName().length() > 0){
				    	String filename = item.getName();
				    	//System.out.println("File field " + field + " with file name "+ filename + " detected.");
				    	//String uploadedStream = item.getContentType();
				        // store file
				        BufferedInputStream  is = null;
				        BufferedOutputStream os = null;
				        BufferedOutputStream tmps = null;
				        File file = new File (filename);
					        				        
			        	is = new BufferedInputStream( stream );
				        os = new BufferedOutputStream(new FileOutputStream(file));
				        //will be redirected to ftp-server in "saveProject"
				        tmps = new BufferedOutputStream(new FileOutputStream ("C://Users/lum/wfapp2011/WebContent/uploads/"+filename));
				        byte[] buff = new byte[8192];
				        int len;
				        
				        while( 0 < (len = is.read(buff))){
				        	os.write( buff, 0, len );
				        	tmps.write( buff, 0, len );
				        }
				        
				        files.put(field, file);	
			        
			        	if( is != null ){
			        		is.close();
			        	}
			        	if( os != null ) {
			        		os.flush();
			        		os.close();
			        	}
			        	if( tmps != null ) {
			        		tmps.flush();
			        		tmps.close();
			        	}
				    }
				} //while iter.hasNext()
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // if (isMultipart)
		else {
		  // Fehlerinfo
		}	
	}

	protected void saveProject (Map<String,String> parameters, Map<String,File> files){
	
		boolean projectIsNew = false;
		
		// create projectProposal
		ProjectProposal projectToSave = getProposalIfExists("projectID");
		if (projectToSave == null) {
			projectIsNew = true;
			projectToSave = new ProjectProposal();
			projectToSave.setProjectID(getNextProjectID());
		}
		
		// set basic attributes
		projectToSave.setProjectName(parameters.get("projectName"));
		projectToSave.setProjectDescription(parameters.get("projectDescription"));
		projectToSave.setKeywords(parameters.get("keywords"));
		projectToSave.setPartnerName(parameters.get("partnerName"));
		projectToSave.setPartnerDescription(parameters.get("partnerDescription"));
		projectToSave.setMinStud(Integer.parseInt(parameters.get("minStud")));
		projectToSave.setMaxStud(Integer.parseInt(parameters.get("maxStud")));
	
		// add contact persons
		ArrayList<Person> contactPersons = getContactPersons(parameters);
		projectToSave.setContactPersons(contactPersons);		

		// add estimated begin
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		Date estimatedBegin = null;		
		if (parameters.get("estimatedBegin")!=null){	
			String dateString = parameters.get("estimatedBegin");
			try {
				estimatedBegin = df.parse(dateString);			
			} catch (Exception e) {
				// ignore invalid Date
			}	
		}
		projectToSave.setEstimatedBegin(estimatedBegin);
	
		// add project file
		projectToSave.setProjectFile(files.get("projectFile"));

		// add additional files
		ArrayList<File> additionalFiles = new ArrayList<File>();
		Integer maxNumberOfAdditionalFiles = Integer.parseInt(parameters.get("maxNumberOfAdditionalFiles"));
		File file;
		
		for (int i=1; i<=maxNumberOfAdditionalFiles; i++){
			// check for existent additional files due to flexible add/remove of additional files
			if (files.get("additionalFile_"+i) != null){		
				file = files.get("additionalFile_"+i);
				additionalFiles.add(file);	
			}
		}
		projectToSave.setAdditionalFiles(additionalFiles);	
	
		
		// add department
		if (projectIsNew){
			projectToSave.setDepartment(db.getDepartments()[(new Random().nextInt(db.getDepartments().length))]);
		}
	
		// add metainfo
		projectToSave.setLastModifiedAt(new Date());
		// TODO this should be done according to user logged in
		projectToSave.setLastModifiedBy(new Person("Matthias Kunze", "matthias.kunze@hpi-web.de"));
	
	

		//TODO save project in activiti-database
		if (projectIsNew){
			db.addProjectProposal(projectToSave);
			
			/*
			boolean savedProposalSuccessfully = saveProjectProposalInDB(projectToSave);
			if (savedProposalSuccessfully){
				System.out.println("Saved project proposal successfully");
			}
			else {
				System.out.println("Project proposal could not be saved");
			}*/
			
		}
				
		//TODO log change of project proposal in DB
}
	
	protected ProjectProposal getProposalIfExists(String projectID){
	
		ProjectProposal projectToSave = null;
		
		if (projectID.length() > 0) {
			
			//Dummy Implementation
			ArrayList<ProjectProposal> projectProposals = db.getProjectProposals();
			for (ProjectProposal project : projectProposals){
				if (project.toString().equals(projectID)){
					projectToSave = project;
				}
			}
		}
		return projectToSave;
	}
	
	protected long getNextProjectID(){
		//Database!!
		return System.currentTimeMillis();
	}
	
	protected ArrayList<Person> getContactPersons(Map<String,String> parameters) {
		
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
		return contactPersons;		
	}
	
	protected boolean saveProjectProposalInDB(ProjectProposal projectToSave){
		
		DbInterface db2 = new DbInterface();

		try {
			db2.connect("Ba", "SS", 2011);
		} catch (SQLTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sqlQuery;
		boolean projectExists = true;
		//DB-Anfrage, ob ID schon vergeben

		if (projectExists){
		// update everything except: period, isPublic, isDeleted, isRejected, department
			sqlQuery = 	"UPDATE PROJECTPROPOSAL"+
						"SET "+
							"projectName='"+projectToSave.getProjectName()+"', "+
							"projectDescription='"+projectToSave.getProjectDescription()+"', "+
							"keywords='"+projectToSave.getKeywords()+"', "+ 

							"minStud='"+projectToSave.getMinStud()+"', "+ 
							"maxStud='"+projectToSave.getMaxStud()+"', "+ 
							"estimatedBegin='"+projectToSave.getEstimatedBegin()+"', "+ //date format?
							
							"partnerName='"+projectToSave.getPartnerName()+"', "+ 
							"partnerDescription='"+projectToSave.getPartnerDescription()+"', "+ 

							"lastModifiedAt='"+projectToSave.getLastModifiedAt()+"', "+ //date format?
							"lastModifiedBy='"+projectToSave.getLastModifiedBy().getID()+"' "+ 	
							
						"WHERE projectID = "+ projectToSave.getProjectID();
		}
		else{
			sqlQuery = "INSERT INTO PROJECTPROPOSAL (" +
							"projectID, projectName, projectDescription, keywords,"+
							"minStud, maxStud, estimatedBegin, period,"+
							"partnerName, partnerDescription,"+
							"department, lastModifiedAt, lastModifiedBy)"+
							"isPublic, isDeleted, isRejected)"+
							
						"VALUES (" +
							projectToSave.getProjectID() +", "+
							projectToSave.getProjectName() +", "+
							projectToSave.getProjectDescription() +", "+
							projectToSave.getKeywords() +", "+
							
							projectToSave.getMinStud() +", "+
							projectToSave.getMaxStud() +", "+
							projectToSave.getEstimatedBegin() +", "+ //date format?
							db.getPeriod() +", "+ //format?
							
							projectToSave.getPartnerName() +", "+
							projectToSave.getPartnerDescription() +", "+
							
							projectToSave.getDepartment().getID() +", "+
							projectToSave.getLastModifiedAt() +", "+ //date format ?
							projectToSave.getLastModifiedBy().getID() +", "+
							
							"false, "+
							"false, "+
							"false, "+
						")";
		}
		
		try {
			db2.executeQueryDirectly(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Alternative:
		//db2.executeQuery(sqlQuery);
		//
		//Verarbeitung:
		//Collection <Map<String, String>> result = db2.executeQuery(sqlQuery);
		//for (Map<String, String> m : result){
		//}		
				
		db2.disconnect();
				
		return false;
	}

}
