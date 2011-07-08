package de.uni_potsdam.hpi.wfapp2011.server;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.zehon.exception.FileTransferException;
import com.zehon.ftp.FTPClient;


public class FtpTransfer {
	
	private static FtpTransfer theInstance = null;
	private static String url; // = "ftp.gwave.gw.ohost.de";
	private static String name; // = "gwave";
	private static String pwd; // = "hpihpihpi";
	
	public void updateLogin(){
		Collection<Map<String,String>> result;
		DbInterface db = new DbInterface();
		db.connectToMetaTables();
		
		result = db.executeQuery("SELECT name,value FROM metaconfig WHERE name LIKE 'ftp%';");
		for (Map<String,String> map:result){
			if (map.get("name").equals("ftp_url")) url = map.get("value");
			if (map.get("name").equals("ftp_name")) name = map.get("value");
			if (map.get("name").equals("ftp_pwd")) pwd = PasswordCrypter.getInstance().decrypt(map.get("value"));
		}
		
		db.disconnect();
	}
	
	private FtpTransfer()
		{
		
		}

	public synchronized static FtpTransfer getInstance() 
			{	
				if (theInstance == null)
						theInstance = new FtpTransfer();
				
				theInstance.updateLogin();
				
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