package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;
import java.util.Map;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Widget which shows the statistic of students' votes 
 * 
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 11.47
 * @see com.google.gwt.user.client.ui.Composite
 *
 */

public class StatisticView extends Composite {

	private final VotingDatabaseServiceAsync databaseService = GWT.create(VotingDatabaseService.class);
	private FlexTable flexTable;
	private HorizontalPanel horizontalPanel;
	private int maxVotes = 0;
	private VerticalPanel verticalPanel;
	private HTMLPanel panel;
	private Button btnGesamt;

	/**
	 *  constructor which creates all windows and buttons and loads the statistic for all votes
	 */
	
	public StatisticView() {
		loadMaxVotes();
		
		//create main panel
		verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		//create headline
		panel = new HTMLPanel("<h1>Statistik:</h1>");
		verticalPanel.add(panel);
		
		//create button panel
		horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		
		//create grouping button for all votes
		btnGesamt = new Button("Gesamt");
		btnGesamt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showStat(0);
			}
		});
		horizontalPanel.add(btnGesamt);
				
		//table where the bar graph is displayed
		flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setWidth("100%");
		flexTable.addStyleName("topicList");
		createHeader();
		
		//load the statistic for all votes combined
		showStat(0);	
	}

	private void addButtons() {
		//create grouping buttons, depending on the number of the maximum votes
		for(int i = 1; i<=maxVotes; i++) {
			final int vote = i;
			Button temp = new Button(i+". Wahl");
			temp.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					showStat(vote);
				}
			});
			horizontalPanel.add(temp);
		}
	}
	
	private void createHeader()
	{	
		//setup the header for the bar graph table
		flexTable.setText(0, 0, "Platz");
		flexTable.setText(0, 1, "Projektname");
		flexTable.setText(0, 2, "Stimmen");
		
		HTMLTable.CellFormatter formatter = flexTable.getCellFormatter();
		
		for (int i = 0; i<3; i++)
			formatter.addStyleName(0, i, "topicListHeader");
		
		formatter.setWidth(0, 0, "1%");
		formatter.setWidth(0, 1, "30%");
		formatter.setWidth(0, 2, "1%");
		formatter.setWidth(0, 3, "*");
	}
	
	private void showStat(int prio){
		//call the remote procedure call to load the statistic for the given priority (0 means all votes combined 1 for first vote only, etc)
		// TODO replace dummy processID
		databaseService.getStatistic("Ba", "SS", 2011, prio, new AsyncCallback<ArrayList<Map<String,Integer>>>(){

			@Override
			public void onFailure(Throwable caught) {
				//display an error message if the statistic couldn't load
				flexTable.removeAllRows();
				flexTable.setText(1, 0, "Statistik konnte nicht geladen werden.");
			}

			@Override
			public void onSuccess(ArrayList<Map<String,Integer>> result) {
				//write topic name and count of votes into the table
				flexTable.removeAllRows();
				createHeader();
				int j = 1;
				int sum = 0;
				for(Map<String,Integer> i: result){
					for (String k: i.keySet()) {
						flexTable.setText(j, 0, j+".");
						flexTable.setText(j, 1,k+": ");
						flexTable.setText(j, 2,i.get(k).toString());
						sum += i.get(k);
					}					
					j++;
				}
				
				//calculate length of the bar and add it to the table
				j = 1;;
				for(Map<String,Integer> i: result){
					for (String k: i.keySet()) {
						int length = Math.round((new Float(i.get(k))/new Float(sum))*100.0f);
						Image bar = new Image("StatisticBackground.png");
						bar.setSize(length+"%", "20px");
						flexTable.setWidget(j, 3, bar);
					}					
					j++;
				}				
			}			
		});
	}
	
	private void loadMaxVotes() {
		//call remote procedre to load the maximum votes
		//TODO replace dummy processID
		databaseService.numberOfVotes("Ba", "SS", 2011, new AsyncCallback<Integer>(){

			@Override
			public void onFailure(Throwable caught) {
				//display an error message
				Window.alert("Fehler beim Laden: "+caught.toString());
			}

			@Override
			public void onSuccess(Integer result) {
				//set the number of maximum votes and add the grouping buttons
				maxVotes = result;
				addButtons();
			}
			
		});
	}
}
