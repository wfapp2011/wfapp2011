package data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DummyDatabase {

	private static DummyDatabase db = null;
	static String DATE_FORMAT = "yyyy-MM-dd";
	Department[] departments = createDummyDepartments();
		
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
	
	public ProjectProposal getProposal(String projectID){
		for (ProjectProposal project : projects) {
			if (project.toString().equals(projectID)) {
				return project;
			}
		}
		return null;
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
		ProjectProposal projectProposal = new ProjectProposal("Du für Deutschland - Nationale Prozessbibliothek", 4, 8);
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
		projectProposal.setDepartment(dep);
		
		ArrayList<Person>contactPersons = new ArrayList<Person>();
		Person p1 = new Person("Matthias Weidlich", "matthias.weidlich@hpi.uni-potsdam.de");
		Person p2 = new Person("Lisa Müller", "lisa.mueller@hpi.uni-potsdam.de");
		contactPersons.add(p1);
		contactPersons.add(p2);
		projectProposal.setContactPersons(contactPersons);
		projectProposal.setIsPublic(true);
		projectProposal.setLastModifiedAt(new Date());
		projectProposal.setLastModifiedBy(new Person("Prof. Weske", "mathias.weske@hpi.uni-potsdam.de"));
		
		Comment dummyComment = new Comment(p1, "Test");
		ArrayList<Comment> comments = new ArrayList<Comment>();
		comments.add(dummyComment);
		Comment anotherComment = new Comment(null, "blubb");
		comments.add(anotherComment);
		projectProposal.setComments(comments);
		
		return projectProposal;	
	}

	public Department[] getDepartments(){
			return departments;
	}
	
	//TODO get from DB
	private Department[] createDummyDepartments() {
		
		Department dep1 = new Department("Business Process Technology");
		Person prof1 = new Person("Prof. Weske", "mathias.weske@hpi.uni-potsdam.de");
		dep1.setProf(prof1);
		
		Department dep2 = new Department("Software Architecture");
		Person prof2 = new Person("Prof. Hirschfeld", "robert.hirschfeld@hpi.uni-potsdam.de");
		dep2.setProf(prof2);
		
		Department dep3 = new Department("Information Systems");
		Person prof3 = new Person("Prof. Naumann", "felix.naumann@hpi.uni-potsdam.de");
		dep3.setProf(prof3);
		
		Department[] departments = {dep1, dep2, dep3};
		return departments;
	}

}
