package de.uni_potsdam.hpi.wfapp2011.proposals.server;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.ProcessInfo;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Department;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Person;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.ProjectProposal;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.ProjectProposalInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.logging.ProjectProposalLogger;

public class ProposalProvider extends RemoteServiceServlet implements ProjectProposalInterface {

	private static final long serialVersionUID = 1L;
	ProjectProposalLogger logger = ProjectProposalLogger.getInstance();
	
	// database
	private DbInterface dbConnection;
	private String type, semester;
	private int year;
	private UtilitiesDB helper = new UtilitiesDB();
	
	// dates
	private static String DATE_FORMAT = "dd/MM/yyyy";
	private static String TIME_FORMAT = "hh:mm";	
	private DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	private DateFormat tf = new SimpleDateFormat(TIME_FORMAT);
	
	public ProposalProvider() {		
		dbConnection = new DbInterface();
		// TODO: Replace with URL
		type = ProcessInfo.getTypeFromURL("");
		semester = ProcessInfo.getSemesterFromURL("");
		year = ProcessInfo.getYearFromURL("");
	}

	/**
	 * Executes query at DB to get proposal with proposalID.
	 * Builds a ProjectProposal out of the data and returns it.
	 * 
	 * Caution: comments and files will not be included.
	 * Use getComments(int proposalId) and 
	 * 
	 * @param proposalId 
	 * @return ProjectProposal
	 */
	public ProjectProposal getProjectProposal(int proposalId) {
		ProjectProposal proposal = null;		
		String query = "SELECT * FROM PROJECTPROPOSAL "+
					   "WHERE PROJECTID = " + proposalId;	
		try {
			dbConnection.connect(type, semester, year);
			ResultSet resultset = dbConnection.executeQueryDirectly(query);
			if (resultset.next()) {
				proposal = createProposalFromResult(resultset);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return proposal;
	}

	/**
	 * Helper method for getProjectProposal.
	 * Builds the proposal from the data.
	 * 
	 * @param resultset
	 * @return ProjectProposal
	 * @throws SQLException
	 */
	private ProjectProposal createProposalFromResult(ResultSet resultset) throws SQLException {

		ProjectProposal proposal = new ProjectProposal();
		
		// project id
		proposal.setProjectId(resultset.getInt("projectid"));
		
		// project name and description
		proposal.setProjectName(resultset.getString("projectname"));
		
		if (resultset.getString("projectdescription") == null) {
			proposal.setProjectDescription("");
		} else {
			proposal.setProjectDescription(resultset.getString("projectdescription"));
		}
		
		// partner name and description
		if (resultset.getString("partnername") == null) {
			proposal.setPartnerName("");
		} else {
			proposal.setPartnerName(resultset.getString("partnername"));
		}
		
		if (resultset.getString("partnerdescription") == null) {
			proposal.setPartnerDescription("");
		} else {
			proposal.setPartnerDescription(resultset.getString("partnerdescription"));
		}
		
		// min max students
		proposal.setMaxStud(resultset.getInt("maxstud"));
		proposal.setMinStud(resultset.getInt("minstud"));
				
		// estimated begin
		if (resultset.getDate("estimatedbegin") != null) {
			Date estimatedBegin = resultset.getDate("estimatedbegin");
			java.util.Date begin = new java.util.Date (estimatedBegin.getTime());
			proposal.setEstimatedBegin(begin);
			proposal.setEstimatedBeginString(df.format(begin));
		}
		
		// keywords
		if (resultset.getString("keywords") == null) {
			proposal.setKeywords("");
		} else {
			proposal.setKeywords(resultset.getString("keywords"));
		}
		
		//department
		int departmentId = resultset.getInt("department");
		Department dep = helper.getDepartment(dbConnection, departmentId);
		proposal.setDepartment(dep);
		
		// contact persons
		List<Person> contactPersons = helper.getContactPersonsFromProposal(dbConnection, proposal.getProjectId());
		proposal.setContactPersons(contactPersons);
			
		//last modified
		if (resultset.getTimestamp("lastmodifiedat") != null) {
			Timestamp lastModifiedAt = resultset.getTimestamp("lastmodifiedat");
			java.util.Date lastModified = new java.util.Date (lastModifiedAt.getTime());
			proposal.setLastModifiedAt(lastModified);
			proposal.setLastModifiedAtString(df.format(lastModified)+" um "+tf.format(lastModified)+" Uhr");
		}
		
		int lastModifiedId = resultset.getInt("lastmodifiedby");
		Person person = helper.getPerson(dbConnection, lastModifiedId);	
		proposal.setLastModifiedBy(person);
	
		// boolean attributes
		if (resultset.getString("isDeleted") == null || resultset.getString("isDeleted") == "FALSE"){
			proposal.setIsDeleted(false);
		} else {
			proposal.setIsDeleted(true);
		}
		if (resultset.getString("isPublic") == null || resultset.getString("isPublic") == "FALSE"){
			proposal.setIsPublic(false);
		} else {
			proposal.setIsPublic(true);
		}
		if (resultset.getString("isRejected") == null || resultset.getString("isRejected") == "TRUE"){
			proposal.setIsRejected(true);
		} else {
			proposal.setIsRejected(false);
		}
		
		return proposal;
	}
	
	/**
	 * Saves given proposal in DB.
	 * @param proposalToSave
	 * @return
	 * @throws SQLException
	 * @throws SQLTableException
	 */
	public int saveNewProposal(ProjectProposal proposalToSave) throws SQLException, SQLTableException {

		int newId = -1;
		dbConnection.connect(type, semester, year);		
		
		// get id for new proposal
		String queryMaxID = "SELECT MAX(projectID) AS maxid "+
						    "FROM PROJECTPROPOSAL";
		ResultSet maxIds;	
		maxIds = dbConnection.executeQueryDirectly(queryMaxID);	
		if (maxIds.next()) {
			proposalToSave.setProjectId(maxIds.getInt("maxid") + 1);
			newId = proposalToSave.getProjectId();
			saveProposal(proposalToSave);	
		}	
		dbConnection.disconnect();
		// log that new proposal has been saved

		logger.logNewProjectProposal(type, semester, year, proposalToSave.
					getLastModifiedBy().getEmail(),
					proposalToSave.getProjectName(),
					proposalToSave.getDepartmentNameOr(""));
			
		System.out.println("Saved proposal in DB: "+proposalToSave.getProjectId());
		return newId;
	}

	/**
	 * Updates given proposal in DB.
	 * @param proposalToSave
	 * @throws SQLTableException
	 */
	public void updateProposal(ProjectProposal proposalToSave) throws SQLTableException {
		// all parameters must be overwritten
		String qDeleteProposal = "DELETE FROM PROJECTPROPOSAL " +
								 "WHERE PROJECTID = "+proposalToSave.getProjectId();		
		String qDeleteContactPersons = queryDeleteContactPersons(proposalToSave.getProjectId());	
		dbConnection.connect(type, semester, year);	
		dbConnection.executeUpdate(qDeleteProposal);
		dbConnection.executeUpdate(qDeleteContactPersons);
		saveProposal(proposalToSave);
		
		// log that a given proposal has been updated
		logger.logChangedProjectProposal(type, semester, year, proposalToSave.getLastModifiedBy().getEmail(), proposalToSave.getProjectName());
		dbConnection.disconnect();
		System.out.println("Updated proposal in DB: "+proposalToSave.getProjectId());
	}
	
	/**
	 * Be aware that this is a helper method and the DB-Connection is already established.
	 * @param proposalToSave
	 * @throws SQLTableException
	 */
	private void saveProposal(ProjectProposal proposalToSave) throws SQLTableException{
		// PREPARE PARAMETERS	
		// estimated begin
		String estimatedBegin = "null";
		if (proposalToSave.getEstimatedBegin() != null) {
			Date begin = new Date(proposalToSave.getEstimatedBegin().getTime());
			estimatedBegin = "'"+begin+"'";
		}

		// department id
		int department = proposalToSave.getDepartment().getId();
		
		// last modified 
		Timestamp lastModifiedAt = new Timestamp(proposalToSave.getLastModifiedAt().getTime());
		int lastModifiedBy = proposalToSave.getLastModifiedBy().getId();
		
		// BUILD QUERYSTRING
		String qInsertProposal = "INSERT INTO PROJECTPROPOSAL ("
										+ "projectID, projectName, projectDescription, keywords,"
										+ "minStud, maxStud, estimatedBegin,"
										+ "partnerName, partnerDescription,"
										+ "department, lastModifiedAt, lastModifiedBy,"
										+ "isPublic, isDeleted, isRejected)" + 
								 "VALUES ('"
										+ proposalToSave.getProjectId()+ "', '"
										+ proposalToSave.getProjectName()+ "', '"
										+ proposalToSave.getProjectDescription()+ "', '"
										+ proposalToSave.getKeywords()+ "', "											
										+ proposalToSave.getMinStud()+ ", "
										+ proposalToSave.getMaxStud()+ ","
										+ estimatedBegin+ ", '"											
										+ proposalToSave.getPartnerName()+ "', '"
										+ proposalToSave.getPartnerDescription() + "', "											
										+ department+", '"			
										+ lastModifiedAt + "',"
										+ lastModifiedBy+","
										+ "false, false, false " +
									")";
		
		dbConnection.executeUpdate(qInsertProposal);
		saveContactPersons(dbConnection, proposalToSave.getProjectId(), proposalToSave.getContactPersons());	
		System.out.println("saved:"+proposalToSave.getKeywords());
	}
	
	
	/**
	 * Helper method
	 * @param dbConnection
	 * @param projectId
	 * @param contactPersons
	 * @throws SQLTableException
	 */
	private void saveContactPersons (DbInterface dbConnection, int projectId, List<Person> contactPersons) throws SQLTableException{
		//TODO existieren auf jeden fall schon alle personen in der db?
		for (Person person: contactPersons){
			String qInsertContactPerson = queryInsertContactPerson(projectId, person.getId());
			dbConnection.executeUpdate(qInsertContactPerson);
		}	
	}

	private String queryInsertContactPerson(int projectId, int personId){
		return  "INSERT INTO CONTACTPERSON (projectID, personId)"+
		 		"VALUES ("+projectId +","+ personId+")";
	}
	
	private String queryDeleteContactPersons(int projectId){
		return  "DELETE FROM CONTACTPERSON "+
		 		"WHERE PROJECTID = "+projectId;
	}
	
	public ArrayList<ProjectProposal> getProjectProposals() {
		ArrayList<ProjectProposal> proposals = new ArrayList<ProjectProposal>();
		ProjectProposal proposal = null;
		String query = "SELECT * FROM PROJECTPROPOSAL";
		try {
			dbConnection.connect(type, semester, year);
			ResultSet resultset = dbConnection.executeQueryDirectly(query);
			while (resultset.next()) {
				proposal = createProposalFromResult(resultset);
				proposals.add(proposal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		dbConnection.disconnect();
		return proposals;
	}

	public void deleteProposal(int proposalId) {
		// DELETE FROM PROJECTPROPOSAL
		// WHERE PROJECTID = 22;

	}
	
	public void setVotable(ProjectProposal proposal, Boolean notVotable){
		System.out.println(notVotable);
		int rejectedValue;
		if (notVotable){
			rejectedValue = 1;
		} else {
			rejectedValue = 0;
		}
		String query = "UPDATE PROJECTPROPOSAL SET ISREJECTED=" + rejectedValue + " WHERE PROJECTID=" + proposal.getProjectId();
		try {
			dbConnection.connect(type, semester, year);
			dbConnection.executeUpdate(query);
			dbConnection.disconnect();
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		
		if (!notVotable){
			String query2 = "INSERT INTO projectTopic (topicID, projectName, minStud, maxStud) Values (" 
				+ proposal.getProjectId() + ", '" + proposal.getProjectName() + "'," + proposal.getMinStud() + "," + proposal.getMaxStud() + ")";
			try {
				dbConnection.connect(type, semester, year);
				dbConnection.executeUpdate(query2);
				dbConnection.disconnect();
			} catch (SQLTableException e) {
				e.printStackTrace();
			}
		} else {
			try {
				String query3 = "DELETE FROM projectTopic WHERE TOPICID =" +  proposal.getProjectId();
				dbConnection.connect(type, semester, year);
				dbConnection.executeUpdate(query3);
				dbConnection.disconnect();
			} catch (SQLTableException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void setPublicness(int projectID, Boolean publicness) {
		int publicValue;
		if (publicness){
			publicValue = 1;
		} else {publicValue = 0;}
		String query = "UPDATE PROJECTPROPOSAL SET ISPUBLIC=" + publicValue + " WHERE PROJECTID=" + projectID;
		try {
			dbConnection.connect(type, semester, year);
			System.out.println(query);
			dbConnection.executeUpdate(query);
			dbConnection.disconnect();
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		
	}	
}
