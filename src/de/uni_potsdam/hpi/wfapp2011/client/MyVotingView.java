package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class MyVotingView extends Composite {
	private FlexTable flexTable;
	private HTMLPanel panelHeadline;
	private VerticalPanel mainPanel;
	private ArrayList<Vote> Voting;
	private final VotingDatabaseServiceAsync databaseService = GWT.create(VotingDatabaseService.class);
	private Button btnWahlLschen;
	

	public ArrayList<Vote> getVoting() {
		return Voting;
	}

	public void setVoting(ArrayList<Vote> voting) {
		Voting = voting;
	}

	public MyVotingView() {
		mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		
		panelHeadline = new HTMLPanel("<h1>Meine Wahl:</h1>");
		mainPanel.add(panelHeadline);
		mainPanel.setCellHeight(panelHeadline, "35px");
		
		flexTable = new FlexTable();
		flexTable.setSize("100%", "");
		flexTable.setStyleName("topicList");
		mainPanel.add(flexTable);
		mainPanel.setCellHeight(flexTable, "");
		
		btnWahlLschen = new Button("Wahl l\u00F6schen");
		btnWahlLschen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean delete = Window.confirm("Wollen Sie ihre Wahl wirklich l\u00F6schen?");
				if(delete){
					deleteVote();
				}
			}
		});
		mainPanel.add(btnWahlLschen);
		
		createHeader();
		loadVoting();		
	}

	private void loadVoting() {
		// create proxy voting list, while the real one is loading
		Voting = new ArrayList<Vote>();
		databaseService.loadVotes("Ba", "SS", 2011, "Bernd das Brot", new AsyncCallback<ArrayList<Vote>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<Vote> result) {
				// TODO Auto-generated method stub
				Voting = result;
				refresh();
			}
			
		});
		
	}

	private void createHeader()
	{
		flexTable.setText(0, 0, "Priorit\u00E4t");
		flexTable.setText(0, 1, "Projektname");
			
		HTMLTable.CellFormatter formatter = flexTable.getCellFormatter();
		formatter.setWidth(0, 0, "1%");
		formatter.setWidth(0, 1, "*");
		
		for (int i = 0; i<2; i++)
			formatter.addStyleName(0, i, "topicListHeader");
	}
	
	private void refresh(){
		int rows = flexTable.getRowCount();
		for (int i = 1; i<rows; i++)
			flexTable.removeRow(1);
		
		if (Voting.size() == 0)
			flexTable.setText(1, 1, "Sie haben noch nicht gew\u00E4hlt");
		int j=1;
		for(Vote i: Voting){
			flexTable.setText(j, 0, i.getPriority()+".");
			flexTable.setText(j, 1, i.getProjectName());
			j++;
		}
	}
	
	private void deleteVote(){
		databaseService.deleteVotes("Ba", "SS", 2011, "Bernd das Brot", new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Es ist ein Fehler beim L\u00F6schen aufgetreten, versuchen sie es erneut.");
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Ihre Wahl wurde gel\u00F6scht.");
				loadVoting();
			}
			
		});
	}
	
	public void reload() {
		loadVoting();
	}
	
}
