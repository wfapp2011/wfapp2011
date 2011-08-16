package de.uni_potsdam.hpi.wfapp2011.proposals.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * FileInfo is a dataclass that encapsulates 
 * filenames and their location on the ftp-server. 
 * 
 * @author Katrin Honauer, Josefine Harzmann
 */
public class FileInfo implements IsSerializable{
	
	private int id;
	private String filename;
	private String filenameFtp;
	private String destFolderFtp;
	private boolean isProjectFile;
	
	// standard constructor (necessary for IsSerializable)
	public FileInfo(){
	}
	
	public FileInfo(String fn, String fnFtp, String destFtp){
		this.setFilename(fn);
		this.setFilenameFtp(fnFtp);
		this.setDestFolderFtp(destFtp);
	}
	
	// standard getters and setters
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getFilenameFtp() {
		return filenameFtp;
	}
	
	public void setFilenameFtp(String filenameFtp) {
		this.filenameFtp = filenameFtp;
	}
	
	public String getDestFolderFtp() {
		return destFolderFtp;
	}
	
	public void setDestFolderFtp(String destFolderFtp) {
		this.destFolderFtp = destFolderFtp;
	}
	
	public boolean isProjectFile() {
		return isProjectFile;
	}
	
	public void setProjectFile(boolean isProjectFile) {
		this.isProjectFile = isProjectFile;
	}
	
	
}
