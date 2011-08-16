package de.uni_potsdam.hpi.wfapp2011.voting.client;
// IMPORTS
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * <code>MyVotingView</code> is a composite widget which displays the current voting of the user.
 * The user can also delete his voting.
 * 
 * 
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 10:45
 * @see com.google.gwt.user.client.ui.Composite
 */
public class MyVotingView extends Composite {
	private FlexTable flexTable;
	private HTMLPanel panelHeadline;
	private VerticalPanel mainPanel;
	private ArrayList<Vote> votings;
	private final VotingDatabaseServiceAsync databaseService = GWT.create(VotingDatabaseService.class);
	private Button btnWahlLschen;
	
	
	public ArrayList<Vote> getVoting() {
		return votings;
	}

	public void setVoting(ArrayList<Vote> voting) {
		votings = voting;
	}
	
	/**
	 * The standard constructor creates and sets up all child widgets
	 * and loads the voting form the database
	 */
	public MyVotingView() {
		// create main panel
		mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		
		// create head line
		panelHeadline = new HTMLPanel("<h1>Meine Wahl:</h1>");
		mainPanel.add(panelHeadline);
		mainPanel.setCellHeight(panelHeadline, "35px");
		
		// create table for the voting list
		flexTable = new FlexTable();
		flexTable.setSize("100%", "");
		flexTable.setStyleName("topicList");
		mainPanel.add(flexTable);
		mainPanel.setCellHeight(flexTable, "");
		
		// create button for deleting the votings
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
		votings = new ArrayList<Vote>();
		
		// call the remote procedure to load the voting
		//get session id
		String sessionID =Cookies.getCookie("Wfapp2011.USER");
		databaseService.loadVotes(Voting.type, Voting.semester, Voting.year, sessionID, new AsyncCallback<ArrayList<Vote>>() {

			@Override
			public void onFailure(Throwable caught) {
				// do nothing when a failure occurs, the empty proxy voting list remains
			}

			@Override
			public void onSuccess(ArrayList<Vote> result) {
				// replace the proxy voting list with the real on and refresh the page
				votings = result;
				refresh();
			}
			
		});
		
	}

	private void createHeader()
	{
		// setup header of the table
		flexTable.setText(0, 0, "Priorit\u00E4t");
		flexTable.setText(0, 1, "Projektname");
			
		HTMLTable.CellFormatter formatter = flexTable.getCellFormatter();
		formatter.setWidth(0, 0, "1%");
		formatter.setWidth(0, 1, "*");
		
		for (int i = 0; i<2; i++)
			formatter.addStyleName(0, i, "topicListHeader");
	}
	
	private void refresh(){
		// remove all table entries except the header
		int rows = flexTable.getRowCount();
		for (int i = 1; i<rows; i++)
			flexTable.removeRow(1);
		
		// insert message if student hasn't voted yet
		if (votings.size() == 0)
			flexTable.setText(1, 1, "Sie haben noch nicht gew\u00E4hlt");
		
		// insert votes
		int j=1;
		for(Vote i: votings){
			flexTable.setText(j, 0, i.getPriority()+".");
			flexTable.setText(j, 1, i.getProjectName());
			j++;
		}
	}
	
	private void deleteVote(){
		// call remote procedure to delete the voting
		String sessionID =Cookies.getCookie("Wfapp2011.USER");
		databaseService.deleteVotes(Voting.type, Voting.semester, Voting.year, sessionID, new AsyncCallback<Void>(){

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
	
	/**
	 * <code>reload()</code> loads the voting from the database and reloads the page
	 * @return void	
	 */
	public void reload() {
		loadVoting();
	}
	
}
