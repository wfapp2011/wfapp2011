package de.uni_potsdam.hpi.wfapp2011.proposals.server.services;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Department;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.FileInfo;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Person;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.ProjectProposal;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.general.FtpTransfer;
/**
 * FormHandlerProposal_Servlet is a subclass of HttpServlet.
 * It handles POST-requests and processes the parameters 
 * to build a project proposal that will be saved in the DB. 
 * 
 * @author Katrin Honauer, Josefine Harzmann
 * @see HttpServlet
 */
public class FormHandlerProposal_Servlet extends HttpServlet {
	
	private ProposalProvider proposalProvider = new ProposalProvider();
	private FileProvider fileProvider = new FileProvider();
	private static FtpTransfer ftp = FtpTransfer.getInstance();
	
	private static final long serialVersionUID = 1L;
	private static String DATE_FORMAT = "dd/MM/yyyy";
	private int projectId = 0;
	private Map<String, String> parameters;
	private List<FileInfo> newFiles;	  //these files are uploaded the first time
	private List<String> fileNamesFtp;    //these files are already in the DB
	String responseString = "";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		parameters = new HashMap<String, String>();
		newFiles = new ArrayList<FileInfo>();
		fileNamesFtp = new ArrayList<String>();
		response.setContentType("text/html");
		
