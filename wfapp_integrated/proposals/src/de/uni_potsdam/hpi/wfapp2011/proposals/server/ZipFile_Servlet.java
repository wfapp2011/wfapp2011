package de.uni_potsdam.hpi.wfapp2011.proposals.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.ProcessInfo;

public class ZipFile_Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private FileProvider fileProvider = new FileProvider();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// build filename		
		String type = ProcessInfo.getTypeFromURL("");
		int year = ProcessInfo.getYearFromURL("");		
		String filename = "Projektvorschl\u00E4ge_"+type+"_"+year;
		
		// get zipped file and stream it to client
		InputStream in = fileProvider.getZippedProjectFilesFromFtp(filename);
		OutputStream out = response.getOutputStream();
					
		response.setContentType("application/data");
		response.setHeader("Content-disposition", "attachment; filename=" + filename+".zip"); 
			
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.flush();
		out.close();
	}
}