package de.uni_potsdam.hpi.wfapp2011.overview.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.uni_potsdam.hpi.wfapp2011.overview.client.ServerRequesterInterface;
import de.uni_potsdam.hpi.wfapp2011.overview.shared.ProcessInformation;
import de.uni_potsdam.hpi.wfapp2011.servercore.activiti.ProcessAdministration;
import de.uni_potsdam.hpi.wfapp2011.servercore.activiti.ProcessStatus;
import de.uni_potsdam.hpi.wfapp2011.servercore.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.servercore.logging.LoggingReader;
import de.uni_potsdam.hpi.wfapp2011.servercore.session.SessionManagement;

/**
 * This class requests information from server-side classes. <br/>
 * Information like status of process, deadlines, projectProposal statistics and voting statistics.
 * 
 * @author Jannik Marten, Yanina Yurchenko
 *
 */

public class ServerRequester  extends RemoteServiceServlet implements ServerRequesterInterface{

	private static final long serialVersionUID = 399984967870244539L;
	
	/**
	 * This method requests information from other packages and returns it in the instance of ProcessInformation. <br/>
	 * This means status of process (active/inactive) and if status is active: deadlines, projectProposal statistics and voting statistics.
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series
	 * @return - a instance of ProcessInformation
	 */
	public ProcessInformation getInformations(String type, String semester, int year) {
		
		HashMap<String, Date> deadlineMap = new HashMap<String, Date>();
		System.out.println("Start getInformations");
		ProcessInformation pInfo = new ProcessInformation();
		deadlineMap = (ProcessAdministration.getInstance().loadDeadlinesFromDatabase(type, semester, year));
		System.out.println("LoadDeadlinesFromDatabses zuurueckgekommen"); 
		SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
		System.out.println("Simple Date Format erstellt");
		String statisticString = "";
		LoggingReader loggingReader = new LoggingReader(type, semester, year);
		System.out.println("Variablen erstellt");
		ProcessStatus processPhase = new ProcessStatus(type, semester, year);
		System.out.println("ProzessStatus erstellt");
		
		if(!(deadlineMap.get(Constants.DEADLINE_TOPICS_PUBL) != null && 
				deadlineMap.get(Constants.DEADLINE_VOTING) != null &&
				deadlineMap.get(Constants.DEADLINE_MATCHING) != null &&
				(new Date()).before(deadlineMap.get(Constants.DEADLINE_MATCHING)))){
			pInfo.setActiv(false);
			return pInfo;
		}
		
		//get deadlines
		pInfo.setActiv(true);
		String[] deadlines = new String[3];
		deadlines[0] = df.format(deadlineMap.get(Constants.DEADLINE_TOPICS_PUBL));
		deadlines[1] = df.format(deadlineMap.get(Constants.DEADLINE_VOTING));
		deadlines[2] = df.format(deadlineMap.get(Constants.DEADLINE_MATCHING));
		pInfo.setDeadlinesString(deadlines);
		System.out.println("Variablen gesetzt.");
		 
		//ProposalStatistics
		if(processPhase.isProjectProposalPhase() || processPhase.isFinalTopicDecisionPhase()){
			int count = loggingReader.getNumberOfProjectProposals(); 
			if (count==0){
				statisticString ="Noch keine Projektvorschl\u00E4ge vorhanden";
			}else if(count==1){
				statisticString ="Ein Projektvorschlag eingereicht";
			}else{
				statisticString = count + " Projektvorschl\u00E4ge eingereicht";
			}
		}
		//VotingStatistics
		else if(processPhase.isVotingPhase()){
			int count = loggingReader.getNumberOfVotings();
			if (count==0){
				statisticString ="Noch kein Student hat gew\u00E4hlt";
			}else if(count==1){
				statisticString ="Ein Student hat gew\u00E4hlt";
			}else{
				statisticString = count + " Studenten haben gew\u00E4hlt";
			}
		}
		pInfo.setStatistics(statisticString);
		
		//ProcessPhase Handling
		if(processPhase.isProjectProposalPhase() || processPhase.isFinalTopicDecisionPhase()){
			pInfo.setIsProposalPhase(true);
			pInfo.setIsVotingPhase(false);
			pInfo.setIisMatchingPhase(false);
		}
		else if(processPhase.isVotingPhase()){
			pInfo.setIsProposalPhase(false);
			pInfo.setIsVotingPhase(true);
			pInfo.setIisMatchingPhase(false);
		}
		else if(processPhase.isProjectMatchingPhase()){
			pInfo.setIsProposalPhase(false);
			pInfo.setIsVotingPhase(false);
			pInfo.setIisMatchingPhase(true);
		}else{
			pInfo.setIsProposalPhase(false);
			pInfo.setIsVotingPhase(false);
			pInfo.setIisMatchingPhase(false);
		}
		
		return pInfo;
	}
	
	/**
	 * close the session of the given id in the session management
	 * @param id : the session-ID of the user. 
	 */
	@Override
	public void logout(String id) {
		SessionManagement.getInstance().logout(Integer.valueOf(id));
	}
}