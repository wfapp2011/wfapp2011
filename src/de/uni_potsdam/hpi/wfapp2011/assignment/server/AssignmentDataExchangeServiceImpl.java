package de.uni_potsdam.hpi.wfapp2011.assignment.server;

import java.util.Collection;
import java.util.Map;

import de.uni_potsdam.hpi.wfapp2011.assignment.client.AssignmentDataExchangeService;
import de.uni_potsdam.hpi.wfapp2011.assignment.client.Project;
import de.uni_potsdam.hpi.wfapp2011.assignment.server.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.assignment.server.SQLTableException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AssignmentDataExchangeServiceImpl extends RemoteServiceServlet implements AssignmentDataExchangeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Project[] getProjects(){
		
		System.out.println("in getProjects");
		DbInterface db = new DbInterface();
		try {
			db.connect("Ba", "SS", 2011);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}

		Collection <Map <String,String>> result = db.executeQuery("SELECT Projectname,MinStud,MaxStud FROM Projecttopic");
		
		Project[] DBProjects = new Project[result.size()];
		int i = 0;
		for(Map<String,String> m : result){
			String ProjectID = m.get("projectname");
			int minStud = new Integer(m.get("minstud"));
			int maxStud = new Integer(m.get("maxstud"));
			DBProjects[i] = new Project(ProjectID, minStud, maxStud);
			i++;
		}

		return DBProjects;
	}
}
