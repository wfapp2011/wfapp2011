package data;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class ProjectProposal {

	private String projectName;
	private String projectDescription;
	private int minStud;
	private int maxStud;
	private String partnerName = "";
	private String partnerDescription;
	private ArrayList<Person> contactPersons = new ArrayList<Person>();
	private Date estimatedBegin;
	private Date year;
	private Department department;
	private File projectFile;
	private ArrayList<File> additionalFiles = new ArrayList<File>();
	private Boolean isDeleted; //von DB?
	private Boolean isPublic;  //in State bereits erfasst?
	private enum status {retained, submitted, rejected, accepted};
	private ArrayList<Comment> comments;
	//private ArrayList<String> keywords;
	private String keywords = "";
	private String shortCut; //anderer Name? Projektk�rzel
	private Date lastModifiedAt;
	private Person lastModifiedBy;
			


	public ProjectProposal(String projectName, int minStud, int maxStud) {		
		this.setProjectName(projectName);
		this.setMinStud(minStud);
		this.setMaxStud(maxStud);
		this.setIsDeleted(false);
		this.setIsPublic(false);
	}



	public void setLastModifiedBy(Person lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}



	public Person getLastModifiedBy() {
		return lastModifiedBy;
	}



	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}



	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}



	public void setShortCut(String shortCut) {
		this.shortCut = shortCut;
	}



	public String getShortCut() {
		return shortCut;
	}



	public void setKeywords(ArrayList<String> keyws) {
		this.keywords = keyws.get(0);
	}

	public void setKeywords(String keyws) {
		this.keywords = keyws;
	}
	
	public String getKeywords() {
		return keywords;
	}

	/*public ArrayList<String> getKeywords() {
		return keywords;
	}*/



	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}



	public ArrayList<Comment> getComments() {
		return comments;
	}



	public void setAdditionalFiles(ArrayList<File> additionalFiles) {
		this.additionalFiles = additionalFiles;
	}



	public ArrayList<File> getAdditionalFiles() {
		return additionalFiles;
	}



	public void setProjectFile(File projectFile) {
		this.projectFile = projectFile;
	}



	public File getProjectFile() {
		return projectFile;
	}



	public void setDepartment(Department department) {
		this.department = department;
	}



	public Department getDepartment() {
		return department;
	}



	public void setYear(Date year) {
		this.year = year;
	}



	public Date getYear() {
		return year;
	}



	public void setEstimatedBegin(Date estimatedBegin) {
		this.estimatedBegin = estimatedBegin;
	}



	public Date getEstimatedBegin() {
		return estimatedBegin;
	}



	public void setContactPersons(ArrayList<Person> contactPersons) {
		this.contactPersons = contactPersons;
	}



	public ArrayList<Person> getContactPersons() {
		return contactPersons;
	}



	public void setPartnerDescription(String partnerDescription) {
		this.partnerDescription = partnerDescription;
	}



	public String getPartnerDescription() {
		return partnerDescription;
	}



	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}



	public String getPartnerName() {
		return partnerName;
	}



	public void setMaxStud(int maxStud) {
		this.maxStud = maxStud;
	}



	public int getMaxStud() {
		return maxStud;
	}



	public void setMinStud(int minStud) {
		this.minStud = minStud;
	}



	public int getMinStud() {
		return minStud;
	}



	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}



	public String getProjectName() {
		return projectName;
	}



	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}



	public Boolean getIsDeleted() {
		return isDeleted;
	}



	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}



	public Boolean getIsPublic() {
		return isPublic;
	}



	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}



	public String getProjectDescription() {
		return projectDescription;
	}
	
}
