package de.uni_potsdam.hpi.wfapp2011.server;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.zehon.exception.FileTransferException;
import com.zehon.ftp.FTPClient;


public class FtpTransfer {
	
	private static FtpTransfer theInstance = null;
	private static String url = "ftp.gwave.gw.ohost.de";
	private static String name = "gwave";
	private static String pwd = "hpihpihpi";
	
	private FtpTransfer()
		{
			
		}

	public synchronized static FtpTransfer getInstance() 
			{
				if (theInstance == null)
						theInstance = new FtpTransfer();
				return theInstance;			
			}

	public synchronized final ArrayList<String> upload(InputStream is, String filename){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date()); // today
		int year = cal.get(Calendar.YEAR);
		String fileName = (cal.getTime().toString()+" "+filename);		
		String destFolder = String.valueOf(year);
		
		int status = -1;
		ArrayList<String> returnValues = new ArrayList<String>();
		
		try {
			// Connect to FTP-Server
		    FTPClient ftpClient = new FTPClient(url,name,pwd);
		    // upload Filestream
			status = ftpClient.sendFile(is, fileName, destFolder);
		}catch (FileTransferException e) {
			e.printStackTrace();	
		}
		
		// Generating returnValues
		// First filelocation
		returnValues.add(destFolder);
		// Second filename
		returnValues.add(fileName);
		// Third status
		returnValues.add(String.valueOf(status));
		
		return returnValues;
	}
	
	public synchronized final InputStream download(String destFolder, String filename){
		
		InputStream file = null;
		
		try {
			FTPClient ftpClient = new FTPClient(url,name,pwd);
			file = ftpClient.getFileAsStream(filename, destFolder);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return file;		
	}
	
}