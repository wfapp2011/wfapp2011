package de.uni_potsdam.hpi.wfapp2011.Activiti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import de.uni_potsdam.hpi.wfapp2011.server.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.server.SmtpEmailSender;

public class EmailSenderTask implements JavaDelegate {
	// TODO: Overide Dummys
	private String Type = "Ba";
	private String Semester = "SS";
	private int Year = 2011;
	private String Enddate = "01.04.2012";
	
	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		DbInterface connection = new DbInterface();
				
		// send reminder mail to all students, who haven't voted yet
		try {
			connection.connect(Type, Semester, Year);
			Collection<Map<String, String>> PersonsWithoutVoting = connection.executeQuery("SELECT NAME, EMAIL FROM PERSON " +
					"WHERE PERSONID NOT IN (SELECT PERSONID FROM VOTE)" +
					" AND ROLE = 'Student'");
		
			for (Map<String, String> person: PersonsWithoutVoting) {
				String name[] = person.get("name").split("\\.");
				
				for (int i = 0; i<name.length; i++)			
					name[i] = name[i].substring(0,1).toUpperCase() + name[i].substring(1).toLowerCase();
				
				System.out.printf("%s %s\n",name[0],name[1]);
				
				String emailText =  "Sehr geehrte/r "+name[0]+" "+name[1]+",\n"+
									"\n" +
								    "Sie haben sich bei der Projektwahl "+Semester+" "+Year+" angemeldet. Allerdings liegt noch keine Stimmenabgabe von Ihnen vor.\n" +
								    "Sind sie sich sicher, dass die nicht an der Projektvergabe "+Semester+" "+Year+" teilnehmen m\u00F6chten?\n" +
								    "Die Themenwahl geht noch bis zum "+Enddate+".\n" +
								    "\n" +
								    "Mit freundlichen Gr\u00FCßen\n" +
								    "WFAPP2011 - Ihr Votingsystem\n\n" +
								    "Diese Email dient lediglich zur Information und muss nicht beantwortet werden.";
				SmtpEmailSender.getInstance().sendEmail(person.get("email"), name[0]+" "+name[1], "Projektwahl "+Semester+" "+Year, emailText);
			}
			connection.disconnect();
		} catch (Exception e) {
			System.out.printf("Error in EmailSender: %s\n", e.toString());
			System.out.printf("%s\n", e.getStackTrace().toString());
		}
		
		// send mail with last voting to the rest of the students
		try {
			connection.connect(Type, Semester, Year);
			Collection<Map<String, String>> PersonsWithVoting = connection.executeQuery("SELECT NAME, EMAIL, PROJECTNAME, PRIORITY" +
					" FROM PERSON, PROJECTTOPIC, VOTE" +
					" WHERE PERSON.PERSONID = VOTE.PERSONID" +
					" AND VOTE.PROJECTID = PROJECTTOPIC.TOPICID" +
					" AND ROLE = 'Student'" +					
					" ORDER BY NAME, PRIORITY");
			
			Map<String, ArrayList<String>> VotingList = new HashMap<String, ArrayList<String>>();
		
			for (Map<String, String> person: PersonsWithVoting) {
				if (VotingList.get(person.get("name")) == null)
					VotingList.put(person.get("name"), new ArrayList<String>());
				VotingList.get(person.get("name")).add(person.get("projectname"));
			}
			
			for (String person: VotingList.keySet()) {
				String name[] = person.split("\\.");
				
				for (int i = 0; i<name.length; i++)			
					name[i] = name[i].substring(0,1).toUpperCase() + name[i].substring(1).toLowerCase();
				
				String emailText =  "Sehr geehrte/r "+name[0]+" "+name[1]+",\n"+
				"\n" +
			    "Sie haben sich bei der Projektwahl "+Semester+" "+Year+" angemeldet.\n" +
			    "Ihrer momentan Wahl ist wie folgt: \n";
				
				for (int i = 0; i < VotingList.get(person).size(); i++) 
					emailText = emailText+"\n"+ (i+1) + ". "+ VotingList.get(person).get(i)+"\n";
					
	
				emailText = emailText + "Bis zum "+Enddate+" k\u00F6nnen sie ihre Stimmenabgabe noch \u00E4ndern.\n" +
				 						"\n" +
				 						"Mit freundlichen Gr\u00FCßen\n" +
				 						"WFAPP2011 - Ihr Votingsystem\n\n" +		
				 						"Diese Email dient lediglich zur Information und muss nicht beantwortet werden.";
				
				SmtpEmailSender.getInstance().sendEmail(person+"@student.hpi.uni-potsdam.de", name[0]+" "+name[1], "Projektwahl "+Semester+" "+Year, emailText);
			}
			connection.disconnect();	
		} catch (Exception e) {
			System.out.printf("Error in EmailSender: %s\n", e.toString());
			System.out.printf("%s\n", e.getStackTrace().toString());
		}
	}
}
