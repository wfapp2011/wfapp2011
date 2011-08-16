package de.uni_potsdam.hpi.wfapp2011.proposals.server.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni_potsdam.hpi.wfapp2011.servercore.general.FtpTransfer;
/**
 * FileDownload_Servlet is a subclass of HttpServlet.
 * It handles GET-requests and returns files from the FTP-Server.
 * 
 * @author Katrin Honauer, Josefine Harzmann
 * @see HttpServlet
 */
public class FileDownload_Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	String responseString;
	private static FtpTransfer ftp = FtpTransfer.getInstance();

	/**
	 * Downloads the requested file from 
	 * FTP and streams it back to the client.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String destFolder = request.getParameter("destFolderFtp");
		String fileNameFtp = request.getParameter("fileNameFtp");
		String fileName = request.getParameter("fileName");
		
		InputStream in = ftp.download(destFolder, fileNameFtp);
		OutputStream out = response.getOutputStream();

		response.setContentType("application/data");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName); 
			
		if (in != null){
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
}