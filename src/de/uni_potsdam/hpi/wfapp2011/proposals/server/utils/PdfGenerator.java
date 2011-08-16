package de.uni_potsdam.hpi.wfapp2011.proposals.server.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.ProjectProposal;

public class PdfGenerator {
	
	public static final String defaultName = "Projekt XY";
	
	/*
	 * method for testing creating pdf-file
	 * */
	
	public static void main(String[] args){
			ProjectProposal proposal = new ProjectProposal();
			proposal.setProjectName("blubb");
			PdfGenerator generator = new PdfGenerator();
			try {
				generator.createPDF(proposal);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	   
	public void createPDF(ProjectProposal proposal) throws com.lowagie.text.DocumentException, IOException{
		   Writer fw = null;
		    
		   /*
		    * Pdf-generation:
		    * first writing temporary html file which will 
		    * then be transferred into pdf
		    * */
		   
		    try {
		    	fw = new FileWriter("tmp.html");
		    	fw.write(getHTMLForProposal(proposal));
		    	fw.append(System.getProperty("line.separator"));
		    } catch (IOException e){
		    	System.out.println("Datei nicht erstellbar");
		    } finally {
		    	if (fw != null){
		    		try {fw.close();} catch (IOException e){e.printStackTrace();}
		    	}
		    }
		    
		    String url = new File("tmp.html").toURI().toURL().toString();
		    System.out.println(url);
		    String HTML_TO_PDF = proposal.getProjectNameOr(defaultName) + ".pdf";
		    FileOutputStream os = new FileOutputStream(HTML_TO_PDF);
		    ITextRenderer renderer = new ITextRenderer();
	        renderer.setDocument(url);      
	        renderer.layout();
	        renderer.createPDF(os);        
	        os.close();
	    }
	   
		/*
		 * creating html from proposal's data
		 * */
	   private String getHTMLForProposal(ProjectProposal proposal){
		   String htmlFile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> " +
			"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " +
			"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">" + 
			"<html xmlns=\"http://www.w3.org/1999/xhtml\"> <head><title>" + proposal.getProjectNameOr(defaultName) +
			"</title></head><body>";
			
	    	htmlFile += "<h2>" + proposal.getProjectNameOr(defaultName) + "</h2>";
	    	
	    	htmlFile += "<h3>Informationen zum Projekt</h3>";
	    	htmlFile += "<p>Bachelorprojekt 2011/12 (aus Datenbank auslesen!)</p>"+
	    	"<p>Fachgebiet:" + proposal.getDepartmentNameOr(defaultName) + "<br/>" + 
	    	"An diesem Projekt koennen " + proposal.getMinStud() + " bis " + proposal.getMaxStud() + "Studenten teilnehmen.<br/>" +
	    	"Voraussichtlicher Projektbeginn ist am " + proposal.getEstimatedBeginString() + "</p>" + 
	    	"<h4>Beschreibung: </h4>";
	    	
	    	htmlFile += "<p>" + proposal.getProjectDescription() + "</p>";
	    	htmlFile += "<h3>Informationen zum Projekt-Partner</h3>";
	    	htmlFile += "<p>Name: " + proposal.getPartnerName() + "</p>";
	    	htmlFile += "<h4>Beschreibung: </h4><p>" + proposal.getPartnerDescription() + "</p>";
	    	htmlFile += "</body> </html>";
			return htmlFile;
	   }
	}

