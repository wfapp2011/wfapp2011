package data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DummyDatabase {

	private static DummyDatabase db = null;
	static String DATE_FORMAT = "yyyy-MM-dd";
	Department[] departments = { new Department("ABC"), new Department("XYZ"),
			new Department("Business Process Technology") };

	private ArrayList<ProjectProposal> projects = new ArrayList<ProjectProposal>();

	public static DummyDatabase getInstance() {
		if (db == null) {
			db = new DummyDatabase();
			db.addProjectProposal(db.getDummyProjectProposal());

		}
		return db;
	}

	public void addProjectProposal(ProjectProposal projectProp) {
		projects.add(projectProp);
	}

	public void deleteProjectProposal(String projectID) {
		for (ProjectProposal project : projects) {
			if (project.toString().equals(projectID)) {
				project.setIsDeleted(true);
				return;
			}
		}
	}

	public ArrayList<ProjectProposal> getProjectProposals() {
		return projects;
	}

	private ProjectProposal getDummyProjectProposal() {
		ProjectProposal projectProposal = new ProjectProposal("Du f�r Deutschland - Nationale Prozessbibliothek", 4, 8);
		projectProposal.setProjectDescription("Dieses Projekt ist das tollste der Welt.");
		projectProposal.setPartnerDescription("Projektpartner ist das BMI.");
		
		DateFormat f = new SimpleDateFormat(DATE_FORMAT);
		Date date = new Date();
		try {
			date = f.parse("2011-10-02");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		projectProposal.setEstimatedBegin(date);
		
		Department dep = departments[2];
		Person prof = new Person("Prof. Weske", "mathias.weske@hpi.uni-potsdam.de");
		dep.setProf(prof);
		projectProposal.setDepartment(dep);
		
		ArrayList<Person>contactPersons = new ArrayList<Person>();
		Person p1 = new Person("Matthias Weidlich", "matthias.weidlich@hpi.uni-potsdam.de");
		Person p2 = new Person("Lisa M�ller", "lisa.mueller@hpi.uni-potsdam.de");
		contactPersons.add(p1);
		contactPersons.add(p2);
		projectProposal.setContactPersons(contactPersons);
		projectProposal.setIsPublic(true);
		projectProposal.setLastModifiedAt(new Date());
		projectProposal.setLastModifiedBy(new Person("Prof. Weske", "mathias.weske@hpi.uni-potsdam.de"));
		
		return projectProposal;	
	}

	public Department[] getDepartments(){
			return departments;
	}

}
