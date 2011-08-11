package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.pageframe.Footer;
import de.uni_potsdam.hpi.wfapp2011.pageframe.Header;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 12.51
 * @see com.google.gwt.core.client.EntryPoint
 * @see com.google.gwt.user.client.HistoryListener
 */
@SuppressWarnings("deprecation")
public class Topicvoting implements EntryPoint, HistoryListener {
	private RootPanel rootPanel;
	private Label btnHome;
	private Label btnThemenbersicht;
	private Label btnThemenwahl;
	private Label btnMeineWahl;
	private Label btnStatistik;
	private Label btnLogout;
	private VerticalPanel mainPanel;
	private ArrayList<Topic> Topics;
	private Topictable topicTable;
	private VotingView vVoting;
	private MyVotingView myVoting;
	private StatisticView vStatistic;
	private String lastHistoryToken = "";
	private final VotingDatabaseServiceAsync databaseService = GWT.create(VotingDatabaseService.class);

	private Header headerPanel;
	private Footer footerPanel;
	private ProcessIdentifier pId;
	private DockLayoutPanel layoutPanel;
	private ScrollPanel mainPanelScrollable;
	
	/**
	 * method is opened by the GWT framework when the side is loaded.
	 * It creates the header and footer of the page and all subpages
	 */
	public void onModuleLoad() {
		//get the ProcessID for the currently running process
		pId = ProcessIdentifier.getProcessIdentifier("");
		loadTopics();
		//create the main panel
		mainPanel = new VerticalPanel();
		rootPanel.setSize("99%", "100%");

		//create dock panel for header, content and footer
		layoutPanel = new DockLayoutPanel(Unit.PX);
		rootPanel.add(layoutPanel, 0, 0);
		layoutPanel.setSize("100%", "100%");
		
		//create scrollpanel for the content
		mainPanelScrollable = new ScrollPanel();
		mainPanelScrollable.add(mainPanel);

		createHeader();
		createFooter();
		layoutPanel.add(mainPanelScrollable);
		mainPanelScrollable.setWidth("100%");
		mainPanel.setWidth("100%");
		
		//create widgets for the topic overview, voting view, my voting overview and the statisticview
		createTopicTable();				
		createVotingView();
		
		myVoting = new MyVotingView();
		mainPanel.add(myVoting);
		myVoting.setWidth("99%");
		myVoting.setVisible(false);
		
		vStatistic = new StatisticView();
		mainPanel.add(vStatistic);
		vStatistic.setWidth("99%");
		vStatistic.setVisible(false);
		
		//add self as an HistoryListener to support the forward and backward button in the browser
		History.addHistoryListener(this);
		History.fireCurrentHistoryState();
	}
	
	private void createHeader(){
		headerPanel = new Header(pId, "Themenwahl");
		
		//add buttons to the menu panel in the header
		btnHome = new Label("Home");
		btnHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.Location.assign("/");
			}
		});
		headerPanel.menuPanel.add(btnHome);
		
		btnThemenbersicht = new Label("Themenübersicht");
		btnThemenbersicht.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showElement(topicTable);
				History.newItem("Themenuebersicht");
			}
		});
		headerPanel.menuPanel.add(btnThemenbersicht);
		
		btnThemenwahl = new Label("Themenwahl");
		btnThemenwahl.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showElement(vVoting);
				History.newItem("Themenwahl");
			}
		});
		headerPanel.menuPanel.add(btnThemenwahl);
		
		btnMeineWahl = new Label("Meine Wahl");
		btnMeineWahl.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showElement(myVoting);
				History.newItem("MeineWahl");
			}
		});
	
		headerPanel.menuPanel.add(btnMeineWahl);
		
		btnStatistik = new Label("Statistik");
		btnStatistik.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showElement(vStatistic);
				History.newItem("Statistik");
			}
		});
		headerPanel.menuPanel.add(btnStatistik);
		
		btnLogout = new Label("Logout");
		headerPanel.menuLoginPanel.add(btnLogout);
		btnLogout.setWidth("44px");
		
		layoutPanel.addNorth(headerPanel,151);

	}
	
	private void createFooter(){
		footerPanel = new Footer();
		layoutPanel.addSouth(footerPanel, 40);
	}

	private void createVotingView() {
		vVoting = new VotingView(Topics);
		mainPanel.add(vVoting);
		vVoting.setWidth("100%");
		vVoting.setVisible(false);
	}

	private void createTopicTable() {
		topicTable = new Topictable(Topics);
		mainPanel.add(topicTable);
		topicTable.setWidth("99%");
		mainPanel.setCellWidth(topicTable, " ");
	}
	
	private void loadTopics()
	{
		// create proxy topic list with fake Topic		
		Topics = new ArrayList<Topic>();
		Topics.add(new Topic("Daten werden geladen ..."));
		
		// load topic list from server
		// TODO replace dummy processIdentifier
		databaseService.loadTopics("Ba", "SS", 2011, new AsyncCallback<ArrayList<Topic>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<Topic> result) {
				Topics = result;
				topicTable.setTopics(Topics);
				topicTable.refresh();
				vVoting.setTopics(Topics);
				vVoting.refresh();
			}
			
		});
	}
	
	private void showElement(Widget w)
	{
		//hide all widgets in the content panel
		topicTable.setVisible(false);
		vVoting.setVisible(false);
		myVoting.setVisible(false);
		vStatistic.setVisible(false);
		
		//display only the wanted widget
		w.setVisible(true);
		
		//reload the my voting overview
		if (w == myVoting)
			myVoting.reload();
	}

	@Override
	/**
	 * displays the right element when the user uses the forward and backward button of the browser
	 * method is called by GWT framework
	 */
	public void onHistoryChanged(String historyToken) {
		if (historyToken.equals(lastHistoryToken))
			return;
		
		lastHistoryToken = historyToken;
		
		if (historyToken.equals("Themenuebersicht"))
			showElement(topicTable);
		else if (historyToken.equals("Themenwahl"))
			showElement(vVoting);
		else if (historyToken.equals("MeineWahl"))
			showElement(myVoting);
		else if (historyToken.equals("Statistik"))
			showElement(vStatistic);
		else
			showElement(topicTable);
		
	}
}
