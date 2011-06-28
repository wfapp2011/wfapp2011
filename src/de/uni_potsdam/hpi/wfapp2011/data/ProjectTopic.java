package de.uni_potsdam.hpi.wfapp2011.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class ProjectTopic {
	private long topicID;
	private String projectName;
	private String projectDescription;
	private int minStud;
	private int maxStud;
	private String partnerName = "";
	private String partnerDescription;
	private ArrayList<Person> contactPersons = new ArrayList<Person>();
	private Date estimatedBegin;
	private Date period;
	private Department department;
	private File projectFile;
	private String keywords = "";
	private String projectShortCut = "";
	private ProjectProposal proposal;
	
	public ProjectTopic() {}
	
	public ProjectTopic(ProjectProposal proposal){
		if (proposal == null) return;
		this.setProjectName(proposal.getProjectName());
		this.setProjectDescription(proposal.getProjectDescription());
		this.setMinStud(proposal.getMinStud());
		this.setMaxStud(proposal.getMaxStud());
		this.setPartnerName(proposal.getPartnerName());
		this.setPartnerDescription(proposal.getPartnerDescription());
		this.setEstimatedBegin(proposal.getEstimatedBegin());
		this.setPeriod(proposal.getYear());
		this.setDepartment(proposal.getDepartment());
		this.setKeywords(proposal.getKeywords());
		this.setProjectFile(proposal.getProjectFile());
		this.setProjectShortCut("bla01");
		this.setProposal(proposal);
	}
	
	

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setMinStud(int minStud) {
		this.minStud = minStud;
	}
	public int getMinStud() {
		return minStud;
	}
	public void setMaxStud(int maxStud) {
		this.maxStud = maxStud;
	}
	public int getMaxStud() {
		return maxStud;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerDescription(String partnerDescription) {
		this.partnerDescription = partnerDescription;
	}
	public String getPartnerDescription() {
		return partnerDescription;
	}
	public void setContactPersons(ArrayList<Person> contactPersons) {
		this.contactPersons = contactPersons;
	}
	public ArrayList<Person> getContactPersons() {
		return contactPersons;
	}
	public void setEstimatedBegin(Date estimatedBegin) {
		this.estimatedBegin = estimatedBegin;
	}
	public Date getEstimatedBegin() {
		return estimatedBegin;
	}
	public void setPeriod(Date period) {
		this.period = period;
	}
	public Date getPeriod() {
		return period;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Department getDepartment() {
		return department;
	}
	public void setProjectFile(File projectFile) {
		this.projectFile = projectFile;
	}
	public File getProjectFile() {
		return projectFile;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setProjectShortCut(String projectShortCut) {
		this.projectShortCut = projectShortCut;
	}
	public String getProjectShortCut() {
		return projectShortCut;
	}

	public void setProposal(ProjectProposal proposal) {
		this.proposal = proposal;
	}

	public ProjectProposal getProposal() {
		return proposal;
	}

	public void setTopicID(long topicID) {
		this.topicID = topicID;
	}

	public long getTopicID() {
		return topicID;
	}
}
