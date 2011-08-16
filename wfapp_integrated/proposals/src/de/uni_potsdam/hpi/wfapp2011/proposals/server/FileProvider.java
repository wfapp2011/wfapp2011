package de.uni_potsdam.hpi.wfapp2011.proposals.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.ProcessInfo;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.FileInfo;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.FileInterface;
import de.uni_potsdam.hpi.wfapp2011.proposals.server.utils.ZipFileGenerator;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.general.FtpTransfer;

public class FileProvider extends RemoteServiceServlet implements FileInterface {

	private static final long serialVersionUID = 1L;
	private static FtpTransfer ftp = FtpTransfer.getInstance();
	
	private DbInterface dbConnection;
	private String type, semester;
	private int year;
	
	public FileProvider() {		
		dbConnection = new DbInterface();
		// TODO: Replace with URL
		type = ProcessInfo.getTypeFromURL("");
		semester = ProcessInfo.getSemesterFromURL("");
		year = ProcessInfo.getYearFromURL("");	
	}

	/**
	 * Saves given files in DB together with proposalId.
	 * @param projectId The files belong to the proposal with this id.	
	 * @param files File info to be saved in DB (real files are on ftp).
	 */
	public void saveFiles(int proposalId, List<FileInfo> files){
		try {
			dbConnection.connect(type, semester, year);
			for (FileInfo fileinfo: files){
				// save as project file
				if (fileinfo.isProjectFile()){
					String qInsertProjectFile = queryInsertFile(proposalId, fileinfo, true);	
					dbConnection.executeUpdate(qInsertProjectFile);
				}
				// save as additional file
				else {
					String qInsertAdditionalFile = queryInsertFile(proposalId, fileinfo, false);
					dbConnection.executeUpdate(qInsertAdditionalFile);
				}	
			}
		} catch (SQLTableException e) {
			e.printStackTrace();
		}	
		dbConnection.disconnect();		
	}
	
	/**
	 * Updates file info in DB after form has been submitted.
	 * Deletes files that have been deleted in the form and
	 * adds recently added files.
	 * @param proposalId 
	 * @param newFiles
	 * @param fileNamesFtp Old files that have not been deleted and shall stay.
	 * @throws SQLTableException
	 */	
	public void updateFiles(int proposalId, List<FileInfo> newFiles, List<String>fileNamesFtp) throws SQLTableException{
		// collect filenames from existing files that will stay
		String concFileNamesFtp = "";
		for(String fileNameFtp: fileNamesFtp){
			concFileNamesFtp += "'"+fileNameFtp+"',";
		}
		if (concFileNamesFtp.length() >= 2){
			concFileNamesFtp = concFileNamesFtp.substring(0, concFileNamesFtp.length()-1);
		}		
		// delete existing files that have been deleted in the form
		dbConnection.connect(type, semester, year);
		String qDeleteFiles = queryDeleteFiles(proposalId, concFileNamesFtp);
		dbConnection.executeUpdate(qDeleteFiles);
		dbConnection.disconnect();
		// save new files
		saveFiles( proposalId, newFiles);
	}
	
