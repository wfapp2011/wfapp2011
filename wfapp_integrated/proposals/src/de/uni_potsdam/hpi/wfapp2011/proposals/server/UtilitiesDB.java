package de.uni_potsdam.hpi.wfapp2011.proposals.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Department;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Person;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.logging.ProjectProposalLogger;

public class UtilitiesDB extends RemoteServiceServlet {

	private static final long serialVersionUID = 1L;
	ProjectProposalLogger logger = ProjectProposalLogger.getInstance();

	public UtilitiesDB() {		
	}
	
	/**
	 * Helper method to get department from id. 
	 * @param departmentId
	 * @return department
	 */
	public Department getDepartment(DbInterface dbConnection, int departmentId){	
		Department dep = null;
		String query = "SELECT * FROM DEPARTMENT "+
					   "WHERE DEPARTMENTID = " + departmentId;	
		try {
			ResultSet resultset = dbConnection.executeQueryDirectly(query);
			if (resultset.next()) {
				// build department object
				dep = new Department();
				dep.setId(departmentId);
				dep.setName(resultset.getString("name"));
				dep.setSymbol(resultset.getString("shortcut"));	
				Person prof = getPerson(dbConnection, resultset.getInt("professor"));
				dep.setProf(prof);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return dep;
	}
	
	/**
	 * Helper method to get person from id. 
	 * @param personId
	 * @return Person
	 */
	public Person getPerson(DbInterface dbConnection, int personId){	
		Person person = null;	
		String query = "SELECT * FROM PERSON "+
					   "WHERE PERSONID = " + personId;		
		try {
			ResultSet resultset = dbConnection.executeQueryDirectly(query);
			if (resultset.next()) {
				person = new Person();
				person.setId(personId);
				person.setEmail(resultset.getString("email"));
				person.setName(resultset.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return person;
	}

	/**
	 * Helper method to get contact persons for specific proposal.
	 * @param projectid
	 * @return list of persons
	 */
	public List<Person> getContactPersonsFromProposal(DbInterface dbConnection, int proposalId){		
		List<Person> persons = new ArrayList<Person>();	
		String qGetPersonIds = "SELECT PERSONID FROM CONTACTPERSON "+
								"WHERE PROJECTID = "+proposalId;	
		try {
			ResultSet resultset = dbConnection.executeQueryDirectly(qGetPersonIds);
			if (resultset.next()) {
				do {
					Person person = getPerson(dbConnection, resultset.getInt("personid"));
					System.out.println(person.getName());
					if (person != null){persons.add(person);}				
				} while(resultset.next());
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persons;
	}
}
