package de.uni_potsdam.hpi.wfapp2011.client;

//# Imports #
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * provides different dates to be changed by the admin
 */
public class ProjectEditPopUp extends MySimplePopUp {
	
	private DateBox deadlineProposalCollection_dateBox;
	private DateBox deadlineTopicsPublication_dateBox;
	private DateBox deadlineVoting_dateBox;
	private DateBox deadlineMatching_dateBox;
	private DateBox deadlineProcess_dateBox;
	private IntegerBox numberOfVotes;
	private VerticalPanel main = new VerticalPanel();
	
	private Date deadlineProposalCollection;
	private Date deadlineTopicsPublication;
	private Date deadlineVoting;
	private Date deadlineMatching;
	private Date deadlineProcess;
	
	private String sdeadlineProposalCollection = "";
	private String sDeadlineTopicsPublication = "";
	private String sDeadlineVoting = "";
	private String sDeadlineMatching = "";
	private String sDeadlineProcess = "";
	private int numberVotes = 5;
	
	private boolean debug = true; 

	/**
	 * Constructor
	 * creates a popup to given project
	 * @param headline : name of the project (includes type, year, semester)
	 * @param content : old data (old dates)
	 */
	public ProjectEditPopUp(String headline, Collection<Map<String,String>> content) {
		
		// getInformation
		extractInformation(content);
		
		// create new Dates
		deadlineProposalCollection = getDate(sdeadlineProposalCollection);
		deadlineTopicsPublication = getDate(sDeadlineTopicsPublication);
		deadlineVoting = getDate(sDeadlineVoting);
		deadlineMatching = getDate(sDeadlineMatching);
		deadlineProcess = getDate(sDeadlineProcess);
		
		
		// Generate Content		
		initWidget(main);
		
		// HEADLINE
		HTML hdln = new HTML("<h2>"+headline+"</h2>");
		main.add(hdln);
		
		// Generate Dateboxes
		deadlineProposalCollection_dateBox = newDatebox("Ende der Einsendung von Projektvorschl\u00E4gen",deadlineProposalCollection);
		deadlineTopicsPublication_dateBox = newDatebox("Begin der Votingphase",deadlineTopicsPublication);
		deadlineVoting_dateBox = newDatebox("Ende der Votingphase",deadlineVoting);		
		deadlineMatching_dateBox = newDatebox("Ende der Zuteilungsphase",deadlineMatching);
		deadlineProcess_dateBox = newDatebox("Ende des (Gesamt-)Prozesses",deadlineProcess);
		
		// Number of Votes
		HorizontalPanel votes = new HorizontalPanel();
		main.add(votes);
		
		Label lblVotes = new Label("Stimmenanzahl pro Student");
		votes.add(lblVotes);
		
		numberOfVotes = new IntegerBox();
		numberOfVotes.setValue(numberVotes);
		votes.add(numberOfVotes);
		
		// Buttonbar
		buttonbar = new HorizontalPanel();
		main.add(buttonbar);
		
	}

	@SuppressWarnings("deprecation")
	private Date getDate(String sDate){
		
		Date temp = new Date();
		
		if (!sDate.equals("")){
			temp.setYear(Integer.valueOf(sDate.split(" ")[2])-1900);
			temp.setMonth(sMonthToInt(sDate.split(" ")[1])-1);
			temp.setDate(Integer.valueOf(sDate.split(" ")[0]));
		}
		
		return temp;
	}
	
	private void extractInformation(Collection<Map<String,String>> content) {

		// Collect all Information from content
		for (Map<String,String> map:content){
			// System.out.println(map);
			String key = map.get("name");
			if(key.equals("deadlineProposalCollection")){
				sdeadlineProposalCollection = map.get("value");
				continue;
			}
			if(key.equals("deadlineTopicsPublication")){
				sDeadlineTopicsPublication = map.get("value");
				continue;
			}
			if(key.equals("deadlineVoting")){
				sDeadlineVoting = map.get("value");
				continue;
			}
			if(key.equals("deadlineMatching")){
				sDeadlineMatching = map.get("value");
				continue;
			}
			if(key.equals("deadlineProcess")){
				sDeadlineProcess = map.get("value");
				continue;
			}
			if(key.equals("votes")){
				numberVotes = Integer.valueOf(map.get("value"));
				continue;
			}
		}
		
	}

	private int sMonthToInt(String month) {
		// Converts string to Integervalue of the month
		if (month.equals("Jan") || month.equals("1")) return 1;
		if (month.equals("Feb") || month.equals("2")) return 2;
		if (month.equals("Mar") || month.equals("3")) return 3;
		if (month.equals("Apr") || month.equals("4")) return 4;
		if (month.equals("May") || month.equals("5")) return 5;
		if (month.equals("Jun") || month.equals("6")) return 6;
		if (month.equals("Jul") || month.equals("7")) return 7;
		if (month.equals("Aug") || month.equals("8")) return 8;
		if (month.equals("Sep") || month.equals("9")) return 9;
		if (month.equals("Oct") || month.equals("10")) return 10;
		if (month.equals("Nov") || month.equals("11")) return 11;
		if (month.equals("Dec") || month.equals("12")) return 12;
		
		return 1;
	}

	@SuppressWarnings("deprecation")
	private boolean saveCheckedDate(DateBox box, Date oldValue, String name, boolean needToCheck, Map<String,String> returnValues){
		// saves new date
		// maybe dates has to be checked, if the project has been started
		// if already started dates can only be change to future
		boolean temp = false;		
		String value = "";
		
		if (debug) {
			System.out.println(name);
			System.out.println(box.getValue().getTime());
			System.out.println(oldValue.getTime());
			System.out.println(box.getValue().getTime()-oldValue.getTime());
		}
		
		if (box.getValue().getTime()-oldValue.getTime()+86400000>=0 || !needToCheck){
			
			value += box.getValue().getDate();
			value += " "+(box.getValue().getMonth()+1);
			value += " "+(box.getValue().getYear()+1900);
			
			returnValues.put(name, value);
			
			temp = true;
		}	
		
		return temp;
	}
	
	/**
	 * read out all new dates and returns checked dates
	 * 
	 * @param started : flag if the project has already been started
	 * @return returns all checked dates in a Map
	 */
	public Map<String,String> getContent(boolean started){
		Map<String,String> returnValues = new HashMap<String,String>();
		
		if (!saveCheckedDate(deadlineProposalCollection_dateBox,deadlineProposalCollection,"deadlineProposalCollection", started,returnValues)) return null;
		if (!saveCheckedDate(deadlineTopicsPublication_dateBox,deadlineTopicsPublication,"deadlineTopicsPublication", started,returnValues)) return null;
		if (!saveCheckedDate(deadlineVoting_dateBox,deadlineVoting,"deadlineVoting", started,returnValues)) return null;
		if (!saveCheckedDate(deadlineMatching_dateBox,deadlineMatching,"deadlineMatching", started,returnValues)) return null;
		if (!saveCheckedDate(deadlineProcess_dateBox,deadlineProcess,"deadlineProcess", started,returnValues)) return null;		
		
		returnValues.put("votes", String.valueOf(numberOfVotes.getValue()));
		
		return returnValues;
	}

	private DateBox newDatebox(String lbl, Date value){
		// creating a new datebox with a given label
		HorizontalPanel panel = new HorizontalPanel();
		main.add(panel);
		
		Label lbldeadlineProposalCollection = new Label(lbl);
		panel.add(lbldeadlineProposalCollection);
		
		DateBox box = new DateBox();
		box.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd.MM.yyyy")));
		box.setValue(value);
		panel.add(box);
		
		return box;
	}
}