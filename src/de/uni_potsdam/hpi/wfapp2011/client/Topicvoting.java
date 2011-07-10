package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.VerticalPanelDropController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
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
 */
@SuppressWarnings("deprecation")
public class Topicvoting implements EntryPoint, HistoryListener {
	private RootPanel rootPanel;
	private HorizontalPanel menuPanel;
	private Label btnHome;
	private Label btnThemenbersicht;
	private Label btnThemenwahl;
	private Label btnMeineWahl;
	private Label btnStatistik;
	private Label btnLogout;
	private VerticalPanel mainPanel;
	private ArrayList<Topic> Topics;
	private Topictable topicTable;
	private HTML htmlFooter;
	private VotingView vVoting;
	private MyVotingView myVoting;
	private StatisticView vStatistic;
	private String lastHistoryToken = "";
	private PickupDragController dragController;
	private final VotingDatabaseServiceAsync databaseService = GWT.create(VotingDatabaseService.class);

	private Header headerPanel;
	private Footer footerPanel;
	private ProcessIdentifier pId;
	private DockLayoutPanel layoutPanel;
	private ScrollPanel mainPanelScrollable;
	
	public void onModuleLoad() {
		pId = ProcessIdentifier.getProcessIdentifier("");
		loadTopics();
		mainPanel = new VerticalPanel();
		
		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");

		layoutPanel = new DockLayoutPanel(Unit.PX);
		rootPanel.add(layoutPanel, 0, 0);
		layoutPanel.setSize("100%", "100%");
		
		mainPanelScrollable = new ScrollPanel();
		mainPanelScrollable.add(mainPanel);

		createHeader();
		createFooter();
		layoutPanel.add(mainPanelScrollable);
		mainPanelScrollable.setWidth("100%");
		mainPanel.setWidth("100%");
		
		dragController = new PickupDragController(rootPanel, false);
	
		//rootPanel.add(mainPanel, 0, 150);
		
		
		createTopicTable();
				
		createVotingView();
		
		myVoting = new MyVotingView();
		mainPanel.add(myVoting);
		myVoting.setWidth("100%");
		myVoting.setVisible(false);
		
		vStatistic = new StatisticView();
		mainPanel.add(vStatistic);
		vStatistic.setWidth("100%");
		vStatistic.setVisible(false);
		
//		htmlFooter = new HTML("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<hr>\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"100\">\r\n    \t\t\t<col width=\"100\">\r\n  \t\t\t</colgroup>\r\n  \t\t\t<td></td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"https://www.hpi.uni-potsdam.de/support/impressum.html\">Impressum</a>\r\n\t\t\t</td>\r\n\t\t\t<td>\r\n\t\t\t\t<a href=\"mailto:test@test.com?subject=Bug in Themenwahl\">Report a Bug</a>\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t</body>\r\n</html>", true);
//		htmlFooter.setStyleName("hr");
//		mainPanel.add(htmlFooter);
//		htmlFooter.setWidth("100%");
		
		History.addHistoryListener(this);
		History.fireCurrentHistoryState();
	}
	private void createHeader(){
		headerPanel = new Header(pId, "Themenwahl");

//		HTMLPanel htmlHeader = new HTMLPanel("<html>\r\n\t<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<div id=\"menu\">\r\n\t\t<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t<colgroup>\r\n    \t\t\t<col width=\"191\">\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"156\">\r\n  \t\t\t</colgroup>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<img src=\"doktorhut.png\" width=\"191px\" height=\"151px\">\r\n\t\t\t</td>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<h1>Themenwahl</h1>\r\n\t\t\t\t<h2>Bachelorprojekt 2011</h1>\r\n\t\t\t</td>\r\n\t\t\t<td valign=\"top\">\r\n\t\t\t\t<img src=\"HPI_Logo.png\" width=\"156px\" height=\"93px\">\r\n\t\t\t</td>\r\n\t\t</table>\r\n\t\t</div>\r\n\t</body>\r\n</html>");
//		rootPanel.add(htmlHeader, 0, 0);
//		htmlHeader.setSize("100%", "151px");
//		
//		menuPanel = new HorizontalPanel();
//		menuPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//		rootPanel.add(menuPanel, 190, 110);
//		menuPanel.setSize("503px", "42px");
				
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
		topicTable.setWidth("100%");
		mainPanel.setCellWidth(topicTable, " ");
	}
	
	private void loadTopics()
	{
		// create proxy topic list with fake Topic
		
		Topics = new ArrayList<Topic>();
		Topics.add(new Topic("Daten werden geladen ..."));
		
		// load topic list from server		
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
		topicTable.setVisible(false);
		vVoting.setVisible(false);
		myVoting.setVisible(false);
		vStatistic.setVisible(false);
		
		w.setVisible(true);
		
		if (w == myVoting)
			myVoting.reload();
	}

	@Override
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