		parseRequestToSetParametersAndFiles(request);		
		ProjectProposal proposal = createProposalFromParameters();	
		int projectId = Integer.parseInt(parameters.get("projectIdIfExistent"));
		if (projectId > 0){ 		// update edited proposal
			proposal.setProjectId(projectId);
			updateProposalInDB(proposal, response);
		}
		else {						// save new proposal
			saveNewProposalInDB(proposal, response);
		}
	}
	
	/**
	 * Updates new proposal and file info in DB.
	 * (overwrites proposal parameters)
	 * @param proposal
	 * @param response
	 * @throws IOException
	 */	
	private void updateProposalInDB(ProjectProposal proposal, HttpServletResponse response) throws IOException{	
		try {
			proposalProvider.updateProposal(proposal);
			fileProvider.updateFiles(proposal.getProjectId(), newFiles, fileNamesFtp);
			responseString = "ID:"+proposal.getProjectId()+":DI";			
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		response.getWriter().write(responseString);	
	}
	
	/**
	 * Saves new proposal and file info in DB.
	 * @param proposal
	 * @param response
	 * @throws IOException
	 */
	private void saveNewProposalInDB(ProjectProposal proposal, HttpServletResponse response) throws IOException{	
		try {
			projectId = proposalProvider.saveNewProposal(proposal);
			fileProvider.saveFiles(projectId, newFiles);
			responseString = " ID:"+projectId+":DI";
		} catch (Exception e) {
			e.printStackTrace();		
		}	
		response.getWriter().write(responseString);
	}
	
	
	/**
	 * Parse the request and put all parameters with their name and value in "parameters".
	 * Put all files with name in "files" and upload them to the FTP-Server.
	 * @param request
	 */
	protected void parseRequestToSetParametersAndFiles(HttpServletRequest request) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			ServletFileUpload upload = new ServletFileUpload();
			try {
				FileItemIterator iter = upload.getItemIterator(request);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					String field = item.getFieldName();
					InputStream stream = item.openStream();

					// parse standard input
					if (item.isFormField()) {
						String value = Streams.asString(stream);
						//System.out.println("field: "+field+"   value: "+value);
						parameters.put(field, value);
					}

					// parse file input
					else if (item.getName().length() > 0) {
						String filename = item.getName();
						//System.out.println("field: "+field+"   value: "+filename);
						
						// store file
						BufferedInputStream is = new BufferedInputStream(stream);						
						ArrayList<String> result = ftp.upload(is, filename);
						String fileNameFtp = result.get(1);
						String destFolderFtp = result.get(0);

						FileInfo fileinfo = new FileInfo(filename, fileNameFtp, destFolderFtp);
						if("projectFile".equals(field)){
							fileinfo.setProjectFile(true);
							}
						else {
							fileinfo.setProjectFile(false);
							}
						newFiles.add(fileinfo);

						if (is != null) {
							is.close();
						}
					}
				} // while iter.hasNext()

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		extractExistingFilesOutOfParameters(parameters);
	}
	
	private void extractExistingFilesOutOfParameters(Map<String,String> parameters){
		
		if ((parameters.get("givenPrFileFilenameFtp") != null) && (parameters.get("givenPrFileFilenameFtp").trim().length() > 0)){
			fileNamesFtp.add(parameters.get("givenPrFileFilenameFtp"));
		}		
		Integer maxNumberOfAdditionalFiles = Integer.parseInt(parameters.get("highestAdditionalFile"));
		String additionalFile_FilenameFtp;	
			for (int i=0; i<=maxNumberOfAdditionalFiles; i++){
			// check for existent additional files due to flexible add/remove of files
			if (parameters.get("givenAddFileFilenameFtp_"+i) != null){
				additionalFile_FilenameFtp = (parameters.get("givenAddFileFilenameFtp_"+i));
				if (additionalFile_FilenameFtp.trim().length() > 0){
					fileNamesFtp.add(additionalFile_FilenameFtp);
				}
			}			
		}				
	}
	
	private ProjectProposal createProposalFromParameters(){		
		ProjectProposal proposal = new ProjectProposal();

		// set basic attributes
		proposal.setProjectName(parameters.get("projectName"));
		proposal.setProjectDescription(parameters.get("projectDescription"));
		proposal.setKeywords(parameters.get("keywords"));
		proposal.setPartnerName(parameters.get("partnerName"));
		proposal.setPartnerDescription(parameters.get("partnerDescription"));
		proposal.setMinStud(Integer.parseInt(parameters.get("minStud")));
		proposal.setMaxStud(Integer.parseInt(parameters.get("maxStud")));
	
		// add contact persons
		ArrayList<Person> contactPersons = getContactPersons(parameters);
		proposal.setContactPersons(contactPersons);		

		// add estimated begin
		proposal.setEstimatedBegin(getEstimatedBegin(parameters));

		// add department
		// TODO get from filter
		proposal.setDepartment(new Department("Business Process Technology"));
		proposal.getDepartment().setId(6);
		
		// add metainfo
		// TODO this should be done according to user logged in
		proposal.setLastModifiedBy(new Person("Matthias Kunze", "matthias.kunze@hpi-web.de"));
		proposal.getLastModifiedBy().setId(20);
		proposal.setLastModifiedAt(new Date());
		
		return proposal;
}
	/**
	 * Try to parse DateString and save it as Date.
	 * Return null, if parsing fails.
	 */
	private Date getEstimatedBegin(Map<String,String> parameters)  {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		Date estimatedBegin = null;
		
		if ((parameters.get("estimatedBegin") != null)&& (parameters.get("estimatedBegin").length() > 0)){	
			String dateString = parameters.get("estimatedBegin");
			try {
				estimatedBegin = df.parse(dateString);	
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return estimatedBegin;
	}
	
	/**
	 * Try to extract contact persons from parameters.
	 * Filter empty and invalid arguments to add only valid persons.
	 * @param parameters Map with keys and values of request parameters
	 * @return ArrayList with Persons
	 */
	private ArrayList<Person> getContactPersons(Map<String,String> parameters) {
		
		ArrayList<Person> contactPersons = new ArrayList<Person>();
		String maxContactPersons = parameters.get("highestContactPerson");
		if (maxContactPersons != null){
			Integer maxNumberOfContactPersons = Integer.parseInt(parameters.get("highestContactPerson"));
			String contactPersonName, contactPersonEmail;	
			
			for (int i=1; i<=maxNumberOfContactPersons; i++){
				// check for existent contact persons due to flexible add/remove of contact persons 
				if ((parameters.get("contactPerson_name_"+i)!= null)&&((parameters.get("contactPerson_email_"+i))!=null)){
					contactPersonName = (parameters.get("contactPerson_name_"+i));
					contactPersonEmail = (parameters.get("contactPerson_email_"+i));
					if (contactPersonName.equals("Name")) contactPersonName = "";
					if (contactPersonEmail.equals("E-Mail-Adresse")) contactPersonEmail = "";
					// at least one field must not be empty!
					if (contactPersonName.trim().length()>0 || contactPersonEmail.trim().length()>0){
						Person contactPerson = new Person(contactPersonName, contactPersonEmail);
						//TODO woher bekommen wir die Ids? sollten im Konstruktor der Person gesetzt werden
						contactPerson.setId(21);
						contactPersons.add(contactPerson);	
					}
				}
			}		
		}
		return contactPersons;		
	}
}
