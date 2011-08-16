package de.uni_potsdam.hpi.wfapp2011.proposals.client.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * ProjectProposal is a dataclass that encapsulates 
 * various attributes (name, description, teamsize, ...)
 * for project proposals.
 * It further provides helper methods for rendering.  
 * 
 * @author Katrin Honauer, Josefine Harzmann
 */

public class ProjectProposal implements IsSerializable{	
	
	private int projectId;	
	private String projectName;
	private String projectDescription;	
	private String partnerName;
	private String partnerDescription;
	
	private int minStud;
	private int maxStud;
	private Date estimatedBegin;
	private String estimatedBeginString;	
	private String keywords;	
	private Department department;
	private List<Person> contactPersons;

	private Date lastModifiedAt;
	private String lastModifiedAtString;
	private Person lastModifiedBy;
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	
	private Boolean isDeleted = false; 
	private Boolean isPublic = false; 
	private Boolean isRejected = true;
	
	// standard constructor (necessary for IsSerializable)
	public ProjectProposal() {	
		this.setIsDeleted(false);
		this.setIsPublic(false); //per default, proposals are not public
	}
	
	public ProjectProposal(String projectName, int minStud, int maxStud) {		
		this.setProjectName(projectName);
		this.setMinStud(minStud);
		this.setMaxStud(maxStud);
		this.setIsDeleted(false);
		this.setIsPublic(false);
	}

	// helper methods for rendering
	public String getDepartmentNameOr(String defaultString) {
		if (department != null) {
			return department.getName();
		}
		return defaultString;
	}
	
	public String getProjectNameOr(String defaultString) {
		if ((projectName != null) && (projectName.trim().length() > 0)) {
			return projectName;
		}
		return defaultString;
	}
	
	public String getProjectDescriptionOr(String defaultString) {
		if ((projectDescription != null) && (projectDescription.trim().length() > 0))  {
			return projectDescription;
		}
		return defaultString;
	}
	
	public String getPartnerNameOr(String defaultString) {
		if ((partnerName != null) && (partnerName.trim().length() > 0)) {
			return partnerName;
		}
		return defaultString;
	}
		
	public String getPartnerDescriptionOr(String defaultString) {
		if ((partnerDescription != null) && (partnerDescription.trim().length() > 0)){
			return partnerDescription;
		}
		return defaultString;
	}
		
	public String getKeywordsOr(String defaultString) {
		if ((keywords != null) && (keywords.trim().length() > 0)){
			return keywords;
		}
		return defaultString;
	}
	
	public int getMaxStudOr(int defaultValue) {
		if (maxStud > 0) {
			return maxStud;
		}
		return defaultValue;
	}

	public int getMinStudOr(int defaultValue) {
		if (minStud > 0) {
			return minStud;
		}
		return defaultValue;
	}

	public String getMinMaxStudOr(String defaultString) {
		if (minStud == 0 || maxStud == 0) {
			return defaultString;
		}
		return "An diesem Projekt können "+minStud+" bis "+maxStud+" Studenten teilnehmen.";
	}
	
	public String getInfoEstimatedBeginOr(String defaultString) {
		if ((estimatedBeginString != null) && (estimatedBeginString.trim().length() > 0)) {
			return "Voraussichtlicher Projektbeginn ist der "+estimatedBeginString+".";
		}
		return defaultString;
	}
	
	public String getEstimatedBeginOr(String defaultString) {
		if ((estimatedBeginString != null) && (estimatedBeginString.trim().length() > 0)) {
			return estimatedBeginString;
		}
		return defaultString;
	}
	
	public String getProfNameAndMailOrDefault() {
		String name = "kein Name angegeben";
		String email = "keine E-Mail-Adresse angegeben";		
		if ((department != null) && (department.getProf() != null)) {
			if (department.getProf().getName().trim().length() > 0){
				name = department.getProf().getName();
			}
			if (department.getProf().getEmail().trim().length() > 0){
				email = department.getProf().getEmail();
			}
		}
		return name+", "+email;
	}

	public String getLastModifiedOrDefault() {
		boolean returnDefault = true;	
		String resultstring = "Dieser Projekt wurde zuletzt";
		if ((lastModifiedAtString != null)&&(lastModifiedAtString.trim().length()>0)){
			resultstring += " am "+lastModifiedAtString;
			returnDefault = false;
		}
		if ((lastModifiedBy != null)&& (lastModifiedBy.getName().trim().length()>0)){
			resultstring += " von "+lastModifiedBy.getName();
			returnDefault = false;
		}
		
		if (returnDefault) {
			return "";
		}
		return resultstring += " geändert.";

	}
	
	// standard getters and setters	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerDescription() {
		return partnerDescription;
	}

	public void setPartnerDescription(String partnerDescription) {
		this.partnerDescription = partnerDescription;
	}

	public int getMinStud() {
		return minStud;
	}

	public void setMinStud(int minStud) {
		this.minStud = minStud;
	}

	public int getMaxStud() {
		return maxStud;
	}

	public void setMaxStud(int maxStud) {
		this.maxStud = maxStud;
	}

	public Date getEstimatedBegin() {
		return estimatedBegin;
	}

	public void setEstimatedBegin(Date estimatedBegin) {
		this.estimatedBegin = estimatedBegin;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Person> getContactPersons() {
		return contactPersons;
	}

	public void setContactPersons(List<Person> contactPersons) {
		this.contactPersons = contactPersons;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public Person getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Person lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	public String getEstimatedBeginString() {
		return estimatedBeginString;
	}

	public void setEstimatedBeginString(String estimatedBeginStr) {
		this.estimatedBeginString = estimatedBeginStr;
	}

	public String getLastModifiedAtString() {
		return lastModifiedAtString;
	}

	public void setLastModifiedAtString(String lastModifiedAtStr) {
		this.lastModifiedAtString = lastModifiedAtStr;
	}

	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}

	public Boolean getIsRejected() {
		return isRejected;
	}
}