	/**
	 * Finds all file info that belongs to the given proposal
	 * and returns a list with this file info.
	 * @param proposalId
	 */
	public  ArrayList<FileInfo> getAllFiles(int proposalId) {	
		ArrayList<FileInfo>  files = new ArrayList<FileInfo>();		
		String qGetFiles = "SELECT * FROM FILES "+ 
		  				   "WHERE PROJECTID = "+proposalId;		 
		try {
			dbConnection.connect(type, semester, year);
			ResultSet resultset = dbConnection.executeQueryDirectly(qGetFiles);
			if (resultset.next()) {
				do {
					FileInfo fileinfo = buildFileInfo(resultset);
					files.add(fileinfo);				
				} while(resultset.next());
			}		
		} catch (SQLTableException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return files;
	}

	/**
	 * Returns all existing specific project files (no additional files)
	 * of the stored proposals.
	 * @return list with FileInfo of project files
	 */
	public  ArrayList<FileInfo> getMainProjectFiles() {
		ArrayList<FileInfo>  files = new ArrayList<FileInfo>();
		String qGetProjectFiles = "SELECT * FROM FILES "+ 
								  "WHERE (ISPROJECTFILE = TRUE)";		
		try {
			dbConnection.connect(type, semester, year);
			ResultSet resultset = dbConnection.executeQueryDirectly(qGetProjectFiles);
			if (resultset.next()) {
				do {
					FileInfo fileinfo = buildFileInfo(resultset);				
					files.add(fileinfo);						
				} while(resultset.next());
			}		
		} catch (SQLTableException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return files;
	}
	
	/**
	 * Requests project file info from DB and loads these files from FTP-Server.
	 * Generates zip file and streams 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public InputStream getZippedProjectFilesFromFtp(String zipName) throws IOException {
	
		// get file info from project files
		List<FileInfo> fileInfos = getMainProjectFiles();
		List<File> files = new ArrayList<File>();
		
		// download files from ftp-server
		Date date = new Date();
		File destFolder = new File("files_"+date.getTime());
		destFolder.mkdir();
		InputStream inFtp = null;
		OutputStream outFile = null;
	
		for(FileInfo fileInfo: fileInfos){
			String ftpFolder = fileInfo.getDestFolderFtp();
			String ftpPath = fileInfo.getFilenameFtp();
			String fileName = fileInfo.getFilename();		
			
			inFtp = ftp.download(ftpFolder, ftpPath);
			File projectFile = new File(destFolder, fileName);	
			outFile = new FileOutputStream(projectFile);
			if (outFile != null){
				files.add(projectFile);
			}
			int read=0;
			byte[] bytes = new byte[1024];	 
			if (inFtp != null){
				while((read = inFtp.read(bytes))!= -1){
					outFile.write(bytes, 0, read);
				}	 
				inFtp.close();
				outFile.flush();
				outFile.close();	
			}
		}
		
		// generate zip file
		ZipFileGenerator zipper = new ZipFileGenerator();
		//File zipFile = new File(localPath+"zips\\", zipName+".zip");
		File zipFile = new File(zipName+".zip");
		try {
			zipper.createZipfile(files, zipFile);
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		InputStream zipInput = new FileInputStream(zipFile);
		deleteTmpFolder(destFolder);
		return zipInput;
	}
	
	
	// HELPER METHODS TO BUILD QUERY STRINGS OR FINAL OBJECTS	
	/**
	 * Helper method: Builds queryString to insert given file into DB.
	 * @param proposalId
	 * @param fileinfo
	 * @param isProjectFile
	 * @return
	 */
	private String queryInsertFile(int proposalId, FileInfo fileinfo, boolean isProjectFile){
		String query = "INSERT INTO FILES (" +
							"projectID, " +
							"destFolderFTP, " +
							"fileNameFTP, " +
							"fileName, " +	
							"isProjectFile)"+
				 		"VALUES ("
							+ proposalId +", '"
				 			+ fileinfo.getDestFolderFtp()+ "', '"
				 			+ fileinfo.getFilenameFtp()+ "', '"
				 			+ fileinfo.getFilename()+ "', "
				 			+isProjectFile
				 		+")";
		return query;
	}

	/**
	 * Helper method: Builds query string to delete files 
	 * @param proposalId
	 * @param concFileNamesFtp names of files that will NOT be deleted
	 * @return
	 */
	private String queryDeleteFiles(int proposalId, String concFileNamesFtp){
		String query = "DELETE FROM FILES " +
					   "WHERE ("+
					   		"projectID = "+proposalId+" AND "+ 
					   		"fileNameFTP NOT IN (" +concFileNamesFtp+")"+
					   	")";
		return query;
	}
	
	/**
	 * Helper method: Builds FileInfo out of resultset.
	 * @param resultset
	 * @return
	 * @throws SQLException
	 */
	private FileInfo buildFileInfo(ResultSet resultset) throws SQLException{
		FileInfo fileinfo = new FileInfo();
		fileinfo.setId(resultset.getInt("projectid"));
		fileinfo.setDestFolderFtp(resultset.getString("destfolderftp"));
		fileinfo.setFilenameFtp(resultset.getString("filenameftp"));
		fileinfo.setFilename(resultset.getString("filename"));
		fileinfo.setProjectFile(resultset.getBoolean("isprojectfile"));
		return fileinfo;
	}
	
	private void deleteTmpFolder(File destFolder){
		for (File file : destFolder.listFiles() ){
			file.delete();
		}
		destFolder.delete();	 
	}
}
