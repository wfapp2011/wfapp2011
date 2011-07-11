package de.uni_potsdam.hpi.wfapp2011;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import de.uni_potsdam.hpi.wfapp2011.data.DummyDatabase;
import de.uni_potsdam.hpi.wfapp2011.data.ProjectProposal;

public class GeneratePDF_test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DummyDatabase db = DummyDatabase.getInstance();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("it seems to work... wrong project");
		/*ProjectProposal projectForPDF = new ProjectProposal();
		if (request.getParameter("projectID") != null) {
			String projectID = request.getParameter("projectID");	
			ArrayList<ProjectProposal> projectProposals = db.getProjectProposals();
			for (ProjectProposal project : projectProposals){
				if (project.toString().equals(projectID)){
					projectForPDF = project;
				}
			}
		}
		String html = generateHTML(projectForPDF);
		try {
			generatePDF(html);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	private String generateHTML(ProjectProposal proposal){
		String htmlFile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"" +
		"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">" + 
		"<html xmlns=\"http://www.w3.org/1999/xhtml\"> <head><title>" +
		"Projektvorschlag " + proposal.getProjectName() + "</title></head><body>";
		htmlFile += proposal.getProjectDescription();
		htmlFile += "</body> </html>";
		return null;
	}
	
	private void generatePDF(String html) throws DocumentException, IOException{
	    String HTML_TO_PDF = "Projektvorschlag XY.pdf";
	    FileOutputStream os = new FileOutputStream(HTML_TO_PDF);
	    ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(new File(html));      
        renderer.layout();
        renderer.createPDF(os);        
        os.close();
	}

}
