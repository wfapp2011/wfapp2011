package de.uni_potsdam.hpi.wfapp2011.overview.server;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ibm.icu.text.SimpleDateFormat;
import de.uni_potsdam.hpi.wfapp2011.Logging.LoggingReader;
import de.uni_potsdam.hpi.wfapp2011.Logging.ProjectProposalLogger;
import de.uni_potsdam.hpi.wfapp2011.Logging.VotingLogger;
import de.uni_potsdam.hpi.wfapp2011.activiti.ProcessAdministration;
import de.uni_potsdam.hpi.wfapp2011.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifierException;
import de.uni_potsdam.hpi.wfapp2011.overview.client.StatisticProviderInterface;

public class StatisticProvider  extends RemoteServiceServlet implements StatisticProviderInterface{

	public String getNumberOfProposals(ProcessIdentifier pId){
		//Projektvorschl√§ge
		
		LoggingReader loggingReader = new LoggingReader(pId);
		try {
			DbInterface.initializeMetaTables();
			DbInterface.initializeDatabase(pId.getType(), pId.getSemester(), pId.getYear());
			ProjectProposalLogger.getInstance().logNewProjectProposal(pId, "email@example.com","Extraction", "Professor2");
			ProjectProposalLogger.getInstance().logNewProjectProposal(pId, "mail@example.com", "ExampleProject", "Professor1");
			ProjectProposalLogger.getInstance().logChangedProjectProposal(pId, "newMail@example.com", "Extraction");
		} catch (ProcessIdentifierException e) {
			e.toString();
			e.printStackTrace();
			System.out.println("Bin im LoggerSimulateClass. Catch: "+e);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}

		int anz = loggingReader.getNumberOfProjectProposals();
		String anzahl = ((Integer) anz).toString();
		
		
		String ichTeste = anzahl + " Projektvorschläge.";
		return ichTeste;
	}
	
	public String getNumberOfVotings(ProcessIdentifier pId){
		
		String votings="votings";
		LoggingReader loggingReader = new LoggingReader(pId);
		String[] vote = {"Projekt 2", "Projekt 4", "Projekt 5", "Projekt 6"};
		try {
			VotingLogger.getInstance().logNewVote(pId, "test@test", vote);
			VotingLogger.getInstance().logNewVote(pId, "test2@test",  vote);
			VotingLogger.getInstance().logNewVote(pId, "test4@test",  vote);
		} catch (ProcessIdentifierException e) {
			e.printStackTrace();
		}
		
		int numberOfVotings = loggingReader.getNumberOfVotings();
		votings = numberOfVotings+" Studenten haben gewählt.";
		return votings;
	}
	
	@SuppressWarnings("deprecation")
	public String[] getDeadlines(ProcessIdentifier pId){
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
		DbInterface db = new DbInterface();
		Date today = new Date();
		Date tomorrow = new Date();
		tomorrow.setDate(today.getDate() +1);
		try {
			db.connect(pId.getType(), pId.getSemester(), pId.getYear());
			db.executeUpdate("INSERT INTO configurations(name, value) VALUES ('"+Constants.DEADLINE_TOPICS_PUBL+"','"+df.format(new Date())+"');");
			db.executeUpdate("INSERT INTO configurations(name, value) VALUES ('"+Constants.DEADLINE_VOTING+"','"+df.format(today)+"');");
			db.executeUpdate("INSERT INTO configurations(name, value) VALUES ('"+Constants.DEADLINE_MATCHING+"','"+df.format(tomorrow)+"');");
			tomorrow.setDate(tomorrow.getDate()+1);
			db.executeUpdate("INSERT INTO configurations(name, value) VALUES ('"+Constants.DEADLINE_PROCESS+"','"+df.format(tomorrow)+"');");
			db.executeUpdate("INSERT INTO configurations(name, value) VALUES ('"+Constants.DEADLINE_PROPOSAL_COL+"','"+df.format(new Date())+"');");
		} catch (SQLTableException e) {
			System.out.println("Couldn't insert new dates! Already in Database");
		}
		HashMap<String, Date> deadlineMap = ProcessAdministration.getInstance().loadDeadlinesFromDatabase(pId); 
		
		String[] deadlines = new String[3];
		deadlines[0] = df.format(deadlineMap.get(Constants.DEADLINE_TOPICS_PUBL));
		deadlines[1] = df.format(deadlineMap.get(Constants.DEADLINE_VOTING));
		deadlines[2] = df.format(deadlineMap.get(Constants.DEADLINE_MATCHING));
		
		return deadlines;
	}
	
	@SuppressWarnings("deprecation")
	public Collection<String[]> getLogEntries(ProcessIdentifier pId){
		
		LoggingReader loggingReader = new LoggingReader(pId);
		Date today = new Date();
		Date yesterday = new Date();
		yesterday.setDate(today.getDate() - 1);
		return loggingReader.getLog(yesterday, today);
		
	}

}