package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

public class ProjectEditPopUp extends MySimplePopUp {
	
	private DateBox start_dateBox;
	private DateBox end_dateBox;
	private IntegerBox numberOfVotes;

	@SuppressWarnings("deprecation")
	public ProjectEditPopUp(String headline, Collection<Map<String,String>> content) {
		
		// Collect all Information from content
		String sstartdate = "";
		String senddate = "";
		int numberVotes = 5;
		
		for (Map<String,String> map:content){
			// System.out.println(map);
			String key = map.get("name");
			if(key.equals("startdate")){
				sstartdate = map.get("value");
				continue;
			}
			if(key.equals("enddate")){
				senddate = map.get("value");
				continue;
			}
			if(key.equals("votes")){
				numberVotes = Integer.valueOf(map.get("value"));
				continue;
			}
		}
		
		Date startdate = new Date();
		if (!sstartdate.equals("")){
			startdate.setYear(Integer.valueOf(sstartdate.split(" ")[2])-1900);
			startdate.setMonth(sMonthToInt(sstartdate.split(" ")[1])-1);
			startdate.setDate(Integer.valueOf(sstartdate.split(" ")[0]));
		}
		Date enddate = new Date();
		if (!sstartdate.equals("")){
			enddate.setYear(Integer.valueOf(senddate.split(" ")[2])-1900);
			enddate.setMonth(sMonthToInt(senddate.split(" ")[1])-1);
			enddate.setDate(Integer.valueOf(senddate.split(" ")[0]));
		}		
		
		VerticalPanel main = new VerticalPanel();
		initWidget(main);
		
		// HEADLINE
		HTML hdln = new HTML("<h2>"+headline+"</h2>");
		main.add(hdln);
		
		// Voting Time start
		HorizontalPanel voting_start = new HorizontalPanel();
		main.add(voting_start);
		
		Label lblVotingTimestart = new Label("Voting Time (Start)");
		voting_start.add(lblVotingTimestart);
		
		start_dateBox = new DateBox();
		start_dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd.MM.yyyy")));
		start_dateBox.setValue(startdate);
		voting_start.add(start_dateBox);
				
		// Voting Time END
		HorizontalPanel voting_end = new HorizontalPanel();
		main.add(voting_end);
		
		Label lblVotingTimeend = new Label("Voting Time (End)");
		voting_end.add(lblVotingTimeend);
		
		end_dateBox = new DateBox();
		end_dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd.MM.yyyy")));
		end_dateBox.setValue(enddate);
		voting_end.add(end_dateBox);
		
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
	
	private int sMonthToInt(String month) {
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
	public Map<String,String> getContent(){
		Map<String,String> returnValues = new HashMap<String,String>();
		
		String startdate = "";
		startdate += start_dateBox.getValue().getDate();
		startdate += " "+(start_dateBox.getValue().getMonth()+1);
		startdate += " "+(start_dateBox.getValue().getYear()+1900);
		
		String enddate = "";
		enddate += end_dateBox.getValue().getDate();
		enddate += " "+(end_dateBox.getValue().getMonth()+1);
		enddate += " "+(end_dateBox.getValue().getYear()+1900);
		
		returnValues.put("startdate", startdate);
		returnValues.put("enddate", enddate);
		returnValues.put("votes", String.valueOf(numberOfVotes.getValue()));
		
		return returnValues;
	}

}
